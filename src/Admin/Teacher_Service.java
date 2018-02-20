package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Created by ANIL on 19/01/2018.
 */
@Path("/teacher_services")
@WebService
public class Teacher_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") String tid, @FormParam("param2")String tname)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction t=session.beginTransaction();
            DB.Teacher teacher=new DB.Teacher();
            teacher.setId(tid);
            teacher.setName(tname);
            session.save(teacher);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return ""+e+"";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") String tid) {
        try {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
//        Teacher teacher = session.load(Teacher.class, tid);
//      session.delete(teacher);
        Serializable id = tid;
//      String i=teacher.getId();
        Object o = session.load(Teacher.class, id);
        if (o != null)
            session.delete(o);
        else if (o == null) {
            t.commit();
            session.close();
            return "0";
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
    @POST
    @Path("gettname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTName(@FormParam("param1") String cid)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            Teacher teacher = session.load(Teacher.class, cid);
            String s=teacher.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Teacher> tlist=session.createQuery("from Teacher").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List  search(@FormParam("param1") String tname)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<Teacher> tlist=session.createQuery("from Teacher s where s.name like :id").setParameter("id","%"+tname+"%").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                Teacher teacher= (Teacher) iterator.next();
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
                list.add(list1);
            }
//            Teacher teacher = session.load(Teacher.class, tid);
//            JSONObject json=new JSONObject();
//            if(teacher==null) {
//                session.close();
//                return "0";
//            }
//            json.put("tid",teacher.getId());
//            json.put("name",teacher.getName());
            transaction.commit();
            session.close();
            return list;
//            return json.toString();
        }
        catch (Exception e)
        {
            return null;
        }

    }
}