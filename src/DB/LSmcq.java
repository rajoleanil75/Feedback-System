package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class LSmcq
{
    private int id;
    private String ans;
    private Student student;
    private Lmcq lmcq;

    public Lmcq getLmcq() {
        return lmcq;
    }

    public void setLmcq(Lmcq lmcq) {
        this.lmcq = lmcq;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
