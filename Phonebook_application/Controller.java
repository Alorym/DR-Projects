package sample;

public class Controller {
    public String name;
    public String number;
    public String notes;

    public Controller() {
        this.name = "";
        this.number = "";
        this.notes = "";
    }
    public Controller(String name, String number,String notes) {
        this.name = name;
        this.number = number;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
