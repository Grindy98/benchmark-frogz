package gui.main;

import gui.sceneUtils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import test.benchmark.IBenchmark;
import test.benchmark.cpu.PiBench;
import test.benchmark.cpu.PiBenchOptimized;
import test.time.ITimer;
import test.time.TimeUnit;
import test.time.Timer;

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
        stage.setScene(SceneManager.getInstance().getScene(SceneManager.SceneType.START_PAGE));

        stage.setResizable(false);
        stage.setTitle("BenchMark Project");

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
