package gui.scenes;

import java.io.IOException;
import javafx.fxml.FXML;
import gui.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}