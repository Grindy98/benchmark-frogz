package test.implementation;

import test.benchmark.IBenchmark;
import test.benchmark.cpu.CPUThreadedLabHash;
import test.benchmark.cpu.PiBench;
import test.logging.ILog;
import test.time.ITimer;
import test.time.TimeUnit;
import test.time.Timer;

public class HashBenchLab {
    double runningTime;
    int noOfThreads;
    int length;
    ILog logger;

    public HashBenchLab(int noOfThreads, int length, ILog logger) {
        this.noOfThreads = noOfThreads;
        this.length = length;
        this.logger = logger;
    }

    public void measure(){
        IBenchmark bench = new CPUThreadedLabHash();
        bench.initialize();
        ITimer timer = new Timer(TimeUnit.Millisec);
        timer.start();
        bench.run(length, noOfThreads, 0);
        runningTime = timer.stop();
        logger.write("HashBenchLab with length ", length, " threads " + noOfThreads,
                "time: ", getRunningTime(), "score: ", getScore());
    }

    public double getRunningTime() {
        return runningTime;
    }

    public double getScore(){
        int hashes = 0;
        int hashes1 = 1;
        for(int i = 1; i <= length; i++)
        {
            hashes1 = 1;
            for(int j = 1; j <= i; j++)
            {
                hashes1 = hashes1 * 26;
            }
            hashes = hashes + hashes1;
        }
        return Math.log(hashes) / runningTime / Math.log(noOfThreads + 1);
    }
}
