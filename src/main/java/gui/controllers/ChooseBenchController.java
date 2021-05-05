package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

//TODO make this scene look good!

public class ChooseBenchController implements Initializable {

    @FXML
    private Button RunBench1Single;
    @FXML
    private Button RunBench1Multi;
    @FXML
    private Button RunBench2Single;
    @FXML
    private Button RunBench2Multi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RunBench1Single.setOnAction(e -> RunBench1SingleButtonClicked() );
    }

    public void RunBench1SingleButtonClicked()
    {
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.SECONDARY);
    }
}
