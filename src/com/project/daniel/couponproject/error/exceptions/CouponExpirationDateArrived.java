package com.project.daniel.couponproject.error.exceptions;




public class CouponExpirationDateArrived extends ApplicationException {

    public CouponExpirationDateArrived() {
        super("This coupon is expired!");
    }
}
