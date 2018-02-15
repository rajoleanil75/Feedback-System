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
    @POST
    @Path("subcheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String subcheck(@FormParam("param1") int stud, @FormParam("param2") int sub)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            LSrate sSmcq= (LSrate) session.createQuery("from LSrate s where s.student.id=:id and s.teacher_labBatch.id=:id1").setParameter("id",stud).setParameter("id1",sub).getSingleResult();
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
            return "0";
        }
        catch (NonUniqueResultException e)
        {
            return "1";
        }
        catch (Exception e)
        {
            return "1";
        }
    }
    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@FormParam("param1") int ans,@FormParam("param2") int stud, @FormParam("param3") int sub, @FormParam("param4") int sm)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Student student= (Student) session.createQuery("from Student s where s.id=:sid ").setParameter("sid",stud).uniqueResult();
            Teacher_LabBatch subject= (Teacher_LabBatch) session.createQuery("from Teacher_LabBatch s where s.id=:id").setParameter("id",sub).uniqueResult();
            Lrate srate= (Lrate) session.createQuery("from Lrate s where s.id=:id").setParameter("id",sm).uniqueResult();
            LSrate ssmcq=new LSrate();
            ssmcq.setAns(ans);
            ssmcq.setStudent(student);
            ssmcq.setTeacher_labBatch(subject);
            ssmcq.setLrate(srate);
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
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll(@FormParam("param1")int sid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Lrate> tlist=session.createQuery("from Lrate s where s.lquestion.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Lrate srate= (Lrate) iterator.next();
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
