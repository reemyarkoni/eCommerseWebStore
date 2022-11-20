package main.Controllers;

import com.auth0.jwt.algorithms.Algorithm;
import main.Beans.*;
import main.Exceptions.IncorrectCredentialsException;
import main.Exceptions.NotFoundException;
import main.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.JWT;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private AdminService adminService = new AdminService();
    private final LoginManager loginManager;
    @Autowired
    private Map<String, Session> sessionPack;

    public AuthController(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials){
        try{
            ClientService service = loginManager.Login(credentials.getEmail(), credentials.getPassword(), credentials.getType());

            String token ="";
            switch (credentials.getType()) {
                case ADMIN:
                    token = createToken(new Admin());
                    break;
                case COMPANY:
                    token = createToken(((CompanyService)service).getCompanyDetail());
                    break;
                case CUSTOMER:
                    token = createToken(((CustomerService)service).getCustomerDetail());
                    break;
            }
            sessionPack.put(token, new Session(service, token));
            return ResponseEntity.ok(token);
        } catch (IncorrectCredentialsException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Credentials");
        }
    }

    private String createToken(Client client){
         if(client instanceof Company) {
             Company company = (Company) client;
            return JWT.create()
                    .withIssuer("reem")
                    .withIssuedAt(new Date())
                    .withClaim("id", company.getId())
                    .withClaim("name", company.getName())
                    .withClaim("email", company.getEmail())
                    .withClaim("type", "COMPANY")
                    .sign(Algorithm.HMAC256("top-secret"));
        }
         else if (client instanceof Customer) {
             Customer customer = (Customer) client;
            return JWT.create()
                    .withIssuer("reem")
                    .withIssuedAt(new Date())
                    .withClaim("id", customer.getId())
                    .withClaim("firstName", customer.getFirstName())
                    .withClaim("lastName", customer.getLastName())
                    .withClaim("email", customer.getEmail())
                    .withClaim("type", "CUSTOMER")
                    .sign(Algorithm.HMAC256("top-secret"));
        }
         else
             return JWT.create()
                     .withIssuer("reem")
                     .withIssuedAt(new Date())
                     .withClaim("email", "admin@admin.com")
                     .withClaim("type", "ADMIN")
                     .sign(Algorithm.HMAC256("top-secret"));
    }
}
