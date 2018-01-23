package DB;

/**
 * Created by ANIL on 27/12/2017.
 */
public class Scomment {
    private int id;
    private String name;
    private Squestion squestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Squestion getSquestion() {
        return squestion;
    }

    public void setSquestion(Squestion squestion) {
        this.squestion = squestion;
    }
}
