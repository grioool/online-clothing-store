package com.epam.training.jwd.online.shop.dao.entity;

import java.util.Objects;

public class ProductCategory extends AbstractEntity<Integer> {
    private String categoryName;
    private String imgFileName;

    ProductCategory() {}

    ProductCategory(Integer id, String categoryName, String imgFilename) {
        super(id);
        this.categoryName = categoryName;
        this.imgFileName = imgFilename;
    }

    public String getImgFilename() {
        return imgFileName;
    }

    public void setImgFilename(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(imgFileName, that.imgFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, imgFileName);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryName='" + categoryName + '\'' +
                ", imgFileName='" + imgFileName + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String categoryName;
        private String imgFileName;

        Builder() {}

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder withImgFileName(String imgFileName) {
            this.imgFileName = imgFileName;
            return this;
        }

        public ProductCategory build(){
            return new ProductCategory(this.id,
                    this.categoryName,
                    this.imgFileName);
        }
    }
}
