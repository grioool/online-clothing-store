package com.epam.training.jwd.online.shop.dao.entity;

import java.util.Objects;

public class ProductInOrder extends AbstractEntity<Integer> {

    private Order order;
    private Product product;
    private int amount;

    ProductInOrder() {}

    ProductInOrder(Integer id, Product product, int amount) {
        super(id);
        this.amount = amount;
        this.product = product;
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
        ProductInOrder that = (ProductInOrder) o;
        return amount == that.amount && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, amount);
    }

    @Override
    public String toString() {
        return "ProductInOrder{" +
                "product=" + product +
                ", amount=" + amount +
                '}';
    }


}
