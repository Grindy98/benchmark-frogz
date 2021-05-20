package test.implementation;

import javafx.scene.shape.Path;
import test.logging.FileLogger;
import test.logging.ILog;

import java.io.File;
import java.net.URI;

public class LogData {
    PiDigits a;
    PiDigitsOptimized b;

    ILog logger;
    LogData(){
        String pathToHome = System.getProperty("user.home");
    }
}
