package simulator.personal;

/**
 * Created by alex on 10/31/2016.
 */
public class AppMenu
{
    // Shared collection of data
    public final MyConcurrentArrayList2<App> apps;

    private static final int NUM_OF_APPS = 70;

    public AppMenu()
    {
        apps = new MyConcurrentArrayList2<>();
        String name;
        for (int i = 0; i < NUM_OF_APPS; i++)
        {
            name = StringConstants.getName();
            apps.add(new App(name));
        }
        //System.out.println(apps);
    }

    public void start()
    {
        for (int i = 0; i < 5; i++)
            new Thread(new Client(this, true, true)).start();
    }
}
