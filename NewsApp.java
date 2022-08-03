import javax.swing.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NewsApp {

    GUI frame;
    Fetcher fetcher;

    public class GUI extends JFrame {
        GUI() {
            this.setSize(400, 400);
            this.setVisible(true);
        }
    }

    public class Fetcher {
        String html = "";
        ArrayList<String[]> news;
        Fetcher(String url) throws IOException {
            URL u = new URL(url);
            URLConnection con = u.openConnection();
            Scanner sc = new Scanner(con.getInputStream());
            while (sc.hasNext())
                html += sc.nextLine() + "\n";
            extract();
        }
        public void extract() {
            
        }
    }

    NewsApp() {
        this.frame = new GUI();
        try {
            this.fetcher = new Fetcher("https://msbte.org.in");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        new NewsApp();
    }
}