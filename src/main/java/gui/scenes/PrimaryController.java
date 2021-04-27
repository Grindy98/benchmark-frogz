package gui.scenes;

import java.io.IOException;
import javafx.fxml.FXML;
import gui.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
