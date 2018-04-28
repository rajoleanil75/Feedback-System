package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.management.Query;
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
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {

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
            t.commit();
            session.close();
            return ""+e+"";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") int tid) {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        Teacher teacher = (Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
        if (teacher== null){

            t.commit();
            session.close();
            return "0";
        }
        else {

            List<Notification> list=session.createQuery("from Notification s where s.user.id=:id and s.user.role=:id1").setParameter("id1",2).setParameter("id",String.valueOf(tid)).list();
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                Notification obj= (Notification) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            org.hibernate.query.Query query1=session.createQuery("delete User s where s.id=:id ").setParameter("id",String.valueOf(tid));
            query1.executeUpdate();
            t.commit();
            t.begin();
            List<LSrate> list1=session.createQuery("from LSrate s where s.teacher_labBatch.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list1.iterator(); iterator.hasNext();)
            {
                LSrate obj= (LSrate) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            List<LScomment> list2=session.createQuery("from LScomment s where s.teacher_labBatch.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list2.iterator(); iterator.hasNext();)
            {
                LScomment obj= (LScomment) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            List<LSmcq> list3=session.createQuery("from LSmcq s where s.teacher_labBatch.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list3.iterator(); iterator.hasNext();)
            {
                LSmcq obj= (LSmcq) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            List<SScomment> list4=session.createQuery("from SScomment s where s.subject.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list4.iterator(); iterator.hasNext();)
            {
                SScomment obj= (SScomment) iterator.next();
                session.delete(obj);
            }
//            t.commit();
//            t.begin();
            List<SSrate> list5=session.createQuery("from SSrate s where s.subject.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list5.iterator(); iterator.hasNext();)
            {
                SSrate obj= (SSrate) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            List<SSmcq> list6=session.createQuery("from SSmcq s where s.subject.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list6.iterator(); iterator.hasNext();)
            {
                SSmcq obj= (SSmcq) iterator.next();
                session.delete(obj);
            }
//            t.commit();
//            t.begin();
            List<Teacher_LabBatch> list7=session.createQuery("from Teacher_LabBatch s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list7.iterator(); iterator.hasNext();)
            {
                Teacher_LabBatch obj= (Teacher_LabBatch) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            List<Subject> list8=session.createQuery("from Subject s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list8.iterator(); iterator.hasNext();)
            {
                Subject obj= (Subject) iterator.next();
                session.delete(obj);
            }
            t.commit();

//            org.hibernate.query.Query query=session.createQuery("delete Notification s where s.user.id=:id").setParameter("id",String.valueOf(tid));
//            query.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query1=session.createQuery("delete User s where s.id=:id ").setParameter("id",String.valueOf(tid));
//            query1.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query2=session.createQuery("delete LSrate s where s.teacher_labBatch.teacher.id=:id").setParameter("id",tid);
//            query2.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query3=session.createQuery("delete LScomment s where s.teacher_labBatch.teacher.id=:id").setParameter("id",tid);
//            query3.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query4=session.createQuery("delete LSmcq s where s.teacher_labBatch.teacher.id=:id").setParameter("id",tid);
//            query4.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query5=session.createQuery("delete SScomment s where s.subject.teacher.id=:id").setParameter("id",tid);
//            query5.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query6=session.createQuery("delete SSrate s where s.subject.teacher.id=:id").setParameter("id",tid);
//            query6.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query7=session.createQuery("delete SSmcq s where s.subject.teacher.id=:id").setParameter("id",tid);
//            query7.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query8=session.createQuery("delete Teacher_LabBatch s where s.teacher.id=:id").setParameter("id",tid);
//            query8.executeUpdate();
//            t.commit();
//            t.begin();
//            org.hibernate.query.Query query9=session.createQuery("delete Subject s where s.teacher.id=:id").setParameter("id",tid);
//            query9.executeUpdate();
//            t.commit();
//            t.begin();
            t.begin();
            org.hibernate.query.Query query10=session.createQuery("delete Teacher s where s.id=:id").setParameter("id",tid);
            query10.executeUpdate();
            t.commit();
            session.close();
            return "1";
        }
    }
    @POST
    @Path("gettname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTName(@FormParam("param1") int cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            Teacher teacher = session.load(Teacher.class, cid);
            String s=teacher.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @GET
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= Global.getSession();
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
        Session session = Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {

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
            transaction.commit();
            session.close();
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
//            FileWriter fstream = new FileWriter("/opt/Feedback_System_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
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
                    "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL95Dialect</property>\n" +
                    "        <property name=\"show_sql\">true</property>\n" +
                    "        <property name=\"connection.pool_size\">1000000</property>\n" +
                    "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                    "\n" +
                    "        <mapping resource=\"DB/sql.xml\"/>\n" +
                    "    </session-factory>\n" +
                    "</hibernate-configuration>");
            out.close();
            Global.closeFactory();
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
//            FileWriter fstream = new FileWriter("/opt/Feedback_System_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
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
                    "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL95Dialect</property>\n" +
                    "        <property name=\"show_sql\">true</property>\n" +
                    "        <property name=\"connection.pool_size\">1000000</property>\n" +
                    "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                    "\n" +
                    "        <mapping resource=\"DB/sql.xml\"/>\n" +
                    "    </session-factory>\n" +
                    "</hibernate-configuration>");
            out.close();
            Global.closeFactory();
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