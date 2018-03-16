package DB;

import java.time.LocalDate;

/**
 * Created by Anil on 22/01/2018
 */
public class LScomment
{
    private int id;
    private String ans;
    private Student student;
    private Lcomment lcomment;
    private Teacher_LabBatch teacher_labBatch;
    private LocalDate date;

    public Lcomment getLcomment() {
        return lcomment;
    }

    public void setLcomment(Lcomment lcomment) {
        this.lcomment = lcomment;
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
