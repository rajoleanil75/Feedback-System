package DB; /**
 * Created by ANIL on 27/12/2017.
 */
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class Global {
    public static int uid;
    public static String role;
    public static Date date;
    private static SessionFactory ourSessionFactory;
    static
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static void reload()
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }

    }
    public static Session getSession() throws HibernateException
    {
//        reload();
        return ourSessionFactory.openSession();
    }
    public static void closeFactory() throws Exception
    {
        ourSessionFactory.close();
    }

    private static final SessionFactory ourSessionFactory1;
    static
    {
        try {
            Configuration configuration=new Configuration();
            configuration.configure("conn1.cfg.xml");
            ourSessionFactory1=configuration.buildSessionFactory();
        }catch (Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static Session getSession1() throws HibernateException
    {
        return ourSessionFactory1.openSession();
//        return ourSessionFactory.getCurrentSession();
    }
//    public static Session getCSession1() throws HibernateException
//    {
//        return ourSessionFactory1.getCurrentSession();
//    }
    public static void closeFactory1() throws Exception
    {
        ourSessionFactory1.close();
    }


}

