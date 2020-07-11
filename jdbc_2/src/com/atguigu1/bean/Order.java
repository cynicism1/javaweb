package com.atguigu1.bean;

import java.sql.Date;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/22 16:53
 */
public class Order {
    private int orderId;
    private String orderNname;
    private Date orderDate;

    public Order() {
    }

    public Order(int id, String name, String date) {
    }

    public Order(int orderId, String orderNname, Date orderDate) {
        this.orderId = orderId;
        this.orderNname = orderNname;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNname() {
        return orderNname;
    }

    public void setOrderNname(String orderNname) {
        this.orderNname = orderNname;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNname='" + orderNname + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
