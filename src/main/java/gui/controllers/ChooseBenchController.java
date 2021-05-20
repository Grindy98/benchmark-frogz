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
    private Button RunBenchHashSingle;
    @FXML
    private Button RunBenchHashMulti;
    @FXML
    private Button RunBenchPiSingle;
    @FXML
    private Button RunBenchPiMulti;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RunBenchHashSingle.setOnAction(e -> RunBenchHashSingleButtonClicked());
        RunBenchPiSingle.setOnAction(e -> RunBenchPiSingleButtonPressed());
    }

    public void RunBenchHashSingleButtonClicked()
    {
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.HASH_PAGE);
    }

    public void RunBenchPiSingleButtonPressed(){
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.PI_PAGE);
    }
}
