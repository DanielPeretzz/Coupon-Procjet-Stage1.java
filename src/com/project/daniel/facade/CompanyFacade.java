package com.project.daniel.facade;

import com.project.daniel.database.dal.CompanyDAL;
import com.project.daniel.database.dal.CouponDAL;
import com.project.daniel.database.dal.CustomerDAL;
import com.project.daniel.enums.Category;
import com.project.daniel.enums.EntityType;
import com.project.daniel.error.exceptions.*;
import com.project.daniel.model.Company;
import com.project.daniel.model.Coupon;
import com.project.daniel.util.InputUserValidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyFacade extends ClientFacade {

    public static final CompanyFacade instance = new CompanyFacade();

    private final CouponDAL couponDAL;
    private final CustomerDAL customerDAL;
    private final CompanyDAL companyDAL;

    private CompanyFacade() {
        this.companyDAL = CompanyDAL.instance;
        this.couponDAL = CouponDAL.instance;
        this.customerDAL = CustomerDAL.instance;


    }

    @Override
    public boolean login(String email, String password) {
        try {
            if (!InputUserValidation.isPasswordValid(password)) {
                throw new UserValidationException();
            }
            if (!InputUserValidation.isEmailValid(email)) {
                throw new UserValidationException();
            }
            Company company = CompanyDAL.instance.readByEmail(email);

            if (company == null) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }

            long hasePassword = password.hashCode();

            return Objects.equals(company.getEmail(), email) && Objects.equals(company.getPassword(), String.valueOf(hasePassword));

        } catch (EntityCrudException | UserValidationException | EntityNotExistException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Long addCoupon(Coupon coupon) {
        Long newCouponId = null;
        try {
            List<Coupon> couponList = couponDAL.readAll();

            for (Coupon currentCoupon : couponList) {
                if (Objects.equals(currentCoupon.getTitle(), coupon.getTitle()) && Objects.equals(currentCoupon.getCompanyId(), coupon.getCompanyId())) {
                    throw new EntityExistException(EntityType.COUPON);
                }
            }
            newCouponId = couponDAL.create(coupon);
        } catch (EntityCrudException | EntityExistException e) {
            e.printStackTrace();
        }
        return newCouponId;
    }

    public void couponUpdate(Coupon coupon) {
        try {
            if (coupon.getStartDate().isAfter(LocalDate.now())) {
                throw new CouponExpirationDateArrived(coupon);
            }
            if (coupon.getEndDate().isBefore(LocalDate.now())) {
                throw new CouponExpirationDateArrived(coupon);
            }

            couponDAL.update(coupon);
        } catch (CouponExpirationDateArrived | EntityCrudException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoupon(Long couponId) {
        try {
            couponDAL.deleteCouponPurchaseById(couponId);
            couponDAL.delete(couponId);
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
    }

    public List<Coupon> readAllCoupon(Long companyId) {
        List<Coupon> couponList = null;
        try {
            couponList = couponDAL.readCouponsByCompanyId(companyId);
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
        return couponList;
    }

    public List<Coupon> readAllCouponByCategory(Category category,Long companyId){
        List<Coupon> couponList = null;
        try {
            couponList = couponDAL.readCouponsByCategory(category,companyId);
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
        return couponList;
    }

    public List<Coupon> readAllCouponUntilPrice(final Double maxPrice, final Long companyId){
        List<Coupon> couponListUntilPrice = new ArrayList<>();
        try {
            List<Coupon> couponList = couponDAL.readCouponsByCompanyId(companyId);

            for (Coupon coupon : couponList) {
                if (coupon.getPrice() <= maxPrice){
                    couponListUntilPrice.add(coupon);
                }
            }
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
        return couponListUntilPrice;
    }

    public Company readCompany(Long companyId){
        Company company = null;
        try {
            company = companyDAL.read(companyId);
        } catch (EntityCrudException e) {
            e.printStackTrace();
        }
        return company;
    }
}
