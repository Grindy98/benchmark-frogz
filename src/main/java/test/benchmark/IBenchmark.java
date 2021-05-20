package test.benchmark;

public interface IBenchmark {

    /**
     * Used to initialize the test.benchmark with specific params depending on the type. <br>
     * It is meant to be used before checking the performance of certain systems with the test.benchmark
     * @param params list of params for initialization - if wrong types given, an exception should be thrown
     */
    void initialize(Object...params);

    /**
     * Used to properly initialize the test.benchmark object to get an accurate timing on the run function. <br>
     * It is meant to be used before checking the performance of certain systems with the test.benchmark.
     */
    void warmUp();

    /**
     * Used to perform the testing algorithm implemented by the test.benchmark
     */
    void run();

    /**
     * Used to perform the testing algorithm implemented by the test.benchmark
     * @param params list of params for running the program
     */
    void run(Object...params);

    /**
     * Stops a benchmark during execution. <br>
     * Should be checked in the main for-loop, or main thread of the benchmark.
     */
    public void cancel();

    /**
     * Called to explicitly release allocated data. <br>
     * This call should not be benchmarked.
     */
    public void clean();

    /**
     * Return the result of the benchmark. <br>
     * This call should not be benchmarked.
     *
     * @return
     */
    public String getResult();
}
