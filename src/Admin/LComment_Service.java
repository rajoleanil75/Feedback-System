package Admin;

import DB.*;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.model.process.internal.ScanningCoordinator;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.time.LocalDate.now;

/**
 * Created by Anil on 08/02/2018
 */
@Path("/lcomment_services")
@WebService
public class LComment_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid, @FormParam("param2") String q)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Lquestion c= (Lquestion) session.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
            Lcomment scomment=new Lcomment();
            scomment.setName(q);
            scomment.setLquestion(c);
            session.save(scomment);
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
            Lcomment smcq= (Lcomment) session.createQuery("from Lcomment s where s.id=:id").setParameter("id",sm).uniqueResult();
            LScomment ssmcq = new LScomment();
            ssmcq.setAns(ans);
            ssmcq.setStudent(student);
            ssmcq.setTeacher_labBatch(subject);
            ssmcq.setLcomment(smcq);
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
    @Path("subcheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String subcheck(@FormParam("param1") int stud, @FormParam("param2") int sub)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            LScomment sSmcq= (LScomment) session.createQuery("from LScomment s where s.student.id=:id and s.teacher_labBatch.id=:id1").setParameter("id",stud).setParameter("id1",sub).getSingleResult();
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
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll(@FormParam("param1")int sid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Lcomment> tlist=session.createQuery("from Lcomment s where s.lquestion.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Lcomment scomment= (Lcomment) iterator.next();
            List list1=new ArrayList();
            list1.add(scomment.getId());
            list1.add(scomment.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
}
