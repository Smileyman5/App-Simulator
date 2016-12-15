package simulator.jdk;

import clojure.lang.LockingTransaction;
import clojure.lang.Ref;

/**
 * Created by alex on 10/31/2016.
 */
public class App implements Comparable
{
    private Ref name;

    public App(String name)
    {
        try
        {
            this.name = new Ref(name);
        } catch (Exception e)
        {
            System.err.println("Transaction cannot be run on " + name);
        }
    }

    public void renameApp(String name)
    {
        try
        {
            LockingTransaction.runInTransaction(() -> this.name.set(name));
        } catch (Exception e)
        {
            System.err.println("Transaction failed on " + name);
        }
    }

    @Override
    public String toString()
    {
        return (String) name.deref();
    }

    @Override
    public int compareTo(Object o)
    {
        final int[] compareNum = new int[1];
        try
        {
            LockingTransaction.runInTransaction(() -> compareNum[0] = ((String) name.deref()).compareTo(o.toString()));
        } catch (Exception e)
        {
            System.err.println("Transaction failed on " + name);
        }
        return compareNum[0];
    }
}
