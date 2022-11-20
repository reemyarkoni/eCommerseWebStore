package main.Services;

import main.Exceptions.IncorrectCredentialsException;
import main.Repositories.CompanyRepository;
import main.Repositories.CouponRepository;
import main.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CustomerRepository customerRepo;
    @Autowired
    protected CompanyRepository companyRepo;
    @Autowired
    protected CouponRepository couponRepo;

    public abstract int login(String email, String password) throws IncorrectCredentialsException;
}
