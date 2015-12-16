package sample;

/**
 * Created by Kristiyan Ayvazov on 11/12/2015.
 */
public class Shift {
    private String position;
    private String date;
    private int salary;
    private int hours;

    public Shift(){
        this.position = "";
        this.date = "";
        this.salary = 0;
        this.hours = 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Shift(String position,String date, int wage, int quantity){
        this.position = position;
        this.date = date;

        this.salary = wage;
        this.hours = quantity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getWage() {
        return salary;
    }

    public void setWage(int wage) {
        this.salary = wage;
    }

    public int getQuantity() {
        return hours;
    }

    public void setQuantity(int quantity) {
        this.hours = quantity;
    }
}
