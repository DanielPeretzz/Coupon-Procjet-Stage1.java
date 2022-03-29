package com.project.daniel.couponproject.facade;

import com.project.daniel.couponproject.database.dal.CompanyDAL;
import com.project.daniel.couponproject.database.dal.CouponDAL;

import com.project.daniel.couponproject.database.dal.CustomerDAL;


public abstract class ClientFacade {
    protected CouponDAL couponDAL;
    protected CustomerDAL customerDAL;
    protected CompanyDAL companyDAL;


    public abstract boolean login(String email, String password);

}
