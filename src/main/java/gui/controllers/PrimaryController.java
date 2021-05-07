package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class                                                                                                                                                                                                                                                                                                   PrimaryController implements Initializable {

    @FXML
    private Button primaryButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        primaryButton.setOnAction(e -> secondaryButtonClicked() );
    }

    public void secondaryButtonClicked()
    {
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.SECONDARY);
    }
}
