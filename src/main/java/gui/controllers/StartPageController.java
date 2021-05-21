package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import test.implementation.LogData;

public class StartPageController implements Initializable {

    @FXML
    private Text nameText;
    @FXML
    private Button startButton;
    @FXML
    private Text identifierText;
    @FXML
    private Text architectureText;
    @FXML
    private Text nrText;
    @FXML
    private Button logAllButton;

    private BooleanProperty isRunning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set PC name
        String hostname = "Unknown";
        isRunning = new SimpleBooleanProperty(false);
        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
        }

        System.out.println("NAME: " + hostname);
        nameText.setText(hostname);

        //set CPU details
        identifierText.setText(System.getenv("PROCESSOR_IDENTIFIER"));
        architectureText.setText(System.getenv("PROCESSOR_ARCHITECTURE"));
        nrText.setText(System.getenv("NUMBER_OF_PROCESSORS"));

        startButton.setOnAction(e -> Main.changeSceneOnMainStage(SceneManager.SceneType.OPTIONS_PAGE));

        logAllButton.setOnAction(e -> {
            // Run async
            CompletableFuture<Void> res = CompletableFuture.runAsync(this::runLogAll);
            res.thenAccept(time ->{
                isRunning.setValue(false);
            });
        });

        logAllButton.disableProperty().bind(isRunning);
        startButton.disableProperty().bind(isRunning);
    }

    private void runLogAll(){
        isRunning.setValue(true);
        var x = new LogData();
        x.run();
        x.close();
    }


//    public void secondaryButtonClicked()
//    {
//        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.START_PAGE);
//    }
}