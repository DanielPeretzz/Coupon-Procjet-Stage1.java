package com.project.daniel.couponproject.testing;

import com.project.daniel.couponproject.util.testUtil;

public class AdminTest {

    //-----------------------------------------------admin-test---------------------------------------------------------
    public static void adminTest() {
        System.out.println("Welcome to Admin test : ");
        System.out.println();
//-----------------------------------------------create-company---------------------------------------------------------
        testUtil.createCompanyTest();
        System.out.println("also check in DB");
        System.out.println();
//-----------------------------------------------update-company---------------------------------------------------------
        testUtil.updateCompanyTest();
        System.out.println();
//-----------------------------------------------delete-company---------------------------------------------------------
        testUtil.deleteCompanyTest();
        System.out.println();
//-----------------------------------------------read-all---------------------------------------------------------------
        testUtil.readAllCompanyTest();
        System.out.println();
//-----------------------------------------------read-company-by-id-----------------------------------------------------
        testUtil.readCompanyByIdTest();
        System.out.println();
//-----------------------------------------------create-customer--------------------------------------------------------
        testUtil.createCustomerTest();
        System.out.println("also check in DB");
        System.out.println();
//-----------------------------------------------update-customer--------------------------------------------------------
        testUtil.updateCustomerTest();
        System.out.println();
//-----------------------------------------------delete-customer--------------------------------------------------------
        testUtil.deleteCustomerTest();
        System.out.println();
//-----------------------------------------------read-all-customer------------------------------------------------------
        testUtil.readAllCustomerTest();
        System.out.println();
//-----------------------------------------------read-customer-by-id----------------------------------------------------
        testUtil.readCustomerByIdTest();
        System.out.println();
        System.out.println("Admin test Successfully ! ");
    }
}
