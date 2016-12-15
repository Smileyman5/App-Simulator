package org.my.app.simulator;

import org.openjdk.jmh.annotations.*;
import simulator.jdk.App;
import simulator.jdk.AppMenu;

/**
 * Created by alex on 11/6/2016.
 */
@State(Scope.Benchmark)
public class AppBenchmark
{
    private static final int THREAD_COUNT = 10;
    private AppMenu appMenuJDK;
    private simulator.personal.AppMenu appMenuPersonal;
    private App currentApp;

    @Setup
    public void setup()
    {
        appMenuJDK = new AppMenu();
        appMenuPersonal = new simulator.personal.AppMenu();
        appMenuJDK.apps.put("ANew App", new App("ANew App"));
        appMenuPersonal.apps.add(new simulator.personal.App("ANew App"));
        currentApp = appMenuJDK.apps.get("ANew App");
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppGetJDK()
    {
        appMenuJDK.apps.get("ANew App");
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppGetPersonal()
    {
        appMenuPersonal.apps.get("ANew App");
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppRenameJDK()
    {
        if (appMenuJDK.apps.remove(currentApp) != null)
        {
            currentApp.renameApp("Something completely Different");
            appMenuJDK.apps.put(currentApp.toString(), currentApp);
        }
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppRenamePersonal()
    {
        currentApp.renameApp("Something completely Different");
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppAddJDK()
    {
        appMenuJDK.apps.put("Definitely New App", new App("Definitely New App"));
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppAddPersonal()
    {
        appMenuPersonal.apps.add(new simulator.personal.App("Definitely New App"));
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppAddIfAbsentJDK()
    {
        appMenuJDK.apps.putIfAbsent("Definitely New App", new App("Definitely New App"));
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppAddIfAbsentPersonal()
    {
        appMenuPersonal.apps.addIfAbsent(new simulator.personal.App("Definitely New App"));
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppRemoveJDK()
    {
        appMenuJDK.apps.remove("ANew App");
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testAppRemovePersonal()
    {
       appMenuPersonal.apps.remove("ANew App");
    }
}
