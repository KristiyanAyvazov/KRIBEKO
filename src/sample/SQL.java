package sample;
/**
 * Created by Kristiyan Ayvazov on 04/12/2015.
 */
//imports
import java.sql.*;
import java.util.ArrayList;

public class SQL {
    //attributes
    public Connection conn;
    /*A ResultSet object maintains a cursor that points to the current row in the result set.
     The term "result set" refers to the row and column data contained in a ResultSet object.*/
    ResultSet rs = null;
    //connecting to the database via mysql-connector java library
    public SQL() {
        try {

            //making the connection to the database
            String DB_URL = "jdbc:mysql://localhost:3306/yearoneproject";
            String USER = "root";
            String PASS = "root";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("conn obj created " + conn + " message: ");

        }
        catch (SQLException e) {

            System.out.println("db error" + e.getMessage());
        }
    }
    //method that checks username and password from database
    public boolean getAdmins(String name, String pass) {
        //used to see if the username and password are correct
        boolean tof = false;
        String sql = "SELECT * FROM aadmins WHERE ausername = ? and apassword = ?";
        try {
            /*An object that represents a precompiled SQL statement.
            A SQL statement is precompiled and stored in a PreparedStatement object.
            This object can then be used to efficiently execute this statement multiple times.*/
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //if the username exists and the password is correct turn tof to true
                tof = true;
            }
        } catch (SQLException eee) {
            eee.printStackTrace();
        }
        //return true if everything is ok or false if not
        return tof;
    }
    // array list connected to database that puts all the information from database into TableView
    public ArrayList<String> getShifts() {
        //the shifts will be stored in this arraylist
        ArrayList<String> shifts = new ArrayList<String>();
        String SQL = "SELECT * FROM shifts";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            rs = preparedStatement.executeQuery();
            //while loop
            while (rs.next()) {
                //getting everything from the shifts table
                int id = rs.getInt("id");
                String wage = rs.getString("wageperhour");
                String position = rs.getString("sposition");
                //getting the information from shift_date based on the id from the shifts table
                PreparedStatement preparedStatement2 = conn.prepareStatement("Select * from shift_date where id='" + id + "'");
                //resultset for shift_date table
                ResultSet rs2 = preparedStatement2.executeQuery();
                //getting everything from shift_date table where the id matches the one selected from shifts
                while (rs2.next()) {
                    int id2 = rs2.getInt("sd_id");
                    String date = rs2.getString("sdate");
                    //getting the details from shift_quantity based on the id from the shifts table and shift_date.
                    //By matching the id and the sd_id we can place in the ArrayList on each position a shift.
                    PreparedStatement preparedStatement3 = conn.prepareStatement("Select * from shift_quantity where id='" + id + "' AND sd_id='" + id2 + "'");
                    ResultSet rs3 = preparedStatement3.executeQuery();
                    while (rs3.next()) {
                        //all the details of the shifts is stored in a long string. "qwqw" is used to make the difference between the different shift properties
                        //in other parts of the program we split the string at "qwqw" and get all the data from it
                        String format = "qwqw" + position + "qwqw" + date + "qwqw" + wage + "qwqw" + rs3.getString("quantity");
                        shifts.add(format);
                    }

                }

            }
        } catch (SQLException e) {

        }
        return shifts;
    }

    //method that checks username and password from employee table in database
    public String getEmployees(String name, String pass) {
        String sql = "SELECT * FROM aemployees WHERE username = ? and password = ?";
        String yesOrNo = "no";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                yesOrNo = rs.getString("fullname");

            } else {
            }
        } catch (SQLException eee) {
            eee.printStackTrace();
        }
        return yesOrNo;
    }

    public String getPositon(String sposition) {
        String sql = "SELECT * FROM shifts WHERE sposition = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sposition);
            rs = preparedStatement.executeQuery();
        } catch (SQLException eee) {
            eee.printStackTrace();
        }
        return sposition;
    }
    //method that inserts row into database table
    public void addEmployee(String user, String pass, String fName) {
        String sql = "INSERT INTO aemployees VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, fName);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e) {
            AlertBox.display("Error!", "Username already exist!");
        }
    }
    //method that inserts row into database table
    public void addAdmin(String user, String pass, String fName) {
        String sql = "  INSERT INTO aadmins VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, fName);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e) {
            AlertBox.display("Error!", "Username already exist!");
        }
    }
    //method that inserts row into database table
    public void addShift(String sposition, String sdate, String wageperhour, String quantity) {
        String sql = "  INSERT INTO shifts VALUES (null, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sposition);

            preparedStatement.setString(2, wageperhour);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String SQL = "SELECT * FROM shifts";
        int id = 0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String position = rs.getString("sposition");
                if (position.equals(sposition)) {
                    id = rs.getInt("id");
                }

            }
        } catch (SQLException e) {

        }

        SQL = "Insert into shift_date Values(null,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setInt(1, id);

            preparedStatement.setString(2, sdate);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        SQL = "SELECT * FROM shift_date";
        int iD = 0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idd = rs.getInt("id");
                if (idd == id) {
                    iD = rs.getInt("sd_id");
                }

            }
        } catch (SQLException e) {

        }
        SQL = "Insert into shift_quantity Values(null,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setInt(1, iD);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, quantity);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //method that deletes row into database table
    public void deleteFromDB(String p, String d, int w, int q) {
        String sql = "DELETE FROM shifts WHERE sposition=? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed delete. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "DELETE FROM shift_date WHERE sdate=? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,d);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed delete. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "DELETE FROM shift_quantity WHERE quantity=? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, q);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed delete. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
