package com.project.daniel.couponproject.util;

import com.project.daniel.couponproject.database.dal.CompanyDAL;
import com.project.daniel.couponproject.database.dal.CouponDAL;
import com.project.daniel.couponproject.database.dal.CustomerDAL;
import com.project.daniel.couponproject.enums.Category;
import com.project.daniel.couponproject.enums.ClientType;
import com.project.daniel.couponproject.error.exceptions.AuthenticationException;
import com.project.daniel.couponproject.error.exceptions.EntityCrudException;
import com.project.daniel.couponproject.facade.AdminFacade;
import com.project.daniel.couponproject.facade.CompanyFacade;
import com.project.daniel.couponproject.facade.CustomerFacade;
import com.project.daniel.couponproject.loginmanger.LoginManger;
import com.project.daniel.couponproject.model.Company;
import com.project.daniel.couponproject.model.Coupon;
import com.project.daniel.couponproject.model.Customer;
import com.project.daniel.couponproject.task.CouponExpirationDailyJob;
import com.project.daniel.couponproject.testing.AdminTest;
import com.project.daniel.couponproject.testing.CompanyTest;
import com.project.daniel.couponproject.testing.CustomerTest;
import com.project.daniel.couponproject.testing.EdgeCaseTest;

import java.time.LocalDate;
import java.util.Scanner;

import static com.project.daniel.couponproject.constants.Constants.NUMBER_OF_CREATE;

public class testUtil {
    public static AdminFacade adminFacade = AdminFacade.instance;
    public static CustomerFacade customerFacade = CustomerFacade.instance;
    public static CompanyFacade companyFacade = CompanyFacade.instance;
    public static CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
    public static Thread dailyJobThread = null;
    public static Scanner scanner = new Scanner(System.in);
    public static LoginManger loginManger = LoginManger.instance;

//-----------------------------------------------Admin-test-util--------------------------------------------------------

//-----------------------------------------------create-company-test----------------------------------------------------
    public static void createCompanyTest() {
        System.out.println("Create new company test : ");
        for (int i = 0; i < NUMBER_OF_CREATE; i++) {
            Company company = new Company("Company" + i, "Company" + i + "@gmail.com", "123456");
            adminFacade.createCompany(company);
            System.out.println(adminFacade.readCompanyByEmail(company.getEmail()));

        }
    }

//---------------------------------------------------update-company-test------------------------------------------------
    public static void updateCompanyTest() {
        System.out.println("Update Company Test : ");
        System.out.println("Company before update : ");
        System.out.println(adminFacade.readCompanyById(1L));
        adminFacade.updateCompany(new Company(1L, "Test@gmail.com", "123123"));
        System.out.println("Company after update");
        System.out.println(adminFacade.readCompanyById(1L));

    }

//-----------------------------------------------delete-company-test----------------------------------------------------
    public static void deleteCompanyTest() {
        System.out.println("Delete Company Test : ");
        System.out.println("Company's before delete : ");
        System.out.println(adminFacade.readAllCompany());
        System.out.println("Company's after delete : ");
        adminFacade.deleteCompany(2L);
        System.out.println(adminFacade.readAllCompany());
    }

//-----------------------------------------------readAll-company-test---------------------------------------------------
    public static void readAllCompanyTest() {
        System.out.println("Read all company test : ");
        System.out.println(adminFacade.readAllCompany());
    }

//-----------------------------------------------read-company-by-id-test------------------------------------------------
    public static void readCompanyByIdTest() {
        System.out.println("Read company by id test : ");
        System.out.println(adminFacade.readCompanyById(3L));
    }

//-----------------------------------------------create-customer-test---------------------------------------------------
    public static void createCustomerTest() {
        System.out.println("Create Customer Test : ");
        for (int i = 0; i < NUMBER_OF_CREATE; i++) {
            Customer customer = new Customer("Customer_FirstName" + i,
                    "Customer_LastName" + i, "Customer" + i + "@gmail.com", "123456");
            adminFacade.createCustomer(customer);
            System.out.println(adminFacade.readCustomerByEmail(customer.getEmail()));
        }

    }

//-----------------------------------------------update-customer-test---------------------------------------------------
    public static void updateCustomerTest() {
        System.out.println("Update Customer Test : ");
        System.out.println("Customer before update : ");
        System.out.println(adminFacade.readCustomer(1L));
        adminFacade.updateCustomer(new Customer(1L, "test1", "test2",
                "test1@gmail.com", "123123"));
        System.out.println("Customer after update : ");
        System.out.println(adminFacade.readCustomer(1L));
    }

//-----------------------------------------------delete-customer-test---------------------------------------------------
    public static void deleteCustomerTest() {
        System.out.println("Delete Customer Test : ");
        System.out.println("all customer before delete :");
        System.out.println(adminFacade.readAllCustomer());
        adminFacade.deleteCustomer(2L);
        System.out.println("all customer after delete :");
        System.out.println(adminFacade.readAllCustomer());
    }

//-----------------------------------------------read-all-customer------------------------------------------------------
    public static void readAllCustomerTest() {
        System.out.println("read all customer test : ");
        System.out.println(adminFacade.readAllCustomer());
    }

//-----------------------------------------------read-customer-by-id-test-----------------------------------------------
    public static void readCustomerByIdTest() {
        System.out.println("read customer by id test : ");
        System.out.println(adminFacade.readCustomer(3L));
    }

//-----------------------------------------------Company-test-util------------------------------------------------------

