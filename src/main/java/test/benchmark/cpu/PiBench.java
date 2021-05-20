package test.benchmark.cpu;

import test.benchmark.IBenchmark;
import test.logging.ConsoleLogger;
import test.logging.ILog;
import test.logging.TimeUnit;
import test.time.ITimer;
import test.time.Timer;

public class PiBench implements IBenchmark {
    private String result;
    volatile boolean running = true;

    @Override
    public void initialize(Object... params) {

    }

    @Override
    public void warmUp() {

    }

    @Override
    public void run() {
        //init
        ILog log = new ConsoleLogger(); // new FileLogger("bench.log");
        ITimer timer = new Timer();
        TimeUnit timeUnit = TimeUnit.Milli;

        //benchmark
        IBenchmark bench = new CPUThreadedLabHash();

        //run
        timer.start();



        double time = timer.stop();
    }

    @Override
    public void run(Object... params) {

    }

    @Override
    public void clean() {

    }

    @Override
    public void cancel(){

    }

    @Override
    public String getResult(){return "";}
}
