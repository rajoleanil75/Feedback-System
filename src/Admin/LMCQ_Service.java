package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Anil on 08/02/2018
 */
@Path("/lmcq_services")
@WebService
public class LMCQ_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid,@FormParam("param2") String q, @FormParam("param3") String o1, @FormParam("param4") String o2, @FormParam("param5") String o3, @FormParam("param6") String o4)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Lquestion m= (Lquestion) session.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
            Lmcq smcq = new Lmcq();
            smcq.setName(q);
            smcq.setOpt1(o1);
            smcq.setOpt2(o2);
            smcq.setOpt3(o3);
            smcq.setOpt4(o4);
            smcq.setLquestion(m);
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
        java.util.List<Lmcq> tlist=session.createQuery("select s.id,s.name,s.opt1,s.opt2,s.opt3,s.opt4,s.lquestion from Lmcq s ").list();
        t.commit();
        session.close();
        return tlist;
    }
}
