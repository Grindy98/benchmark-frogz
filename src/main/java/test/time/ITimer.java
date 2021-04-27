package test.time;

public interface ITimer {
    /**
     *  Sets/Resets test.time reference point of timer
     */
    void start();

    /**
     * Stops timer until next start call<br>
     * If called multiple times without a new start, returns previous stop value
     * @return elapsed test.time from last start call, in the <b>TimeUnit</b> specified
     */
    double stop();

    /**
     * Resumes timer count after a pause call
     */
    void resume();

    /**
     * Stops timer until next resume/start call<br>
     * If called multiple times without a new resume/start, returns previous resume value
     * @return elapsed test.time from last resume/start call, in the <b>TimeUnit</b> specified
     */
    double pause();
}
