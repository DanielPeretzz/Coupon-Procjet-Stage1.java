package com.project.daniel;



import com.project.daniel.database.DatabaseInitializer;
import com.project.daniel.database.dal.CouponDAL;
import com.project.daniel.enums.Category;
import com.project.daniel.error.exceptions.DBException;
import com.project.daniel.error.exceptions.EntityCrudException;
import com.project.daniel.facade.AdminFacade;
import com.project.daniel.facade.CompanyFacade;
import com.project.daniel.model.Company;
import com.project.daniel.model.Coupon;
import com.project.daniel.model.Customer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CouponApplication {

    public static void main(String[] args) throws EntityCrudException {

      /*  CompanyFacade.instance.addCoupon(new Coupon(1L, Category.RESTAURANT,"hara","hara", LocalDate.now(),LocalDate.of(2023,5,20),25,10.0,"hara"));
*/

        /*AdminFacade.instance.createCompany(new Company("apro","po@gmail.com","asd123"));*/
    }
}
