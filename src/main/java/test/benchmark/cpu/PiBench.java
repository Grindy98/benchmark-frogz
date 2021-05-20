package test.benchmark.cpu;

import test.benchmark.IBenchmark;

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
