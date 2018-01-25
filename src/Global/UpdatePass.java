package Global;

import DB.Global;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Anil on 23/01/2018
 */
@Path("/UpdatePass")
@WebService
public class UpdatePass
{
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String updatepass(@FormParam("param1")int uid,@FormParam("param2")String opass,@FormParam("param3")String npass)
    {
        try
        {
            Session session= Global.getSession();
            Transaction transaction = session.beginTransaction();
            User user = session.load(User.class,uid);
            if (opass.equals(user.getPassword())) {
                user.setPassword(npass);
                session.persist(user);
                transaction.commit();
                session.close();
                return "1";
            }
            else {
                transaction.commit();
                session.close();
                return "0";
            }
        }
        catch (Exception e){
            return "E";
        }
    }
}