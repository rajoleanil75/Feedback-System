package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Created by ANIL on 19/01/2018.
 */
@Path("/teacher_services")
@WebService
public class Teacher_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add( @FormParam("param2")String tname)
    {
        try
        {
            Session session= DB.Global.getSession();
            Transaction t=session.beginTransaction();
            DB.Teacher teacher=new DB.Teacher();
//            teacher.setId(tid);
            teacher.setName(tname);
//            teacher.setRole(tid);
            session.save(teacher);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return ""+e+"";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") int tid) {
//        try {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        Teacher teacher = session.load(Teacher.class, tid);
//      session.delete(teacher);
//        Serializable id = tid;
//      String i=teacher.getId();
//        Object o = session.load(Teacher.class, id);
        if (teacher== null)
            return "0";
        else {




        }
        t.commit();
        session.close();
        return "1";
//    }
//    catch (Exception e)
//        {
//            return String.valueOf(e);
//        }
    }
    @POST
    @Path("gettname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTName(@FormParam("param1") int cid)
    {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            Teacher teacher = session.load(Teacher.class, cid);
            String s=teacher.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            return "E";
        }
    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<Teacher> tlist=session.createQuery("from Teacher").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List  search(@FormParam("param1") String tname)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<Teacher> tlist=session.createQuery("from Teacher s where s.name like :id").setParameter("id","%"+tname+"%").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                Teacher teacher= (Teacher) iterator.next();
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
                list.add(list1);
            }
//            Teacher teacher = session.load(Teacher.class, tid);
//            JSONObject json=new JSONObject();
//            if(teacher==null) {
//                session.close();
//                return "0";
//            }
//            json.put("tid",teacher.getId());
//            json.put("name",teacher.getName());
            transaction.commit();
            session.close();
            return list;
//            return json.toString();
        }
        catch (Exception e)
        {
            return null;
        }

    }
    @POST
    @Path("viewTeacherLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public List  viewTeacherLogin()
    {
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
            return null;
        }
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<Teacher> tlist=session.createQuery("from Teacher").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                Teacher teacher= (Teacher) iterator.next();
                if(teacher.getRole()!=1)
                    continue;
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
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
    @Path("viewTeacherReg")
    @Produces(MediaType.APPLICATION_JSON)
    public List  viewTeacherLoginReg()
    {
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
            return null;
        }
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<Teacher> tlist=session.createQuery("from Teacher").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                Teacher teacher= (Teacher) iterator.next();
                if(teacher.getRole()==1)
                    continue;
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
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
}