    //-------------------------------------------add-coupon-------------------------------------------------------------
    public static void addCouponTest() {
        System.out.println("add coupon Test : ");
        companyFacade.addCoupon(new Coupon(1L, Category.ELECTRICITY, "coupon1", "coupon1",
                LocalDate.now(), LocalDate.of(2023, 12, 12), 20, 15.00, "www.coupon1.com"));
        companyFacade.addCoupon(new Coupon(3L, Category.RESTAURANT, "coupon2", "coupon2",
                LocalDate.now(), LocalDate.of(2023, 11, 11), 12, 23.00, "www.coupon2.com"));
        companyFacade.addCoupon(new Coupon(4L, Category.VACATION, "coupon3", "coupon3",
                LocalDate.now(), LocalDate.of(2023, 11, 21), 11, 21.00, "www.coupon3.com"));
        companyFacade.addCoupon(new Coupon(5L, Category.FOOD, "coupon4", "coupon4",
                LocalDate.now(), LocalDate.of(2023, 2, 14), 14, 24.00, "www.coupon4.com"));
        companyFacade.addCoupon(new Coupon(1L, Category.ELECTRICITY, "test", "test",
                LocalDate.now(), LocalDate.of(2023, 12, 12), 20, 15.00, "www.test.com"));
        System.out.println(companyFacade.readAllCoupon(1L));
        System.out.println(companyFacade.readAllCoupon(3L));
        System.out.println(companyFacade.readAllCoupon(4L));
        System.out.println(companyFacade.readAllCoupon(5L));
    }

    //-------------------------------------------update-coupon----------------------------------------------------------
    public static void updateCouponTest(Long companyId) {
        System.out.println("update coupon test : ");
        System.out.println("coupon before update : ");
        System.out.println(companyFacade.readCouponById(1L));
        companyFacade.couponUpdate(new Coupon(1L, companyId, Category.VACATION, "test_update", "test_update",
                LocalDate.now(), LocalDate.of(2023, 12, 12), 5, 20, "www.test.com"));
        System.out.println("coupon after update : ");
        System.out.println(companyFacade.readCouponById(1L));
    }

