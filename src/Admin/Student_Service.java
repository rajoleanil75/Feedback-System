package Admin;

import DB.CSClass;
import DB.Division;
import DB.Global;
import DB.Student;
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
}
