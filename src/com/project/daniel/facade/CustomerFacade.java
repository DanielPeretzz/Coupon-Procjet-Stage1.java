package com.project.daniel.facade;

import com.project.daniel.database.dal.CompanyDAL;
import com.project.daniel.database.dal.CouponDAL;
import com.project.daniel.database.dal.CustomerDAL;
import com.project.daniel.error.exceptions.EntityCrudException;
import com.project.daniel.model.Customer;

import java.util.List;

public class CustomerFacade extends ClientFacade{
    public static final CustomerFacade instance = new CustomerFacade();

    private final CouponDAL couponDAL;
    private final CustomerDAL customerDAL;
    private final CompanyDAL companyDAL;

    private CustomerFacade(){
        this.companyDAL = CompanyDAL.instance;
        this.couponDAL = CouponDAL.instance;
        this.customerDAL = CustomerDAL.instance;


    }

    @Override
    public boolean login(String email, String password) {
    return false;
    }
}
