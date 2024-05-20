package JavaCode;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

import static Constants.Constant.*;

public class GameController extends DatabaseConnection implements Initializable {
    @FXML
    private Button learningBtn1, learningBtn2;
    @FXML
    private ListView<String> listViewRanking;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        learningBtn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(GAME_TESTING);
            }
        });
        learningBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(GAME_UNSCRAMBLE);
            }
        });
    }
}
