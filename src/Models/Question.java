package Models;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;

import static Constants.Constant.*;

public class Question {
    private String questionTitle;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
    private Duration finishedTime;
    private boolean ansIsCorrect = false;

    public Question(String questionTitle, String answerA, String answerB,
                    String answerC, String answerD, String correctAnswer) {
        this.questionTitle = questionTitle;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Duration getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Duration finishedTime) {
        this.finishedTime = finishedTime;
    }

    public boolean isAnsIsCorrect() {
        return ansIsCorrect;
    }

    public void setAnsIsCorrect(boolean ansIsCorrect) {
        this.ansIsCorrect = ansIsCorrect;
    }

    //change Question object to JSON string
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static void readQuestionFile(ArrayList<Question> questions) {
        ClassLoader classLoader = Question.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(QUESTION_FILE);

        if (inputStream == null) {
            System.err.println("File not found: " + QUESTION_FILE);
            return;
        }

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        try {
            StringBuilder contentBuilder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                contentBuilder.append((char) c);
            }

            String content = contentBuilder.toString();
            JSONParser parser = new JSONParser();
            JSONArray questionsArray = (JSONArray) parser.parse(content);
            for (Object o : questionsArray) {
                Question question = getQuestionFromJson((JSONObject) o);

                questions.add(question);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Error closing reader: " + e.getMessage());
            }
        }
    }

    private static Question getQuestionFromJson(JSONObject o) {
        JSONObject questionObject = o;
        String questionTitle = (String) questionObject.get("questionTitle");
        String answerA = (String) questionObject.get("answerA");
        String answerB = (String) questionObject.get("answerB");
        String answerC = (String) questionObject.get("answerC");
        String answerD = (String) questionObject.get("answerD");
        String correctAnswer = (String) questionObject.get("correctAnswer");


        Question question = new Question(questionTitle, answerA, answerB, answerC, answerD,
                correctAnswer);
        return question;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Question) {
            Question question = (Question) obj;
            return question.getQuestionTitle().equals(this.getQuestionTitle());
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        ArrayList<Question> questions = new ArrayList<>();
        readQuestionFile(questions);
        for (Question question : questions) {
            System.out.println(question);
        }

    }
}
