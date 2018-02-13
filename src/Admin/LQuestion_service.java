package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLClientInfoException;
import java.util.List;

/**
 * Created by Anil on 05/02/2018
 */
@Path("/lquestion_services")
@WebService
public class LQuestion_service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") int sub, @FormParam("param2") int m, @FormParam("param3") int r, @FormParam("param4") int c)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Course course= (Course) session.createQuery("from  Course s where s.id=:id").setParameter("id",sub).uniqueResult();

            Lquestion squestion=new Lquestion();
            squestion.setQtype(1);
            squestion.setTotal(m);
            squestion.setCourse(course);
            session.save(squestion);

            Lquestion squestion2=new Lquestion();
            squestion2.setQtype(2);
            squestion2.setTotal(r);
            squestion2.setCourse(course);
            session.save(squestion2);

            Lquestion squestion3=new Lquestion();
            squestion3.setQtype(3);
            squestion3.setTotal(c);
            squestion3.setCourse(course);
            session.save(squestion3);

            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return String.valueOf(e);
        }

    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewAll(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
//        java.util.List<Lquestion> tlist=session1.createQuery("select s.id,s.qtype,s.total,s.subject  from Lquestion s").list();
//        java.util.List<Student> tlist=session1.createQuery("select s.id,s.name,s.roll,s.CSClass,s.division from Student s").list();
        Lquestion m= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
        Lquestion r= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
        Lquestion c= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
        t1.commit();
        session1.close();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("1",m.getTotal());
        jsonObject.put("2",r.getTotal());
        jsonObject.put("3",c.getTotal());
//        return tlist;
//        return String.valueOf(squestion.getTotal());
        return String.valueOf(jsonObject);
    }
    @POST
    @Path("getMSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMSq(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Lquestion m= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
        t1.commit();
        session1.close();
        if(m==null)
            return "0";
        return String.valueOf(m.getId());
    }

    @POST
    @Path("getRSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRSq(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Lquestion r= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
        t1.commit();
        session1.close();
        if(r==null)
            return "0";
        return String.valueOf(r.getId());
    }

    @POST
    @Path("getCSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCSq(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Lquestion c= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
        t1.commit();
        session1.close();
        if(c==null)
            return "0";
        return String.valueOf(c.getId());
    }

}
