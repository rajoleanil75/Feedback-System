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
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 05/02/2018
 */
@Path("/lquestion_services")
@WebService
public class LQuestion_service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") int sub, @FormParam("param2") int m, @FormParam("param3") int r, @FormParam("param4") int c)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Course course= (Course) session.createQuery("from  Course s where s.id=:id").setParameter("id",sub).uniqueResult();

            Lquestion squestion=new Lquestion();
            squestion.setQtype(1);
            squestion.setTotal(m);
            squestion.setCourse(course);
            session.save(squestion);

            Lquestion squestion2=new Lquestion();
            squestion2.setQtype(2);
            squestion2.setTotal(r);
            squestion2.setCourse(course);
            session.save(squestion2);

            Lquestion squestion3=new Lquestion();
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
            transaction.commit();
            session.close();
            return String.valueOf(e);
        }

    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewAll(@FormParam("param1") int sid)
    {
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
//        java.util.List<Lquestion> tlist=session1.createQuery("select s.id,s.qtype,s.total,s.subject  from Lquestion s").list();
//        java.util.List<Student> tlist=session1.createQuery("select s.id,s.name,s.roll,s.CSClass,s.division from Student s").list();
        Lquestion m= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
        Lquestion r= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
        Lquestion c= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
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
    @Path("getMSq")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMSq(@FormParam("param1") int sid)
    {
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
        Lquestion m= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",1).uniqueResult();
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
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
        Lquestion r= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",2).uniqueResult();
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
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
        Lquestion c= (Lquestion) session1.createQuery("from Lquestion s where s.course.id=:sid and s.qtype=:qid").setParameter("sid",sid).setParameter("qid",3).uniqueResult();
        t1.commit();
        session1.close();
        if(c==null)
            return "0";
        return String.valueOf(c.getId());
    }
    @POST
    @Path("viewAllQues")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAllQues(@FormParam("param1") int sid)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        List<Lmcq> smcqs=session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",sid).list();
        List<Lrate> srates=session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",sid).list();
//        List<Scomment> scomments=session.createQuery("from Scomment s where s.squestion.course.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = smcqs.iterator(); iterator.hasNext();)
        {
            Lmcq smcq= (Lmcq) iterator.next();
            List list1=new ArrayList();
            list1.add(smcq.getId());
            list1.add(smcq.getName());
            list.add(list1);
        }
        for (Iterator iterator=srates.iterator();iterator.hasNext();)
        {
            Lrate  srate= (Lrate) iterator.next();
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
    @Path("getAllMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllMcqReport(@FormParam("param1") int coid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lmcq> smcqs = session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lmcq smcq = (Lmcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4;
                sum/=4;
                sum*=5;
                sum/=4;
                String s = String.format(("%.2f"), sum);
                if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
                {
                    list1.add("1");
                }
                else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
                {
                    list1.add("2");
                }
                else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
                {
                    double sum1=cnt1+cnt2+cnt3+cnt4;
                    sum1/=2;
                    if(cnt3>sum1)
                        list1.add("4");
                    else
                        list1.add("3");
                }
                else {
                    list1.add("5");
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllMcqReport1")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllMcqReport1(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int lid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lmcq> smcqs = session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lmcq smcq = (Lmcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4;
                sum/=4;
                sum*=5;
                sum/=4;
                String s = String.format(("%.2f"), sum);
                if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
                {
                    list1.add("1");
                }
                else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
                {
                    list1.add("2");
                }
                else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
                {
                    double sum1=cnt1+cnt2+cnt3+cnt4;
                    sum1/=2;
                    if(cnt3>sum1)
                        list1.add("4");
                    else
                        list1.add("3");
                }
                else {
                    list1.add("5");
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneMcqReport(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int qid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lmcq smcq =  session.get(Lmcq.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list1.add("Average Rating(5)");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
            {
                list2.add("1");
            }
            else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
            {
                list2.add("2");
            }
            else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
            {
                double sum1=cnt1+cnt2+cnt3+cnt4;
                sum1/=2;
                if(cnt3>sum1)
                    list2.add("4");
                else
                    list2.add("3");
            }
            else {
                list2.add("5");
            }
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=(double) cnt1+cnt2+cnt3+cnt4;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add("");
            sum*=5;
            sum/=4;
            String s = String.format(("%.2f"), sum);

            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneMcqReport1")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneMcqReport1(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int qid,@FormParam("param4")int subid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lmcq smcq =  session.get(Lmcq.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list1.add("Average Rating(5)");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);

//                list1.add(s);

            if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
            {
                list2.add("1");
            }
            else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
            {
                list2.add("2");
            }
            else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
            {
                double sum1=cnt1+cnt2+cnt3+cnt4;
                sum1/=2;
                if(cnt3>sum1)
                    list2.add("4");
                else
                    list2.add("3");
            }
            else {
                list2.add("5");
            }
//                if (cnt1 > cnt2 && cnt1 > cnt3 && cnt1 > cnt4)
//                    list2.add(smcq.getOpt1());
//                else if (cnt2 > cnt1 && cnt2 > cnt3 && cnt2 > cnt4)
//                    list2.add(smcq.getOpt2());
//                else if (cnt3 > cnt1 && cnt3 > cnt2 && cnt3 > cnt4)
//                    list2.add(smcq.getOpt3());
//                else
//                    list2.add(smcq.getOpt4());

            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=(double) cnt1+cnt2+cnt3+cnt4;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add("");
            sum*=5;
            sum/=4;
            String s = String.format(("%.2f"), sum);

            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllRateReport(@FormParam("param1") int coid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lrate> smcqs = session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lrate smcq = (Lrate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4+cnt5*5;
                sum/=5;
                String s = String.format(("%.2f"), sum);
                if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                    if(cnt1!=0)
                        list1.add("3");
                    else
                        list1.add("0");
                }
                else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
                {
                    list1.add("1");
                }
                else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
                {
                    list1.add("2");
                }
                else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
                {
                    list1.add("3");
                }
                else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
                {
                    list1.add("4");
                }
                else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
                {
                    list1.add("5");
                }
                else
                    list1.add("0");

//                list1.add(s);
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllRateReport1")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllRateReport(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int lid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lrate> smcqs = session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lrate smcq = (Lrate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",lid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4+cnt5*5;
                sum/=5;
                String s = String.format(("%.2f"), sum);
                if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                    if(cnt1!=0)
                        list1.add("3");
                    else
                        list1.add("0");
                }
                else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
                {
                    list1.add("1");
                }
                else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
                {
                    list1.add("2");
                }
                else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
                {
                    list1.add("3");
                }
                else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
                {
                    list1.add("4");
                }
                else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
                {
                    list1.add("5");
                }
                else
                    list1.add("0");
//                list1.add(s);
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneRateReport(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int qid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lrate smcq =  session.get(Lrate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");

            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2").setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            double avg=(double) (cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            String s = String.format(("%.2f"), avg);
            if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                if(cnt1!=0)
                    list2.add("3");
                else
                    list2.add("0");
            }
            else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
            {
                list2.add("1");
            }
            else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
            {
                list2.add("2");
            }
            else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
            {
                list2.add("3");
            }
            else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
            {
                list2.add("4");
            }
            else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
            {
                list2.add("5");
            }
            else
                list2.add("0");
//            list2.add(s);
            list.add(list2);
//            if (avg>=4.0)
//                list2.add("Best");
//            else if (avg>=2.0)
//                list2.add("Better");
//            else
//                list2.add("Good");
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=cnt1+cnt2+cnt3+cnt4+cnt5;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            double s5=(cnt5/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add(String.format(("%.2f"), s5));
            list3.add("");
            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneRateReport1")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneRateReport1(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int qid,@FormParam("param4")int subid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lrate smcq =  session.get(Lrate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");

            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            double avg=(double) (cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            String s = String.format(("%.2f"), avg);

            if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                if(cnt1!=0)
                    list2.add("3");
                else
                    list2.add("0");
            }
            else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
            {
                list2.add("1");
            }
            else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
            {
                list2.add("2");
            }
            else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
            {
                list2.add("3");
            }
            else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
            {
                list2.add("4");
            }
            else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
            {
                list2.add("5");
            }
            else
                list2.add("0");


//            list2.add(s);
            list.add(list2);
//            if (avg>=4.0)
//                list2.add("Best");
//            else if (avg>=2.0)
//                list2.add("Better");
//            else
//                list2.add("Good");
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=cnt1+cnt2+cnt3+cnt4+cnt5;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            double s5=(cnt5/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add(String.format(("%.2f"), s5));
            list3.add("");
            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllCommReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCommReport(@FormParam("param1") int coid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<LScomment> sScomment=session.createQuery("from LScomment s where s.teacher_labBatch.teacher.id=:id and s.lcomment.lquestion.course.id=:id1").setParameter("id", tid).setParameter("id1",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                LScomment smcq = (LScomment) iterator.next();
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
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllCommReport1")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCommReport(@FormParam("param1") int coid,@FormParam("param2")int tid,@FormParam("param3")int subid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<LScomment> sScomment=session.createQuery("from LScomment s where s.teacher_labBatch.teacher.id=:id and s.lcomment.lquestion.course.id=:id1 and s.teacher_labBatch.labBatch.id=:id2").setParameter("id2",subid).setParameter("id", tid).setParameter("id1",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                LScomment smcq = (LScomment) iterator.next();
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
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllQLMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllQLMcqReport(@FormParam("param1") int coid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lmcq> smcqs = session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lmcq smcq = (Lmcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4;
                sum/=4;
                sum*=5;
                sum/=4;
                String s = String.format(("%.2f"), sum);
                if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
                {
                    list1.add("1");
                }
                else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
                {
                    list1.add("2");
                }
                else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
                {
                    double sum1=cnt1+cnt2+cnt3+cnt4;
                    sum1/=2;
                    if(cnt3>sum1)
                        list1.add("4");
                    else
                        list1.add("3");
                }
                else {
                    list1.add("5");
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllQLRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllQLRateReport(@FormParam("param1") int coid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lrate> smcqs = session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lrate smcq = (Lrate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();

                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4+cnt5*5;
                sum/=5;
                String s = String.format(("%.2f"), sum);
                if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                    if(cnt1!=0)
                        list1.add("3");
                    else
                        list1.add("0");
                }
                else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
                {
                    list1.add("1");
                }
                else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
                {
                    list1.add("2");
                }
                else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
                {
                    list1.add("3");
                }
                else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
                {
                    list1.add("4");
                }
                else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
                {
                    list1.add("5");
                }
                else
                    list1.add("0");
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllQLCommReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllQLCommReport(@FormParam("param1") int coid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<LScomment> sScomment=session.createQuery("from LScomment s where s.lcomment.lquestion.course.id=:id1").setParameter("id1",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                LScomment smcq = (LScomment) iterator.next();
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
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneQLMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneQLMcqReport(@FormParam("param1") int coid,@FormParam("param3")int qid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lmcq smcq =  session.get(Lmcq.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list1.add("Average Rating(5)");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);

//                list1.add(s);

            if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
            {
                list2.add("1");
            }
            else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
            {
                list2.add("2");
            }
            else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
            {
                double sum1=cnt1+cnt2+cnt3+cnt4;
                sum1/=2;
                if(cnt3>sum1)
                    list2.add("4");
                else
                    list2.add("3");
            }
            else {
                list2.add("5");
            }
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=(double) cnt1+cnt2+cnt3+cnt4;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add("");
            sum*=5;
            sum/=4;
            String s = String.format(("%.2f"), sum);

            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneQLRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneQLRateReport(@FormParam("param1") int coid,@FormParam("param3")int qid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lrate smcq =  session.get(Lrate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");

            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2").setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            double avg=(double) (cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            String s = String.format(("%.2f"), avg);
//            list2.add(s);
            if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                if(cnt1!=0)
                    list2.add("3");
                else
                    list2.add("0");
            }
            else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
            {
                list2.add("1");
            }
            else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
            {
                list2.add("2");
            }
            else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
            {
                list2.add("3");
            }
            else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
            {
                list2.add("4");
            }
            else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
            {
                list2.add("5");
            }
            else
                list2.add("0");
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=cnt1+cnt2+cnt3+cnt4+cnt5;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            double s5=(cnt5/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add(String.format(("%.2f"), s5));
            list3.add("");
            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    ///////

    @POST
    @Path("getAllCLMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCLMcqReport(@FormParam("param1") int coid,@FormParam("param2")int clid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lmcq> smcqs = session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lmcq smcq = (Lmcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4;
                sum/=4;
                sum*=5;
                sum/=4;
                String s = String.format(("%.2f"), sum);
                if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
                {
                    list1.add("1");
                }
                else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
                {
                    list1.add("2");
                }
                else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
                {
                    double sum1=cnt1+cnt2+cnt3+cnt4;
                    sum1/=2;
                    if(cnt3>sum1)
                        list1.add("4");
                    else
                        list1.add("3");
                }
                else {
                    list1.add("5");
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllCLRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCLRateReport(@FormParam("param1") int coid,@FormParam("param2")int clid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lrate> smcqs = session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lrate smcq = (Lrate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();

                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4+cnt5*5;
                sum/=5;
                String s = String.format(("%.2f"), sum);
                if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                    if(cnt1!=0)
                        list1.add("3");
                    else
                        list1.add("0");
                }
                else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
                {
                    list1.add("1");
                }
                else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
                {
                    list1.add("2");
                }
                else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
                {
                    list1.add("3");
                }
                else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
                {
                    list1.add("4");
                }
                else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
                {
                    list1.add("5");
                }
                else
                    list1.add("0");
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllCLCommReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCLCommReport(@FormParam("param1") int coid,@FormParam("param2")int clid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<LScomment> sScomment=session.createQuery("from LScomment s where s.lcomment.lquestion.course.id=:id1 and s.student.CSClass.id=:id2").setParameter("id2",clid).setParameter("id1",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                LScomment smcq = (LScomment) iterator.next();
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
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneCLMcqReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneCLMcqReport(@FormParam("param1") int coid,@FormParam("param3")int qid,@FormParam("param2")int clid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lmcq smcq =  session.get(Lmcq.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list1.add("Average Rating(5)");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);

//                list1.add(s);

            if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
            {
                list2.add("1");
            }
            else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
            {
                list2.add("2");
            }
            else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
            {
                double sum1=cnt1+cnt2+cnt3+cnt4;
                sum1/=2;
                if(cnt3>sum1)
                    list2.add("4");
                else
                    list2.add("3");
            }
            else {
                list2.add("5");
            }
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=(double) cnt1+cnt2+cnt3+cnt4;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add("");
            sum*=5;
            sum/=4;
            String s = String.format(("%.2f"), sum);

            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneCLRateReport")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneCLRateReport(@FormParam("param1") int coid,@FormParam("param3")int qid,@FormParam("param2")int clid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lrate smcq =  session.get(Lrate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");

            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.student.CSClass.id=:id3").setParameter("id3",clid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            double avg=(double) (cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            String s = String.format(("%.2f"), avg);
//            list2.add(s);
            if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                if(cnt1!=0)
                    list2.add("3");
                else
                    list2.add("0");
            }
            else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
            {
                list2.add("1");
            }
            else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
            {
                list2.add("2");
            }
            else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
            {
                list2.add("3");
            }
            else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
            {
                list2.add("4");
            }
            else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
            {
                list2.add("5");
            }
            else
                list2.add("0");
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=cnt1+cnt2+cnt3+cnt4+cnt5;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            double s5=(cnt5/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add(String.format(("%.2f"), s5));
            list3.add("");
            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("viewAllQuesLabWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAllQuesLabWise(@FormParam("param1") int sid)
    {
        Session session=Global.getSession();
        Transaction transaction=session.beginTransaction();
        LabBatch labBatch=session.get(LabBatch.class,sid);
        List<Lmcq> smcqs=session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",labBatch.getCSClass().getCourse().getId()).list();
        List<Lrate> srates=session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",labBatch.getCSClass().getCourse().getId()).list();
//        List<Scomment> scomments=session.createQuery("from Scomment s where s.squestion.course.id=:id").setParameter("id",sid).list();
        List list=new ArrayList();
        for(Iterator iterator = smcqs.iterator(); iterator.hasNext();)
        {
            Lmcq smcq= (Lmcq) iterator.next();
            List list1=new ArrayList();
            list1.add(smcq.getId());
            list1.add(smcq.getName());
            list.add(list1);
        }
        for (Iterator iterator=srates.iterator();iterator.hasNext();)
        {
            Lrate  srate= (Lrate) iterator.next();
            List list1=new ArrayList();
            list1.add(srate.getId());
            list1.add(srate.getName());
            list.add(list1);
        }
        transaction.commit();
        session.close();
        return list;
    }
    /////////////

    @POST
    @Path("getAllMcqReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllMcqReportT(@FormParam("param1") int ftid,@FormParam("param2")int fsid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            LabBatch subject=session.get(LabBatch.class,fsid);
            List<Lmcq> smcqs = session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",subject.getCSClass().getCourse().getId()).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lmcq smcq = (Lmcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4;
                sum/=4;
                sum*=5;
                sum/=4;
                String s = String.format(("%.2f"), sum);
                if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
                {
                    list1.add("1");
                }
                else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
                {
                    list1.add("2");
                }
                else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
                {
                    double sum1=cnt1+cnt2+cnt3+cnt4;
                    sum1/=2;
                    if(cnt3>sum1)
                        list1.add("4");
                    else
                        list1.add("3");
                }
                else {
                    list1.add("5");
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllRateReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllRateReportT(@FormParam("param1") int ftid,@FormParam("param2")int fsid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            LabBatch subject=session.get(LabBatch.class,fsid);
            List<Lrate> smcqs = session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",subject.getCSClass().getCourse().getId()).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lrate smcq = (Lrate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",fsid).setParameter("id", ftid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();

                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4+cnt5*5;
                sum/=5;
                String s = String.format(("%.2f"), sum);
                if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                    if(cnt1!=0)
                        list1.add("3");
                    else
                        list1.add("0");
                }
                else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
                {
                    list1.add("1");
                }
                else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
                {
                    list1.add("2");
                }
                else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
                {
                    list1.add("3");
                }
                else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
                {
                    list1.add("4");
                }
                else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
                {
                    list1.add("5");
                }
                else
                    list1.add("0");

                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllCommReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllCommReportT(@FormParam("param1")int ftid,@FormParam("param2")int fsid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            LabBatch subject=session.get(LabBatch.class,fsid);
            List<LScomment> sScomment=session.createQuery("from LScomment s where s.teacher_labBatch.teacher.id=:id and s.lcomment.lquestion.course.id=:id1 and s.teacher_labBatch.labBatch.id=:id2").setParameter("id2",fsid).setParameter("id", ftid).setParameter("id1",subject.getCSClass().getCourse().getId()).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                LScomment smcq = (LScomment) iterator.next();
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
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneMcqReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneMcqReportT(@FormParam("param1")int tid,@FormParam("param2")int qid,@FormParam("param3")int subid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lmcq smcq =  session.get(Lmcq.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list1.add("Average Rating(5)");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.teacher_labBatch.teacher.id=:id and s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
            {
                list2.add("1");
            }
            else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
            {
                list2.add("2");
            }
            else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
            {
                double sum1=cnt1+cnt2+cnt3+cnt4;
                sum1/=2;
                if(cnt3>sum1)
                    list2.add("4");
                else
                    list2.add("3");
            }
            else {
                list2.add("5");
            }
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=(double) cnt1+cnt2+cnt3+cnt4;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add("");
            sum*=5;
            sum/=4;
            String s = String.format(("%.2f"), sum);

            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneRateReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneRateReportT(@FormParam("param1")int tid,@FormParam("param2")int qid,@FormParam("param3")int subid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lrate smcq =  session.get(Lrate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");

            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.teacher_labBatch.teacher.id=:id and s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.labBatch.id=:id3").setParameter("id3",subid).setParameter("id", tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            double avg=(double) (cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            String s = String.format(("%.2f"), avg);
            if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                if(cnt1!=0)
                    list2.add("3");
                else
                    list2.add("0");
            }
            else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
            {
                list2.add("1");
            }
            else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
            {
                list2.add("2");
            }
            else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
            {
                list2.add("3");
            }
            else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
            {
                list2.add("4");
            }
            else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
            {
                list2.add("5");
            }
            else
                list2.add("0");
            list.add(list2);

            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=cnt1+cnt2+cnt3+cnt4+cnt5;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            double s5=(cnt5/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add(String.format(("%.2f"), s5));
            list3.add("");
            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    ////////////////
    @POST
    @Path("getAllQLMcqReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllQLMcqReportT(@FormParam("param1") int coid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lmcq> smcqs = session.createQuery("from Lmcq s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lmcq smcq = (Lmcq) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSmcq s where s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
                Long cnt4 = (Long) query4.uniqueResult();
                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4;
                sum/=4;
                sum*=5;
                sum/=4;
                String s = String.format(("%.2f"), sum);
                if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
                {
                    list1.add("1");
                }
                else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
                {
                    list1.add("2");
                }
                else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
                {
                    double sum1=cnt1+cnt2+cnt3+cnt4;
                    sum1/=2;
                    if(cnt3>sum1)
                        list1.add("4");
                    else
                        list1.add("3");
                }
                else {
                    list1.add("5");
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllQLRateReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllQLRateReportT(@FormParam("param1") int coid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<Lrate> smcqs = session.createQuery("from Lrate s where s.lquestion.course.id=:id").setParameter("id",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = smcqs.iterator(); iterator.hasNext(); ) {
                Lrate smcq = (Lrate) iterator.next();
                List list1 = new ArrayList();
                list1.add(smcq.getName());
                Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
                Long cnt1 = (Long) query1.uniqueResult();
                Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
                Long cnt2 = (Long) query2.uniqueResult();
                Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
                Long cnt3 = (Long) query3.uniqueResult();
                Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
                Long cnt4 = (Long) query4.uniqueResult();
                Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
                Long cnt5 = (Long) query5.uniqueResult();

                double sum=(double) cnt1*1+cnt2*2+cnt3*3+cnt4*4+cnt5*5;
                sum/=5;
                String s = String.format(("%.2f"), sum);
                if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                    if(cnt1!=0)
                        list1.add("3");
                    else
                        list1.add("0");
                }
                else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
                {
                    list1.add("1");
                }
                else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
                {
                    list1.add("2");
                }
                else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
                {
                    list1.add("3");
                }
                else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
                {
                    list1.add("4");
                }
                else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
                {
                    list1.add("5");
                }
                else
                    list1.add("0");
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getAllQLCommReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllQLCommReportT(@FormParam("param1") int coid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List<LScomment> sScomment=session.createQuery("from LScomment s where s.lcomment.lquestion.course.id=:id1 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1",coid).list();
            List list = new ArrayList();
            for (Iterator iterator = sScomment.iterator(); iterator.hasNext(); ) {
                LScomment smcq = (LScomment) iterator.next();
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
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneQLMcqReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneQLMcqReportT(@FormParam("param1") int coid,@FormParam("param3")int qid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lmcq smcq =  session.get(Lmcq.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add(smcq.getOpt1());
            list1.add(smcq.getOpt2());
            list1.add(smcq.getOpt3());
            list1.add(smcq.getOpt4());
            list1.add("Average Rating(5)");
            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "1");
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "2");
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "3");
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSmcq s where  s.lmcq.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", "4");
            Long cnt4 = (Long) query4.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);

//                list1.add(s);

            if(cnt1>cnt2&&cnt1>cnt3&&cnt1>cnt4)
            {
                list2.add("1");
            }
            else if(cnt2>cnt1&&cnt2>cnt3&&cnt2>cnt4)
            {
                list2.add("2");
            }
            else if(cnt3>cnt1&&cnt3>cnt2&&cnt3>cnt4)
            {
                double sum1=cnt1+cnt2+cnt3+cnt4;
                sum1/=2;
                if(cnt3>sum1)
                    list2.add("4");
                else
                    list2.add("3");
            }
            else {
                list2.add("5");
            }
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=(double) cnt1+cnt2+cnt3+cnt4;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add("");
            sum*=5;
            sum/=4;
            String s = String.format(("%.2f"), sum);

            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
    @POST
    @Path("getOneQLRateReportT")
    @Produces(MediaType.APPLICATION_JSON)
    public List getOneQLRateReportT(@FormParam("param1") int coid,@FormParam("param3")int qid,@FormParam("param2")int tid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            List list = new ArrayList();
            Lrate smcq =  session.get(Lrate.class,qid);
            List list1 = new ArrayList();
            list1.add(smcq.getName());
            list1.add("Rating 1");
            list1.add("Rating 2");
            list1.add("Rating 3");
            list1.add("Rating 4");
            list1.add("Rating 5");
            list1.add("Average");

            list.add(list1);
            Query query1 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 1);
            Long cnt1 = (Long) query1.uniqueResult();
            Query query2 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 2);
            Long cnt2 = (Long) query2.uniqueResult();
            Query query3 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 3);
            Long cnt3 = (Long) query3.uniqueResult();
            Query query4 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 4);
            Long cnt4 = (Long) query4.uniqueResult();
            Query query5 = session.createQuery("select count (s.ans) from LSrate s where s.lrate.id=:id1 and s.ans=:id2 and s.teacher_labBatch.teacher.id=:id3").setParameter("id3",tid).setParameter("id1", smcq.getId()).setParameter("id2", 5);
            Long cnt5 = (Long) query5.uniqueResult();
            List list2 = new ArrayList();
            list2.add("No.of Students with Rating");
            list2.add(cnt1);
            list2.add(cnt2);
            list2.add(cnt3);
            list2.add(cnt4);
            list2.add(cnt5);
            double avg=(double) (cnt1+cnt2*2+cnt3*3+cnt4*4+cnt5*5)/5;
            String s = String.format(("%.2f"), avg);
//            list2.add(s);
            if(cnt1.equals(cnt2) && cnt2.equals(cnt3) && cnt3.equals(cnt4)&& cnt4.equals(cnt5)) {
                if(cnt1!=0)
                    list2.add("3");
                else
                    list2.add("0");
            }
            else if(cnt1>=cnt2&&cnt1>=cnt3&&cnt1>=cnt4&&cnt1>=cnt5)
            {
                list2.add("1");
            }
            else if(cnt2>=cnt1&&cnt2>=cnt3&&cnt2>=cnt4&&cnt2>=cnt5)
            {
                list2.add("2");
            }
            else if(cnt3>=cnt1&&cnt3>=cnt2&&cnt3>=cnt4&&cnt3>=cnt5)
            {
                list2.add("3");
            }
            else if(cnt4>=cnt1&&cnt4>=cnt2&&cnt4>=cnt3&&cnt4>=cnt5)
            {
                list2.add("4");
            }
            else if(cnt5>=cnt1&&cnt5>=cnt2&&cnt5>=cnt3&&cnt5>=cnt4)
            {
                list2.add("5");
            }
            else
                list2.add("0");
            list.add(list2);
            List list3 = new ArrayList();
            list3.add("Percentage Student");
            double sum=cnt1+cnt2+cnt3+cnt4+cnt5;
            double s1=(cnt1/sum)*100;
            double s2=(cnt2/sum)*100;
            double s3=(cnt3/sum)*100;
            double s4=(cnt4/sum)*100;
            double s5=(cnt5/sum)*100;
            list3.add(String.format(("%.2f"), s1));
            list3.add(String.format(("%.2f"), s2));
            list3.add(String.format(("%.2f"), s3));
            list3.add(String.format(("%.2f"), s4));
            list3.add(String.format(("%.2f"), s5));
            list3.add("");
            list.add(list3);
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return (List) e;
        }
    }
}
