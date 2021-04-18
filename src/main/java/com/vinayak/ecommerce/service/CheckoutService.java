package com.vinayak.ecommerce.service;

import com.vinayak.ecommerce.dto.Purchase;
import com.vinayak.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {  

    PurchaseResponse placeOrder(Purchase purchase);
}
