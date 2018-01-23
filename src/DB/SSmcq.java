package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class SSmcq {
    private int id;
    private String ans;
    private Student student;
    private Smcq smcq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Smcq getSmcq() {
        return smcq;
    }

    public void setSmcq(Smcq smcq) {
        this.smcq = smcq;
    }
}
