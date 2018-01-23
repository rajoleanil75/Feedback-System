package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class Lrate
{
    private int id;
    private String name;
    private Lquestion lquestion;

    public Lquestion getLquestion() {
        return lquestion;
    }

    public void setLquestion(Lquestion lquestion) {
        this.lquestion = lquestion;
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
