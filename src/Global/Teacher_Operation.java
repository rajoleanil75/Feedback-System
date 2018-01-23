package Global;

import DB.Teacher;
import Entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ANIL on 19/01/2018.
 */
@Path("/teacher_operation")
@WebService
public class Teacher_Operation {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") int tid, @FormParam("param2")String tname)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction t=session.beginTransaction();
            Teacher teacher=new Teacher();
            teacher.setId(tid);
            teacher.setName(tname);
            session.save(teacher);
            t.commit();
            return "1";
        }
        catch (Exception e)
        {
            return "0";
        }
      /*  String message;       //create json object and send it to coller
        JSONObject json = new JSONObject();
        json.put("name", "student");
        JSONObject item = new JSONObject();
        item.put("information", "test");
        item.put("id", 3);
        item.put("name", "course1");
        message = item.toString();
        return "Other";*/
    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List tlist=session.createQuery("from Teacher").list();
        t.commit();
        JSONObject result = new JSONObject();
        result.put("id", "name");
//        int count=0;

        List result1=new ArrayList();
//        result.add(new Employee(1,"Anil"));
        int i=0;
        for (Iterator iterator = tlist.iterator(); iterator.hasNext();i++)
        {
            Object o= iterator.next();  //class caste exception
            Teacher teacher=(Teacher)o;
            result.put("id",teacher.getId());
            result.put("name",teacher.getName());

            result1.add(i,teacher.getName());

            //result.put(teacher.getId(),teacher.getName());
//                 DB.Global.uid= (int) o.uid;
            //DB.Global.role="admin";
            //System.out.print(o);
//            count++;
        }


        String s;
        s=result.toString();
        return result1;
    }
}
