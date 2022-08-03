import javax.swing.*;
import java.sql.*;

public class CarManager extends JFrame {
    CarManager() {
        try {
            // this.setSize(800, 600);
            // this.setVisible(true);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "mrz", "mrz"); 
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from cars");
            System.out.println(rs.toString());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        new CarManager();
    }
}