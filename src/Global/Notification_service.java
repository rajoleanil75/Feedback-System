package Global;

import DB.Global;
import DB.Notification;
import DB.Subject;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 25/02/2018
 */
@Path("/notification")
@WebService
public class Notification_service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String nme,@FormParam("param2")int uid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, uid);
            Notification notification = new Notification();
            notification.setDate(LocalDate.now());
            notification.setTime(LocalTime.now());
            notification.setName(nme);
            notification.setUser(user);
            session.persist(notification);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewAll")
    public List viewAll(@FormParam("param1")int uid)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            java.util.List<Notification> tlist = session.createQuery("from Notification s where s.user.uid=:id order by s.date desc ").setParameter("id", uid).setMaxResults(25).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                Notification subject = (Notification) iterator.next();
                List list1 = new ArrayList();
                list1.add(subject.getDate());
                list1.add(subject.getTime());
                list1.add(subject.getName());
                list.add(list1);
            }
            t.commit();
            session.close();
//            return "111";
             return list;

        }
        catch (Exception e)
        {
            return Collections.singletonList(e);
//            return String.valueOf(e);
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add1")
    public String add1(@FormParam("param1")String nme,@FormParam("param2")int uid)
    {
        try {
            Session session = Global.getSession1();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, uid);
            Notification notification = new Notification();
            notification.setDate(LocalDate.now());
            notification.setTime(LocalTime.now());
            notification.setName(nme);
            notification.setUser(user);
            session.persist(notification);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewAll1")
    public List viewAll1(@FormParam("param1")int uid)
    {
        try {
            Session session = DB.Global.getSession1();
            Transaction t = session.beginTransaction();
            java.util.List<Notification> tlist = session.createQuery("from Notification s where s.user.uid=:id order by s.date desc ").setParameter("id", uid).setMaxResults(25).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                Notification subject = (Notification) iterator.next();
                List list1 = new ArrayList();
                list1.add(subject.getDate());
                list1.add(subject.getTime());
                list1.add(subject.getName());
                list.add(list1);
            }
            t.commit();
            session.close();
//            return "111";
            return list;

        }
        catch (Exception e)
        {
            return Collections.singletonList(e);
//            return String.valueOf(e);
        }
    }
}
