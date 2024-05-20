package JavaCode;

import API_Dictionary.TranslateAPI;
import API_Dictionary.VoiceRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

public class TranslateController extends DatabaseConnection implements Initializable {
        String languageFrom = "";
        String languageTo = "vi";
        String speakFrom;
        String speakTo;
//
//        @FXML
//        private ChoiceBox<String> choseLangDestination;
//
//        @FXML
//        private ChoiceBox<String> choseLangSource;
        @FXML
        private Label main_label;
        @FXML
        private Label trans_label;
        @FXML
        private TextArea inputSentence;

        @FXML
        private TextArea outputMeaning;
        @FXML
        private Button cancelBtn;

        @FXML
        private Button copyText;

        private String Language1 = "English";
        private String Language2 = "Vietnamese";
        private TranslateAPI api;
        // khởi tạo và interface với các thành phần trong UI
        @Override
        public void initialize(URL location, ResourceBundle resources) {
//
                main_label.setText(Language1);
                trans_label.setText(Language2);
                inputSentence.setOnKeyReleased(new EventHandler<InputEvent>() {
                        @Override
                        public void handle(InputEvent t) {
                                if (t instanceof KeyEvent) {
                                        //KeyEvent event = (KeyEvent) t;
                                        try {
                                                findans();
                                        } catch (Exception e) {}
                                }
                        }
                });

//                // Thiết lập "Phát hiện ngôn ngữ" là mặc định
                speakTo = "vi-vn";
                speakFrom = "en-us";
//                languageTo = "vi";
//
//                // xử lý sự kiện khi click vào nút cancel
                cancelBtn.setOnMouseClicked(event -> {
                        inputSentence.clear();
                        outputMeaning.clear();
                });
//
//                // meaning text không được edit
                outputMeaning.setEditable(false);
        }
        // nhấn nút đổi ngôn ngữ
        @FXML
        void swapLanguage() {
                String temp = Language1;
                Language1 = Language2;
                Language2 = temp;
                String temp2= languageFrom;
                speakFrom = speakTo;
                speakTo = temp2;
                main_label.setText(Language1);
                trans_label.setText(Language2);
        }

        public void findans() throws Exception {
                if (inputSentence.getText().trim().isEmpty()) return;
                String s = inputSentence.getText();
                api = new TranslateAPI(s, Language1,Language2);
                api.valueProperty().addListener((observable, oldValue, newValue) -> outputMeaning.setText(String.valueOf(newValue)));
//                System.out.println(s);
                Thread th = new Thread(api);
                th.setDaemon(true);
                th.start();
        }
        @FXML
        void speakLangSource() throws Exception {
                if (!Objects.equals(inputSentence.getText(), "")) {
                        VoiceRequest wordListening = new VoiceRequest(inputSentence.getText(), speakFrom);
                        wordListening.valueProperty().addListener(
                                (observable, oldValue, newValue) -> newValue.start());
                        Thread thread = new Thread(wordListening);
                        thread.setDaemon(true);
                        thread.start();
                }
        }

        @FXML
        void  speakLangDestination() throws Exception {
                if (!Objects.equals(outputMeaning.getText(), "")) {
                        VoiceRequest wordListening = new VoiceRequest(outputMeaning.getText(), speakTo);
                        wordListening.valueProperty().addListener(
                                (observable, oldValue, newValue) -> newValue.start());
                        Thread thread = new Thread(wordListening);
                        thread.setDaemon(true);
                        thread.start();
                }
        }
    // copy text to pc
        @FXML
        void copyText() {
                String text = outputMeaning.getText();
                if (!text.isEmpty()) {
                        final Clipboard clipboard = Clipboard.getSystemClipboard();
                        final ClipboardContent content = new ClipboardContent();
                        content.putString(text);
                        clipboard.setContent(content);
                }
        }
}
