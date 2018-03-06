package Admin;

import DB.Backup;
import DB.Global;
import DB.Notification;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Anil on 05/03/2018
 */
@Path("/backup_services")
@WebService
public class Backup_Services {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "phd";

    protected String getSaltString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewAll")
    public List viewAll()
    {
        try {
            Session session = DB.Global.getSession1();
            Transaction t = session.beginTransaction();
            java.util.List<Backup> tlist = session.createQuery("from Backup s order by s.date desc ").list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                Backup subject = (Backup) iterator.next();
//                if(subject.getCur()==1)
//                    continue;
                List list1 = new ArrayList();
                list1.add(subject.getBname());
                list1.add(subject.getDate());
                if(subject.getCur()==1)
                    list1.add("YES");
                else
                    list1.add("NO");
                list1.add(subject.getId());
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
    @Path("backup")
    public String backup()
    {
        try
        {
            Session session= Global.getSession1();
            Transaction transaction = session.beginTransaction();
            Backup backup = (Backup) session.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
            backup.setDate(LocalDate.now());
            session.persist(backup);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e){
//            return String.valueOf(e);
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String bname)
    {
        Connection conn = null;
        Statement stmt = null;
        String dbname=getSaltString();
        try{
            //STEP 2: Register JDBC driver
            Class.forName("org.postgresql.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE "+dbname;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return String.valueOf(se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return String.valueOf(e);
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
                return String.valueOf(se);
            }//end finally try
        }//end try


        try
        {

            Session session= Global.getSession1();
            Transaction transaction = session.beginTransaction();
            Backup backup1 = (Backup) session.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
            backup1.setCur(0);
            session.persist(backup1);
            Backup backup =new Backup();
            backup.setBname(bname);
            backup.setDname(dbname);
            backup.setCur(1);
            backup.setDate(LocalDate.now());
            session.persist(backup);
            transaction.commit();
            session.close();
//            Global.reload();
            return "1";
        }
        catch (Exception e){
//            return String.valueOf(e);
            return "E";
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("restore")
    public String restore(@FormParam("param1")int bid)
    {
        Session session= Global.getSession1();
        Transaction transaction = session.beginTransaction();
        Backup backup=session.load(Backup.class,bid);
        String dbname=backup.getDname();
        transaction.commit();
        session.close();
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
            return "1";
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
            return "E";
        }
    }
}
