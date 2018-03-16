package DB;

import java.time.LocalDate;

/**
 * Created by Anil on 22/01/2018
 */
public class LSrate
{
    private int id;
    private int ans;
    private Student student;
    private Lrate lrate;
    private Teacher_LabBatch teacher_labBatch;
    private LocalDate date;

    public Lrate getLrate() {
        return lrate;
    }

    public void setLrate(Lrate lrate) {
        this.lrate = lrate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Teacher_LabBatch getTeacher_labBatch() {
        return teacher_labBatch;
    }

    public void setTeacher_labBatch(Teacher_LabBatch teacher_labBatch) {
        this.teacher_labBatch = teacher_labBatch;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
