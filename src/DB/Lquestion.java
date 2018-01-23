package DB;

/**
 * Created by Anil on 22/01/2018
 */
public class Lquestion
{
    private int id;
    private int qtype;
    private int total;
    private LabBatch labBatch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LabBatch getLabBatch() {
        return labBatch;
    }

    public void setLabBatch(LabBatch labBatch) {
        this.labBatch = labBatch;
    }
}
