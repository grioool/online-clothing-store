package com.epam.training.jwd.online.shop.dao.entity;

import java.util.Objects;

public class Product extends AbstractEntity<Integer> {

    private String productName;
    private String nameOfImage;
    private String productDescription;
    private Brand brand;
    private ProductCategory category;
    private Double price;
    private Integer article;

    public Product() {}

    public Product(Integer id, String productName, String nameOfImage, String productDescription, Brand brand, ProductCategory category, Double price, Integer article) {
        super(id);
        this.productName = productName;
        this.nameOfImage = nameOfImage;
        this.productDescription =productDescription;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.article = article;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNameOfImage() {
        return nameOfImage;
    }

    public void setNameOfImage(String nameOfImage) {
        this.nameOfImage = nameOfImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName) && Objects.equals(nameOfImage, product.nameOfImage) && Objects.equals(productDescription, product.productDescription) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(price, product.price) && Objects.equals(article, product.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, nameOfImage, productDescription, brand, category, price, article);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", nameOfImage='" + nameOfImage + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", brand=" + brand +
                ", category=" + category +
                ", price=" + price +
                ", article=" + article +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String nameOfImage;
        private String productName;
        private String productDescription;
        private Brand brand;
        private ProductCategory category;
        private Double price;
        private Integer article;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withNameOfImage(String nameOfImage) {
            this.nameOfImage = nameOfImage;
            return this;
        }

        public Builder withProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder withProductDescription(String productDescription) {
            this.productDescription = productDescription;
            return this;
        }

        public Builder withBrand(Brand brand) {
            this.brand = brand;
            return this;
        }

        public Builder withProductCategory(ProductCategory category) {
            this.category = category;
            return this;
        }

        public Builder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder withArticle(Integer article) {
            this.article = article;
            return this;
        }

        public Product build() {
            return new Product(
                    this.id,
                    this.productName,
                    this.nameOfImage,
                    this.productDescription,
                    this.brand,
                    this.category,
                    this.price,
                    this.article);
        }
    }
}
