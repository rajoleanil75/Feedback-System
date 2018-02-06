package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anil on 29/01/2018
 */
@Path("/student_services")
@WebService
public class Student_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String studjson,@FormParam("param2") int cl, @FormParam("param3") int dv)
    {
        try
        {
            Object object=null;
            JSONArray arrayObj=null;
            JSONParser jsonParser=new JSONParser();
            object=jsonParser.parse(studjson);
            arrayObj=(JSONArray) object;

            Session session= Global.getSession();
            Transaction transaction=session.beginTransaction();
            CSClass csClass= (CSClass) session.createQuery("from CSClass c where c.id= :id").setParameter("id",cl).uniqueResult();
            Division division= (Division) session.createQuery("from Division d where d.id=:id").setParameter("id",dv).uniqueResult();

            for (int i = 0; i < arrayObj.size(); i++)
            {
                JSONObject jsonObj = (JSONObject) arrayObj.get(i);
                if(jsonObj!=null)
                {
                    Student s=new Student();
                    String nme= (String) jsonObj.get("roll");
                    s.setRoll(Integer.parseInt(nme));
                    s.setName((String) jsonObj.get("name"));
                    s.setCSClass(csClass);
                    s.setDivision(division);
                    session.persist(s);

                }
            }
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return ""+e+"";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewDivisionWise")
    public List viewDivisionWise(@FormParam("param1")int div)
    {
        try
        {
            Session session1= DB.Global.getSession();
            Transaction t=session1.beginTransaction();
            java.util.List<Student> slist=session1.createQuery("select s.id,s.name,s.roll,s.CSClass,s.division from Student s where s.division.id=:id").setParameter("id",div).list();
            t.commit();
            session1.close();
            return slist;
        }
        catch (Exception e)
        {
            return Collections.singletonList("" + e + "");
        }
    }

    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Student> slist=session.createQuery("select s.id,s.name,s.roll,s.CSClass,s.division from Student s").list();
        t.commit();
        session.close();
        return slist;
    }
}