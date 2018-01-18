package DB;

/**
 * Created by ANIL on 27/12/2017.
 */
public class LabBatch {
    private int id;
    private String name;
    private DB.CSClass CSClass;

    public DB.CSClass getCSClass() {
        return CSClass;
    }

    public void setCSClass(DB.CSClass CSClass) {
        this.CSClass = CSClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
