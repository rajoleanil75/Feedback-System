package DB;

/**
 * Created by ANIL on 27/12/2017.
 */
public class Lab_Feedback {
    private int id;
    private DB.Student Student;
    private DB.Question Question;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public DB.Question getQuestion() {
        return Question;
    }

    public void setQuestion(DB.Question question) {
        Question = question;
    }

    public DB.Student getStudent() {
        return Student;
    }

    public void setStudent(DB.Student student) {
        Student = student;
    }
}
