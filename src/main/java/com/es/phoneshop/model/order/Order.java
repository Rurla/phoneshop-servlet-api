package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.Date;

public class Order extends Cart {

    private long id;

    private PaymentMethod paymentMethod;

    //Contact info
    private String firstName;

    private String lastName;

    private long phoneNumber;

    //Delivery info
    private Date deliveryDate;

    private String deliveryAddress;

    private BigDecimal deliveryCosts;

    public Order() {
    }

    public Order(Order order) {
        this.setId(order.getId());
        this.setItems(order.getItems());
        this.setTotalPrice(order.getTotalPrice());
        this.setFirstName(order.getFirstName());
        this.setLastName(order.getLastName());
        this.setPhoneNumber(order.getPhoneNumber());
        this.setDeliveryAddress(order.getDeliveryAddress());
        this.setDeliveryDate(order.getDeliveryDate());
        this.setDeliveryCosts(order.getDeliveryCosts());
        this.setPaymentMethod(order.getPaymentMethod());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getDeliveryCosts() {
        return deliveryCosts;
    }

    public void setDeliveryCosts(BigDecimal deliveryCosts) {
        this.deliveryCosts = deliveryCosts;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
