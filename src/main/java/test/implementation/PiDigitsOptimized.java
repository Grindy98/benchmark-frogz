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
    int noOfThreads;
    int iterations;

    public PiDigitsOptimized(int noOfThreads, int iterations) {
        this.noOfThreads = noOfThreads;
        this.iterations = iterations;
    }

    public void measure(){
        IBenchmark bench = new PiBenchOptimized();
        bench.initialize();
        ITimer timer = new Timer(TimeUnit.Millisec);
        timer.start();
        bench.run(noOfThreads, iterations);
        runningTime = timer.stop();
    }

    public double getRunningTime() {
        return runningTime;
    }

    public double getScore(){
        return iterations / runningTime / Math.log(noOfThreads);
    }
}
