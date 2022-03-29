package com.project.daniel.couponproject.task;

import com.project.daniel.couponproject.database.dal.CouponDAL;
import com.project.daniel.couponproject.error.exceptions.EntityCrudException;
import com.project.daniel.couponproject.facade.CompanyFacade;
import com.project.daniel.couponproject.model.Coupon;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.project.daniel.couponproject.constants.Constants.SLEEP_THREAD_TIME;

@NoArgsConstructor
@Data

//--------------------------------------------coupon-daily-job----------------------------------------------------------
public class CouponExpirationDailyJob implements Runnable {
    private final CompanyFacade companyFacade = CompanyFacade.instance;
    private final CouponDAL couponDAL = CouponDAL.instance;
    private boolean isRunning = false;


    //this task Runs parallel to the software and checks if there is a coupon date has been expired & delete him form DB
    @Override
    public void run() {
        startRunning();
        try {
            System.out.println("Daily job start");
            List<Coupon> couponList = couponDAL.readAll();

            while (isRunning) {
                for (Coupon coupon : couponList) {
                    if (coupon.getEndDate().isBefore(LocalDate.now())) {
                        companyFacade.deleteCoupon(coupon.getId());
                        System.out.println("Coupon name : " + coupon.getTitle() + " is expired and delete form data base");
                    }
                }
                Thread.sleep(SLEEP_THREAD_TIME);
            }
        } catch (EntityCrudException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void startRunning() {
        isRunning = true;
    }

    public void stopRunning() {
        isRunning = false;
    }
}
