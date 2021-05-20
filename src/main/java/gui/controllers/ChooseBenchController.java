package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gui.main.Main;
import gui.sceneUtils.SceneManager;
import gui.sceneUtils.SceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        RunBenchHashSingle.setOnAction(e -> {
            RunBenchHashSingleButtonClicked();
        });
        RunBenchHashMulti.setOnAction(e -> {
            RunBenchHashMultiButtonClicked();

        });
        RunBenchPiSingle.setOnAction(e -> RunBenchPiSingleButtonPressed());

    }

    public void RunBenchHashSingleButtonClicked() {
        HashBenchController controllerLogIn = SceneManager.getInstance().getController(SceneType.HASH_PAGE);
        controllerLogIn.setThreadnumber(1);
        Main.changeSceneOnMainStage(SceneType.HASH_PAGE);
    }
    public void RunBenchHashMultiButtonClicked() {
        HashBenchController controllerLogIn = SceneManager.getInstance().getController(SceneType.HASH_PAGE);
        controllerLogIn.setThreadnumber(4);
        Main.changeSceneOnMainStage(SceneType.HASH_PAGE);
    }
    

    public void RunBenchPiSingleButtonPressed(){
        Main.changeSceneOnMainStage(SceneType.PI_PAGE);
    }

}
