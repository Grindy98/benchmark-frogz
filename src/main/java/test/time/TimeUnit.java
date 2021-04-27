package test.time;

public enum TimeUnit {
    Sec(1e-9),
    Millisec(1e-6),
    Microsec(1e-3),
    Nanosec(1.0);

    private final double scaleFactor;

    TimeUnit(double scaleFactor){
        this.scaleFactor = scaleFactor;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }
}
