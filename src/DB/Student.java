package DB;

/**
 * Created by ANIL on 27/12/2017.
 */
public class Student {
    private int id;
    private int roll;
    private int name;
    private DB.CSClass CSClass;
    private DB.Division division;

    public DB.CSClass getCSClass() {
        return CSClass;
    }

    public void setCSClass(DB.CSClass CSClass) {
        this.CSClass = CSClass;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}
