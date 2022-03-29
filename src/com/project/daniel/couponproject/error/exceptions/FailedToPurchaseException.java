package com.project.daniel.couponproject.error.exceptions;

import com.project.daniel.couponproject.model.Coupon;

public class FailedToPurchaseException extends ApplicationException {
    public FailedToPurchaseException(Coupon coupon) {
        super("Failed to purchase Coupon id : " + coupon.getId());
    }
}
