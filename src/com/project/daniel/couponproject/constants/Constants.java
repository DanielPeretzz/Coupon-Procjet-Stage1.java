package com.project.daniel.couponproject.constants;

public class Constants {
    public static int SLEEP_THREAD_TIME = 86400000;
    public static final String SQL_URL = "jdbc:mysql://localhost:3306/coupons_project";
    public static final String SQL_USER = "root";
    public static final String SQL_PASS = System.getenv("SQL_PASSWORD");
    public static final int NUMBER_OF_CONNECTIONS = 20;
    public static final int NUMBER_OF_CREATE = 5;

}
