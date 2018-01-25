package Admin;

import DB.Global;
import DB.Teacher;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
    @Produces(MediaType.TEXT_PLAIN)
    public String  search(@FormParam("param1") String tid)
    {
        try {
            Session session = Global.getSession();
            Teacher teacher = session.load(Teacher.class, tid);
            JSONObject json=new JSONObject();
            if(teacher==null) {
                session.close();
                return "0";
            }
            json.put("tid",teacher.getId());
            json.put("name",teacher.getName());
            session.close();
            return json.toString();
        }
        catch (Exception e)
        {
            return "E";
        }
    }
}