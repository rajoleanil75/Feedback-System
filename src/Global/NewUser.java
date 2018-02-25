package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Anil on 13/02/2018
 */
@Path("/newuser")
@WebService
public class NewUser {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("getId")
    public String add(@FormParam("param1")int roll, @FormParam("param2") int div)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.id=:id1").setParameter("id",roll).setParameter("id1",div).uniqueResult();
        if(student==null)
            return "-1";
        String s= String.valueOf(student.getId());
        t.commit();
        session.close();
        return s;
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String name, @FormParam("param2") String pass, @FormParam("param3")String sid)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setId(sid);
            user.setRole(1);
            user.setLcount(0);
            user.setDate(LocalDate.now());
            user.setTime(LocalTime.now());
            session.persist(user);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add1")
    public String add1(@FormParam("param1")String name, @FormParam("param2") String pass, @FormParam("param3")int role)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setId(name);
            user.setRole(role);
            user.setLcount(0);
            user.setDate(LocalDate.now());
            user.setTime(LocalTime.now());
            session.persist(user);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "E";
        }
    }
}
