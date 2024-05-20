package JavaCode;

import API_Dictionary.TranslateAPI;
import API_Dictionary.VoiceRequest;
import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

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

        @FXML
        private ChoiceBox<String> choseLangDestination;

        @FXML
        private ChoiceBox<String> choseLangSource;

        @FXML
        private TextArea inputSentence;

        @FXML
        private TextArea outputMeaning;
        @FXML
        private Button cancelBtn;
        @FXML
        private Button copyText;


        // khởi tạo và interface với các thành phần trong UI
        @Override
        public void initialize(URL location, ResourceBundle resources) {

                LangSource();
                LangDestination();
                // Thiết lập "Phát hiện ngôn ngữ" là mặc định
                choseLangSource.setValue("Phát hiện ngôn ngữ");
                speakTo = "vi-vn";
                speakFrom = "en-us";
                languageTo = "vi";

                // xử lý sự kiện khi click vào nút cancel
                cancelBtn.setOnMouseClicked(event -> {
                        inputSentence.clear();
                        outputMeaning.clear();
                });

                // meaning text không được edit
                outputMeaning.setEditable(false);
        }

        void LangSource() {
                choseLangSource.getItems().addAll("Phát hiện ngôn ngữ", "Tiếng Anh", "Tiếng Việt", "Tiếng Hàn", "Tiếng Nga", "Tiếng Trung");
                choseLangSource.setValue("Phát hiện ngôn ngữ");
                choseLangSource.setOnAction(event -> {
                        switch (choseLangSource.getValue()) {
                                case "Phát hiện ngôn ngữ":
                                        languageFrom = ""; // Đặt ngôn ngữ từ là rỗng để phát hiện ngôn ngữ
                                        speakFrom = "en-us";
                                        break;
                                case "Tiếng Anh":
                                        languageFrom = "en";
                                        speakFrom = "en-us";
                                        break;
                                case "Tiếng Việt":
                                        languageFrom = "vi";
                                        speakFrom = "vi-vn";
                                        break;
                                case "Tiếng Hàn":
                                        languageFrom = "ko";
                                        speakFrom = "ko-kr";
                                        break;
                                case "Tiếng Nga":
                                        languageFrom = "ru";
                                        speakFrom = "ru-ru";
                                        break;
                                case "Tiếng Trung":
                                        languageFrom = "zh";
                                        speakFrom = "zh-cn";
                                        break;
                        }
                });
        }


        void LangDestination() {
                choseLangDestination.getItems().addAll("Tiếng Việt", "Tiếng Anh", "Tiếng Hàn", "Tiếng Nga", "Tiếng Trung");
                choseLangDestination.setValue("Tiếng Việt");
                choseLangDestination.setOnAction(event -> {
                        switch (choseLangDestination.getValue()) {
                                case "Tiếng Việt":
                                        languageTo = "vi";
                                        speakTo = "vi-vn";
                                        break;
                                case "Tiếng Anh":
                                        languageTo = "en";
                                        speakTo = "en-us";
                                        break;
                                case "Tiếng Hàn":
                                        languageTo = "ko";
                                        speakTo = "ko-kr";
                                        break;
                                case "Tiếng Nga":
                                        languageTo = "ru";
                                        speakTo = "ru-ru";
                                        break;
                                case "Tiếng Trung":
                                        languageTo = "zh";
                                        speakTo = "zh-cn";
                                        break;
                        }
                });
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

        @FXML
        void translateBtn() throws IOException {
                if (!inputSentence.getText().isBlank()) {
                        outputMeaning.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, inputSentence.getText()));
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
