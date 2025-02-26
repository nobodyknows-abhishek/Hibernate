package com.abhishek.hibernate.assignment_technical;



import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.entity.Category;
import com.demo.entity.Orders;
import com.demo.entity.OrderDetails;
import com.demo.entity.Product;
import com.demo.entity.Users;
import com.example.util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class App {
    public static void main(String[] args) {
        insertCategory();
        createOrderWithDetails();
        fetchOrderAssociations(1L); // replace with an actual order ID
        // Don't forget to shutdown the SessionFactory after you're done
        HibernateUtil.shutdown();
    }

    public static void insertCategory() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Category category = new Category();
            category.setName("Electronics");
            category.setDescription("Devices and gadgets.");
            session.persist(category);
            tx.commit();
            System.out.println("Category saved successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void createOrderWithDetails() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            // Assuming user and product already exist (or create them similarly)
            Users user = new Users();
            user.setUsername("john_doe");
            user.setPassword("hashedpassword"); // hash it, bro!
            user.setEmail("john@example.com");
            user.setRole(Users.Role.CUSTOMER);

            Product product = new Product();
            product.setName("Smartphone");
            product.setPrice(new BigDecimal("99.99"));
            product.setStockQuantity(100);
            // Set product's category, etc. (if necessary)

            Orders order = new Orders();
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
            com.google.protobuf.Timestamp protoTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int)(sqlTimestamp.getTime() % 1000 * 1_000_000))
                .build();
            order.setOrderDate(protoTimestamp);
            order.setTotalAmount(new BigDecimal("299.99"));
            order.setUser(user);

            OrderDetails detail = new OrderDetails();
            detail.setQuantity(2);
            detail.setUnitPrice(new BigDecimal("99.99"));
            detail.setOrder(order);
            detail.setProduct(product);

            order.getOrderDetails().add(detail);

            // Save all; cascading might take care of some relationships
            session.persist(user);
            session.persist(product);
            session.persist(order);

            tx.commit();
            System.out.println("Order created successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void fetchOrderAssociations(Long orderId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Orders order = session.get(Orders.class, orderId);
            if(order != null){
                // Since fetch is LAZY, accessing associations inside session
                Users orderUser = order.getUser();
                System.out.println("Order placed by: " + orderUser.getUsername());
                order.getOrderDetails().forEach(detail ->
                        System.out.println("Product: " + detail.getProduct().getName()));
            }
        } finally {
            session.close();
        }
    }
}
