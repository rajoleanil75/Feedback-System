package Admin;

import DB.CSClass;
import DB.Global;
import DB.Subject;
import DB.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Anil on 31/01/2018
 */
@Path("/subject_services")
@WebService
public class Subject_Service
{
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") String scode, @FormParam("param2") String sname, @FormParam("param3") String tid, @FormParam("param4") int cid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            CSClass csClass = (CSClass) session.createQuery("from CSClass c where c.id= :id").setParameter("id", cid).uniqueResult();
            Teacher teacher = (Teacher) session.createQuery("from Teacher t where t.id=:id").setParameter("id", tid).uniqueResult();
            Subject subject = new Subject();
            subject.setId(scode);
            subject.setName(sname);
            subject.setCSClass(csClass);
            subject.setTeacher(teacher);
            session.persist(subject);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "E";
        }

    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Subject> tlist=session.createQuery("select s.id,s.name,s.CSClass,s.teacher from Subject s").list();
        t.commit();
        session.close();
        return tlist;
    }
}
