package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class SSrate
{
    private int id;
    private int ans;
    private Student student;
    private Subject subject;
    private Srate srate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Srate getSrate() {
        return srate;
    }

    public void setSrate(Srate srate) {
        this.srate = srate;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
