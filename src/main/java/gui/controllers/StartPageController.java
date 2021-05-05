package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.net.InetAddress;
import java.net.UnknownHostException;

import gui.main.Main;
import gui.sceneUtils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set PC name
        String hostname = "Unknown";

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
    }

    public void secondaryButtonClicked()
    {
        Main.getI().changeSceneOnMainStage(SceneManager.SceneType.CHOOSE_BENCH);
    }
}