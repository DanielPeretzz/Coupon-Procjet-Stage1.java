package com.project.daniel.couponproject.loginmanger;

import com.project.daniel.couponproject.enums.ClientType;
import com.project.daniel.couponproject.error.exceptions.AuthenticationException;
import com.project.daniel.couponproject.error.exceptions.AuthorizationException;
import com.project.daniel.couponproject.facade.AdminFacade;
import com.project.daniel.couponproject.facade.ClientFacade;
import com.project.daniel.couponproject.facade.CompanyFacade;
import com.project.daniel.couponproject.facade.CustomerFacade;

import java.util.HashSet;
import java.util.Set;


public class LoginManger {
    public static LoginManger instance = new LoginManger();

    private final AdminFacade adminFacade;
    private final CompanyFacade companyFacade;
    private final CustomerFacade customerFacade;
    private final Set<String> authenticatedUsers = new HashSet<>();


    private LoginManger() {
        adminFacade = AdminFacade.instance;
        companyFacade = CompanyFacade.instance;
        customerFacade = CustomerFacade.instance;
    }

    //login method comparison between the input details to Database & return the correct facade
    public ClientFacade login(String email, String password, ClientType clientType) throws AuthenticationException {

        ClientFacade clientFacade = null;
        boolean isAuthenticated = false;

        switch (clientType) {
            case ADMINISTRATOR:
                clientFacade = adminFacade;
                break;
            case COMPANY:
                clientFacade = companyFacade;
                break;
            case CUSTOMER:
                clientFacade = customerFacade;
                break;
            default:
                throw new AuthenticationException(email, clientType);
        }

        isAuthenticated = clientFacade.login(email, password);

        if (isAuthenticated) {
            authenticatedUsers.add(email);
            return clientFacade;
        }
        throw new AuthenticationException(email, clientType);
    }

    public boolean authorize(final String email) {
        try {
            if (!LoginManger.instance.authenticatedUsers.contains(email)) {
                throw new AuthorizationException(email);
            }
        } catch (AuthorizationException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

}
