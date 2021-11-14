package com.epam.training.jwd.online.shop.dao.entity;

import java.util.Objects;

public class Basket extends AbstractEntity<Integer> {
    private User user;
    private Product product;
    private Integer amount;

    public Basket() {}

    public Basket(Integer id, User user, Product product, Integer amount) {
        super(id);
        this.amount = amount;
        this.product = product;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return amount == basket.amount && Objects.equals(user, basket.user) && Objects.equals(product, basket.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, product, amount);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "user=" + user +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
