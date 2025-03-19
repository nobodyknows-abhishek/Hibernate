package dao;

import com.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.util.*;

public class ProductDAO {
    public void saveProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
