package gui.controllers;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(e -> backButtonPressed());
        arcsinSlider.valueProperty().addListener((obs, oldval, newVal) ->
                arcsinSlider.setValue(Math.round(newVal.doubleValue())));

        chudSlider.valueProperty().addListener((obs, oldval, newVal) ->
                chudSlider.setValue(Math.round(newVal.doubleValue())));

        runSimpleButton.setOnAction(e -> {
            runSimpleButtonPressed();
        });

        runOptimizedButton.setOnAction(e -> {
            runOptimizedButtonPressed();
        });



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

    private void runSimpleButtonPressed(){
        ILog log = new ConsoleLogger();
        PiDigits bench = new PiDigits((int)Math.round(arcsinIterSlider.getValue()), (int)Math.round(arcsinSlider.getValue()), log);
        bench.measure();
        System.out.println(bench.getScore());
        simpleDigLabel.setText(String.valueOf(new DecimalFormat("#.##").format(bench.getScore())));
        simpleTimeLabel.setText(String.valueOf(bench.getRunningTime()));
    }

    private void runOptimizedButtonPressed(){
        ILog log = new ConsoleLogger();
        PiDigitsOptimized bench = new PiDigitsOptimized((int)Math.round(arcsinIterSlider.getValue()), (int)Math.round(arcsinSlider.getValue()), log);
        bench.measure();
        optimizedTimeLabel.setText(String.valueOf(bench.getRunningTime()));
        optimizedDigLabel.setText(String.valueOf(new DecimalFormat("#.##").format(bench.getScore())));
    }
}
