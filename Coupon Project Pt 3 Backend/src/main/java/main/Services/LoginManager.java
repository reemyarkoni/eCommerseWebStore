package main.Services;

import main.Beans.ClientType;
import main.Exceptions.IncorrectCredentialsException;
import main.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    @Autowired
    AdminService adminService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;

    public LoginManager() {
    }

    /**checks client type and tries to log in with it and given email and password
     * @param email email of the requested user
     * @param password password of the requested user
     * @param clientType client type of the requested user (ADMIN/COMPANY/CUSTOMER)
     * @return a service of the requested admin/company/customer
     * @throws IncorrectCredentialsException incorrect email or password
     * @throws NotFoundException unknown client type
     */
    public ClientService Login(String email, String password, ClientType clientType)
            throws IncorrectCredentialsException, NotFoundException {
        switch (clientType){

            case ADMIN:
                if (email.equals("admin@admin.com")  && password.equals("admin")) {
                    return adminService;
                }
                else throw new IncorrectCredentialsException();

            case COMPANY:
                if(companyService.login(email, password) > 0) {
                    return companyService;
                }
                else throw new IncorrectCredentialsException();

            case CUSTOMER:
                if(customerService.login(email, password) > 0){
                    return customerService;
                }
                else throw new IncorrectCredentialsException();
        }
        throw new NotFoundException("Unknown client type");
    }
}
