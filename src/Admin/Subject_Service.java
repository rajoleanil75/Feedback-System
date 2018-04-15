package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Anil on 31/01/2018
 */
@Path("/subject_services")
@WebService
public class Subject_Service
{
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") String scode, @FormParam("param2") String sname, @FormParam("param3")int tid, @FormParam("param4") int cid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            CSClass csClass = (CSClass) session.createQuery("from CSClass c where c.id= :id").setParameter("id", cid).uniqueResult();
            Teacher teacher = (Teacher) session.createQuery("from Teacher t where t.id=:id").setParameter("id", tid).uniqueResult();
            Subject subject = new Subject();
            subject.setId(scode);
            subject.setName(sname);
            subject.setCSClass(csClass);
            subject.setTeacher(teacher);
            session.persist(subject);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return "E";
        }
    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Subject> tlist=session.createQuery("from Subject").list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list1.add(subject.getCSClass().getCourse().getId());
            list1.add(subject.getCSClass().getId());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("viewAllFeedbackWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAllFeedbackWise(@FormParam("param1")int sid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        Student student=session.load(Student.class,sid);
        java.util.List<Subject> tlist=session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id",student.getCSClass().getId()).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List<SSmcq> sSmcq=  session.createQuery("from SSmcq s where s.student.id=:id and s.subject.id=:id1").setParameter("id",sid).setParameter("id1",subject.getId()).list();
            List<SSrate> sSrate=  session.createQuery("from SSrate s where s.student.id=:id and s.subject.id=:id1").setParameter("id",sid).setParameter("id1",subject.getId()).list();
            List<SScomment> sScomments= session.createQuery("from SScomment s where s.student.id=:id and s.subject.id=:id1").setParameter("id",sid).setParameter("id1",subject.getId()).list();
            int flag=0;
            Iterator iterator5=sSmcq.iterator();
            if(iterator5.hasNext())
                flag=1;
            Iterator iterator6=sSrate.iterator();
            if(iterator6.hasNext())
                flag=1;
            Iterator iterator7=sScomments.iterator();
            if(iterator7.hasNext())
                flag=1;

            if(flag==1)
                continue;
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list1.add(subject.getCSClass().getCourse().getId());
            list1.add(subject.getCSClass().getId());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getCourseWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCourseWise(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Subject> tlist=session.createQuery("from Subject s where s.CSClass.course.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getCourseTeachWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCourseTeachWise(@FormParam("param1") int cid,@FormParam("param2") int tid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Subject> tlist=session.createQuery("from Subject s where s.CSClass.course.id=:id and s.teacher.id=:id1").setParameter("id",cid).setParameter("id1",tid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getClassWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassWise(@FormParam("param1") int cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            java.util.List<Subject> tlist = session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id", cid).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                Subject subject = (Subject) iterator.next();
                List list1 = new ArrayList();
                list1.add(subject.getId());
                list1.add(subject.getName());
                list.add(list1);
            }
            t.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return (List) e;
        }
    }

    @POST
    @Path("getTeacherWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacherWise(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Subject> tlist=session.createQuery("from Subject s where s.teacher.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getsname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSName(@FormParam("param1") String cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            Subject subject = session.load(Subject.class, cid);
            String s=subject.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Path("getcousname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCouSName(@FormParam("param1") String cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            Subject subject = session.load(Subject.class, cid);
            String s=subject.getCSClass().getCourse().getName()+", Class: "+subject.getCSClass().getName()+", Subject: "+  subject.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") String tid)
    {
//        try
//        {
            Session session= Global.getSession();
            Transaction t=session.beginTransaction();
            Subject subject= session.get(Subject.class,tid);
            if (subject==null) {

                return "0";
            }
            else
            {
                Query query=session.createQuery("delete from SSmcq s where s.subject.id=:id").setParameter("id",tid);
                query.executeUpdate();
                Query query4=session.createQuery("delete from SSrate s where s.subject.id=:id").setParameter("id",tid);
                query4.executeUpdate();
                Query query5=session.createQuery("delete from SScomment s where s.subject.id=:id").setParameter("id",tid);
                query5.executeUpdate();
                Query query8=session.createQuery("delete from Subject s where s.id=:id").setParameter("id",subject.getId());
                query8.executeUpdate();


//                session.delete(subject);
//                try {
//                    String i=teacher.getId();
//                    Object o = session.load(Teacher.class, i);
//                    if (o != null)
//                        session.delete(o);
//                    else if (o==null) {
//                        t.commit();
//                        session.close();
//                        return "0";
//                    }
//                }
//                catch (Exception e)
//                {
//                    return String.valueOf(e)+"Innter";
//                }
            }
            t.commit();
            session.close();
            return "1";
//        }
//        catch (Exception e)
//        {
//            return String.valueOf(e);
//        }
    }
}