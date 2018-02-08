package Admin;

import DB.CSClass;
import DB.Course;
import DB.Squestion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Anil on 07/02/2018
 */
@Path("/course_services")
@WebService
public class Course_service {
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Course> tlist=session.createQuery("from Course ").list();
        t.commit();
        session.close();
        return tlist;
    }

    @POST
    @Path("getCourse")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCourse(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Course r= (Course) session1.createQuery("from Course s where s.id=:sid").setParameter("sid",sid).uniqueResult();
        t1.commit();
        session1.close();
        if(r==null)
            return "0";
        return String.valueOf(r.getName());
    }
}
