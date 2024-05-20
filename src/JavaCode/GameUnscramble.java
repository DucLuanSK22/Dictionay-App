package JavaCode;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;

import static Constants.Constant.GAME_WORD;

public class GameUnscramble {

    @FXML
    private WebView webView;
    @FXML
    public void initialize() {
            try {
                // Đọc nội dung từ tệp và tạo một chuỗi chứa nó
                StringBuilder contentBuilder = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(GAME_WORD));
                String line;
                while ((line = br.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
                br.close();
                String htmlContent = contentBuilder.toString();

                // Tải nội dung vào WebView
                webView.getEngine().loadContent(htmlContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        String url = "https://e.gamevui.vn/web/2017/06/duoi-hinh-bat-chu-tieng-anh/?gid=15908&amp;returnurl=https%3a%2f%2fgamevui.vn%2fduoi-hinh-bat-chu-tieng-anh%2fgame&amp;ratedages=0";
//        String url = "https://gamevui.vn/https://e.gamevui.vn/web/2023/08/ban-bong-pha-gach-3?gid=22500&returnurl=https%3a%2f%2fgamevui.vn%2fban-bong-pha-gach-3%2fgame&ratedages=0";
//        webView.getEngine().load(url);

        }
    }
