package com.project.daniel.error.exceptions;


import com.project.daniel.model.Coupon;

public class CouponExpirationDateArrived extends ApplicationException {

    public CouponExpirationDateArrived(Coupon coupon) {
        super("This coupon id: " + coupon.getId() + "," + " is expired!");
    }
}
