package com.project.daniel.couponproject.facade;

import com.project.daniel.couponproject.database.dal.CouponDAL;
import com.project.daniel.couponproject.database.dal.CustomerDAL;
import com.project.daniel.couponproject.enums.Category;
import com.project.daniel.couponproject.enums.EntityType;
import com.project.daniel.couponproject.error.exceptions.*;
import com.project.daniel.couponproject.model.Coupon;
import com.project.daniel.couponproject.model.Customer;
import com.project.daniel.couponproject.util.InputUserValidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerFacade extends ClientFacade {
    public static final CustomerFacade instance = new CustomerFacade();

    private final CouponDAL couponDAL;
    private final CustomerDAL customerDAL;

    private CustomerFacade() {
        this.couponDAL = CouponDAL.instance;
        this.customerDAL = CustomerDAL.instance;


    }

    //login method comparison between the input details to Database & return true/false
    @Override
    public boolean login(final String email, String password) {
        try {
            if (!InputUserValidation.isEmailValid(email)) {
                throw new UserValidationException();
            }
            if (!InputUserValidation.isPasswordValid(password)) {
                throw new UserValidationException();
            }
            if (!customerDAL.isExistsByEmail(email)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }

            Customer customer = CustomerDAL.instance.readByEmail(email);

            long hasePassword = password.hashCode();

            return Objects.equals(customer.getEmail(), email) && String.valueOf(hasePassword).equals(customer.getPassword());

        } catch (UserValidationException | EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

//----------------------------------------------------purchase-coupon---------------------------------------------------

    public void purchaseCoupon(final Long customerID, final Long couponId) {
        try {
            Coupon currentCoupon = couponDAL.read(couponId);
            Customer customer = readCustomer(customerID);
            if (currentCoupon.getAmount() <= 0) {
                throw new FailedToPurchaseException(currentCoupon);
            }
            if (currentCoupon.getEndDate().isBefore(LocalDate.now())) {
                throw new CouponExpirationDateArrived();
            }
            assert false;
            for (Coupon coupon : customer.getCouponList()) {
                if (Objects.equals(coupon.getId(), couponId)) {
                    throw new FailedToPurchaseException(coupon);
                }
            }
            couponDAL.updateAmount(currentCoupon);
            couponDAL.addCouponPurchase(customerID, currentCoupon.getId());

        } catch (EntityCrudException | FailedToPurchaseException | CouponExpirationDateArrived e) {
            System.err.println(e.getMessage());
        }
    }

//-----------------------------------------read-customer-coupon---------------------------------------------------------

    public List<Coupon> readCustomerCoupon(final Long customerId) {
        List<Coupon> couponList = null;
        try {
            if (!customerDAL.isExistsById(customerId)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            List<Long> couponIdList = couponDAL.readCouponsByCustomerId(customerId);

            couponList = new ArrayList<>();

            for (Long id : couponIdList) {
                couponList.add(couponDAL.read(id));
            }

        } catch (EntityNotExistException | EntityCrudException e) {
            System.err.println(e.getMessage());
        }
        return couponList;
    }

//------------------------------------------read-customer-coupon-by-category--------------------------------------------

    public List<Coupon> readCustomerCouponByCategory(final Category category, final Long customerId) {
        List<Coupon> newCouponList = null;
        try {
            if (!customerDAL.isExistsById(customerId)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            List<Coupon> currentCouponList = readCustomerCoupon(customerId);
            newCouponList = new ArrayList<>();

            for (Coupon coupon : currentCouponList) {
                if (coupon.getCategory() == category) {
                    newCouponList.add(coupon);
                }
            }

        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return newCouponList;
    }

//---------------------------------------------read-customer-coupon-until-price-----------------------------------------

    public List<Coupon> readCustomerCouponWithMaxPrice(final double maxPrice, final Long customerId) {

        List<Coupon> newCouponList = null;
        try {
            if (!customerDAL.isExistsById(customerId)) {
                throw new EntityNotExistException(EntityType.CUSTOMER);
            }
            List<Coupon> currentCouponList = readCustomerCoupon(customerId);
            newCouponList = new ArrayList<>();

            for (Coupon coupon : currentCouponList) {
                if (coupon.getPrice() <= maxPrice) {
                    newCouponList.add(coupon);
                }
            }

        } catch (EntityCrudException | EntityNotExistException e) {
            System.err.println(e.getMessage());
        }
        return newCouponList;
    }

//------------------------------------------------read-customer-by-id---------------------------------------------------

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


}
