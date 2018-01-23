package Global;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import DB.User;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.Iterator;

/**
 * Created by ANIL on 17/01/2018.
 */
@Path("/Login")
@WebService
public class Login {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login_check")
    public String login_check(@FormParam("param1") String uname,@FormParam("param2")String pass,@FormParam("param3")int role)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction t=session.beginTransaction();
            java.util.List ulist=session.createQuery("select l.uid,l.date from User l where l.name='"+uname+"' and l.password='"+pass+"' and role="+role+" ").list();
            t.commit();
            int count=0;
            for (Iterator iterator = ulist.iterator(); iterator.hasNext();)
            {
                Object o= iterator.next();
//
                //System.out.print(o);
                count++;
            }
            if (count==1)
            {
                return "1";
            }
            else
            {
                return "0";
            }
        }
        catch (Exception e)
        {
            return "Exception"+e;
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
}
