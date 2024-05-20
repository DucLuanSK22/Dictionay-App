package JavaCode;

import API_Dictionary.DataLoadedListener;
import Models.Word;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Constants.Constant.*;


public class Dictionary_main extends Application {
    private static Stage stg;

    private double xOffset = 0;
    private double yOffset = 0;

    public static Map<String, Word> dataEngVie = new HashMap<>();
    public static Map<String, Word> dataVieEng = new HashMap<>();
    public static Map<String, Word> currentData = new HashMap<>();
    public static boolean isLoadedAllData = false;
    private static List<DataLoadedListener> listeners = new ArrayList<>();


    public void initializeStage(Stage primaryStage) throws IOException {
        stg = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("Layers/SearchWordView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource(MENU_LAYER));

        primaryStage.setTitle("Dictionary Application");
        readData(dataVieEng, DATA_VE_FILE_PATH);
        readData(dataEngVie, DATA_EV_FILE_PATH);
        primaryStage.setResizable(false);
        ClassLoader classLoader = Dictionary_main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(LOGO_IMAGE_PATH);
        assert inputStream != null;
        Image image = new Image(inputStream);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeStage(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    public static void readData(Map<String, Word> data, String DATA_FILE_PATH) throws IOException {
        data.clear();
        ClassLoader classLoader = Dictionary_main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(DATA_FILE_PATH);
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
        currentData = data;
        dataLoaded();
    }

    public static void dataLoaded() {
        isLoadedAllData = true;
        notifyListeners();
    }

    public static void addDataLoadedListener(DataLoadedListener listener) {
        listeners.add(listener);
    }
    private static void notifyListeners() {
        for (DataLoadedListener listener : listeners) {
            listener.onDataLoaded();
        }
    }
}
