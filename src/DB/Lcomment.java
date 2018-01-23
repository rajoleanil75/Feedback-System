package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class Lcomment
{
    private int id;
    private String name;
    private Lquestion lquestion;

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

    public Lquestion getLquestion() {
        return lquestion;
    }

    public void setLquestion(Lquestion lquestion) {
        this.lquestion = lquestion;
    }
}
