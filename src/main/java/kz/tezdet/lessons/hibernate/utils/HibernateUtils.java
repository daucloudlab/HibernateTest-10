package kz.tezdet.lessons.hibernate.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try{
            return new AnnotationConfiguration().configure().buildSessionFactory() ;
        }catch (Throwable ex){
            System.err.println("SessionFactory құруда қәте; " + ex);
            throw new ExceptionInInitializerError(ex) ;
        }
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory ;
    }
}
