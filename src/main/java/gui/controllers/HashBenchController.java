package gui.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import test.benchmark.IBenchmark;
import test.benchmark.cpu.CPUThreadedLabHash;
import test.benchmark.cpu.CPUThreadedSHA256Hash;
import test.logging.ConsoleLogger;
import test.logging.ILog;
import test.logging.TimeUnit;
import test.time.ITimer;
import test.time.Timer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class HashBenchController implements Initializable {
    @FXML
    private TextField hashSimpleTF;

    @FXML
    private Label hsimpleValueLabel;

    @FXML
    private Label hSimpleTimeLabel;

    @FXML
    private Button runSimpleButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField hash256TF;

    @FXML
    private Label h256ValueLabel;

    @FXML
    private Label h256TimeLabel;

    @FXML
    private Button run256Button;
    private IBenchmark bench;
    private BooleanProperty isRunning;
    private int threadnumber;
    private int length = 5;

    public void setThreadnumber(int threadnumber) {
        this.threadnumber = threadnumber;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isRunning = new SimpleBooleanProperty(false);
        backButton.setOnAction(e -> {
            try {
                backButtonPressed();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        runSimpleButton.setOnAction(e -> {
            // Run async
            CompletableFuture<Double> res = CompletableFuture.supplyAsync(this::runSimpleButtonPressed);
            res.thenAccept(time ->{
                Platform.runLater(()->{
                    endBenchmark(time);
                });
            });
        });
        run256Button.setOnAction(e -> {
            // Run async
            CompletableFuture<Double> res = CompletableFuture.supplyAsync(this::run256ButtonPressed);
            res.thenAccept(time ->{
                Platform.runLater(()->{
                    endBenchmark1(time);
                });
            });
        });
        runSimpleButton.disableProperty().bind(isRunning);
        backButton.disableProperty().bind(isRunning);
        run256Button.disableProperty().bind(isRunning);
        //hashSimpleTF.disableProperty().bind(isRunning);
        //hash256TF.disableProperty().bind(isRunning);
    }

    private void backButtonPressed() throws IOException {
        Stage stage1=(Stage) runSimpleButton.getScene().getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/ChooseBenchHere.fxml"));
        Parent root = loader.load();
        ChooseBenchController controllerLogIn = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BenchMark Project");
        stage.show();
    }

    private double runSimpleButtonPressed(){
        //set to running
        isRunning.setValue(true);
        //init
        ITimer timer = new Timer();

        //benchmark
        bench = new CPUThreadedLabHash();

        //params
        int maxLength = length;
        int nThreads = threadnumber;
        int hashCode = 904300281;

        //run
        timer.start();
        bench.run(maxLength, nThreads, hashCode);
        return timer.stop();
    }

    private double run256ButtonPressed(){
        //set to running
        isRunning.setValue(true);
        //init
        ITimer timer = new Timer();

        //benchmark
        bench = new CPUThreadedSHA256Hash();

        //params
        int maxLength = length - 1; //else we wait 90s
        int nThreads = threadnumber;

        int hashCode = 904300281;

        //run
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
        int hashes = 0;
        int hashes1 = 1;
        for(int i = 1; i <= length; i++)
        {
            hashes1 = 1;
            for(int j = 1; j <= i; j++)
            {
                hashes1 = hashes1 * 26;
            }
            hashes = hashes + hashes1;
        }
        System.out.println("Total hashes: " + hashes);
        double tim=TimeUnit.toTimeUnit((long)time,timeUnit);
        double hashreate=hashes/tim;
        hSimpleTimeLabel.setText(String.valueOf(TimeUnit.toTimeUnit((long) time, timeUnit))+" "+ "milliseconds");
        hsimpleValueLabel.setText(String.valueOf((long)hashreate)+" "+"hashes/millisecond");

        //cleanup
        bench.clean();
        log.close();
        isRunning.setValue(false);
    }
    private void endBenchmark1(double time){
        ILog log = new ConsoleLogger(); // new FileLogger("bench.log");

        TimeUnit timeUnit = TimeUnit.Milli;
        //display status
        log.writeTime("SHA Finished in", (long) time, timeUnit);
        log.write("Result is", bench.getResult());
        int hashes = 0;
        int hashes1 = 1;
        for(int i = 1; i < length; i++)     //less for sha, otherwise we wait 90s
        {
            hashes1 = 1;
            for(int j = 1; j <= i; j++)
            {
                hashes1 = hashes1 * 26;
            }
            hashes = hashes + hashes1;
        }
        double tim=TimeUnit.toTimeUnit((long)time,timeUnit);
        double hashreate=hashes/tim;
        h256TimeLabel.setText(String.valueOf(TimeUnit.toTimeUnit((long) time, timeUnit))+" "+ "milliseconds");
        h256ValueLabel.setText(String.valueOf((long)hashreate)+" "+"hashes/millisecond");


        //cleanup
        bench.clean();
        log.close();
        isRunning.setValue(false);
    }
}
