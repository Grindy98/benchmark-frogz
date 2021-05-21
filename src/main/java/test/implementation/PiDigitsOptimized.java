package test.implementation;

import test.benchmark.IBenchmark;
import test.benchmark.cpu.PiBench;
import test.benchmark.cpu.PiBenchOptimized;
import test.logging.ILog;
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

    ILog logger;

    public PiDigitsOptimized(int noOfThreads, int iterations, ILog logger) {
        this.noOfThreads = noOfThreads;
        this.iterations = iterations;
        this.logger = logger;
    }

    public void measure(){
        IBenchmark bench = new PiBenchOptimized();
        bench.initialize();
        ITimer timer = new Timer(TimeUnit.Millisec);
        timer.start();
        bench.run(noOfThreads, iterations);
        runningTime = timer.stop();
        logger.write("PiDigitsOptimized with iter ", iterations, " threads " + noOfThreads,
                "time: ", getRunningTime(), "score: ", getScore());
    }

    public double getRunningTime() {
        return runningTime;
    }

    public double getScore(){
        return iterations / runningTime / Math.log(noOfThreads + 1);
    }
}
