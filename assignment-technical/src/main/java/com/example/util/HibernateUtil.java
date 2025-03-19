package com.example.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.demo.entity.Category;
import com.demo.entity.Product;
import com.demo.entity.Users;
import com.demo.entity.Orders;
import com.demo.entity.OrderDetails;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml and add annotated classes
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Category.class)
                    .addAnnotatedClass(Product.class)
                    .addAnnotatedClass(Users.class)
                    .addAnnotatedClass(Orders.class)
                    .addAnnotatedClass(OrderDetails.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // This method shuts down the SessionFactory and releases resources
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
