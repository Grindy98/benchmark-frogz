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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import test.implementation.PiDigits;
import test.implementation.PiDigitsOptimized;
import test.logging.ConsoleLogger;
import test.logging.ILog;

import java.net.URL;
import java.util.ResourceBundle;

import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;

public class PiBenchController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button runSimpleButton;
    @FXML
    private Button runOptimizedButton;
    @FXML
    private TextField simplePrecTF;
    @FXML
    private TextField optimizedPrecTF;
    @FXML
    private Label simpleDigLabel;
    @FXML
    private Label simpleTimeLabel;
    @FXML
    private Label optimizedDigLabel;
    @FXML
    private Label optimizedTimeLabel;
    @FXML
    private Slider arcsinSlider;
    @FXML
    private Slider chudSlider;
    @FXML
    private Slider arcsinIterSlider;
    @FXML
    private Slider chudIterSlider;
    @FXML
    private Label arcsinIterLabel;
    @FXML
    private Label chudIterLabel;

    private BooleanProperty isRunning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(e -> backButtonPressed());
        arcsinSlider.valueProperty().addListener((obs, oldval, newVal) ->
                arcsinSlider.setValue(Math.round(newVal.doubleValue())));

        chudSlider.valueProperty().addListener((obs, oldval, newVal) ->
                chudSlider.setValue(Math.round(newVal.doubleValue())));

        arcsinIterSlider.valueProperty().addListener((obs, oldval, newVal) ->
                arcsinIterLabel.setText(String.valueOf(Math.round(newVal.intValue()))));

        chudIterSlider.valueProperty().addListener((obs, oldval, newVal) ->
                chudIterLabel.setText(String.valueOf(Math.round(newVal.intValue()))));

        runSimpleButton.setOnAction(e -> {
            // Run async
            CompletableFuture<PiDigits> res = CompletableFuture.supplyAsync(this::runSimpleButtonPressed);
            res.thenAccept(param ->{
                Platform.runLater(()->{
                    endRunSimple(param);
                });
            });
        });

        runOptimizedButton.setOnAction(e -> {
            // Run async
            CompletableFuture<PiDigitsOptimized> res = CompletableFuture.supplyAsync(this::runOptimizedButtonPressed);
            res.thenAccept(param ->{
                Platform.runLater(()->{
                    endRunOptimized(param);
                });
            });
        });

        isRunning = new SimpleBooleanProperty(false);

        backButton.disableProperty().bind(isRunning);
        runOptimizedButton.disableProperty().bind(isRunning);
        runSimpleButton.disableProperty().bind(isRunning);
    }

    public void setSlidersSingle(){
        arcsinSlider.setValue(1);
        chudSlider.setValue(1);
        arcsinSlider.setDisable(true);
        chudSlider.setDisable(true);
    }

    public void setSlidersMulti(){
        arcsinSlider.setDisable(false);
        chudSlider.setDisable(false);
    }

    private void backButtonPressed(){
        Main.changeSceneOnMainStage(SceneManager.SceneType.OPTIONS_PAGE);
    }

    private PiDigits runSimpleButtonPressed(){
        isRunning.setValue(true);
        ILog log = new ConsoleLogger();
        PiDigits bench = new PiDigits((int)Math.round(arcsinSlider.getValue()), (int)Math.round(arcsinIterSlider.getValue()), log);
        bench.measure();
        return bench;
    }

    private void endRunSimple(PiDigits bench){
        simpleDigLabel.setText((new DecimalFormat("#.##").format(bench.getScore())));
        simpleTimeLabel.setText((new DecimalFormat("#.##").format(bench.getRunningTime())) + " ms");
        isRunning.setValue(false);
    }

    private PiDigitsOptimized runOptimizedButtonPressed(){
        isRunning.setValue(true);
        ILog log = new ConsoleLogger();
        PiDigitsOptimized bench = new PiDigitsOptimized((int)Math.round(chudSlider.getValue()), (int)Math.round(chudIterSlider.getValue()), log);
        bench.measure();
        return bench;
    }

    private void endRunOptimized(PiDigitsOptimized bench){
        optimizedTimeLabel.setText((new DecimalFormat("#.##").format(bench.getRunningTime())) + " ms");
        optimizedDigLabel.setText((new DecimalFormat("#.##").format(bench.getScore())));
        isRunning.setValue(false);
    }
}
