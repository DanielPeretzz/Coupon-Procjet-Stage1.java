package com.project.daniel.facade;

import com.project.daniel.database.dal.CompanyDAL;
import com.project.daniel.database.dal.CouponDAL;
import com.project.daniel.database.dal.CustomerDAL;
import com.project.daniel.enums.EntityType;
import com.project.daniel.error.exceptions.EntityCrudException;
import com.project.daniel.error.exceptions.EntityExistException;
import com.project.daniel.error.exceptions.EntityNotExistException;
import com.project.daniel.error.exceptions.UserValidationException;
import com.project.daniel.model.Company;
import com.project.daniel.model.Coupon;
import com.project.daniel.model.Customer;
import com.project.daniel.util.InputUserValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminFacade extends ClientFacade {
    public static AdminFacade instance = new AdminFacade();

    private final CouponDAL couponDAL;
    private final CustomerDAL customerDAL;
    private final CompanyDAL companyDAL;

    private AdminFacade() {
        this.companyDAL = CompanyDAL.instance;
        this.couponDAL = CouponDAL.instance;
        this.customerDAL = CustomerDAL.instance;
    }


    @Override
    public boolean login(String email, String password) {
        return Objects.equals(email, "admin@admin.com") && Objects.equals(password, "admin");
    }

    public Long createCompany(Company company) {
        try {
            if (!InputUserValidation.isPasswordValid(String.valueOf(company.getPassword()))) {
                throw new UserValidationException();
            }

            if (!InputUserValidation.isEmailValid(company.getEmail())) {
                throw new UserValidationException();
            }

            if (companyDAL.isExistsByEmail(company.getEmail())) {
                throw new EntityExistException(EntityType.COMPANY);
            }

            if (companyDAL.isExistsByName(company.getName())) {
                throw new EntityExistException(EntityType.COMPANY);
            }

            return companyDAL.create(company);
        } catch (EntityCrudException | UserValidationException | EntityExistException e) {
            throw new RuntimeException("Failed to create company with id : " + company.getId());
        }
    }

    public void updateCompany(Company company) {
        try {
            if (!companyDAL.isExistsById(company.getId())) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }
            companyDAL.update(company);
        } catch (EntityCrudException | EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompany(final Long companyId) {
        try {
            if (!companyDAL.isExistsById(companyId)) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }
            List<Coupon> couponList = couponDAL.readAll();
            for (Coupon coupon : couponList) {
                if (Objects.equals(coupon.getCompanyId(), companyId)) {
                    couponDAL.deleteCouponPurchaseById(coupon.getId());
                    couponDAL.delete(coupon.getId());
                }
            }
            companyDAL.delete(companyId);
        } catch (EntityCrudException | EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    public List<Company> readAllCompany() {
        List<Company> companyList = null;
        try {
            companyList = companyDAL.readAll();
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    public Company readCompanyById(Long companyId) {
        Company company = null;
        try {
            if (!companyDAL.isExistsById(companyId)) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }
            List<Coupon> couponList = couponDAL.readCouponsByCompanyId(companyId);
            company = companyDAL.read(companyId);
            company.setCouponList(couponList);
        } catch (EntityCrudException | EntityNotExistException e) {
            e.printStackTrace();
        }
        return company;
    }

    public void createCustomer(Customer customer) {
        try {
            if (!InputUserValidation.isPasswordValid(String.valueOf(customer.getPassword()))) {
                throw new UserValidationException();
            }

            if (!InputUserValidation.isEmailValid(customer.getEmail())) {
                throw new UserValidationException();
            }
            if (customerDAL.isExistsByEmail(customer.getEmail())) {
                throw new EntityExistException(EntityType.CUSTOMER);
            }
            customerDAL.create(customer);
        } catch (EntityCrudException | EntityExistException | UserValidationException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        try {
            if (!customerDAL.isExistsById(customer.getId())) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            customerDAL.update(customer);
        } catch (EntityCrudException | EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Long customerId) {
        try {
            if (!customerDAL.isExistsById(customerId)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }

            CouponDAL.instance.deleteCouponPurchaseById(customerId);
            customerDAL.delete(customerId);
        } catch (EntityCrudException | EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    public Customer readCustomer(Long customerId) {
        Customer customer = null;
        try {
            if (!customerDAL.isExistsById(customerId)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            customer = customerDAL.read(customerId);

            List<Long> couponIdList = couponDAL.readCouponsByCustomerId(customerId);

            List<Coupon> couponList = new ArrayList<>();

            for (Long id : couponIdList) {
                couponList.add(couponDAL.read(id));
            }
            customer.setCouponList(couponList);

        } catch (EntityCrudException | EntityNotExistException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> readAllCustomer() {
        List<Customer> customerList = null;
        try {
            customerList = customerDAL.readAll();
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
        return customerList;
    }


}
