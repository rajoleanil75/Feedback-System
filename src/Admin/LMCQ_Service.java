package Admin;

import DB.*;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.time.LocalDate.now;

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
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

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
            transaction.commit();
            session.close();
            return "0";
        }

    }

    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@FormParam("param1") String ans,@FormParam("param2") int stud, @FormParam("param3") int sub, @FormParam("param4") int sm)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Student student= (Student) session.createQuery("from Student s where s.id=:sid ").setParameter("sid",stud).uniqueResult();
            Teacher_LabBatch subject= (Teacher_LabBatch) session.createQuery("from Teacher_LabBatch s where s.id=:id").setParameter("id",sub).uniqueResult();
            Lmcq smcq= (Lmcq) session.createQuery("from Lmcq s where s.id=:id").setParameter("id",sm).uniqueResult();
            LSmcq ssmcq = new LSmcq();
            ssmcq.setAns(ans);
            ssmcq.setStudent(student);
            ssmcq.setTeacher_labBatch(subject);
            ssmcq.setLmcq(smcq);
            ssmcq.setDate(now());
            session.save(ssmcq);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return "0";
        }
    }


    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll(@FormParam("param1")int sid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Lmcq> tlist=session.createQuery("from Lmcq s where s.lquestion.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Lmcq smcq= (Lmcq) iterator.next();
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
    @POST
    @Path("subcheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String subcheck(@FormParam("param1") int stud, @FormParam("param2") int sub)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            LSmcq sSmcq= (LSmcq) session.createQuery("from LSmcq s where s.student.id=:id and s.teacher_labBatch.id=:id1").setParameter("id",stud).setParameter("id1",sub).getSingleResult();
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
        catch (NoResultException e)
        {
            transaction.commit();
            session.close();
            return "0";
        }
        catch (NonUniqueResultException e)
        {
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return "1";
        }
    }
    @POST
    @Path("mcqcheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String mcqcheck(@FormParam("param1") int sub)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Lmcq sSmcq= (Lmcq) session.createQuery("from Lmcq s where s.id=:id1").setParameter("id1",sub).getSingleResult();
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
        catch (NoResultException e)
        {
            transaction.commit();
            session.close();
            return "0";
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return "0";
        }
    }
}
