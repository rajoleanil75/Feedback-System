package DB;

/**
 * Created by ANIL on 08/01/2018.
 */
public class Student_Lab
{
    private int id;
    private DB.LabBatch LabBatch;
    private DB.Student Student;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DB.Student getStudent() {
        return Student;
    }

    public void setStudent(DB.Student student) {
        Student = student;
    }

    public DB.LabBatch getLabBatch() {
        return LabBatch;
    }

    public void setLabBatch(DB.LabBatch labBatch) {
        LabBatch = labBatch;
    }
}