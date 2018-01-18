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
    private static final SessionFactory ourSessionFactory;
    static
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }



}

