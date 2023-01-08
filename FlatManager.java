import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class FlatManager extends JFrame{
    Connection con;
    Statement st;
    JPanel pan;
    JTextField fno, owner, paid, due;

    protected class searchAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String no = fno.getText();
            try {
                ResultSet rs = st.executeQuery("select * from flats where fno='" + no + "'");
                rs.next();
                fno.setText(rs.getString(1));
                owner.setText(rs.getString(2));
                paid.setText(rs.getString(3));
                due.setText(rs.getString(4));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }}
    protected class deleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String no = fno.getText();
            try {
                st.executeUpdate("delete from flats where fno='" + no + "'");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }}
    protected class updateAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PreparedStatement ps = con.prepareStatement("update flats set owner=?, paid=?, due=? where fno=?");

                ps.setString(1, owner.getText());
                ps.setString(2, paid.getText());
                ps.setString(3, due.getText());
                ps.setString(4, fno.getText());

                ps.executeUpdate();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }}

    FlatManager(){
        JButton search = new JButton("Search");
        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");
        
        this.add(new JScrollPane(pan = new JPanel()));
        pan.setLayout(new GridLayout(2, 1));

        JPanel p1 = new JPanel(), p2 = new JPanel(), temp;
        pan.add(p1);
        p1.setLayout(new GridLayout(4, 1));
        
        temp = new JPanel();
        temp.add(new JLabel("Flat No. : "));
        temp.add(fno = new JTextField(20));
        p1.add(temp);
        
        temp = new JPanel();
        temp.add(new JLabel("Name of owner : "));
        temp.add(owner = new JTextField(20));
        p1.add(temp);
        
        temp = new JPanel();
        temp.add(new JLabel("Paid Maintenance : "));
        temp.add(paid = new JTextField(20));
        p1.add(temp);
        
        temp = new JPanel();
        temp.add(new JLabel("Due Maintenance : "));
        temp.add(due = new JTextField(20));
        p1.add(temp);

        pan.add(p2);

        p2.add(search);
        search.addActionListener(new searchAction());

        p2.add(delete);
        delete.addActionListener(new deleteAction());

        p2.add(update);
        update.addActionListener(new updateAction());

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/junaid", "mrz", "mrz");
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FlatManager();
    }
}