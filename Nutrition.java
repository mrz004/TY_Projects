import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Nutrition extends JFrame implements ActionListener {

    JComboBox<String> jc;
    Connection con;
    Statement st;
    JPanel pan;
    String[] arr = {"srno", "name", "calories", "total fat", "cholesterol", "vitamin a", "vitamin b12", "vitamin 6", "vitamin c", "vitamin d", "vitamin e", "vitamin k", "calcium", "iron", "protein", "carbohydrate", "fiber", "sugar", "glucose", "fat", "caffeine", "water"};

    Nutrition() {
        pan = new JPanel();
        JPanel jp = new JPanel(), temp = new JPanel();
        JButton btn = new JButton("Search");
        this.add(new JScrollPane(jp));

        jp.setLayout(new GridLayout(2, 1, 5, 10));
        pan.setLayout(new FlowLayout());

        temp.add(new JLabel("Select the food from drop down : "));
        temp.add(jc = new JComboBox<>());
        temp.add(btn);
        jp.add(temp);
        jp.add(pan);

        btn.addActionListener(this);

        try {
            String user = "mrz", pass = "mrz";
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar", user, pass);
            this.st = con.createStatement();
            ResultSet rs = st.executeQuery("select name from nutrition;");
            while (rs.next()) {
                jc.addItem(rs.getString(1));
                // System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Nutrition();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String str = (String) jc.getSelectedItem();
        pan.removeAll();
        // System.out.println(str);
        try {
            ResultSet rs = st.executeQuery("select * from nutrition where name = \"" + str + "\";");
            String[][] arr1 = new String[2][22];
            arr1[0] = arr;
            rs.next();
            for (int i = 1; i<=22; i++)
                arr1[1][i-1] = rs.getString(i);
            pan.add(new JTable(arr1, arr));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}