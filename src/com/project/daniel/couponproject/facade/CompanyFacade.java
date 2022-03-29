package com.project.daniel.couponproject.facade;

import com.project.daniel.couponproject.database.dal.CompanyDAL;
import com.project.daniel.couponproject.database.dal.CouponDAL;
import com.project.daniel.couponproject.enums.Category;
import com.project.daniel.couponproject.enums.EntityType;
import com.project.daniel.couponproject.error.exceptions.*;
import com.project.daniel.couponproject.model.Company;
import com.project.daniel.couponproject.model.Coupon;
import com.project.daniel.couponproject.util.InputUserValidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyFacade extends ClientFacade {

    public static final CompanyFacade instance = new CompanyFacade();

    private final CouponDAL couponDAL;
    private final CompanyDAL companyDAL;

    private CompanyFacade() {
        this.companyDAL = CompanyDAL.instance;
        this.couponDAL = CouponDAL.instance;
    }

    //login method comparison between the input details to Database & return true/false
    @Override
    public boolean login(final String email, String password) {
        try {
            if (!InputUserValidation.isPasswordValid(password)) {
                throw new UserValidationException();
            }
            if (!InputUserValidation.isEmailValid(email)) {
                throw new UserValidationException();
            }
            if (!companyDAL.isExistsByEmail(email)) {
                throw new EntityNotExistException(EntityType.COMPANY);
            }

            Company company = CompanyDAL.instance.readByEmail(email);

            long hasePassword = password.hashCode();

            return Objects.equals(company.getEmail(), email) && Objects.equals(company.getPassword(), String.valueOf(hasePassword));

        } catch (EntityCrudException | UserValidationException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

//-----------------------------------------------------create-coupon----------------------------------------------------

    public Long addCoupon(final Coupon coupon) {
        Long newCouponId = null;
        try {
            List<Coupon> couponList = couponDAL.readAll();

            if (coupon.getEndDate().isBefore(LocalDate.now())) {
                throw new CouponExpirationDateArrived();

            }
            for (Coupon currentCoupon : couponList) {
                if (Objects.equals(currentCoupon.getTitle(), coupon.getTitle()) && Objects.equals(currentCoupon.getCompanyId(), coupon.getCompanyId())) {
                    throw new EntityExistException(EntityType.COUPON);
                }
            }
            newCouponId = couponDAL.create(coupon);
        } catch (EntityCrudException | EntityExistException | CouponExpirationDateArrived e) {
            System.err.println(e.getMessage());
        }
        return newCouponId;
    }

//-------------------------------------------------update-coupon--------------------------------------------------------

    public void couponUpdate(final Coupon coupon) {
        try {
            if (coupon.getEndDate().isBefore(LocalDate.now()) || Objects.equals(coupon.getEndDate(), LocalDate.now())) {
                throw new CouponExpirationDateArrived();
            }

            List<Coupon> couponList = couponDAL.readAll();

            couponDAL.update(coupon);
        } catch (CouponExpirationDateArrived | EntityCrudException e) {
            System.err.println(e.getMessage());
        }
    }

//-------------------------------------------------delete-coupon-by-id--------------------------------------------------

    public void deleteCoupon(final Long couponId) {
        try {

            List<Long> couponIdList = couponDAL.readCouponsByCouponId(couponId);

            if (couponIdList.isEmpty()) {
                couponDAL.delete(couponId);
                return;
            }

            couponDAL.deleteCouponPurchaseById(couponId);
            couponDAL.delete(couponId);

        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
    }

//--------------------------------------------------read-all-coupon-----------------------------------------------------

    public List<Coupon> readAllCoupon(final Long companyId) {
        List<Coupon> couponList = null;
        try {
            couponList = couponDAL.readCouponsByCompanyId(companyId);
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return couponList;
    }

//----------------------------------------------------read-all-coupon-by-category---------------------------------------

    public List<Coupon> readAllCouponByCategory(final Category category, final Long companyId) {
        List<Coupon> couponList = null;
        try {
            couponList = couponDAL.readCouponsByCategory(category, companyId);
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return couponList;
    }

//---------------------------------------------------read-coupon-until-price--------------------------------------------

    public List<Coupon> readAllCouponUntilPrice(final Double maxPrice, final Long companyId) {
        List<Coupon> couponListUntilPrice = new ArrayList<>();
        try {
            List<Coupon> couponList = couponDAL.readCouponsByCompanyId(companyId);

            for (Coupon coupon : couponList) {
                if (coupon.getPrice() <= maxPrice) {
                    couponListUntilPrice.add(coupon);
                }
            }
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return couponListUntilPrice;
    }

//-----------------------------------------------------read-coupon-by-id------------------------------------------------

    public Coupon readCouponById(final Long couponId) {
        Coupon coupon = null;
        try {
            coupon = couponDAL.read(couponId);
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return coupon;
    }

    //------------------------------------------------------read-company-by-id----------------------------------------------
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


}
