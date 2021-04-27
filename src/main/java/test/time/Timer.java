package test.time;

import static test.time.TimeUnit.Nanosec;

public class Timer implements ITimer{

    private long totalTime, elapsedTime, timeLastOverride;
    private boolean clockPaused, clockStopped;
    private final TimeUnit unit;

    /**
     * Initialize the timer and start it (if reset is necessary, call start again)
     * @param unit Object will display the test.time based on this choice
     */
    public Timer(TimeUnit unit){
        this.unit = unit;
        start();
    }

    /**
     * Initialize the timer and start it (if reset is necessary, call start again).<br>
     * A TimeUnit of Nanosec is assumed
     */
    public Timer(){
        this.unit = Nanosec;
        start();
    }

    @Override
    public void start() {
        elapsedTime = 0;
        totalTime = 0;
        clockPaused = clockStopped = false;
        // Make sure that test.time spent in function call is not counted
        timeLastOverride = System.nanoTime();
    }

    @Override
    public double stop() {
        long currentTime = System.nanoTime();

        if(!clockStopped && !clockPaused){
            totalTime += currentTime - timeLastOverride;
        }
        // Else stop regardless and return previously stored test.time
        clockStopped = true;
        return totalTime * unit.getScaleFactor();
    }

    @Override
    public void resume() {
        // Do nothing, stop has higher priority than resume
        if(clockStopped){
            return;
        }

        long currentTime = System.nanoTime();
        if(clockPaused){
            // If clock paused, don't add up any test.time to totalTime
            clockPaused = false;
        }else{
            totalTime += currentTime - timeLastOverride;
        }
        // Make sure that test.time spent in function call is not counted
        timeLastOverride = System.nanoTime();
    }

    @Override
    public double pause() {
        long currentTime = System.nanoTime();

        if(!clockPaused && !clockStopped){
            // Pause clock and compute new elapsed test.time value
            clockPaused = true;
            elapsedTime = currentTime - timeLastOverride;
            totalTime += elapsedTime;
        }
        // Else return previously stored value
        return elapsedTime * unit.getScaleFactor();
    }
}
