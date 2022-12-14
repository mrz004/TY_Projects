import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class CarManager extends JFrame implements ActionListener {

    JComboBox<String> jc;
    JLabel[] labels = new JLabel[7];
    Connection con;
    Statement st;

    CarManager() {
        JPanel pan = new JPanel();
        JPanel jp = new JPanel(), temp = new JPanel();
        JButton btn = new JButton("Search");
        for (int i = 0; i < labels.length; i++)
            pan.add(labels[i] = new JLabel());
        this.add(new JScrollPane(jp));

        jp.setLayout(new GridLayout(2, 1, 5, 10));
        pan.setLayout(new GridLayout(6, 1, 2, 5));

        temp.add(new JLabel("Select the car you want : "));
        temp.add(jc = new JComboBox<>());
        temp.add(btn);
        jp.add(temp);
        jp.add(pan);

        btn.addActionListener(this);

        try {
            String user = "mrz", pass = "mrz";
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", user, pass);
            this.st = con.createStatement();
            ResultSet rs = st.executeQuery("select model from cars");
            while (rs.next()) {
                jc.addItem(rs.getString(1));
                // System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        this.setSize(400, 400);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new CarManager();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String str = (String) jc.getSelectedItem();
        // System.out.println(str);
        try {
            ResultSet rs = st.executeQuery("select * from cars where model = \"" + str + "\";");
            rs.next();
            labels[0].setText("Sr. No. : " + rs.getInt(1));
            labels[1].setText("Company : " + rs.getString(2));
            labels[2].setText("Model : " + rs.getString(3));
            labels[3].setText("Price : " + rs.getInt(4));
            labels[4].setText("CC : " + rs.getInt(5));
            labels[5].setText("Color : " + rs.getString(6));
            labels[6].setText("Fuel : " + rs.getString(7));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}