package com.vinayak.ecommerce.service;

import com.vinayak.ecommerce.dao.CustomerRepository;
import com.vinayak.ecommerce.dto.Purchase;
import com.vinayak.ecommerce.dto.PurchaseResponse;
import com.vinayak.ecommerce.entity.Customer;
import com.vinayak.ecommerce.entity.Order;
import com.vinayak.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {

        //retrieve the order info from dto
        Order order=purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber=generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with orderItems
        Set<OrderItem> orderItems=purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //populate order with billingAddress and ShippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //populate customer with order
        Customer customer=purchase.getCustomer();
        customer.add(order);

        //save to the DB
        customerRepository.save(customer);

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

}
