package main.Controllers;

import main.Beans.Category;
import main.Beans.Coupon;
import main.Beans.Session;
import main.Exceptions.CouponAlreadyPurchasedException;
import main.Exceptions.CouponExpiredException;
import main.Exceptions.CouponOutOfStockException;
import main.Exceptions.NotFoundException;
import main.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController extends ClientController{

    @Autowired
    private Map<String, Session> sessionPack;
    @Autowired
    private HttpServletRequest request;

    public CustomerController() {
    }

    public CustomerService getService(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Session session = sessionPack.get(token);
        session.setLastUse(System.currentTimeMillis());
        return (CustomerService) session.getService();
    }

    @PostMapping
    public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon){
        try {
            CustomerService customerService = getService(request);
            customerService.purchaseCoupon(coupon);
            return ResponseEntity.ok("Coupon Purchased");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CouponAlreadyPurchasedException | CouponExpiredException | CouponOutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping(path = "/coupons")
    public ResponseEntity<?> getAllCoupons (){
        CustomerService customerService = getService(request);
        return ResponseEntity.ok(customerService.getAllCoupons());
    }

    @GetMapping(path = "/customersCoupons")
    public ResponseEntity<?> getCustomerCoupons(){
        try {
            CustomerService customerService = getService(request);
            return ResponseEntity.ok(customerService.getCustomerCoupons());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Oops, something went wrong\nplease try again later");
        }
    }

    @GetMapping(path = "/coupons/{category}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable Category category){
        try {
            CustomerService customerService = getService(request);
            return ResponseEntity.ok(customerService.getCustomerCoupons(category));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Oops, something went wrong\nplease try again later");
        }
    }

    @GetMapping(path = "/coupons/{max}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable double max){
        try {
            CustomerService customerService = getService(request);
            return ResponseEntity.ok(customerService.getCustomerCoupons(max));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Oops, something went wrong\nplease try again later");
        }
    }

    @GetMapping(path = "/details")
    public ResponseEntity<?> getCustomerDetails(){
        try {
            CustomerService customerService = getService(request);
            return ResponseEntity.ok(customerService.getCustomerDetail());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Oops, something went wrong\nplease try again later");
        }
    }
}
