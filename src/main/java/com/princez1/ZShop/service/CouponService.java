package com.princez1.ZShop.service;

public interface CouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}
