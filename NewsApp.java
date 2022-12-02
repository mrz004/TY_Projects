import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NewsApp {

    GUI frame;

    /********* Class to store title and url collectively *********/
    protected class News {
        String text, url;

        News(String text, String url) {
            this.text = text;
            this.url = url;
        }

        News() {
        }
    }

    protected class GUI extends JFrame {
        Fetcher fetcher;

        GUI() {
            /********* Fetch and extract the news *********/
            fetcher = new Fetcher();
            ArrayList<News> news = fetcher.extract();

            /********* Constructing GUI *********/
            JPanel pan = new JPanel();
            pan.setLayout(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridx = 0;
            gc.gridy = 0;
            gc.insets = new Insets(10, 5, 10, 5);
            pan.add(new JLabel());
            this.add(new JScrollPane(pan));

            /********* Displaying news on GUI *********/
            for (News n : news) {
                JPanel jp = new JPanel();
                jp.setLayout(new GridLayout(2, 1));
                JLabel text = new JLabel(n.text);
                text.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                JButton link = new JButton("download pdf...");
                link.setFont(new Font("Times New Roman", Font.ITALIC, 12));
                link.setForeground(Color.BLUE);
                link.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                              Desktop.getDesktop().browse(new URI(n.url.replace("\\s", "%20")));
                            } catch (Exception ex) {
                                // System.out.println("Exception : " + ex);
                            }
                        }
                        else{
                            // System.out.println("Desktop not supported");
                        }
                    }
                });
                jp.add(text);
                jp.add(link);
                pan.add(jp, gc);
                gc.gridy++;
            }

            /********* Make frame visible *********/
            this.setSize(400, 400);
            this.setVisible(true);
        }
    }

        protected class Fetcher {
            String html = "", tag = "<p class=\"news-short-content news-short-contentnews1\">";

            /********* Fetch all the HTML from the URL *********/
            Fetcher() {
                try {
                    URL u = new URL("https://msbte.org.in");
                    URLConnection con = u.openConnection();
                    Scanner sc = new Scanner(con.getInputStream());
                    while (sc.hasNext())
                        html += sc.nextLine() + "\n";
                    extract();
                } catch (Exception e) {
                    System.out.println("URL error : " + e);
                }
            }

            Fetcher(String url, String tag) {
                this.tag = tag;
                try {
                    URL u = new URL(url);
                    URLConnection con = u.openConnection();
                    Scanner sc = new Scanner(con.getInputStream());
                    while (sc.hasNext())
                        html += sc.nextLine() + "\n";
                } catch (Exception e) {
                    System.out.println("URL error : " + e);
                }
            }

            /********* Extract the news and link from the HTML *********/
            public ArrayList<News> extract() {
                int index = 0;
                ArrayList<News> newsList = new ArrayList<>();
                while (true) {
                    News temp = new News();
                    index = html.indexOf(tag, index);
                    if (index == -1)
                        break;
                    int index1 = html.indexOf("href=\"", index) + 6;
                    int index2 = html.indexOf("\"", index1);
                    temp.url = html.substring(index1, index2);
                    int index3 = html.indexOf("title=\"", index) + 7;
                    int index4 = html.indexOf("\"", index3);
                    temp.text = html.substring(index3, index4);
                    newsList.add(temp);
                    index++;
                    // System.out.println("title : " + temp.text);
                    // System.out.println("url : " + temp.url);
                }
                return newsList;
            }
        }

    /********* Make Frame Visible *********/
    NewsApp() {
        this.frame = new GUI();
    }

        public static void main(String[] args) {
            new NewsApp();
        }
}
