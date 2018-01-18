package DB;

import java.util.Date;

/**
 * Created by ANIL on 08/01/2018.
 */
public class Limit_date
{
    private int id;
    private Date date;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
