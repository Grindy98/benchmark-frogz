package gui.controllers;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import test.benchmark.IBenchmark;
import test.benchmark.cpu.CPUThreadedLabHash;
import test.logging.ConsoleLogger;
import test.logging.ILog;
import test.logging.TimeUnit;
import test.time.ITimer;
import test.time.Timer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class HashBenchController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button runSimpleButton;
    @FXML
    private Button run256Button;
    @FXML
    private TextField hashSimpleTF;
    @FXML
    private TextField hash256TF;
    @FXML
    private Label hSimpleValueLabel;
    @FXML
    private Label h256ValueLabel;
    @FXML
    private Label hSimpleTimeLabel;
    @FXML
    private Label h256TimeLabel;

    private IBenchmark bench;
    private BooleanProperty isRunning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isRunning = new SimpleBooleanProperty(false);
        backButton.setOnAction(e -> backButtonPressed());
        runSimpleButton.setOnAction(e -> {
            // Run async
            CompletableFuture<Double> res = CompletableFuture.supplyAsync(this::runSimpleButtonPressed);
            res.thenAccept(time ->{
                Platform.runLater(()->{
                    endBenchmark(time);
                });
            });
        });
        runSimpleButton.disableProperty().bind(isRunning);
        backButton.disableProperty().bind(isRunning);
        run256Button.disableProperty().bind(isRunning);
        hashSimpleTF.disableProperty().bind(isRunning);
        hash256TF.disableProperty().bind(isRunning);
    }

    private void backButtonPressed(){
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.OPTIONS_PAGE);
    }

    private double runSimpleButtonPressed(){
        //set to running
        isRunning.setValue(true);
        //init
        ITimer timer = new Timer();

        //benchmark
        bench = new CPUThreadedLabHash();

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
        return timer.stop();
    }

    private void endBenchmark(double time){
        ILog log = new ConsoleLogger(); // new FileLogger("bench.log");

        TimeUnit timeUnit = TimeUnit.Milli;
        //display status
        log.writeTime("Finished in", (long) time, timeUnit);
        log.write("Result is", bench.getResult());

        hSimpleTimeLabel.setText(String.valueOf(TimeUnit.toTimeUnit((long) time, timeUnit)));
        //hSimpleValueLabel.setText(bench.getResult());

        //cleanup
        bench.clean();
        log.close();
        isRunning.setValue(false);
    }
}
