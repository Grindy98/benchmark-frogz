package test.implementation;
import javafx.scene.control.PasswordField;
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

public class PiDigits
{
    double runningTime;
    int noOfThreads;
    int iterations;
    ILog logger;

    public PiDigits(int noOfThreads, int iterations, ILog logger) {
        this.noOfThreads = noOfThreads;
        this.iterations = iterations;
        this.logger = logger;
    }

    public void measure(){
        IBenchmark bench = new PiBench();
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
