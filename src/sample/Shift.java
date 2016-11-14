package sample;

/**
 * Created by Kristiyan Ayvazov on 11/12/2015.
 */
public class Shift {
    //attributes
    private String position;
    private String date;
    private int salary;
    private int hours;
    //get method
    public String getDate() {
        return date;
    }
    //set method
    public void setDate(String date) {
        this.date = date;
    }
    // Shift constructor
    public Shift( String position, String date, int wage, int quantity){
        this.position = position;
        this.date = date;
        this.salary = wage;
        this.hours = quantity;
    }
    //get method
    public String getPosition() {
        return position;
    }
    //set method
    public void setPosition(String position) {
        this.position = position;
    }
    //get method
    public int getWage() {
        return salary;
    }
    //set method
    public void setWage(int wage) {
        this.salary = wage;
    }
    //get method
    public int getQuantity() {
        return hours;
    }
    //set method
    public void setQuantity(int quantity) {
        this.hours = quantity;
    }

}
