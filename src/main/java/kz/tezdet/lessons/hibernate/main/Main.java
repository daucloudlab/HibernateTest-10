package kz.tezdet.lessons.hibernate.main;

import kz.tezdet.lessons.hibernate.models.Author;
import kz.tezdet.lessons.hibernate.models.Book;
import kz.tezdet.lessons.hibernate.models.Category;
import kz.tezdet.lessons.hibernate.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory() ;
        Session session = sessionFactory.openSession() ;

        Transaction tx = session.beginTransaction() ;

        Category category1 = new Category("Java") ;
        Category category2 = new Category("Python") ;
        Category category3 = new Category("JavaScript") ;
        session.save(category1) ;
        session.save(category2) ;
        session.save(category3) ;

        Author author1 = new Author("Марк","Лутц", "mark@gmail.com") ;
        Author author2 = new Author("Харви","Дейтел", "xardeitel@gmail.com") ;
        Author author3 = new Author("Джон","Дейтел", "djohnDeitel@gmail.com") ;
        session.save(author1);
        session.save(author2);
        session.save(author3);

        Book book1 = new Book("Изучаем Python", "1233-1234-2131-2122", category2) ;
        book1.setAuthor(author1);

        Book book2 = new Book("How To Programm Java", "1233-1234-2435-1345", category1) ;
        book2.setAuthor(author2);
        book2.setAuthor(author3);

        session.save(book1) ;
        session.save(book2) ;

        tx.commit();

        Query query1 =  session.createQuery("from Category") ;
        List<Category> categories = query1.list() ;
        for(Category category:categories)
            System.out.println(category.getName());

        Query query2 = session.createQuery("from Book b inner join b.authors a where a.lastName='Дейтел'") ;
        List<Object[]> books1 = query2.list() ;
        for(Object[] row:books1){
            Book book = (Book)row[0] ;
            Author author = (Author)row[1] ;
            System.out.println(book.getTitle() + " == " + author.getFirstName());
        }

        Query query3 = session.createQuery("from Book b inner join b.category as c where c.name='Python'") ;
        List<Object[]> books2 = query3.list() ;
        for(Object[] row:books2){
            Book b = (Book)row[0] ;
            Category c = (Category)row[1] ;
            System.out.println(b.getTitle() + " - " + c.getName());
        }

        Query query4 = session.createQuery("from Author a inner join a.books where a.lastName='Дейтел'");
        List<Object[]> authors = query4.list() ;
        for(Object[] row : authors){
            Author author = (Author)row[0] ;
            Book book = (Book)row[1] ;
            System.out.println(author.getFirstName()+" //// " + book.getTitle());
        }

        Query query5 = session.createQuery("from Book as b join b.category as c where c.name='Java'") ;
        List<Object[]> books = query5.list() ;
        for(Object[] row : books){
            Book book = (Book)row[0] ;
            for (Author author: book.getAuthors()){
                System.out.println(author.getFirstName());
            }
        }
        session.close();
    }
}
