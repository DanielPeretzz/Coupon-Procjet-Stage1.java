package com.project.daniel.couponproject.testing;

import com.project.daniel.couponproject.util.testUtil;

public class EdgeCaseTest {
    //-------------------------------------------------edge-case-test---------------------------------------------------
    public static void edgeCaseTest() {
        System.out.println("more edge case test : ");
        System.out.println();
//-------------------------------------------------add-more-coupon-for-test---------------------------------------------
        testUtil.addMoreCoupons();
        System.out.println();
//-------------------------------------------------read-company-with-all-coupons----------------------------------------
        testUtil.readCompanyWithMoreThanOneCoupon();
        System.out.println();
//-------------------------------------------------delete-company-with-all-her-coupons----------------------------------
        testUtil.deleteCompanyWithMoreThanOneCoupon();
        System.out.println();
//-------------------------------------------------purchase-more-coupon-------------------------------------------------
        testUtil.PurchaseMoreCoupon();
        System.out.println();
//-------------------------------------------------delete-Customer------------------------------------------------------
        testUtil.deleteCustomerWithPurchaseHistoryTest();
        System.out.println();
    }

}
