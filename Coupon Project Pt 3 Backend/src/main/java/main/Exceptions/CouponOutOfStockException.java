package main.Exceptions;

public class CouponOutOfStockException extends Exception {
    public CouponOutOfStockException(){
        super("Coupon out of stock");
    }
}
