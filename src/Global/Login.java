package Global;

import DB.Backup;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
//            switch (role) {
//                case 3:
//                    Session session = DB.Global.getSession1();
//                    Transaction t = session.beginTransaction();
//                    User user = (User) session.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
//                    t.commit();
//                    session.close();
//                    if (user == null) {
//                        return "0";
//                    } else if (user.getLcount() == -3)
//                        return "B";
//                    else {
//                        if (pass.equals(user.getPassword())) {
//                            String message;
//                            LocalTime lt = user.getTime();
//                            Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
//                            Date time = Date.from(instant);
//
//                            JSONObject json = new JSONObject();
//                            json.put("uid", user.getUid());
//                            json.put("uname", user.getName());
//                            json.put("id", user.getId());
//                            json.put("role", user.getRole());
//                            json.put("llogin", time.toString());
//                            message = json.toString();
//                            Session session1 = Global.getSession1();
//                            Transaction transaction = session1.beginTransaction();
//                            User user1 = session1.load(User.class, user.getUid());
//                            user1.setDate(LocalDate.now());
//                            user1.setTime(LocalTime.now());
//                            user1.setLcount(0);
//                            session1.persist(user1);
//                            transaction.commit();
//                            session1.close();
//
//                            Session session2=Global.getSession1();
//                            Transaction transaction1=session2.beginTransaction();
//                            Backup backup= (Backup) session2.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
//                            String dbname=backup.getDname();
//                            transaction1.commit();
//                            session2.close();
//                            BufferedWriter out = null;
//                            try
//                            {
//                                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
//                                out = new BufferedWriter(fstream);
////                                String dbnme="temp1";
//                                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
//                                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
//                                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
//                                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
//                                        "<hibernate-configuration>\n" +
//                                        "    <session-factory>\n" +
//                                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
//                                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
//                                        "        <property name=\"connection.username\">postgres</property>\n" +
//                                        "        <property name=\"connection.password\">phd</property>\n" +
//                                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
//                                        "        <property name=\"show_sql\">true</property>\n" +
//                                        "        <property name=\"connection.pool_size\">10000</property>\n" +
//                                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
//                                        "\n" +
//                                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
//                                        "    </session-factory>\n" +
//                                        "</hibernate-configuration>");
//                                out.close();
//                                Global.reload();
//                            }
//                            catch (IOException e)
//                            {
//                                System.err.println("Error: " + e.getMessage());
//                                return "E";
//                            }
//
//                            return message;
//                        } else {
//                            Session session2 = Global.getSession1();
//                            Transaction transaction1 = session2.beginTransaction();
//                            User user2 = session2.load(User.class, user.getUid());
//                            int l = user2.getLcount();
//                            user2.setLcount(l - 1);
//                            session2.persist(user2);
//                            transaction1.commit();
//                            session2.close();
//                            return "" + (l - 1) + "";
//                        }
//                    }
////                break;
//                case 4:
//                    Session session5 = DB.Global.getSession1();
//                    Transaction t5 = session5.beginTransaction();
//                    User user5 = (User) session5.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
//                    t5.commit();
//                    session5.close();
//                    if (user5 == null) {
//                        return "0";
//                    } else if (user5.getLcount() == -3)
//                        return "B";
//                    else {
//                        if (pass.equals(user5.getPassword())) {
//                            String message;
//                            LocalTime lt = user5.getTime();
//                            Instant instant = lt.atDate(user5.getDate()).atZone(ZoneId.systemDefault()).toInstant();
//                            Date time = Date.from(instant);
//
//                            JSONObject json = new JSONObject();
//                            json.put("uid", user5.getUid());
//                            json.put("uname", user5.getName());
//                            json.put("id", user5.getId());
//                            json.put("role", user5.getRole());
//                            json.put("llogin", time.toString());
//                            message = json.toString();
//                            Session session6 = Global.getSession1();
//                            Transaction transaction6 = session6.beginTransaction();
//                            User user1 = session6.load(User.class, user5.getUid());
//                            user1.setDate(LocalDate.now());
//                            user1.setTime(LocalTime.now());
//                            user1.setLcount(0);
//                            session6.persist(user1);
//                            transaction6.commit();
//                            session6.close();
//
//                            Session session7=Global.getSession1();
//                            Transaction transaction7=session7.beginTransaction();
//                            Backup backup= (Backup) session7.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
//                            String dbname=backup.getDname();
//                            transaction7.commit();
//                            session7.close();
//                            BufferedWriter out = null;
//                            try
//                            {
//                                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
//                                out = new BufferedWriter(fstream);
////                                String dbnme="temp1";
//                                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
//                                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
//                                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
//                                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
//                                        "<hibernate-configuration>\n" +
//                                        "    <session-factory>\n" +
//                                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
//                                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
//                                        "        <property name=\"connection.username\">postgres</property>\n" +
//                                        "        <property name=\"connection.password\">phd</property>\n" +
//                                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
//                                        "        <property name=\"show_sql\">true</property>\n" +
//                                        "        <property name=\"connection.pool_size\">10000</property>\n" +
//                                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
//                                        "\n" +
//                                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
//                                        "    </session-factory>\n" +
//                                        "</hibernate-configuration>");
//                                out.close();
//                                Global.reload();
//                            }
//                            catch (IOException e)
//                            {
//                                System.err.println("Error: " + e.getMessage());
//                                return "E";
//                            }
//
//                            return message;
//                        } else {
//                            Session session8 = Global.getSession1();
//                            Transaction transaction8 = session8.beginTransaction();
//                            User user2 = session8.load(User.class, user5.getUid());
//                            int l = user2.getLcount();
//                            user2.setLcount(l - 1);
//                            session8.persist(user2);
//                            transaction8.commit();
//                            session8.close();
//                            return "" + (l - 1) + "";
//                        }
//                    }
////                break;
//                default:
                    Session session7=Global.getSession1();
                    Transaction transaction7=session7.beginTransaction();
                    Backup backup= (Backup) session7.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
                    String dbname=backup.getDname();
                    transaction7.commit();
                    session7.close();
                    BufferedWriter out = null;
                    try
                    {
                        FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
                        out = new BufferedWriter(fstream);
//                                String dbnme="temp1";
                        out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                                "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                                "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                                "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                                "<hibernate-configuration>\n" +
                                "    <session-factory>\n" +
                                "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                                "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                                "        <property name=\"connection.username\">postgres</property>\n" +
                                "        <property name=\"connection.password\">phd</property>\n" +
                                "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                                "        <property name=\"show_sql\">true</property>\n" +
                                "        <property name=\"connection.pool_size\">10000</property>\n" +
                                "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                                "\n" +
                                "        <mapping resource=\"DB/sql.xml\"/>\n" +
                                "    </session-factory>\n" +
                                "</hibernate-configuration>");
                        out.close();
                        Global.reload();
                    }
                    catch (IOException e)
                    {
                        System.err.println("Error: " + e.getMessage());
                        return "E";
                    }

                    Session session15 = DB.Global.getSession();
                    Transaction t15 = session15.beginTransaction();
                    User user15 = (User) session15.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
                    t15.commit();
                    session15.close();
                    if (user15 == null) {
                        return "0";
                    } else if (user15.getLcount() == -3)
                        return "B";
                    else {
                        if (pass.equals(user15.getPassword())) {
                            String message;
                            LocalTime lt = user15.getTime();
                            Instant instant = lt.atDate(user15.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                            Date time = Date.from(instant);

                            JSONObject json = new JSONObject();
                            json.put("uid", user15.getUid());
                            json.put("uname", user15.getName());
                            json.put("id", user15.getId());
                            json.put("role", user15.getRole());
                            json.put("llogin", time.toString());
                            message = json.toString();
                            Session session16 = Global.getSession();
                            Transaction transaction16 = session16.beginTransaction();
                            User user1 = session16.load(User.class, user15.getUid());
                            user1.setDate(LocalDate.now());
                            user1.setTime(LocalTime.now());
                            user1.setLcount(0);
                            session16.persist(user1);
                            transaction16.commit();
                            session16.close();
                            return message;
                        } else {
                            Session session18 = Global.getSession();
                            Transaction transaction18 = session18.beginTransaction();
                            User user2 = session18.load(User.class, user15.getUid());
                            int l = user2.getLcount();
                            user2.setLcount(l - 1);
                            session18.persist(user2);
                            transaction18.commit();
                            session18.close();
                            return "" + (l - 1) + "";
                        }
                    }
//                break;
//            }
//            return "0";
        }
        catch (Exception e)
        {
            return "E";
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login_check_teacher")
    public String login_check_teacher(@FormParam("param1") String uname,@FormParam("param2")String pass)
    {
        try
        {
//            switch (role) {
//                case 3:
//                    Session session = DB.Global.getSession1();
//                    Transaction t = session.beginTransaction();
//                    User user = (User) session.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
//                    t.commit();
//                    session.close();
//                    if (user == null) {
//                        return "0";
//                    } else if (user.getLcount() == -3)
//                        return "B";
//                    else {
//                        if (pass.equals(user.getPassword())) {
//                            String message;
//                            LocalTime lt = user.getTime();
//                            Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
//                            Date time = Date.from(instant);
//
//                            JSONObject json = new JSONObject();
//                            json.put("uid", user.getUid());
//                            json.put("uname", user.getName());
//                            json.put("id", user.getId());
//                            json.put("role", user.getRole());
//                            json.put("llogin", time.toString());
//                            message = json.toString();
//                            Session session1 = Global.getSession1();
//                            Transaction transaction = session1.beginTransaction();
//                            User user1 = session1.load(User.class, user.getUid());
//                            user1.setDate(LocalDate.now());
//                            user1.setTime(LocalTime.now());
//                            user1.setLcount(0);
//                            session1.persist(user1);
//                            transaction.commit();
//                            session1.close();
//
//                            Session session2=Global.getSession1();
//                            Transaction transaction1=session2.beginTransaction();
//                            Backup backup= (Backup) session2.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
//                            String dbname=backup.getDname();
//                            transaction1.commit();
//                            session2.close();
//                            BufferedWriter out = null;
//                            try
//                            {
//                                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
//                                out = new BufferedWriter(fstream);
////                                String dbnme="temp1";
//                                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
//                                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
//                                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
//                                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
//                                        "<hibernate-configuration>\n" +
//                                        "    <session-factory>\n" +
//                                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
//                                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
//                                        "        <property name=\"connection.username\">postgres</property>\n" +
//                                        "        <property name=\"connection.password\">phd</property>\n" +
//                                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
//                                        "        <property name=\"show_sql\">true</property>\n" +
//                                        "        <property name=\"connection.pool_size\">10000</property>\n" +
//                                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
//                                        "\n" +
//                                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
//                                        "    </session-factory>\n" +
//                                        "</hibernate-configuration>");
//                                out.close();
//                                Global.reload();
//                            }
//                            catch (IOException e)
//                            {
//                                System.err.println("Error: " + e.getMessage());
//                                return "E";
//                            }
//
//                            return message;
//                        } else {
//                            Session session2 = Global.getSession1();
//                            Transaction transaction1 = session2.beginTransaction();
//                            User user2 = session2.load(User.class, user.getUid());
//                            int l = user2.getLcount();
//                            user2.setLcount(l - 1);
//                            session2.persist(user2);
//                            transaction1.commit();
//                            session2.close();
//                            return "" + (l - 1) + "";
//                        }
//                    }
////                break;
//                case 4:
//                    Session session5 = DB.Global.getSession1();
//                    Transaction t5 = session5.beginTransaction();
//                    User user5 = (User) session5.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
//                    t5.commit();
//                    session5.close();
//                    if (user5 == null) {
//                        return "0";
//                    } else if (user5.getLcount() == -3)
//                        return "B";
//                    else {
//                        if (pass.equals(user5.getPassword())) {
//                            String message;
//                            LocalTime lt = user5.getTime();
//                            Instant instant = lt.atDate(user5.getDate()).atZone(ZoneId.systemDefault()).toInstant();
//                            Date time = Date.from(instant);
//
//                            JSONObject json = new JSONObject();
//                            json.put("uid", user5.getUid());
//                            json.put("uname", user5.getName());
//                            json.put("id", user5.getId());
//                            json.put("role", user5.getRole());
//                            json.put("llogin", time.toString());
//                            message = json.toString();
//                            Session session6 = Global.getSession1();
//                            Transaction transaction6 = session6.beginTransaction();
//                            User user1 = session6.load(User.class, user5.getUid());
//                            user1.setDate(LocalDate.now());
//                            user1.setTime(LocalTime.now());
//                            user1.setLcount(0);
//                            session6.persist(user1);
//                            transaction6.commit();
//                            session6.close();
//
//                            Session session7=Global.getSession1();
//                            Transaction transaction7=session7.beginTransaction();
//                            Backup backup= (Backup) session7.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
//                            String dbname=backup.getDname();
//                            transaction7.commit();
//                            session7.close();
//                            BufferedWriter out = null;
//                            try
//                            {
//                                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
//                                out = new BufferedWriter(fstream);
////                                String dbnme="temp1";
//                                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
//                                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
//                                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
//                                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
//                                        "<hibernate-configuration>\n" +
//                                        "    <session-factory>\n" +
//                                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
//                                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
//                                        "        <property name=\"connection.username\">postgres</property>\n" +
//                                        "        <property name=\"connection.password\">phd</property>\n" +
//                                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
//                                        "        <property name=\"show_sql\">true</property>\n" +
//                                        "        <property name=\"connection.pool_size\">10000</property>\n" +
//                                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
//                                        "\n" +
//                                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
//                                        "    </session-factory>\n" +
//                                        "</hibernate-configuration>");
//                                out.close();
//                                Global.reload();
//                            }
//                            catch (IOException e)
//                            {
//                                System.err.println("Error: " + e.getMessage());
//                                return "E";
//                            }
//
//                            return message;
//                        } else {
//                            Session session8 = Global.getSession1();
//                            Transaction transaction8 = session8.beginTransaction();
//                            User user2 = session8.load(User.class, user5.getUid());
//                            int l = user2.getLcount();
//                            user2.setLcount(l - 1);
//                            session8.persist(user2);
//                            transaction8.commit();
//                            session8.close();
//                            return "" + (l - 1) + "";
//                        }
//                    }
////                break;
//                default:
//            Session session7=Global.getSession1();
//            Transaction transaction7=session7.beginTransaction();
//            Backup backup= (Backup) session7.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
//            String dbname=backup.getDname();
//            transaction7.commit();
//            session7.close();
//            BufferedWriter out = null;
//            try
//            {
//                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
//                out = new BufferedWriter(fstream);
////                                String dbnme="temp1";
//                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
//                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
//                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
//                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
//                        "<hibernate-configuration>\n" +
//                        "    <session-factory>\n" +
//                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
//                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
//                        "        <property name=\"connection.username\">postgres</property>\n" +
//                        "        <property name=\"connection.password\">phd</property>\n" +
//                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
//                        "        <property name=\"show_sql\">true</property>\n" +
//                        "        <property name=\"connection.pool_size\">10000</property>\n" +
//                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
//                        "\n" +
//                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
//                        "    </session-factory>\n" +
//                        "</hibernate-configuration>");
//                out.close();
//                Global.reload();
//            }
//            catch (IOException e)
//            {
//                System.err.println("Error: " + e.getMessage());
//                return "E";
//            }

            Session session15 = DB.Global.getSession();
            Transaction t15 = session15.beginTransaction();
            User user15 = (User) session15.createQuery("from User l where l.name='" + uname + "' and role=" + 2 + " ").uniqueResult();
            t15.commit();
            session15.close();
            if (user15 == null) {
                return "0";
            } else if (user15.getLcount() == -3)
                return "B";
            else {
                if (pass.equals(user15.getPassword())) {
                    String message;
                    LocalTime lt = user15.getTime();
                    Instant instant = lt.atDate(user15.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                    Date time = Date.from(instant);

                    JSONObject json = new JSONObject();
                    json.put("uid", user15.getUid());
                    json.put("uname", user15.getName());
                    json.put("id", user15.getId());
                    json.put("role", user15.getRole());
                    json.put("llogin", time.toString());
                    message = json.toString();
                    Session session16 = Global.getSession();
                    Transaction transaction16 = session16.beginTransaction();
                    User user1 = session16.load(User.class, user15.getUid());
                    user1.setDate(LocalDate.now());
                    user1.setTime(LocalTime.now());
                    user1.setLcount(0);
                    session16.persist(user1);
                    transaction16.commit();
                    session16.close();
                    return message;
                } else {
                    Session session18 = Global.getSession();
                    Transaction transaction18 = session18.beginTransaction();
                    User user2 = session18.load(User.class, user15.getUid());
                    int l = user2.getLcount();
                    user2.setLcount(l - 1);
                    session18.persist(user2);
                    transaction18.commit();
                    session18.close();
                    return "" + (l - 1) + "";
                }
            }
//                break;
//            }
//            return "0";
        }
        catch (Exception e)
        {
            return "E";
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login_check_admin")
    public String login_check_admin(@FormParam("param1") String uname,@FormParam("param2")String pass,@FormParam("param3")int role,@FormParam("param4")int bid)
    {
        try
        {
            switch (role) {
                case 3:
                    Session session = DB.Global.getSession1();
                    Transaction t = session.beginTransaction();
                    User user = (User) session.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
                    t.commit();
                    session.close();
                    if (user == null) {
                        return "0";
                    } else if (user.getLcount() == -3)
                        return "B";
                    else {
                        if (pass.equals(user.getPassword())) {
                            String message;
                            LocalTime lt = user.getTime();
                            Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                            Date time = Date.from(instant);

                            JSONObject json = new JSONObject();
                            json.put("uid", user.getUid());
                            json.put("uname", user.getName());
                            json.put("id", user.getId());
                            json.put("role", user.getRole());
                            json.put("llogin", time.toString());
                            message = json.toString();
                            Session session1 = Global.getSession1();
                            Transaction transaction = session1.beginTransaction();
                            User user1 = session1.load(User.class, user.getUid());
                            user1.setDate(LocalDate.now());
                            user1.setTime(LocalTime.now());
                            user1.setLcount(0);
                            session1.persist(user1);
                            transaction.commit();
                            session1.close();

                            Session session2=Global.getSession1();
                            Transaction transaction1=session2.beginTransaction();
                            Backup backup= (Backup) session2.createQuery("from Backup s where s.id=:id").setParameter("id",bid).uniqueResult();
                            String dbname=backup.getDname();
                            transaction1.commit();
                            session2.close();
                            BufferedWriter out = null;
                            try
                            {
                                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
                                out = new BufferedWriter(fstream);
//                                String dbnme="temp1";
                                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                                        "<hibernate-configuration>\n" +
                                        "    <session-factory>\n" +
                                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                                        "        <property name=\"connection.username\">postgres</property>\n" +
                                        "        <property name=\"connection.password\">phd</property>\n" +
                                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                                        "        <property name=\"show_sql\">true</property>\n" +
                                        "        <property name=\"connection.pool_size\">10000</property>\n" +
                                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                                        "\n" +
                                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
                                        "    </session-factory>\n" +
                                        "</hibernate-configuration>");
                                out.close();
                                Global.reload();

                            }
                            catch (IOException e)
                            {
                                System.err.println("Error: " + e.getMessage());
                                return "E";
                            }

                            return message;
                        } else {
                            Session session2 = Global.getSession1();
                            Transaction transaction1 = session2.beginTransaction();
                            User user2 = session2.load(User.class, user.getUid());
                            int l = user2.getLcount();
                            user2.setLcount(l - 1);
                            session2.persist(user2);
                            transaction1.commit();
                            session2.close();
                            return "" + (l - 1) + "";
                        }
                    }
//                break;
                case 4:
                    Session session5 = DB.Global.getSession1();
                    Transaction t5 = session5.beginTransaction();
                    User user5 = (User) session5.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
                    t5.commit();
                    session5.close();
                    if (user5 == null) {
                        return "0";
                    } else if (user5.getLcount() == -3)
                        return "B";
                    else {
                        if (pass.equals(user5.getPassword())) {
                            String message;
                            LocalTime lt = user5.getTime();
                            Instant instant = lt.atDate(user5.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                            Date time = Date.from(instant);

                            JSONObject json = new JSONObject();
                            json.put("uid", user5.getUid());
                            json.put("uname", user5.getName());
                            json.put("id", user5.getId());
                            json.put("role", user5.getRole());
                            json.put("llogin", time.toString());
                            message = json.toString();
                            Session session6 = Global.getSession1();
                            Transaction transaction6 = session6.beginTransaction();
                            User user1 = session6.load(User.class, user5.getUid());
                            user1.setDate(LocalDate.now());
                            user1.setTime(LocalTime.now());
                            user1.setLcount(0);
                            session6.persist(user1);
                            transaction6.commit();
                            session6.close();

                            Session session7=Global.getSession1();
                            Transaction transaction7=session7.beginTransaction();
                            Backup backup= (Backup) session7.createQuery("from Backup s where s.id=:id").setParameter("id",bid).uniqueResult();
                            String dbname=backup.getDname();
                            transaction7.commit();
                            session7.close();
                            BufferedWriter out = null;
                            try
                            {
                                FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
                                out = new BufferedWriter(fstream);
//                                String dbnme="temp1";
                                out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                                        "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                                        "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                                        "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                                        "<hibernate-configuration>\n" +
                                        "    <session-factory>\n" +
                                        "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                                        "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                                        "        <property name=\"connection.username\">postgres</property>\n" +
                                        "        <property name=\"connection.password\">phd</property>\n" +
                                        "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                                        "        <property name=\"show_sql\">true</property>\n" +
                                        "        <property name=\"connection.pool_size\">10000</property>\n" +
                                        "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                                        "\n" +
                                        "        <mapping resource=\"DB/sql.xml\"/>\n" +
                                        "    </session-factory>\n" +
                                        "</hibernate-configuration>");
                                out.close();
                                Global.reload();
                            }
                            catch (IOException e)
                            {
                                System.err.println("Error: " + e.getMessage());
                                return "E";
                            }

                            return message;
                        } else {
                            Session session8 = Global.getSession1();
                            Transaction transaction8 = session8.beginTransaction();
                            User user2 = session8.load(User.class, user5.getUid());
                            int l = user2.getLcount();
                            user2.setLcount(l - 1);
                            session8.persist(user2);
                            transaction8.commit();
                            session8.close();
                            return "" + (l - 1) + "";
                        }
                    }
//                break;
                default:
                    Session session7=Global.getSession1();
                    Transaction transaction7=session7.beginTransaction();
                    Backup backup= (Backup) session7.createQuery("from Backup s where s.id=:id").setParameter("id",bid).uniqueResult();
                    String dbname=backup.getDname();
                    transaction7.commit();
                    session7.close();
                    BufferedWriter out = null;
                    try
                    {
                        FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\Feedback System\\out\\artifacts\\Feedback_System_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false); //true tells to append data.
                        out = new BufferedWriter(fstream);
//                                String dbnme="temp1";
                        out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                                "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                                "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                                "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                                "<hibernate-configuration>\n" +
                                "    <session-factory>\n" +
                                "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                                "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                                "        <property name=\"connection.username\">postgres</property>\n" +
                                "        <property name=\"connection.password\">phd</property>\n" +
                                "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                                "        <property name=\"show_sql\">true</property>\n" +
                                "        <property name=\"connection.pool_size\">10000</property>\n" +
                                "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                                "\n" +
                                "        <mapping resource=\"DB/sql.xml\"/>\n" +
                                "    </session-factory>\n" +
                                "</hibernate-configuration>");
                        out.close();
                        Global.reload();
                    }
                    catch (IOException e)
                    {
                        System.err.println("Error: " + e.getMessage());
                        return "E";
                    }

                    Session session15 = DB.Global.getSession();
                    Transaction t15 = session15.beginTransaction();
                    User user15 = (User) session15.createQuery("from User l where l.name='" + uname + "' and role=" + role + " ").uniqueResult();
                    t15.commit();
                    session15.close();
                    if (user15 == null) {
                        return "0";
                    } else if (user15.getLcount() == -3)
                        return "B";
                    else {
                        if (pass.equals(user15.getPassword())) {
                            String message;
                            LocalTime lt = user15.getTime();
                            Instant instant = lt.atDate(user15.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                            Date time = Date.from(instant);

                            JSONObject json = new JSONObject();
                            json.put("uid", user15.getUid());
                            json.put("uname", user15.getName());
                            json.put("id", user15.getId());
                            json.put("role", user15.getRole());
                            json.put("llogin", time.toString());
                            message = json.toString();
                            Session session16 = Global.getSession();
                            Transaction transaction16 = session16.beginTransaction();
                            User user1 = session16.load(User.class, user15.getUid());
                            user1.setDate(LocalDate.now());
                            user1.setTime(LocalTime.now());
                            user1.setLcount(0);
                            session16.persist(user1);
                            transaction16.commit();
                            session16.close();
                            return message;
                        } else {
                            Session session18 = Global.getSession();
                            Transaction transaction18 = session18.beginTransaction();
                            User user2 = session18.load(User.class, user15.getUid());
                            int l = user2.getLcount();
                            user2.setLcount(l - 1);
                            session18.persist(user2);
                            transaction18.commit();
                            session18.close();
                            return "" + (l - 1) + "";
                        }
                    }
//                break;
            }
//            return "0";
        }
        catch (Exception e)
        {
            return "E";
        }
    }

}