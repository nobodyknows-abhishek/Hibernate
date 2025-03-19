package com.abhishek.hibernate.assignment_technical;

import dao.*;
import com.demo.entity.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();
        UsersDAO usersDAO = new UsersDAO();
        OrdersDAO ordersDAO = new OrdersDAO();
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

        // Create Category
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic gadgets and devices");
        categoryDAO.saveCategory(category);

        // Create Product
        Product product = new Product();
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("499.99"));
        product.setStockQuantity(50);
        product.setCategory(category);
        productDAO.saveProduct(product);

        // Create User
        Users user = new Users();
        user.setUsername("john_doe");
        user.setPassword("hashedpassword123");
        user.setEmail("john@example.com");
        user.setRole("CUSTOMER");
        usersDAO.save(user);

        // Create Order
        Orders order = new Orders();
        order.setUser(user);
//        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(new BigDecimal("499.99"));
        ordersDAO.saveOrder(order);

        // Create OrderDetails
        OrderDetails orderDetail = new OrderDetails();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(1);
        orderDetail.setUnitPrice(product.getPrice());
        orderDetailsDAO.saveOrderDetail(orderDetail);

        System.out.println("All records saved successfully!");
    }
}
