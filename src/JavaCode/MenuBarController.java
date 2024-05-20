package JavaCode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Constants.Constant.*;

public class MenuBarController extends DatabaseConnection implements Initializable {
    @FXML
    private Button addWordBtn;
    @FXML
    private Button learningEngBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button translateBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the default component to display when the application is started.
        showComponent(SEARCH_LAYER);
        addWordBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(ADD_WORD_LAYER);
            }
        });
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(SEARCH_LAYER);
            }
        });
        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(TRANSLATE_LAYER);
            }
        });
        learningEngBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(GAMES_LAYER);
            }
        });
    }
}
