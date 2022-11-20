if you want to run CouponSpringProjectApplication more then once, run this script before each time

delete from customers_coupons where customers_id > 0;
delete from coupons where id > 0;
delete from companies where id > 0;
delete from customers where id > 0;
ALTER TABLE customers AUTO_INCREMENT = 0;
ALTER TABLE companies AUTO_INCREMENT = 0;
ALTER TABLE coupons AUTO_INCREMENT = 0;
ALTER TABLE customers_coupons AUTO_INCREMENT = 0;