package simulator.jdk;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alex on 10/31/2016.
 */
public class AppMenu
{
    // Shared collection of data
    public final ConcurrentHashMap<String, App> apps;

    private static final int NUM_OF_APPS = 70;
    
    public AppMenu()
    {
        apps = new ConcurrentHashMap<>();
        String name;
        for (int i = 0; i < NUM_OF_APPS; i++)
        {
            name = StringConstants.getName();
            apps.put(name, new App(name));
        }
        //System.out.println(apps.keySet());
    }

    public void start()
    {
        for (int i = 0; i < 5; i++)
            new Thread(new Client(this, true, true)).start();
    }
}
