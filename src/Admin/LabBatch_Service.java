package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addteacherlab")
    public String add(@FormParam("param1") String tname, @FormParam("param2") int lab)
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
    @Path("getTeacherWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacherWise(@FormParam("param1") String cid)
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
    public String delete(@FormParam("param1") String tid)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction t=session.beginTransaction();
            LabBatch labBatch= session.load(LabBatch.class,tid);
            if (labBatch==null) {

                return "0";
            }
            else
            {
                session.delete(labBatch);
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
        }
        catch (Exception e)
        {
            return String.valueOf(e);
        }
    }
}
