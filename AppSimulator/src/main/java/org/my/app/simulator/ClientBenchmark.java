/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.my.app.simulator;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import simulator.jdk.AppMenu;
import simulator.jdk.Client;

@State(Scope.Benchmark)
public class ClientBenchmark
{
    private static final int THREAD_COUNT = 100;
    private AppMenu appMenuJDK;
    private simulator.personal.AppMenu appMenuPersonal;

    @Setup
    public void setup()
    {
        appMenuJDK = new AppMenu();
        appMenuPersonal = new simulator.personal.AppMenu();
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testClientsJDK()
    {
        new Client(appMenuJDK).run();
    }

    @Threads(THREAD_COUNT)
    @Benchmark
    public void testClientsPersonal()
    {
        new simulator.personal.Client(appMenuPersonal).run();
    }


    public static void main(String[] args) throws RunnerException
    {
        Options opt = new OptionsBuilder()
                        .forks(10)
                        .warmupIterations(20)
                        .measurementIterations(20)
                        .resultFormat(ResultFormatType.CSV)
                        .result("output.csv")
                        .build();
        new Runner(opt).run();
    }
}
