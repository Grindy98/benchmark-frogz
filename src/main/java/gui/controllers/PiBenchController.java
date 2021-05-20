package gui.controllers;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         backButton.setOnAction(e -> backButtonPressed());
    }

    private void backButtonPressed(){
        Main.changeSceneOnMainStage(SceneManager.SceneType.OPTIONS_PAGE);
    }
}
