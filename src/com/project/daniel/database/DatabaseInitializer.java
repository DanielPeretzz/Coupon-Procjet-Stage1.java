package com.project.daniel.database;

import com.project.daniel.database.connection.ConnectionPool;
import com.project.daniel.database.connection.JDBCUtil;
import com.project.daniel.error.DBInitError;
import com.project.daniel.error.exceptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {
    private static final Connection connection;

    static {
        try {
            connection = JDBCUtil.getConnection();
        } catch (SQLException e) {
            throw new DBInitError();
        }
    }

    public static void createTables() throws DBException, SQLException {
        createCategoriesTable();
        createCompaniesTable();
        createCustomersTable();
        createCouponsTable();
        createCustomerToCouponTable();
        setupCategoriesInTable();
    }

    public static void createCategoriesTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `categories` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `name` enum('FOOD','ELECTRICITY','RESTAURANT','VACATION') NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create categories table");
        }
    }

    public static void createCompaniesTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `companies` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `email` varchar(45) NOT NULL,\n" +
                "  `password` bigint NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `name_UNIQUE` (`name`),\n" +
                "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create companies table");
        }
    }

    public static void createCustomersTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `customers` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `first_name` varchar(45) NOT NULL,\n" +
                "  `last_name` varchar(45) NOT NULL,\n" +
                "  `email` varchar(45) NOT NULL,\n" +
                "  `password` bigint NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create customers table");
        }
    }

    public static void createCouponsTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `coupons` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `company_id` bigint NOT NULL,\n" +
                "  `category` varchar(45) NOT NULL,\n" +
                "  `title` varchar(45) NOT NULL,\n" +
                "  `description` varchar(45) DEFAULT NULL,\n" +
                "  `start_date` date NOT NULL,\n" +
                "  `end_date` date NOT NULL,\n" +
                "  `amount` int NOT NULL,\n" +
                "  `price` varchar(45) NOT NULL,\n" +
                "  `image` varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create coupons table");
        }
    }

    public static void createCustomerToCouponTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `customer_to_coupon` (\n" +
                "  `customer_id` bigint NOT NULL,\n" +
                "  `coupon_id` bigint NOT NULL,\n" +
                "  KEY `customer.id_idx` (`customer_id`),\n" +
                "  KEY `coupon.id_idx` (`coupon_id`),\n" +
                "  CONSTRAINT `coupon.id` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`),\n" +
                "  CONSTRAINT `customer.id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create customers and coupons table");
        }
    }

    public static void dropTables() {
        try {
            String query = "DROP TABLE `coupon`.`coupons`, `coupon`.`customer_to_coupon`," +
                    " `coupon`.`customers`, `coupon`.`categories`, `coupon`.`companies` ";
           connection.prepareStatement(query).execute();
            System.out.println("All tables in * coupons_project * have been deleted");
        } catch (SQLException e) {
            throw new DBInitError();
        }
    }

    public static void setupCategoriesInTable() {
        try {
            String query =  "INSERT INTO `coupon`.`categories` (`name`) VALUE ('FOOD')";
            String query1 = "INSERT INTO `coupon`.`categories` (`name`) VALUE ('ELECTRICITY')";
            String query2 = "INSERT INTO `coupon`.`categories` (`name`) VALUE ('RESTAURANT');";
            String query3 = "INSERT INTO `coupon`.`categories` (`name`) VALUE ('VACATION');";


            List<String> queries = new ArrayList<>();
            queries.add(query);
            queries.add(query1);
            queries.add(query2);
            queries.add(query3);

            for (String q : queries) {
                connection.prepareStatement(q).execute();
            }
            System.out.println("Categories table in * coupons_project * has been created with all categories");
        } catch (SQLException e) {
            throw new DBInitError();
        }
    }
}