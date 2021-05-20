package test.implementation;

import test.benchmark.IBenchmark;
import test.benchmark.cpu.PiBench;
import test.benchmark.cpu.PiBenchOptimized;
import test.time.ITimer;
import test.time.TimeUnit;
import test.time.Timer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PiDigitsOptimized {

    double runningTime;

    public void measure(int noOfThreads, int iterations){
        IBenchmark bench = new PiBenchOptimized();
        bench.initialize();
        ITimer timer = new Timer(TimeUnit.Millisec);
        timer.start();
        bench.run(4, 3000);
        runningTime = timer.stop();
    }

    public double getRunningTime() {
        return runningTime;
    }
}
