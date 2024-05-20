package JavaCode;


import Constants.Constant;
import Models.Word;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import static Constants.codekey.DATABASE_PASSWORD;
import static Constants.codekey.DATABASE_USER;
//import static JavaCode.SearchController.listAddWords;

public class DatabaseConnection {

    @FXML
    AnchorPane container;
    public Connection databaseLink;
    protected static Timeline countdownTimer;

//    public static ArrayList<User> listUsers = new ArrayList<>();
    public static ArrayList<Word> listAddWords = new ArrayList<>();
//
//
    public Connection getConnection() {

        try {
            Class.forName(Constant.DRIVER);
            databaseLink = DriverManager.getConnection(Constant.URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
//
    void showComponent(String path) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


// kéo list word từ bảng add_word vào listAddWords
    public void pullAddedWords() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM add_word");
            while (resultSet.next()) {
                String word = resultSet.getString("word");
                String meaning = resultSet.getString("meaning");
                listAddWords.add(new Word(word, meaning));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


// thêm từ mới vào  bảng add_word
    public void addWord(String word, String meaning) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO add_word(word, meaning) VALUES(?, ?)");
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, meaning);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
   public HashMap<String, Word> searchWord(String searchKey, String tableName) {
            return null;
        }

    public static void main(String[] args) {
        HashMap<String, Word> currentData = new HashMap<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();
        databaseConnection.pullAddedWords();


        for (Word word : listAddWords) {
            System.out.println(word.getWord() + ": " + word.getMeaning());
        }
    }
}