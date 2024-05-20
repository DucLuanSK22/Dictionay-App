package JavaCode;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AddWordController extends DatabaseConnection implements Initializable {
    @FXML
    private TextField inpputAddword;

    @FXML
    private TextArea input_meaning;

    @FXML
    private Label successLabel;

    @FXML
    private Label titleWord;
    @FXML
    private Button switchLang;

    private static int keyLang = 1;// 1 them từ tiếng việt - 2: thêm từ tiếng anh
    private static int currentKeyLang;
    @FXML
    void cancel_inputBtn(ActionEvent event) {
        inpputAddword.setText("");
        input_meaning.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        successLabel.setVisible(false);
        titleWord.setText("Nhập từ tiếng Anh");
        currentKeyLang = 2;

        inpputAddword.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String input = inpputAddword.getText();
                if (input.isEmpty()) {
                    successLabel.setVisible(false);
                }
            }
    });
    }


    @FXML
    void addWordBtn(ActionEvent event) {
        String word = inpputAddword.getText();
        String meaning = input_meaning.getText();
        if (word.isEmpty() || meaning.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể thêm từ");
            alert.setContentText("Vui lòng nhập từ và nghĩa của từ");
            alert.showAndWait();
            successLabel.setVisible(false);


        } else {
            addWord(word, meaning);
            successLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(1));
            pause.setOnFinished(e -> {
                successLabel.setVisible(false);
                inpputAddword.setText("");
                input_meaning.setText("");
            });
            pause.play();
        }
//    }
    }

    // switch language
    @FXML
    void switchLangBtn(ActionEvent event) {
        if (keyLang == 2) {
            keyLang = 1;
            currentKeyLang = keyLang;
            titleWord.setText("Nhập từ tiếng Việt");
            switchLang.setText("VIỆT NAM");
        } else {
            keyLang = 2;
            currentKeyLang = keyLang;
            titleWord.setText("Nhập từ tiếng Anh");
            switchLang.setText("ENGLISH");
        }
    }

}
