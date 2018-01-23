package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class Lmcq
{
    private int id;
    private String name;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
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

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public Lquestion getLquestion() {
        return lquestion;
    }

    public void setLquestion(Lquestion lquestion) {
        this.lquestion = lquestion;
    }
}
