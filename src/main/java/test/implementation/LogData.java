package test.implementation;

import javafx.scene.shape.Path;
import test.logging.FileLogger;
import test.logging.ILog;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

public class LogData {

    ILog logger;
    public LogData(){
        String pathToHome = System.getProperty("user.home");
        File f = Paths.get(pathToHome, "Desktop", "projectFrogzLogger.txt").toFile();
        logger = new FileLogger(f);
    }

    public void run(){
        // PiDigits
        for(int i = 1000; i <= 3000; i+= 500){
            PiDigits a = new PiDigits(2, i, logger);
            a.measure();
        }
        for(int i = 1; i <= 10; i++){
            PiDigits a = new PiDigits(i, 2000, logger);
            a.measure();
        }
        // PiDigitsOptimized
        for(int i = 200; i <= 1000; i+= 100){
            PiDigitsOptimized a = new PiDigitsOptimized(2, i, logger);
            a.measure();
        }
        for(int i = 1; i <= 10; i++){
            PiDigitsOptimized a = new PiDigitsOptimized(i, 600, logger);
            a.measure();
        }
        // HashBenchLab
        for(int i = 1; i <= 5; i++){
            HashBenchLab a = new HashBenchLab(4, i, logger);
            a.measure();
        }
        for(int i = 1; i <= 10; i++){
            HashBenchLab a = new HashBenchLab(i, 4, logger);
            a.measure();
        }
        // HashBenchSHA256
        for(int i = 1; i <= 4; i++){
            HashBenchSHA256 a = new HashBenchSHA256(4, i, logger);
            a.measure();
        }
        for(int i = 2; i <= 10; i++){
            HashBenchSHA256 a = new HashBenchSHA256(i, 4, logger);
            a.measure();
        }
    }

    public void close(){
        logger.close();
    }
}
