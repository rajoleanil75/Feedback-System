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

    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@FormParam("param1") String ans,@FormParam("param2") int stud, @FormParam("param3") String sub, @FormParam("param4") int sm)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Student student= (Student) session.createQuery("from Student s where s.id=:sid ").setParameter("sid",stud).uniqueResult();
            Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",sub).uniqueResult();
            Smcq smcq= (Smcq) session.createQuery("from Smcq s where s.id=:id").setParameter("id",sm).uniqueResult();
            SSmcq ssmcq = new SSmcq();
            ssmcq.setAns(ans);
            ssmcq.setStudent(student);
            ssmcq.setSubject(subject);
            ssmcq.setSmcq(smcq);
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
            SSmcq sSmcq= (SSmcq) session.createQuery("from SSmcq s where s.student.id=:id and s.subject.id=:id1").setParameter("id",stud).setParameter("id1",sub).getSingleResult();
            if (sSmcq==null) {
                transaction.commit();
                session.close();
                return "0";
            }
            else {

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
        java.util.List<Smcq> tlist=session.createQuery("from Smcq s where s.squestion.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Smcq smcq= (Smcq) iterator.next();
            List list1=new ArrayList();
            list1.add(smcq.getId());
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
}
