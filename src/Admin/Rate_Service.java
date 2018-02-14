package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 08/02/2018
 */
@Path("/rate_services")
@WebService
public class Rate_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid, @FormParam("param2") String q)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Squestion r= (Squestion) session.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
            Srate srate=new Srate();
            srate.setName(q);
            srate.setSquestion(r);
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
    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@FormParam("param1") int ans,@FormParam("param2") int stud, @FormParam("param3") String sub, @FormParam("param4") int sm)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Student student= (Student) session.createQuery("from Student s where s.id=:sid ").setParameter("sid",stud).uniqueResult();
            Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",sub).uniqueResult();
            Srate srate= (Srate) session.createQuery("from Srate s where s.id=:id").setParameter("id",sm).uniqueResult();
            SSrate ssmcq=new SSrate();
            ssmcq.setAns(ans);
            ssmcq.setStudent(student);
            ssmcq.setSubject(subject);
            ssmcq.setSrate(srate);
            session.save(ssmcq);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "0";
        }
    }
    @POST
    @Path("subcheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String subcheck(@FormParam("param1") int stud, @FormParam("param1") String sub)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            SSrate sSmcq= (SSrate) session.createQuery("from SSrate s where s.student.id=:id and s.subject.id=:id1").setParameter("id",stud).setParameter("id1",sub).setMaxResults(1);
            if (sSmcq==null) {
                transaction.commit();
                session.close();
                return "0";
            }
            else {
                transaction.commit();
                session.close();
                return "1";
            }
        }
        catch (Exception e)
        {
            return String.valueOf(e);
        }
    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll(@FormParam("param1")int sid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Srate> tlist=session.createQuery("from Srate s where s.squestion.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Srate srate= (Srate) iterator.next();
            List list1=new ArrayList();
            list1.add(srate.getId());
            list1.add(srate.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
}
