package com.project.daniel.facade;

import com.project.daniel.database.dal.CompanyDAL;
import com.project.daniel.database.dal.CouponDAL;
import com.project.daniel.database.dal.CrudDAL;
import com.project.daniel.database.dal.CustomerDAL;

import javax.swing.text.html.parser.Entity;

public abstract class ClientFacade {
    protected CouponDAL couponDAL;
    protected CustomerDAL customerDAL;
    protected CompanyDAL companyDAL;


    public abstract boolean login(String email, String password);

}
