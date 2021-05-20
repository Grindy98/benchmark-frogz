package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gui.main.Main;
import gui.sceneUtils.SceneManager;
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
            try {
                RunBenchHashSingleButtonClicked();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        RunBenchHashMulti.setOnAction(e -> {
            try {
                RunBenchHashMultiButtonClicked();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        RunBenchPiSingle.setOnAction(e -> RunBenchPiSingleButtonPressed());

    }

    public void RunBenchHashSingleButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/HashBench.fxml"));
        Parent root = loader.load();
        HashBenchController controllerLogIn = loader.getController();
        controllerLogIn.setThreadnumber(1);
        //controllerLogIn.setLength(5);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Hash");
        stage.show();
        Stage stage1=(Stage) RunBenchHashSingle.getScene().getWindow();
        stage1.close();
    }
    public void RunBenchHashMultiButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/HashBench.fxml"));
        Parent root = loader.load();
        HashBenchController controllerLogIn = loader.getController();
        controllerLogIn.setThreadnumber(4);
        //controllerLogIn.setLength(6);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Hash");
        stage.show();
        Stage stage1=(Stage) RunBenchHashSingle.getScene().getWindow();
        stage1.close();
    }
    

    public void RunBenchPiSingleButtonPressed(){
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.PI_PAGE);
    }

}
