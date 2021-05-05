package gui.main;

import gui.sceneUtils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private static Main instance;
    public static void main(String[] args) { launch(args); }
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        instance = this;

        // Initialize all scenes
        SceneManager.getInstance();

        //Choose first appearing scene
        stage.setScene(SceneManager.getInstance().getScene(SceneManager.SceneType.PRIMARY));

        stage.setResizable(false);
        stage.setTitle("WeddApp");

        stage.show();
    }
    public static Main getI() {
        return instance;
    }

    public void changeSceneOnMainStage(SceneManager.SceneType sceneType)
    {
        stage.setScene(SceneManager.getInstance().getScene(sceneType));
    }

}
