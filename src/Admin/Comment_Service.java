package Admin;

import DB.Global;
import DB.Scomment;
import DB.Squestion;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.model.process.internal.ScanningCoordinator;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Anil on 08/02/2018
 */
@Path("/comment_services")
@WebService
public class Comment_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid, @FormParam("param2") String q)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Squestion c= (Squestion) session.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
            Scomment scomment=new Scomment();
            scomment.setName(q);
            scomment.setSquestion(c);
            session.save(scomment);
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
