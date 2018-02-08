package Admin;

import DB.CSClass;
import DB.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Anil on 31/01/2018
 */
@Path("/class_services")
@WebService
public class Class_Service
{
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<CSClass> tlist=session.createQuery("select s.id,s.name,s.course from CSClass s ").list();
        t.commit();
        session.close();
        return tlist;
    }
}
