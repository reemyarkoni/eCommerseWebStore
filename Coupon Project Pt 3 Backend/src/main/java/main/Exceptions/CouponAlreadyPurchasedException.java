package main.Exceptions;

public class CouponAlreadyPurchasedException extends Exception {
    public CouponAlreadyPurchasedException(){
        super("Coupon already purchased, you can't buy the same coupon twice");
    }
}
