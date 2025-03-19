package dao;

import com.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.util.*;

public class UsersDAO {
    public void save(Users user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
    
    // Add update, delete, findById etc.
}
