package org.my.app.simulator;

import org.openjdk.jmh.annotations.Benchmark;
import simulator.jdk.AppMenu;

/**
 * Created by alex on 11/6/2016.
 */
public class AppMenuBenchmark
{
    @Benchmark
    public void measureAppMenuConstructorJDK()
    {
        new AppMenu();
    }

    @Benchmark
    public void measureAppMenuConstructorPersonal()
    {
        new simulator.personal.AppMenu();
    }
}
