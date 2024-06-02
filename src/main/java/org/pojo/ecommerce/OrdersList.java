package org.pojo.ecommerce;

import java.util.List;

public class OrdersList {

    List<OrderDetail> orders;

    public List<OrderDetail> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }
}
