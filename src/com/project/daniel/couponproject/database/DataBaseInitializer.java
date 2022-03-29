package com.project.daniel.couponproject.database;


import com.project.daniel.couponproject.database.connection.ConnectionPool;
import com.project.daniel.couponproject.error.DBError;
import com.project.daniel.couponproject.error.DBType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


//-------------------------------------------------data-base-initializer------------------------------------------------
public class DataBaseInitializer {

    //all method to initialize the SQL DB
    public static void createTables() {
        createCompany();
        createCategory();
        createCoupon();
        createCustomer();
        createCustomerVsCoupon();
        setupCategoriesInTable();
    }


    // create company table
    public static void createCompany() {
        try {
            ConnectionPool.getInstance().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `coupons_project`.`companies` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `email` VARCHAR(45) NOT NULL,\n" +
                    "  `password` VARCHAR(45) NOT NULL,\n" +
                    "   PRIMARY KEY (`id`),\n" +
                    "   UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                    "   UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,\n" +
                    "   UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);"
            ).execute();
        } catch (SQLException | InterruptedException e) {
            throw new DBError(DBType.CREATE_TABLES);
        }
    }

    //create category table
    public static void createCategory() {
        try {
            ConnectionPool.getInstance().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `coupons_project`.`categories` (\n" +
                    " `category` VARCHAR(45) NOT NULL,\n" +
                    "  UNIQUE INDEX `category_UNIQUE` (`category` ASC));"
            ).execute();
        } catch (SQLException | InterruptedException e) {
            throw new DBError(DBType.CREATE_TABLES);
        }
    }

    //create coupon table
    public static void createCoupon() {
        try {
            ConnectionPool.getInstance().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `coupons_project`.`coupons` (\n" +
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
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
            ).execute();
        } catch (SQLException | InterruptedException e) {
            throw new DBError(DBType.CREATE_TABLES);
        }
    }

    //create customer table
    public static void createCustomer() {
        try {
            ConnectionPool.getInstance().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `coupons_project`.`customers` (\n" +
                    " `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    " `first_name` VARCHAR(45) NOT NULL,\n" +
                    " `last_name` VARCHAR(45) NOT NULL,\n" +
                    " `email` VARCHAR(45) NOT NULL,\n" +
                    " `password` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);"
            ).execute();
        } catch (SQLException | InterruptedException e) {
            throw new DBError(DBType.CREATE_TABLES);
        }
    }

    //create customer vs coupon table Centralizes the purchase data between customer to coupon purchase
    public static void createCustomerVsCoupon() {
        try {
            ConnectionPool.getInstance().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `coupons_project`.`customer_vs_coupon` (\n" +
                    " `customer_id` BIGINT NOT NULL,\n" +
                    " `coupon_id` BIGINT NOT NULL,\n" +
                    "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
                    "  CONSTRAINT `coupon_id`\n" +
                    "  FOREIGN KEY (`coupon_id`)\n" +
                    "  REFERENCES `coupons_project`.`coupons` (`id`)\n" +
                    "  ON DELETE NO ACTION\n" +
                    "  ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `customer_id`\n" +
                    "  FOREIGN KEY (`customer_id`)\n" +
                    "  REFERENCES `coupons_project`.`customers` (`id`)\n" +
                    "  ON DELETE NO ACTION\n" +
                    "  ON UPDATE NO ACTION);"
            ).execute();
        } catch (SQLException | InterruptedException e) {
            throw new DBError(DBType.CREATE_TABLES);
        }
    }

    //Initializing the category table with category's
    public static void setupCategoriesInTable() {
        Connection connection = null;
        String[] category = {"FOOD", "ELECTRICITY", "RESTAURANT", "VACATION"};
        try {
            connection = ConnectionPool.getInstance().getConnection();

            for (String currentCategory : category) {
                String query = "INSERT INTO `coupons_project`.`categories` (`category`) VALUE (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, currentCategory);
                preparedStatement.execute();
            }
        } catch (InterruptedException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //drop all the table are Initialized
    public static void dropTables() {
        try {
            String query = "DROP TABLE `coupons_project`.`coupons`, `coupons_project`.`customer_vs_coupon`," +
                    " `coupons_project`.`customers`, `coupons_project`.`categories`, `coupons_project`.`companies` ";
            ConnectionPool.getInstance().getConnection().prepareStatement(query).execute();
        } catch (SQLException | InterruptedException e) {
            throw new DBError(DBType.DROP_TABLES);
        }
    }
}
