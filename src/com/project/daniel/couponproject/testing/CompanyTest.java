package com.project.daniel.couponproject.testing;

import com.project.daniel.couponproject.util.testUtil;

public class CompanyTest {

    //----------------------------------------------company-test--------------------------------------------------------
    public static void companyTest(Long companyId) {
        System.out.println("Welcome to Company test : ");
        System.out.println();
//--------------------------------------------------add-coupon-test-----------------------------------------------------
        testUtil.addCouponTest();
        System.out.println();
//--------------------------------------------------update-coupon-------------------------------------------------------
        testUtil.updateCouponTest(companyId);
        System.out.println();
//--------------------------------------------------delete-coupon-------------------------------------------------------
        testUtil.deleteCoupon(companyId);
        System.out.println();
//--------------------------------------------------read-all-coupon-by-company-id-test----------------------------------
        testUtil.readAllCouponByCompanyIdTest(companyId);
        System.out.println();
//--------------------------------------------------read-all-coupon-by-category-test------------------------------------
        testUtil.readAllCompanyCouponByCategory(companyId);
        System.out.println();
//--------------------------------------------------read-coupon-until-price---------------------------------------------
        testUtil.readCouponByMaxPrice(companyId);
        System.out.println();
//--------------------------------------------------read-company-by-id--------------------------------------------------
        testUtil.readCompanyByIdWithCoupons(companyId);
        System.out.println();
        System.out.println("Company test Successfully ! ");
    }

}
