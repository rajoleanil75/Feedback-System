package Admin;

import DB.CSClass;
import DB.Global;
import DB.Smcq;
import DB.Squestion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Anil on 08/02/2018
 */
@Path("/mcq_services")
@WebService
public class MCQ_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid,@FormParam("param2") String q, @FormParam("param3") String o1, @FormParam("param4") String o2, @FormParam("param5") String o3, @FormParam("param6") String o4)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Squestion m= (Squestion) session.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
            Smcq smcq = new Smcq();
            smcq.setName(q);
            smcq.setOpt1(o1);
            smcq.setOpt2(o2);
            smcq.setOpt3(o3);
            smcq.setOpt4(o4);
            smcq.setSquestion(m);
            session.save(smcq);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "0";
        }

    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Smcq> tlist=session.createQuery("select s.id,s.name,s.opt1,s.opt2,s.opt3,s.opt4,s.squestion from Smcq s ").list();
        t.commit();
        session.close();
        return tlist;
    }
}
