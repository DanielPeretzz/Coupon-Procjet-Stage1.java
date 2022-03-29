package com.project.daniel.couponproject.facade;

import com.project.daniel.couponproject.database.dal.CompanyDAL;
import com.project.daniel.couponproject.database.dal.CouponDAL;
import com.project.daniel.couponproject.database.dal.CustomerDAL;
import com.project.daniel.couponproject.enums.EntityType;
import com.project.daniel.couponproject.error.exceptions.*;
import com.project.daniel.couponproject.model.Company;
import com.project.daniel.couponproject.model.Coupon;
import com.project.daniel.couponproject.model.Customer;
import com.project.daniel.couponproject.util.InputUserValidation;

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

    //login method to check hard coded if admin have access to login
    @Override
    public boolean login(final String email, final String password) {
        return Objects.equals(email, "admin@admin.com") && Objects.equals(password, "admin");
    }

//------------------------------------------------create-company--------------------------------------------------------

    public Long createCompany(final Company company) {
        Long companyId = null;
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

            companyId = companyDAL.create(company);
        } catch (EntityCrudException | UserValidationException | EntityExistException e) {
            System.err.println(e.getMessage());
        }
        return companyId;
    }

//------------------------------------------------update-company--------------------------------------------------------

    public void updateCompany(final Company company) {
        try {
            if (!companyDAL.isExistsById(company.getId())) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }

            List<Company> companyList = companyDAL.readAll();

            for (Company currentCompany : companyList) {
                if (Objects.equals(currentCompany.getEmail(), company.getEmail()) ||
                        Objects.equals(currentCompany.getName(), company.getName())) {
                    throw new EntityExistException(EntityType.COMPANY);
                }
            }

            companyDAL.update(company);
        } catch (EntityCrudException | EntityNotExistException | EntityExistException e) {
            System.err.println(e.getMessage());
        }
    }

//------------------------------------------------delete-company--------------------------------------------------------

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
            System.err.println(e.getMessage());
        }
    }

//------------------------------------------------read-all-company------------------------------------------------------

    public List<Company> readAllCompany() {
        List<Company> companyList = null;
        try {
            companyList = companyDAL.readAll();
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return companyList;
    }

    public Company readCompanyById(final Long companyId) {
        Company company = null;
        try {
            if (!companyDAL.isExistsById(companyId)) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }

            List<Coupon> couponList = couponDAL.readCouponsByCompanyId(companyId);
            company = companyDAL.read(companyId);
            company.setCouponList(couponList);

        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return company;
    }

//------------------------------------------------create-customer-------------------------------------------------------

    public Long createCustomer(final Customer customer) {
        Long customerId = null;
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
            customerId = customerDAL.create(customer);
        } catch (EntityCrudException | EntityExistException | UserValidationException e) {
            System.err.println(e.getMessage());
        }
        return customerId;
    }

//------------------------------------------------update-customer-------------------------------------------------------

    public void updateCustomer(final Customer customer) {
        try {
            if (!customerDAL.isExistsById(customer.getId())) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            customerDAL.update(customer);
        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
    }

//------------------------------------------------delete-customer-------------------------------------------------------

    public void deleteCustomer(final Long customerId) {
        try {
            if (!customerDAL.isExistsById(customerId)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }

            Customer customer = readCustomer(customerId);

            if (customer.getCouponList() != null) {
                CouponDAL.instance.deleteCustomerPurchaseById(customerId);
            }

            customerDAL.delete(customerId);
        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
    }

//-------------------------------------------------read-customer--------------------------------------------------------

    public Customer readCustomer(final Long customerId) {
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
            System.err.println(e.getMessage());
        }
        return customer;
    }

    //-------------------------------------------------read-all-customer----------------------------------------------------
    public List<Customer> readAllCustomer() {
        List<Customer> customerList = null;
        try {
            customerList = customerDAL.readAll();
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return customerList;
    }

//-------------------------------------------------read-customer-by-email-----------------------------------------------

    public Customer readCustomerByEmail(final String email) {
        Customer customer = null;
        try {
            if (!customerDAL.isExistsByEmail(email)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            customer = customerDAL.readByEmail(email);

        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }

//------------------------------------------------read-company-by-email-------------------------------------------------

    public Company readCompanyByEmail(final String email) {
        Company company = null;
        try {
            if (!companyDAL.isExistsByEmail(email)) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }
            company = companyDAL.readByEmail(email);

        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return company;
    }

}
