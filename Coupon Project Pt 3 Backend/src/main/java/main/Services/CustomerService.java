package main.Services;

import main.Beans.Category;
import main.Beans.Coupon;
import main.Beans.Customer;
import main.Exceptions.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Scope("prototype")
public class CustomerService extends ClientService{
    int customerId;

    public CustomerService() {
    }

    @Override
    public int login(String email, String password) throws IncorrectCredentialsException {
        Optional<Customer> customer = customerRepo.findByEmailAndPassword(email, password);
        if (customer.isPresent()) {
            customerId = customer.get().getId();
            return customerId;
        }
        else return -1;
    }

    //CREATE

    /**checks if the coupon is not expired, if it's out of stock, if the customer hadn't already purchased it.
     * if not then gets the customer's object, pulls its coupons by the id, adds the coupon to the customer's coupon
     * collection, update the customer, substructure from the coupon amount and update the coupon.
     * @param coupon a full coupon object to be purchased
     * @throws CouponExpiredException can't purchase a coupon after it's end date
     * @throws CouponOutOfStockException can't purchase a coupons if it's amount is lower than 1
     * @throws CouponAlreadyPurchasedException can't purchase the same coupon twice
     * @throws NotFoundException can't find customer
     */
    public void purchaseCoupon(Coupon coupon) throws CouponExpiredException, CouponOutOfStockException,
            CouponAlreadyPurchasedException, NotFoundException {
        if(Calendar.getInstance().getTime().after(coupon.getEndDate()))
            throw new CouponExpiredException();
        if(coupon.getAmount() < 1)
            throw new CouponOutOfStockException();
        if(couponRepo.findCouponPurchase(customerId, coupon.getId()).isPresent())
            throw new CouponAlreadyPurchasedException();
        //get an object of the current customer and its coupons
        Customer customer = customerRepo.findById(customerId).orElseThrow( () -> new NotFoundException("Customer not found"));
        List<Coupon> customerCoupons = getCustomerCoupons();
        //add the coupon to the new coupon collection
        customerCoupons.add(coupon);
        //save the customer with the new collection to the database
        customer.setCoupons(customerCoupons);
        customerRepo.save(customer);
        //update coupon amount
        coupon.setAmount(coupon.getAmount()-1);
        //save coupon with updated amount to the database
        couponRepo.save(coupon);
        System.out.println("Customer " + customer.getFirstName() + " " + customer.getLastName() + " purchased " +
                        coupon.getTitle() + " coupon");
    }

    //READ

    /** @return list of all coupons
     */
    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }

    /**@param id the ID of the requested coupon
     * @return coupon by a given ID
     * @throws NotFoundException could not find coupon
     */
    public Coupon getCoupon(int id) throws NotFoundException {
        return couponRepo.findById(id).orElseThrow( () -> new NotFoundException("Coupon not found"));
    }

    /**@param id the ID of the requested coupon
     * @return coupon from the customer's collection by a given ID
     * @throws NotFoundException could not find coupon
     */
    public Coupon getCustomerCoupon(int id) throws NotFoundException {
        List<Coupon> customerCoupons = getCustomerCoupons();
        for(Coupon coupon : customerCoupons){
            if(coupon.getId() == id)
                return coupon;
        }
        throw new NotFoundException("Coupon not found");
    }

    /**@return list of the customer's purchased coupons
     */
    public List<Coupon> getCustomerCoupons(){
        List<Integer> customerCouponsId = couponRepo.findCustomerCouponsId(customerId);
        List<Coupon> customerCoupons = new ArrayList<>();
        customerCouponsId.forEach( id -> customerCoupons.add(couponRepo.findById(id).get()) );
        return customerCoupons;
    }

    /**@param category the category of the coupons you want to receive
     * @return list of coupons for the given category
     */
    public List<Coupon> getCustomerCoupons(Category category){
        List<Coupon> customerCoupons = getCustomerCoupons();
        List<Coupon> filteredCoupons = new ArrayList<>();
        customerCoupons.stream().filter( coupon -> couponRepo.findById(coupon.getId()).get().getCategory().equals(category) )
                .forEach( coupon -> filteredCoupons.add(couponRepo.findById(coupon.getId()).get()) );
        return filteredCoupons;
    }

    /**@param max maximum price
     * @return list of the customer's coupons under the given maximum price
     */
    public List<Coupon> getCustomerCoupons(double max) {
        List<Coupon> customerCoupons = getCustomerCoupons();
        List<Coupon> filteredCoupons = new ArrayList<>();
        customerCoupons.stream().filter( coupon -> (couponRepo.findById(coupon.getId()).get().getPrice() <= max) )
                .forEach( coupon -> filteredCoupons.add(couponRepo.findById(coupon.getId()).get()) );
        return filteredCoupons;
    }

    /** @return customer object with coupons
     */
    public Customer getCustomerDetail(){
        Customer customer = customerRepo.findById(customerId).get();
        customer.setCoupons(getCustomerCoupons());
        return customer;
    }
}
