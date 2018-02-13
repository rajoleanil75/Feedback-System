package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Anil on 08/02/2018
 */
@Path("/lrate_services")
@WebService
public class LRate_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid, @FormParam("param2") String q)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Lquestion r= (Lquestion) session.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
            Lrate srate=new Lrate();
            srate.setName(q);
            srate.setLquestion(r);
            session.save(srate);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "0";
        }
    }
}
