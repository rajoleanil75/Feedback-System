package DB;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by ANIL on 08/01/2018.
 */
public class Limit_date
{
    private int id;
    private LocalDate sdate;
    private LocalDate edate;

    public LocalDate getEdate() {
        return edate;
    }

    public void setEdate(LocalDate edate) {
        this.edate = edate;
    }

    public LocalDate getSdate() {
        return sdate;
    }

    public void setSdate(LocalDate sdate) {
        this.sdate = sdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
