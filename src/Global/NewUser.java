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
    public String getId(@FormParam("param1")int roll, @FormParam("param2") int div)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.id=:id1").setParameter("id",roll).setParameter("id1",div).uniqueResult();
        if(student==null)
            return "-1";
        String s1=student.getCSClass().getSf();

        String s= s1+""+String.valueOf(student.getRoll());
        t.commit();
        session.close();
        return s;
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("getStudId")
    public String getStudId(@FormParam("param1")int roll, @FormParam("param2") int div)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.id=:id1").setParameter("id",roll).setParameter("id1",div).uniqueResult();
        if(student==null)
            return "-1";
//        String s1=student.getCSClass().getSf();
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
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

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
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addteach")
    public String addteach(@FormParam("param1")String name, @FormParam("param2") String pass, @FormParam("param3")int tid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setId(name);
            user.setRole(2);
            user.setLcount(0);
            user.setDate(LocalDate.now());
            user.setTime(LocalTime.now());
            session.persist(user);
            Teacher teacher = session.load(Teacher.class, tid);
            teacher.setRole(1);
            session.persist(teacher);
            t.commit();
            session.close();
            return "1";
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
    @Path("add1")
    public String add1(@FormParam("param1")String name, @FormParam("param2") String pass, @FormParam("param3")int role)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

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
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add_hod")
    public String add_hod(@FormParam("param1")String name, @FormParam("param2") String pass)
    {
        Session session = Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setId("3");
            user.setRole(3);
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
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add_admin")
    public String add_admin(@FormParam("param1")String name, @FormParam("param2") String pass)
    {
        Session session = Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setId("4");
            user.setRole(4);
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
            t.commit();
            session.close();
            return "E";
        }
    }

}
