package sample;


import java.sql.*;

public class SQL {
    private Connection conn = null;
    ResultSet rs = null;
    public SQL(){
        try {

            String DB_URL = "jdbc:mysql://localhost:3306/year1project";
            String USER = "root";
            String PASS = "root";

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("conn obj created " + conn + " message: ");
        } catch (SQLException e)
        {
            System.out.println("db error" + e.getMessage());
        }
    }

    public boolean getAdmins(String name, String pass){
        boolean tof = false;
        String sql = "SELECT * FROM admins WHERE ausername = ? and apassword = ?";
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, pass);
            rs = preparedStatement.executeQuery();
            if (rs.next()){

                tof = true;
            }
            else {
                System.out.println("fuck off");
            }
        } catch (SQLException eee)
        {
            eee.printStackTrace();
        }
        return tof;
    }

    public String getEmployees(String name, String pass) {
        String sql = "SELECT * FROM employees WHERE username = ? and password = ?";
        String yesOrNo = "no";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                yesOrNo = rs.getString("fullname");


            } else {
                System.out.println("fuck off");
            }
        } catch (SQLException eee) {
            eee.printStackTrace();
        }
        return yesOrNo;
    }

    public void addEmployee(String user, String pass, String fName){
        String sql = "INSERT INTO employees VALUES (null, ?, ?, ?)";

        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, fName);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addAdmin(String user, String pass, String fName){
        String sql = "  INSERT INTO admins VALUES (null, ?, ?, ?)";

        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, fName);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