    //------------------------------------------delete-coupon-by-company-id---------------------------------------------
    public static void deleteCoupon(Long companyId) {
        System.out.println("delete coupon test : ");
        try {
            System.out.println("all coupons before delete :");
            System.out.println(CouponDAL.instance.readAll());
            companyFacade.deleteCoupon(companyId);
            System.out.println("all coupons after delete one :");
            System.out.println(CouponDAL.instance.readAll());
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
    }

    //-----------------------------------------read-all-company-coupon-by-id--------------------------------------------
    public static void readAllCouponByCompanyIdTest(Long companyId) {
        System.out.println("read all coupon by company id  test : ");
        System.out.println(CompanyFacade.instance.readAllCoupon(companyId));
    }

    //----------------------------------------read-all-company-coupon-by-category---------------------------------------
    public static void readAllCompanyCouponByCategory(Long companyId) {
        System.out.println("read all coupon by category test : ");
        System.out.println(CompanyFacade.instance.readAllCouponByCategory(Category.ELECTRICITY, companyId));
    }

    //----------------------------------------read-coupon-by-max-price--------------------------------------------------
    public static void readCouponByMaxPrice(Long companyId) {
        System.out.println("read coupon until price test : ");
        System.out.println(companyFacade.readAllCouponUntilPrice(20.00, companyId));
    }

    //----------------------------------------read-company-with-her-coupons---------------------------------------------
    public static void readCompanyByIdWithCoupons(Long companyId) {
        System.out.println("read company by id test : ");
        System.out.println(companyFacade.readCompanyById(companyId));
    }


//------------------------------------------------Customer-test-util----------------------------------------------------

    //------------------------------------------------purchase-coupon-test----------------------------------------------
    public static void purchaseCoupon(Long CustomerId) {
        System.out.println("Purchase coupon test : ");
        System.out.println("Customer before purchase coupon : ");
        System.out.println(customerFacade.readCustomer(CustomerId));
        customerFacade.purchaseCoupon(CustomerId, 2L);
        System.out.println("Customer after purchase coupon : ");
        System.out.println(customerFacade.readCustomer(CustomerId));
        System.out.println("The purchase was successfully added to purchase history (customer_vs_coupon)");
    }

    //------------------------------------------------read-customer-coupon----------------------------------------------
    public static void readAllCustomerCoupon(Long CustomerId) {
        System.out.println("read all customer coupon test :");
        System.out.println(customerFacade.readCustomerCoupon(CustomerId));
    }

    //-----------------------------------------------read-all-customer-by-category--------------------------------------
    public static void readAllCustomerCouponByCategory(Long CustomerId) {
        System.out.println("read all customer coupon by category(RESTAURANT) test : ");
        System.out.println(customerFacade.readCustomerCouponByCategory(Category.RESTAURANT, CustomerId));
    }

    //------------------------------------------------read-customer-coupon-until-price----------------------------------
    public static void readCustomerCouponUntilMaxPrice(Long CustomerId) {
        System.out.println("read all customer coupon until max price = 25 test : ");
        System.out.println(customerFacade.readCustomerCouponWithMaxPrice(25.00, CustomerId));
    }

    //------------------------------------------------read-customer-with-coupon-----------------------------------------
    public static void readCustomerWithCoupon(Long CustomerId) {
        System.out.println("read customer test (with coupon) : ");
        System.out.println(customerFacade.readCustomer(CustomerId));
    }

//----------------------------------------------Edge-case---------------------------------------------------------------

    //------------------------------------------------add-more-coupon---------------------------------------------------
    public static void addMoreCoupons() {
        System.out.println("add coupon Test : ");
        try {
            companyFacade.addCoupon(new Coupon(1L, Category.RESTAURANT, "couponx1", "couponx1",
                    LocalDate.now(), LocalDate.of(2023, 12, 12), 20, 15.00, "www.couponx1.com"));
            companyFacade.addCoupon(new Coupon(1L, Category.RESTAURANT, "couponx2", "couponx2",
                    LocalDate.now(), LocalDate.of(2023, 12, 12), 10, 12.00, "www.couponx2.com"));
            companyFacade.addCoupon(new Coupon(1L, Category.RESTAURANT, "couponx3", "couponx3",
                    LocalDate.now(), LocalDate.of(2023, 12, 12), 10, 12.00, "www.couponx3.com"));
            companyFacade.addCoupon(new Coupon(3L, Category.RESTAURANT, "couponx3", "couponx3",
                    LocalDate.now(), LocalDate.of(2023, 12, 12), 15, 14.00, "www.couponx3.com"));
            System.out.println("all Coupons : ");
            System.out.println(CouponDAL.instance.readAll());
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
    }

    //-------------------------------------------read-Company-With-More-Than-One-Coupon---------------------------------
    public static void readCompanyWithMoreThanOneCoupon() {
        System.out.println("read company with all coupons test : ");
        System.out.println(companyFacade.readCompanyById(1L));
    }

    //-------------------------------------------delete-Company-With-Mor-eThan-One-Coupon-------------------------------
    public static void deleteCompanyWithMoreThanOneCoupon() {
        try {
            System.out.println("delete company with more than one coupons test : ");
            System.out.println("all company before delete : ");
            System.out.println(adminFacade.readAllCompany());
            System.out.println("all coupons before delete company : ");
            System.out.println(CouponDAL.instance.readAll());
            adminFacade.deleteCompany(1L);
            System.out.println("all company after delete company id 1 : ");
            System.out.println(adminFacade.readAllCompany());
            System.out.println("all coupons belonging to the company have also been deleted ");
            System.out.println(CouponDAL.instance.readAll());
        } catch (EntityCrudException e) {
            System.err.println(e.getMessage());
        }
    }

    //-------------------------------------------Purchase-More-Coupon---------------------------------------------------
    public static void PurchaseMoreCoupon() {
        //purchase more coupon by customer id 1
        System.out.println("customer id 1 purchase more coupons ");
        customerFacade.purchaseCoupon(1L, 3L);
        customerFacade.purchaseCoupon(1L, 4L);
        customerFacade.purchaseCoupon(1L, 9L);
        System.out.println("read customer with all coupons : ");
        System.out.println(customerFacade.readCustomer(1L));
    }

    //--------------------------------delete-Customer-With-Purchase-History---------------------------------------------
    public static void deleteCustomerWithPurchaseHistoryTest() {
        //this method check edge case deleting customer & all purchase history when he purchases more than one coupon
        System.out.println("read customer before delete : ");
        System.out.println(customerFacade.readCustomer(1L));
        adminFacade.deleteCustomer(1L);
        System.out.println("read all customer :");
        System.out.println(adminFacade.readAllCustomer());
        System.out.println("also check db customer vs coupon to see the history purchase are deleted ");
    }

//---------------------------------------------Exception-test-----------------------------------------------------------

    //try to create exist company
    public static void createSameCompany() {
        System.out.println("try to create exist company : ");
        adminFacade.createCompany(new Company("Company2", "Company2@gmail.com", "123123"));
    }

    //try to create exist customer
    public static void createSameCustomer() {
        System.out.println("try to create exist customer : ");
        adminFacade.createCustomer(new Customer(
                "Customer_FirstName2", "Customer_LastName2", "Customer2@gmail.com", "147147"));
    }

    //"try to create coupon to the same company
    public static void createSameCoupon() {
        System.out.println("try to create coupon to the same company : ");
        companyFacade.addCoupon(new Coupon(3L, Category.RESTAURANT, "coupon2", "coupon2",
                LocalDate.now(), LocalDate.of(2023, 12, 12), 20, 15.00, "www.coupon2.com"));

    }



    //----------------------------------------------cast-string-to-client-type------------------------------------------
    public static ClientType castStringToClientType(String type) {
        ClientType currentClientType = null;

        switch (type) {
            case "admin":
                currentClientType = ClientType.ADMINISTRATOR;
                break;
            case "company":
                currentClientType = ClientType.COMPANY;
                break;
            case "customer":
                currentClientType = ClientType.CUSTOMER;
        }

        return currentClientType;
    }

    //----------------------------------------------menu-method---------------------------------------------------------
    public static void menu() throws AuthenticationException, InterruptedException {
        System.out.println(LocalDate.now() + "\nhello dear client, \n" +
                "Login press - 1 to create Table  \n" +
                "Edge case (after finish the regular test) press - 2\n" +
                "Exception test press - 3 \n" +
                "Exit press - 4");

        int inputUser = scanner.nextInt();

        if (inputUser == 1) {
            System.out.println("please enter your email :");

            String email = scanner.next();

            System.out.println("please enter your password :");

            String password = scanner.next();

            System.out.println("please enter your Client type (admin, company, customer) :");

            String clientType = scanner.next();

            ClientType currentClientType = castStringToClientType(clientType);

            //client login check authentication :
            System.out.println("Login success return the correct Facade : "
                    + loginManger.login(email, password, currentClientType));
            System.out.println();

            //authorize check:
            System.out.println("client have authorize ? " + "(" + email + ")" + " : " + loginManger.authorize(email));
            System.out.println();


            switch (currentClientType) {
                case ADMINISTRATOR:
                    //active admin test
                    AdminTest.adminTest();
                    break;
                case COMPANY:
                    try {
                        //read company by email
                        Company company = CompanyDAL.instance.readByEmail(email);
                        //active company test with the company are logged in
                        CompanyTest.companyTest(company.getId());
                    } catch (EntityCrudException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case CUSTOMER:
                    try {
                        //read customer by email
                        Customer customer = CustomerDAL.instance.readByEmail(email);
                        //active customer test with the company are logged in
                        CustomerTest.customerTest(customer.getId());
                    } catch (EntityCrudException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
            }

        }
        if (inputUser == 2) {
            EdgeCaseTest.edgeCaseTest();
        }
        if (inputUser == 3) {
            boolean run = true;
            while (run) {
                System.out.println("throwing exception test : \n" +
                        "create the same company press - 1 \n" +
                        "create the same customer press - 2 \n" +
                        "create coupon to the same company press - 3 \n" +
                        "back to main menu press - 4");

                int input = scanner.nextInt();
                if (input == 1) {
                    testUtil.createSameCompany();
                }
                if (input == 2) {
                    testUtil.createSameCustomer();
                }
                if (input == 3) {
                    testUtil.createSameCoupon();
                }
                if (input == 4) {
                    run = false;
                }
            }
        }
        if (inputUser == 4) {
            System.out.println("Good bye! see you next time :P ");
            dailyJobThread.stop();
            couponExpirationDailyJob.stopRunning();
        }
    }
}



