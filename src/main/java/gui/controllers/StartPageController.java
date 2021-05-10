package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class StartPageController implements Initializable {

    @FXML
    private Button secondaryButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        secondaryButton.setOnAction(e -> secondaryButtonClicked() );
    }

    public void secondaryButtonClicked()
    {
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.PRIMARY);
    }
}