package JavaCode;

import API_Dictionary.DataLoadedListener;
import API_Dictionary.VoiceRequest;
import Models.AlertMessage;
import Models.Word;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static JavaCode.Dictionary_main.*;
import static JavaCode.Dictionary_main.currentData;
import static java.sql.DriverManager.getConnection;

public class SearchController extends DatabaseConnection implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteWordBtn;

    @FXML
    private Button editDefinitionBtn;

    @FXML
    private ListView<String> listWordView, suggestListWord;

    @FXML
    private TextField inputWord;

    @FXML
    private Button saveBtn;

    @FXML
    private WebView meaningArea;

    @FXML
    private TextField setWord;

    @FXML
    private Button speakBtn;

    @FXML
    private Label switchLangBtn;

    private String speckLang = "en-us";

    private WebEngine definitionWebEngine;

    private Word currentSelectedWord;


    public static final String engLangCode = "en-US";
    public static final String vieLangCode = "vi-VN";
    public static String currentLang = "en-US";

    public static boolean isEngVie = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dataEngVie.isEmpty()) {
            Dictionary_main.addDataLoadedListener(new DataLoadedListener() {
                @Override
                public void onDataLoaded() {
                    loadSuggestWordList(); // Thực hiện loadWordList() khi dữ liệu đã được tải
                }
            });
        } else {
            loadSuggestWordList();
        }
        handleListAddedWord();
        handleListWordDic();
        updateListAddWord();
        showListAddedWords(listAddWords);
        saveBtn.setDisable(true);
        deleteWordBtn.setDisable(true);
        editDefinitionBtn.setDisable(true);
        setWord.setText("");
        isEngVie = true ;
        setWord.setEditable(false);
        switchLangBtn.setText("ENGLISH");
        suggestListWord.setVisible(false);
        switchLangBtn.setOnMouseClicked(event -> switchLanguage(event));
        suggestListWord.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                suggestListWord.setVisible(false);
            }
        });
        cancelBtn.setOnMouseClicked(event -> {
            inputWord.clear();
            meaningArea.getEngine().loadContent("");
            suggestListWord.setVisible(false);
            setWord.setText("");
        });

        // Tìm kiếm từ khi người dùng nhập từ khóa
        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            private final Timeline searchTimeline = new Timeline();
            {
                // Set the delay
                searchTimeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(500), this::executeSearch)
                );
                searchTimeline.setCycleCount(1);
            }

            @Override
            public void handle(KeyEvent keyEvent) {
                // Reset the timeline to cancel any pending execution
                searchTimeline.stop();

                String searchKey = inputWord.getText();
                if (searchKey.isEmpty()) {
                    showDefaultListView();
                    cancelBtn.setVisible(false);
                } else {
                    cancelBtn.setVisible(true);
                    // Schedule the search operation after the delay
                    searchTimeline.playFromStart();
                }
            }

            private void executeSearch(ActionEvent event) {
                // This method will be called after the delay
                String searchKey = inputWord.getText();
                handleSearchOnKeyTyped(searchKey);
            }
        });
        // hiện danh sách list từ đã thêm



    }
    @FXML
    private void handleSearchOnKeyTyped(String searchKey) {
        if (searchKey.isEmpty()) {
            suggestListWord.setVisible(false);
            return;
        }
        suggestListWord.setVisible(true);
        List<String> searchResultList = new ArrayList<>();
        // Chuyển sang chữ thường để tìm kiếm không phân biệt chữ hoa/chữ thường
        searchKey = searchKey.trim().toLowerCase();

        for (String key : currentData.keySet()) {
            if (key.toLowerCase().startsWith(searchKey)) {
                searchResultList.add(key);
            }
        }

        if (searchResultList.isEmpty()) {
            showDefaultListView();
        } else {
            showSearchResultListView(searchResultList);
        }
    }
    private void showSearchResultListView(List<String> searchResultList) {
        // Sắp xếp danh sách các từ theo thứ tự bảng chữ cái (alpha)
        Collections.sort(searchResultList);

        // Đặt danh sách đã sắp xếp vào wordListView
        this.suggestListWord.getItems().setAll(searchResultList);
    }
    public void loadSuggestWordList() {
        this.suggestListWord.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đảm bảo rằng newValue không null
                Word selectedWord = currentData.get(newValue.trim());
                currentSelectedWord = selectedWord;
                saveBtn.setVisible(false);
                String definition = selectedWord.getMeaning();
                this.setWord.setText(selectedWord.getWord());

                definitionWebEngine = meaningArea.getEngine();
                definitionWebEngine.loadContent(definition, "text/html");
            }
        });

        showDefaultListView();
    }

    public void switchLanguage(MouseEvent event) {
        Object source = event.getSource();
        if (source == switchLangBtn) {
            if (isEngVie) {
                currentLang = vieLangCode;
                switchLangBtn.setText("VIET NAM");
                speckLang = "vi-vn";
                currentData = dataVieEng;
            } else {
                currentLang = engLangCode;
                switchLangBtn.setText("ENGLISH");
                speckLang = "en-us";
                currentData = dataEngVie;
            }
            isEngVie = !isEngVie;
        }
    }

    @FXML
    public void setSpeakBtn(ActionEvent event) throws Exception {
        if (setWord == null || setWord.getText().isEmpty()) return;
        VoiceRequest wordListening = new VoiceRequest(setWord.getText(), speckLang);
        wordListening.valueProperty().addListener(
                (observable, oldValue, newValue) -> newValue.start());
        Thread thread = new Thread(wordListening);
        thread.setDaemon(true);
        thread.start();
    }
    private void showDefaultListView() {
        // Chuyển danh sách từ Map thành danh sách có thứ tự
        List<String> sortedWords = new ArrayList<>(currentData.keySet());

        // Sắp xếp danh sách các từ theo thứ tự bảng chữ cái (alpha)
        Collections.sort(sortedWords);

        // Đặt danh sách đã sắp xếp vào wordListView
        this.suggestListWord.getItems().setAll(sortedWords);
    }


    private void showListAddedWords(ArrayList<Word> listAddWords) {
        ObservableList<String> list = FXCollections.observableArrayList();
        int i = 0;
        for (Word word : listAddWords) {
            i++;
            list.add(i +". "+ word.getWord());
        }
        listWordView.setItems(list);
        listWordView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    }

    private void handleListAddedWord() {
        this.listWordView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đảm bảo rằng newValue không null
                Word selectedWord = listAddWords.get(Integer.parseInt(newValue.split("\\.")[0].trim()) - 1);
                currentSelectedWord = selectedWord;
                saveBtn.setDisable(false);
                deleteWordBtn.setDisable(false);
                editDefinitionBtn.setDisable(false);
                String definition = selectedWord.getMeaning();
                this.setWord.setText(selectedWord.getWord());
                definitionWebEngine = meaningArea.getEngine();
                definitionWebEngine.loadContent(definition, "text/html");
            }
        });
    }

    // cho lưu chỉnh sửa từ đã thêm

    @FXML
    public void saveWord(ActionEvent event){
        AlertMessage alert = new AlertMessage();
        if (currentSelectedWord != null) {
            String definition = definitionWebEngine.getDocument().getDocumentElement().getTextContent();
            currentSelectedWord.setMeaning(definition);
            listAddWords.clear();
            // update từ đã chỉnh sửa vào bảng add_word
            updateWordInAddWordTable(currentSelectedWord.getWord(), setWord.getText(), definition);
            // thông bảo lưu thành công
            alert.successMessage("Lưu từ mới thành công!");
            saveBtn.setDisable(true);
            deleteWordBtn.setDisable(true);
            editDefinitionBtn.setDisable(true);
            updateListAddWord();
        }
    }

    // cho phép người dùng xóa từ đã thêm vào database từ đã thêm
    @FXML
    public void deleteWord(ActionEvent event) {
        AlertMessage alert = new AlertMessage();
        if (currentSelectedWord != null) {
            listAddWords.remove(currentSelectedWord);
            // xóa từ đó trong bảng add_word
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM add_word WHERE word = ?");
                preparedStatement.setString(1, currentSelectedWord.getWord());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            saveBtn.setDisable(true);
            deleteWordBtn.setDisable(true);
            editDefinitionBtn.setDisable(true);
            setWord.setText("");
            meaningArea.getEngine().loadContent("");
            // thông báo xóa thành công
            alert.successMessage("Xóa từ thành công!");
            updateListAddWord();
        }
    }

    public void updateListAddWord() {
        listAddWords.clear();
        pullAddedWords();
        showListAddedWords(listAddWords);
    }
    // cho phép người dùng chỉnh sửa từ nằm trong list từ đã thêm
    @FXML
    public void editDefinition(ActionEvent event) {
        if (currentSelectedWord != null) {
            saveBtn.setDisable(false);
            // cho phep nguoi dung sua selectedWord va definition
            definitionWebEngine.setJavaScriptEnabled(true);
            definitionWebEngine.executeScript("document.body.contentEditable = true;");
            // cho phép chỉnh sửa selectword
            setWord.setEditable(true);
        }
    }
    public void handleListWordDic() {
        this.suggestListWord.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đảm bảo rằng newValue không null
                Word selectedWord = currentData.get(newValue);
                currentSelectedWord = selectedWord;
                String definition = selectedWord.getMeaning();
                this.setWord.setText(selectedWord.getWord());
                definitionWebEngine = meaningArea.getEngine();
                definitionWebEngine.loadContent(definition, "text/html");
            }
        });
    }
    // update từ được chỉnh sửa vào bảng add_word update cả nghĩa và từ
    public void updateWordInAddWordTable(String word, String word_replace, String meaning) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE add_word SET word = ?, meaning = ? WHERE word = ?");
            preparedStatement.setString(1, word_replace);
            preparedStatement.setString(2, meaning);
            preparedStatement.setString(3, word);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}