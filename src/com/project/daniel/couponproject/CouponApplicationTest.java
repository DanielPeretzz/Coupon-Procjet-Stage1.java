package com.project.daniel.couponproject;

import com.project.daniel.couponproject.database.DataBaseInitializer;
import com.project.daniel.couponproject.error.exceptions.ApplicationException;
import com.project.daniel.couponproject.error.exceptions.AuthenticationException;
import com.project.daniel.couponproject.util.testUtil;


public class CouponApplicationTest {

    public static void main(String[] args) {
    //run test
        try {
            testAll();
        } catch (ApplicationException e) {
            System.err.println(e.getMessage());
        }

    }

    //----------------------------------------------test-all-method-----------------------------------------------------
    public static void testAll() throws ApplicationException {
        //drop all table in SQL
        DataBaseInitializer.dropTables();
        //create all table in SQL
        DataBaseInitializer.createTables();
        System.out.println();
        //create & inject daily job task to thread & start
        testUtil.dailyJobThread = new Thread(testUtil.couponExpirationDailyJob);
        testUtil.couponExpirationDailyJob.startRunning();
        testUtil.dailyJobThread.start();

        //call menu method in while loop
        while (testUtil.couponExpirationDailyJob.isRunning()) {
            try {
                //menu method
                testUtil.menu();
            } catch (InterruptedException | AuthenticationException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}




