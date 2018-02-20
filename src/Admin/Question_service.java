package Admin;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 05/02/2018
 */
@Path("/question_services")
@WebService
public class Question_service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") int sub, @FormParam("param2") int m, @FormParam("param3") int r, @FormParam("param4") int c)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Course course= (Course) session.createQuery("from  Course s where s.id=:id").setParameter("id",sub).uniqueResult();

            Squestion squestion=new Squestion();
            squestion.setQtype(1);
            squestion.setTotal(m);
            squestion.setCourse(course);
            session.save(squestion);

            Squestion squestion2=new Squestion();
            squestion2.setQtype(2);
            squestion2.setTotal(r);
            squestion2.setCourse(course);
            session.save(squestion2);

            Squestion squestion3=new Squestion();
            squestion3.setQtype(3);
            squestion3.setTotal(c);
            squestion3.setCourse(course);
            session.save(squestion3);

            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return String.valueOf(e);
        }

    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewAll(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
//        java.util.List<Squestion> tlist=session1.createQuery("select s.id,s.qtype,s.total,s.subject  from Squestion s").list();
//        java.util.List<Student> tlist=session1.createQuery("select s.id,s.name,s.roll,s.CSClass,s.division from Student s").list();
        Squestion m= (Squestion) session1.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
        Squestion r= (Squestion) session1.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
        Squestion c= (Squestion) session1.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
        t1.commit();
        session1.close();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("1",m.getTotal());
        jsonObject.put("2",r.getTotal());
        jsonObject.put("3",c.getTotal());
        jsonObject.put("4",m.getId());
        jsonObject.put("5",r.getId());
        jsonObject.put("6",c.getId());
//        return tlist;
//        return String.valueOf(squestion.getTotal());
        return String.valueOf(jsonObject);
    }
    @POST
    @Path("viewAllQues")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAllQues(@FormParam("param1") int sid)
    {
        Session session=Global.getSession();
        Transaction transaction=session.beginTransaction();
        List<Smcq> smcqs=session.createQuery("from Smcq s where s.squestion.course.id=:id").setParameter("id",sid).list();
        List<Srate> srates=session.createQuery("from Srate s where s.squestion.course.id=:id").setParameter("id",sid).list();
//        List<Scomment> scomments=session.createQuery("from Scomment s where s.squestion.course.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = smcqs.iterator(); iterator.hasNext();)
        {
            Smcq smcq= (Smcq) iterator.next();
            List list1=new ArrayList();
            list1.add(smcq.getId());
            list1.add(smcq.getName());
            list.add(list1);
        }
        for (Iterator iterator=srates.iterator();iterator.hasNext();)
        {
            Srate  srate= (Srate) iterator.next();
            List list1=new ArrayList();
            list1.add(srate.getId());
            list1.add(srate.getName());
            list.add(list1);
        }
//        for (Iterator iterator=scomments.iterator();iterator.hasNext();)
//        {
//            Scomment  scomment= (Scomment) iterator.next();
//            List list1=new ArrayList();
//            list1.add(scomment.getId());
//            list1.add(scomment.getName());
//            list.add(list1);
//        }
        transaction.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getMSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMSq(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Squestion m= (Squestion) session1.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
        t1.commit();
        session1.close();
        if(m==null)
            return "0";
        return String.valueOf(m.getId());
    }

    @POST
    @Path("getRSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRSq(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Squestion r= (Squestion) session1.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
        t1.commit();
        session1.close();
        if(r==null)
            return "0";
        return String.valueOf(r.getId());
    }

    @POST
    @Path("getCSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCSq(@FormParam("param1") int sid)
    {
        Session session1= DB.Global.getSession();
        Transaction t1=session1.beginTransaction();
        Squestion c= (Squestion) session1.createQuery("from Squestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
        t1.commit();
        session1.close();
        if(c==null)
            return "0";
        return String.valueOf(c.getId());
    }

    @POST
    @Path("getAllMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllMcqReport(@FormParam("param1") int coid,@FormParam("param2")String tid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List<Smcq> smcqs = session.createQuery("from Smcq s where s.squestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Smcq smcq = (Smcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                list1.add(smcq.getOpt1() + " (" + cnt1 + ")");
                list1.add(smcq.getOpt2() + " (" + cnt2 + ")");
                list1.add(smcq.getOpt3() + " (" + cnt3 + ")");
                list1.add(smcq.getOpt4() + " (" + cnt4 + ")");
                if (cnt1 > cnt2 && cnt1 > cnt3 && cnt1 > cnt4)
                    list1.add(smcq.getOpt1());
                else if (cnt2 > cnt1 && cnt2 > cnt3 && cnt2 > cnt4)
                    list1.add(smcq.getOpt2());
                else if (cnt3 > cnt1 && cnt3 > cnt2 && cnt3 > cnt4)
                    list1.add(smcq.getOpt3());
                else
                    list1.add(smcq.getOpt4());

                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getOneMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneMcqReport(@FormParam("param1") int coid,@FormParam("param2")String tid,@FormParam("param3")int qid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List list = new ArrayList();
                Smcq smcq =  session.get(Smcq.class,qid);
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                list1.add(smcq.getOpt1());
                list1.add(smcq.getOpt2());
                list1.add(smcq.getOpt3());
                list1.add(smcq.getOpt4());
                list1.add("Performancee");
                list.add(list1);
                Query query1 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                List list2 = new ArrayList();
                list2.add("Total Count");
                list2.add(cnt1);
                list2.add(cnt2);
                list2.add(cnt3);
                list2.add(cnt4);
                if (cnt1 > cnt2 && cnt1 > cnt3 && cnt1 > cnt4)
                    list2.add(smcq.getOpt1());
                else if (cnt2 > cnt1 && cnt2 > cnt3 && cnt2 > cnt4)
                    list2.add(smcq.getOpt2());
                else if (cnt3 > cnt1 && cnt3 > cnt2 && cnt3 > cnt4)
                    list2.add(smcq.getOpt3());
                else
                    list2.add(smcq.getOpt4());

                list.add(list2);

            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getOneMcqGraph")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneMcqGraph(@FormParam("param1") int coid,@FormParam("param2")String tid,@FormParam("param3")int qid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List list = new ArrayList();
            Smcq smcq =  session.get(Smcq.class,qid);

            Query query1 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from SSmcq s where s.subject.teacher.id=:id and s.smcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();

            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);


            list.add(list2);

            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getAllRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllRateReport(@FormParam("param1") int coid,@FormParam("param2")String tid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List<Srate> smcqs = session.createQuery("from Srate s where s.squestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Srate smcq = (Srate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();
                list1.add(cnt1);
                list1.add(cnt2);
                list1.add(cnt3);
                list1.add(cnt4);
                list1.add(cnt5);
                long avg=(cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
                list1.add(avg);

                if (avg>=4.0)
                    list1.add("Best");
                else if (avg>=2.0)
                    list1.add("Better");
                else
                    list1.add("Good");

                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getAllRateGraph")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllRategraph(@FormParam("param1") int coid,@FormParam("param2")String tid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List<Srate> smcqs = session.createQuery("from Srate s where s.squestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            int cn1 = 0,cn2=0,cn3=0,cn4=0,cn5=0;

            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Srate smcq = (Srate) iterator.next();
//                List list1 = new ArrayList();
//                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();
                cn1= (int) (cn1+cnt1);
                cn2= (int) (cn2+cnt2);
                cn3= (int) (cn3+cnt3);
                cn4= (int) (cn4+cnt4);
                cn5= (int) (cn5+cnt5);
//                list1.add(cnt1);
//                list1.add(cnt2);
//                list1.add(cnt3);
//                list1.add(cnt4);
//                list1.add(cnt5);
//                long avg=(cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
//                list1.add(avg);

//                if (avg>=4.0)
//                    list1.add("Best");
//                else if (avg>=2.0)
//                    list1.add("Better");
//                else
//                    list1.add("Good");

//                list.add(list1);
            }
            list.add(cn1);
            list.add(cn2);
            list.add(cn3);
            list.add(cn4);
            list.add(cn5);

            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getAllCommReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCommReport(@FormParam("param1") int coid,@FormParam("param2")String tid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List<SScomment> sScomment=session.createQuery("from SScomment s where s.subject.teacher.id=:id and s.scomment.squestion.course.id=:id1").setParameter("id", tid).setParameter("id1",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                SScomment smcq = (SScomment) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getAns());
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getOneRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneRateReport(@FormParam("param1") int coid,@FormParam("param2")String tid,@FormParam("param3")int qid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List list = new ArrayList();
            Srate smcq =  session.get(Srate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");
            list1.add("Performancee");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("Total Count");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            long avg=(cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            list2.add(avg);

            if (avg>=4.0)
                list2.add("Best");
            else if (avg>=2.0)
                list2.add("Better");
            else
                list2.add("Good");


            list.add(list2);

            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
    @POST
    @Path("getOneRateGraph")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneRateGraph(@FormParam("param1") int coid,@FormParam("param2")String tid,@FormParam("param3")int qid)
    {
        try {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            List list = new ArrayList();
            Srate smcq =  session.get(Srate.class,qid);
            Query query1 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from SSrate s where s.subject.teacher.id=:id and s.srate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();

            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);

            list.add(list2);

            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return (List) e;
        }
    }
}
