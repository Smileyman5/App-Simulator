package simulator.personal;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by alex on 11/5/2016.
 */
public class MyConcurrentArrayList2<T extends Object>
{
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile T[] array;
    private volatile int size;
    private volatile int filled;
    private volatile ReentrantReadWriteLock.ReadLock readLock;
    private volatile ReentrantReadWriteLock.WriteLock writeLock;

    public MyConcurrentArrayList2()
    {
        this(10);
    }

    public MyConcurrentArrayList2(int size)
    {
        this.size = size;
        array = (T[]) new App[size];
        filled = 0;
        readLock = null;
        writeLock = null;
    }

    public T add(T app)
    {
        return addVal(app, false, false);
    }

    public T addIfAbsent(T app)
    {
        return addVal(app, true, false);
    }

    private T addVal(T app, boolean onlyIfAbsent, boolean useLock)
    {
        if (app == null)
            return null;
        writeLock = (ReentrantReadWriteLock.WriteLock) lock.writeLock();
        if(useLock || writeLock.tryLock())
        {
            if (useLock)
                writeLock.lock();
            try
            {
                if (filled == size)
                    expand();
                int openIndex = filled;
//                System.out.println("[" + array.length);
//                System.out.println(filled);
//                for (int i = 0; i < array.length; i++)
//                    System.out.println(array[i]);
                for (int i = 0; i < array.length; i++)
                {
                    if (array[i] != null && array[i].toString().equals(app.toString()))
                    {
                        if (onlyIfAbsent)
                            return array[i];
                        else
                            return array[i] = app;
                    }
                    if (array[i] == null && i < openIndex)
                        openIndex = i;
                }
                filled++;
                return array[openIndex] = app;
            } finally
            {
                writeLock.unlock();
            }
        }
        else if (filled != size)
        {
            int openIndex = filled;
            for (int i = 0; i < array.length; i++)
            {
                T temp = array[i];
                if (onlyIfAbsent && temp != null && temp.toString().equals(app.toString()))
                    return addVal(app, onlyIfAbsent, true);
                if (array[i] == null && i < openIndex)
                    openIndex = i;
            }
            return addVal(openIndex, app);
        }
        return null;
    }

    private T addVal(int index, T app)
    {
        writeLock = (ReentrantReadWriteLock.WriteLock) lock.writeLock();
        writeLock.lock();
        try
        {
            if (array[index] == null)
            {
                filled++;
                return array[index] = app;
            }
            return addVal(app, true, false);
        }
        finally
        {
            writeLock.unlock();
        }
    }

    private void expand()
    {
        size = size << 1;
        T[] newArray = (T[]) new App[size];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];
        array = newArray;
    }

    public T get(String name)
    {
        readLock = (ReentrantReadWriteLock.ReadLock) lock.readLock();
        if (readLock.tryLock())
        {
            try
            {
                for (int i = 0; i < array.length; i++)
                {
                    if (array[i] != null && array[i].toString().equals(name))
                        return array[i];
                }
            } finally
            {
                readLock.unlock();
            }
        }
        else
        {
            for (int i = 0; i < array.length; i++)
            {
                T temp = array[i];
                if (temp != null && temp.toString().equals(name))
                    return get(i, temp);
            }
        }
        return null;
    }

    private T get(int i, T t)
    {
        readLock = (ReentrantReadWriteLock.ReadLock) lock.readLock();
        readLock.lock();
        try
        {
            if (array[i] == t)
                return array[i];
            else
                return null;
        } finally
        {
            readLock.unlock();
        }
    }

    public boolean remove(String name)
    {
        writeLock = (ReentrantReadWriteLock.WriteLock) lock.writeLock();
        if (writeLock.tryLock())
        {
            try
            {
                for (int i = 0; i < array.length; i++)
                {
                    if (array[i] != null && array[i].toString().equals(name))
                    {
                        array[i] = null;
                        filled--;
                        return true;
                    }
                }
                return false;
            } finally
            {
                writeLock.unlock();
            }
        }
        else
        {
            for (int i = 0; i < array.length; i++)
            {
                T temp = array[i];
                if (temp != null && temp.toString().equals(name))
                    return remove(i, temp);
            }
        }
        return false;
    }

    private boolean remove(int i, T t)
    {
        writeLock = (ReentrantReadWriteLock.WriteLock) lock.writeLock();
        writeLock.lock();
        try
        {
            if (array[i] == t)
            {
                array[i] = null;
                filled--;
                return true;
            }
            return false;
        }
        finally
        {
            writeLock.unlock();
        }
    }

    @Override
    public String toString()
    {
        readLock = (ReentrantReadWriteLock.ReadLock) lock.readLock();
        readLock.lock();
        try
        {
            StringBuilder rtnString = new StringBuilder("[");
            T temp = null;
            for (T app: array)
            {
                if (app != null)
                {
                    temp = app;
                    rtnString.append(app.toString() + ", ");
                }
            }
            if (temp != null)
                rtnString.replace(rtnString.lastIndexOf(", "), rtnString.length(), "]");
            else
                rtnString.append("]");
            return rtnString.toString();
        }
        finally
        {
            readLock.unlock();
        }
    }
}
