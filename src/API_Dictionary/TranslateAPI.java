package API_Dictionary;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//public class TranslateAPI {
//
//    public static String googleTranslate(String langFrom, String langTo, String text) throws IOException {
//        String urlScript = "https://script.google.com/macros/s/AKfycbw1qSfs1Hvfnoi3FzGuoDWijwQW69eGcMM_iGDF7p5vu1oN_CaFqIDFmCGzBuuGCk_N/exec" +
//                "?q=" + URLEncoder.encode(text, "UTF-8") +
//                "&target=" + langTo +
//                "&source=" + langFrom;
//        URL url = new URL(urlScript);
//        StringBuilder response = new StringBuilder();
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        return response.toString();
//    }
//
//}
//



import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateAPI extends Task<String> {

    private String s;
    private String language1;
    private String language2;

    public TranslateAPI (String s, String language1, String language2) {
        this.language1 = convert(language1);
        this.language2 = convert(language2);
        this.s = s;
    }

    private String convert(String text) {
        if (text.equals("English")) return "en";
        if (text.equals("Vietnamese")) return "vi";
        return "";
    }

    @Override
    protected String call() throws Exception {
        //System.out.println(language1 + " " + language2);
        String urlStr = "https://script.google.com/macros/s/AKfycbxImVKCoyddpnSJGhq8RL_J0kkwlNXx90GY0yE_IsB835G1_BWdQS9vsuX14zOgRyXB/exec" +
                "?q=" + URLEncoder.encode(s, "UTF-8") +
                "&target=" + language2 +
                "&source=" + language1;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}