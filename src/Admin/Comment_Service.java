package Admin;

import DB.*;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.model.process.internal.ScanningCoordinator;

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
@Path("/comment_services")
@WebService
public class Comment_Service {
    @POST
    @Path("adds")
    @Produces(MediaType.TEXT_PLAIN)
    public String addS(@FormParam("param1") int sid, @FormParam("param2") String q)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

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
            transaction.commit();
            session.close();
            return "0";
        }
    }
    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@FormParam("param1") String ans,@FormParam("param2") int stud, @FormParam("param3") String sub, @FormParam("param4") int sm)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Student student= (Student) session.createQuery("from Student s where s.id=:sid ").setParameter("sid",stud).uniqueResult();
            Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",sub).uniqueResult();
            Scomment smcq= (Scomment) session.createQuery("from Scomment s where s.id=:id").setParameter("id",sm).uniqueResult();
            SScomment ssmcq = new SScomment();
            ssmcq.setAns(ans);
            ssmcq.setStudent(student);
            ssmcq.setSubject(subject);
            ssmcq.setScomment(smcq);
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
    public String subcheck(@FormParam("param1") int stud, @FormParam("param2") String sub)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            SScomment sSmcq= (SScomment) session.createQuery("from SScomment s where s.student.id=:id and s.subject.id=:id1").setParameter("id",stud).setParameter("id1",sub).getSingleResult();
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
    @Path("subcheck1")
    @Produces(MediaType.TEXT_PLAIN)
    public String subcheck1(@FormParam("param1") String sub)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            SScomment sSmcq= (SScomment) session.createQuery("from SScomment s where s.subject.id=:id1").setParameter("id1",sub).getSingleResult();
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
    @Path("searchsscomm")
    @Produces(MediaType.TEXT_PLAIN)
    public String searchSScomm(@FormParam("param1") int stud, @FormParam("param2") String sub, @FormParam("param3") int sm)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            SScomment sScomment= (SScomment) session.createQuery("from SScomment s where s.subject.id=:id and s.student.id=:id1 and s.scomment.id=:id2").setParameter("id",sub).setParameter("id1",stud).setParameter("id2",sm).uniqueResult();
            String i=sScomment.getAns();
            transaction.commit();
            session.close();
            return i;
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
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Scomment> tlist=session.createQuery("from Scomment s where s.squestion.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Scomment scomment= (Scomment) iterator.next();
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
