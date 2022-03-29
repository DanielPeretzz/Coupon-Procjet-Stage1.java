package com.project.daniel.couponproject.testing;

import com.project.daniel.couponproject.util.testUtil;

public class CustomerTest {
    //----------------------------------------------customer-test-------------------------------------------------------
    public static void customerTest(Long customerId) {
        System.out.println("Welcome to Customer test : ");
        System.out.println();
//--------------------------------------------------Purchase-coupon-test------------------------------------------------
        testUtil.purchaseCoupon(customerId);
        System.out.println();
//--------------------------------------------------read-all-customer-coupon-test---------------------------------------
        testUtil.readAllCustomerCoupon(customerId);
        System.out.println();
//--------------------------------------------------read-coupon-by-category-test----------------------------------------
        testUtil.readAllCustomerCouponByCategory(customerId);
        System.out.println();
//--------------------------------------------------read-coupon-until-max-price-test------------------------------------
        testUtil.readCustomerCouponUntilMaxPrice(customerId);
        System.out.println();
//--------------------------------------------------read-customer-test--------------------------------------------------
        testUtil.readCustomerWithCoupon(customerId);
        System.out.println();
    }

}
