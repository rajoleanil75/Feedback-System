package Admin;

import DB.Global;
import DB.Smcq;
import DB.Teacher;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 16/02/2018
 */
@Path("/user_services")
@WebService
public class User_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("unblock")
    public String unblock(@FormParam("param1") int uid)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction transaction=session.beginTransaction();
            User user = session.load(User.class,uid);
            String ps=user.getName();
            user.setPassword(ps);
            user.setLcount(0);
            session.persist(user);
            transaction.commit();
            session.close();
            return "0";
        }
        catch (Exception e)
        {
            return "1";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("block")
    public String block(@FormParam("param1") String uid)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction transaction=session.beginTransaction();
            User user = (User) session.createQuery("from User s where s.name=:id").setParameter("id",uid).uniqueResult();
            user.setLcount(3);
            session.persist(user);
            transaction.commit();
            session.close();
            return "0";
        }
        catch (Exception e)
        {
            return "1";
        }
    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<User> tlist=session.createQuery("from User s where s.lcount=:id").setParameter("id",3).list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                User user= (User) iterator.next();
                List list1=new ArrayList();
                list1.add(user.getUid());
                list1.add(user.getName());
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return null;
        }

    }
    @POST
    @Path("newusercheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String mcqcheck(@FormParam("param1") String sub)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            User sSmcq= (User) session.createQuery("from User s where s.id=:id1").setParameter("id1",sub).getSingleResult();
//            if (sSmcq==null) {
//                transaction.commit();
//                session.close();
//                return "0";
//            }
//            else {
                transaction.commit();
                session.close();
                return "1";
//            }
        }
        catch (NoResultException e)
        {
            try {
                Session session1 = Global.getSession();
                Transaction transaction1 = session1.beginTransaction();
                Teacher sSmcq = (Teacher) session1.createQuery("from Teacher s where s.id=:id1").setParameter("id1", sub).getSingleResult();
                transaction1.commit();
                session1.close();
                return "0";
            }
            catch (NoResultException e1)
            {

                return "1";
            }
        }
        catch (Exception e)
        {
            return "1";
        }
    }
}
