package simulator.personal;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alex on 10/24/2016.
 */
public class Client implements Runnable
{
    private static int POP = 0;

    public final AtomicInteger READ_COUNT = new AtomicInteger(0);
    public final AtomicInteger WRITE_COUNT = new AtomicInteger(0);

    private final AppMenu appMenu;
    private final String name;
    private final boolean runInfinitely;
    private final boolean giveFeedback;

    private RWW rww;
    private App currentApp;
    private int COUNT;

    public Client(AppMenu appMenu)
    {
        this(appMenu, false, false);
    }

    public Client(AppMenu appMenu, boolean runInfinitely, boolean giveFeedback)
    {
        this.appMenu = appMenu;
        this.runInfinitely = runInfinitely;
        this.giveFeedback = giveFeedback;

        rww = RWW.READ;
        name = "Client " + POP++;
        currentApp = null;
        COUNT = 0;
    }

    public enum RWW
    {
        READ,
        WRITE
    }

    @Override
    public void run()
    {
        do
        {
            selectRWW();
            switch (rww)
            {
                case READ:
                    currentApp = appMenu.apps.get(StringConstants.getName());
                    if (currentApp != null)
                    {
                        int match = currentApp.compareTo(StringConstants.getName());
                        if ((match == 0 && ThreadLocalRandom.current().nextInt(10000) == 5000 && giveFeedback) || (!runInfinitely && giveFeedback))
                            System.out.println(name + " reading " + currentApp);
                    }
                    else if (!runInfinitely && giveFeedback)
                        System.out.println("Invalid App");
                    break;
                case WRITE:
                    currentApp = appMenu.apps.get(StringConstants.getName());
                    String response = doWrite(currentApp);
                    if ((currentApp != null && ThreadLocalRandom.current().nextInt(10000) == 5000 && giveFeedback) || (!runInfinitely && giveFeedback))
                        System.out.println(response);
                    break;
                default:
                    throw new AssertionError("Hitting default is not possible.");
            }
            COUNT++;
        } while (runInfinitely && COUNT < 100000);
    }

    @Override
    public String toString()
    {
        return name + " is set to " + rww;
    }

    private void selectRWW()
    {
        double selector = ThreadLocalRandom.current().nextDouble();
        if (selector >= 0.1 && selector <= 0.9)
        {
            rww = RWW.READ;
            READ_COUNT.incrementAndGet();
        }
        else
        {
            rww = RWW.WRITE;
            WRITE_COUNT.incrementAndGet();
        }
    }

    private String doWrite(App currentApp)
    {
        if (currentApp == null)
            return "Invalid App";
        String response;
        int rand = ThreadLocalRandom.current().nextInt(5);
        if (rand == 0)
        {
            currentApp.renameApp(StringConstants.getName());
            response = name + " renamed an app to " + currentApp.toString();
        }
        else if (rand == 1)
        {
            if (appMenu.apps.remove(currentApp.toString()))
                response = name + " removed " + currentApp.toString();
            else
                response = currentApp.toString() + " already removed!";
        }
        else
        {
            String name = StringConstants.getName();
            if (appMenu.apps.addIfAbsent(new App(name)) == null)
                response = this.name + " added an app called " + name;
            else
                response = name + " already exists!";
        }
        return response;
    }
}
