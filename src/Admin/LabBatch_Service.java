package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 13/02/2018
 */
@Path("/labbatch_services")
@WebService
public class LabBatch_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") String lname, @FormParam("param2") int from, @FormParam("param3") int to, @FormParam("param4") int cid, @FormParam("param5") int did)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction transaction = session.beginTransaction();
            CSClass csClass = (CSClass) session.createQuery("from CSClass c where c.id= :id").setParameter("id", cid).uniqueResult();
            Division division =(Division) session.createQuery("from Division c where c.id= :id").setParameter("id", did).uniqueResult();
            LabBatch labBatch=new LabBatch();
            labBatch.setName(lname);
            labBatch.setFrom(from);
            labBatch.setTo(to);
            labBatch.setCSClass(csClass);
            labBatch.setDivision(division);
            session.persist(labBatch);
            int i=labBatch.getId();
            transaction.commit();
            session.close();
            return ""+i+"";
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @POST
    @Path("getlname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLName(@FormParam("param1") int cid)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            LabBatch subject = session.load(LabBatch.class, cid);
            String s=subject.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @POST
    @Path("getcoulname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCouLName(@FormParam("param1") int lid)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            LabBatch subject = session.load(LabBatch.class, lid);
            String s=subject.getCSClass().getCourse().getName()+", Class: "+subject.getCSClass().getName()+", Lab Name: "+subject.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addteacherlab")
    public String add(@FormParam("param1") int tname, @FormParam("param2") int lab)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction transaction = session.beginTransaction();
            Teacher teacher= (Teacher) session.createQuery("from Teacher c where c.id= :id").setParameter("id", tname).uniqueResult();
            LabBatch labBatch= (LabBatch) session.createQuery("from LabBatch c where c.id= :id").setParameter("id", lab).uniqueResult();
            Teacher_LabBatch teacher_labBatch=new Teacher_LabBatch();
            teacher_labBatch.setTeacher(teacher);
            teacher_labBatch.setLabBatch(labBatch);
            session.persist(teacher_labBatch);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @POST
    @Path("getClassWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassWise(@FormParam("param1") int cid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<LabBatch> tlist=session.createQuery("from LabBatch s where s.CSClass.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            LabBatch labBatch= (LabBatch) iterator.next();
            List list1=new ArrayList();
            list1.add(labBatch.getId());
            list1.add(labBatch.getName());
            list1.add(labBatch.getFrom());
            list1.add(labBatch.getTo());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getteacherlab")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacherLab(@FormParam("param1") int cid,@FormParam("param2")int tid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Teacher_LabBatch> tlist=session.createQuery("from Teacher_LabBatch s where s.labBatch.CSClass.course.id=:id and s.teacher.id=:id1").setParameter("id1",tid).setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Teacher_LabBatch teacher_labBatch= (Teacher_LabBatch) iterator.next();
            List list1=new ArrayList();
            list1.add(teacher_labBatch.getLabBatch().getId());
            list1.add(teacher_labBatch.getLabBatch().getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getteacher")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacher(@FormParam("param1") int lid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Teacher_LabBatch> tlist=session.createQuery("from Teacher_LabBatch s where s.labBatch.id=:id").setParameter("id",lid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Teacher_LabBatch teacher_labBatch= (Teacher_LabBatch) iterator.next();
            List list1=new ArrayList();
            list1.add(teacher_labBatch.getId());
            list1.add(teacher_labBatch.getTeacher().getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getTeacherWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacherWise(@FormParam("param1") int cid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Teacher_LabBatch> tlist=session.createQuery("from Teacher_LabBatch s where s.teacher.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Teacher_LabBatch teacher_labBatch= (Teacher_LabBatch) iterator.next();
            List list1=new ArrayList();
            list1.add(teacher_labBatch.getLabBatch().getId());
            list1.add(teacher_labBatch.getLabBatch().getName());
            list1.add(teacher_labBatch.getLabBatch().getFrom());
            list1.add(teacher_labBatch.getLabBatch().getTo());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") int tid)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction t=session.beginTransaction();
//            LabBatch labBatch= session.get(LabBatch.class,tid);
//            if (labBatch==null) {
//
//                return "0";
//            }
//            else
//            {

                Query query=session.createQuery("delete from LSmcq s where s.teacher_labBatch.id=:id").setParameter("id",tid);
                query.executeUpdate();
                Query query4=session.createQuery("delete from LSrate s where s.teacher_labBatch.id=:id").setParameter("id",tid);
                query4.executeUpdate();
                Query query5=session.createQuery("delete from LScomment s where s.teacher_labBatch.id=:id").setParameter("id",tid);
                query5.executeUpdate();
                Query query8=session.createQuery("delete from LabBatch s where s.id=:id").setParameter("id",tid);
                query8.executeUpdate();
//                session.delete(labBatch);
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
//            }
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return String.valueOf(e);
        }
    }
}
