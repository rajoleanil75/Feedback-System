package Global;

import DB.Global;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import DB.User;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.time.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
            User user= (User) session.createQuery("from User l where l.name='"+uname+"' and role="+role+" ").uniqueResult();
            t.commit();
            session.close();
            if (user==null) {
                return "0";
            }
            else if (user.getLcount()==-3)
                return "B";
            else
            {
                if(pass.equals(user.getPassword()))
                {
                    String message;
                    LocalTime lt=user.getTime();
                    Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                    Date time = Date.from(instant);

                    JSONObject json = new JSONObject();
                    json.put("uid", user.getUid());
                    json.put("uname", user.getName());
                    json.put("id", user.getId());
                    json.put("role", user.getRole());
                    json.put("llogin", time.toString());
                    message = json.toString();
                    Session session1= Global.getSession();
                    Transaction transaction = session1.beginTransaction();
                    User user1 = session1.load(User.class, user.getUid());
                    user1.setDate(LocalDate.now());
                    user1.setTime(LocalTime.now());
                    user1.setLcount(0);
                    session1.persist(user1);
                    transaction.commit();
                    session1.close();
                    return message;
                }
                else
                {
                    Session session2= Global.getSession();
                    Transaction transaction1 = session2.beginTransaction();
                    User user2 = session2.load(User.class, user.getUid());
                    int l=user2.getLcount();
                    user2.setLcount(l-1);
                    session2.persist(user2);
                    transaction1.commit();
                    session2.close();
                    return ""+(l-1)+"";
                }
            }
        }
        catch (Exception e)
        {
            return "E";
        }
    }
}