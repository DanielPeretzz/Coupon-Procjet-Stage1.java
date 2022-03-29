package com.project.daniel.couponproject.util;

import com.project.daniel.couponproject.enums.Category;
import com.project.daniel.couponproject.model.Company;
import com.project.daniel.couponproject.model.Coupon;
import com.project.daniel.couponproject.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ObjectExtractionUtil {

    //-------------------------------company-Result---------------------------------------------------------------------
    //return Company object from the ResultSet
    public static Company companyResult(ResultSet result) throws SQLException {
        return new Company(
                result.getLong("id"),
                result.getString("name"),
                result.getString("email"),
                result.getString("password")
        );
    }

    //-----------------------------------customer-Result--------------------------------------------------------------------
    //return Customer object from the ResultSet
    public static Customer customerResult(ResultSet result) throws SQLException {
        return new Customer(
                result.getLong("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email"),
                result.getString("password")
        );
    }

    //-----------------------------------coupon-Result----------------------------------------------------------------------
    //return Coupon object from the ResultSet
    public static Coupon couponResult(ResultSet result) throws SQLException {
        return new Coupon(
                result.getLong("id"),
                result.getLong("company_id"),
                Category.valueOf(result.getString("category")),
                result.getString("title"),
                result.getString("description"),
                LocalDate.parse(result.getString("start_date")),
                LocalDate.parse(result.getString("end_date")),
                result.getInt("amount"),
                result.getDouble("price"),
                result.getString("image")
        );
    }


}
