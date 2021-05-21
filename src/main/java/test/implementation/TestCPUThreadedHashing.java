package test.implementation;

import test.benchmark.cpu.CPUThreadedSHA256Hash;
import test.logging.ConsoleLogger;
import test.logging.ILog;
import test.time.TimeUnit;
import test.time.ITimer;
import test.time.Timer;
import test.benchmark.IBenchmark;
import test.benchmark.cpu.CPUThreadedLabHash;

public class TestCPUThreadedHashing
{
    public static void main(String[] args)
    {
        //init
        ILog log = new ConsoleLogger(); // new FileLogger("bench.log");
        ITimer timer = new Timer(TimeUnit.Millisec);

        //benchmark
        IBenchmark bench = new CPUThreadedLabHash();

        //params
        int maxLength = 6;
        int nThreads = 3;
        //int hashCode = 524381996;	//frodo
        //int hashCode = 276111076;	//akarua, gmogrf
        int hashCode = 904300281;	//absinth

        //run
        System.out.println("Cracking hash " + hashCode);
        timer.start();
        bench.run(maxLength, nThreads, hashCode);
        double time = timer.stop();

        //display status
        log.write("Finished in", (long) time);
        log.write("Result is", bench.getResult());

        //cleanup
        bench.clean();
        log.close();
    }
}