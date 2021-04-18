package com.vinayak.ecommerce.dto;

import com.vinayak.ecommerce.entity.Address;
import com.vinayak.ecommerce.entity.Customer;
import com.vinayak.ecommerce.entity.Order;
import com.vinayak.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
