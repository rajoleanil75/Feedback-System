package DB;

/**
 * Created by ANIL on 27/12/2017.
 */
public class Teacher_LabBatch {
    private int id;
    private DB.Teacher Teacher;
    private DB.LabBatch LabBatch;

    public DB.Teacher getTeacher() {
        return Teacher;
    }

    public void setTeacher(DB.Teacher teacher) {
        Teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DB.LabBatch getLabBatch() {
        return LabBatch;
    }

    public void setLabBatch(DB.LabBatch labBatch) {
        LabBatch = labBatch;
    }
}
