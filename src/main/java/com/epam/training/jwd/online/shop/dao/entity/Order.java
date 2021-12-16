package com.epam.training.jwd.online.shop.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Order extends AbstractEntity<Integer> {
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private Map<Product, Integer> products;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private Country deliveryCountry;
    private Town deliveryTown;
    private User user;
    private BigDecimal orderCost;

    public Order() {}

    public Order(Integer id, PaymentMethod paymentMethod, OrderStatus orderStatus, Map<Product, Integer> products, LocalDateTime orderDate, LocalDateTime deliveryDate, Country deliveryCountry, Town deliveryTown, User user, BigDecimal orderCost) {
        super(id);
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.products = products;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.deliveryCountry = deliveryCountry;
        this.deliveryTown = deliveryTown;
        this.user = user;
        this.orderCost = orderCost;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Country getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(Country deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public Town getDeliveryTown() {
        return deliveryTown;
    }

    public void setDeliveryTown(Town deliveryTown) {
        this.deliveryTown = deliveryTown;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getOrderCost() {
        return products.entrySet().stream()
                .map((entry) -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return paymentMethod == order.paymentMethod && orderStatus == order.orderStatus && Objects.equals(products, order.products) && Objects.equals(orderDate, order.orderDate) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(deliveryCountry, order.deliveryCountry) && Objects.equals(deliveryTown, order.deliveryTown) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethod, orderStatus, products, orderDate, deliveryDate, deliveryCountry, deliveryTown, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "paymentMethod=" + paymentMethod +
                ", orderStatus=" + orderStatus +
                ", products=" + products +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", deliveryCountry=" + deliveryCountry +
                ", deliveryTown=" + deliveryTown +
                ", user=" + user +
                '}';
    }

    public static Builder builder(){
        return new Order.Builder();
    }

    public static class Builder {
        private Integer id;
        private PaymentMethod paymentMethod;
        private OrderStatus orderStatus;
        private Map<Product, Integer> products;
        private LocalDateTime orderDate;
        private LocalDateTime deliveryDate;
        private Country deliveryCountry;
        private Town deliveryTown;
        private User user;
        private BigDecimal orderCost;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder withOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder withProducts(Map<Product, Integer> products) {
            this.products = products;
            return this;
        }

        public Builder withOrderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder withDeliveryDate(LocalDateTime deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder withDeliveryCountry(Country deliveryCountry) {
            this.deliveryCountry = deliveryCountry;
            return this;
        }

        public Builder withDeliveryTown(Town deliveryTown) {
            this.deliveryTown = deliveryTown;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withOrderCost(BigDecimal orderCost) {
            this.orderCost = orderCost;
            return this;
        }

        public Order build() {
            return new Order(this.id,
                    this.paymentMethod,
                    this.orderStatus,
                    this.products,
                    this.orderDate,
                    this.deliveryDate,
                    this.deliveryCountry,
                    this.deliveryTown,
                    this.user,
                    this.orderCost);
        }
    }
}
