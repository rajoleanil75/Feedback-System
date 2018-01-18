package DB;

import java.util.Date;

/**
 * Created by ANIL on 27/12/2017.
 */
public class Subject_Feedback {
    private int id;
    private DB.Student Student;
    private DB.Subject Subject;
    private DB.Question Question;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public DB.Subject getSubject() {
        return Subject;
    }

    public void setSubject(DB.Subject subject) {
        this.Subject = subject;
    }

    public DB.Question getQuestion() {
        return Question;
    }

    public void setQuestion(DB.Question question) {
        this.Question = question;
    }

    public DB.Student getStudent() {
        return Student;
    }

    public void setStudent(DB.Student student) {
        this.Student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
