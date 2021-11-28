package com.epam.training.jwd.online.shop.service.dto;

import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.ProductCategoryField;
import com.epam.training.jwd.online.shop.dao.impl.ProductCategoryDao;
import com.epam.training.jwd.online.shop.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;


public class ProductCategoryServiceImpl implements ProductCategoryService{
    private static volatile ProductCategoryServiceImpl instance;
    private final Logger logger = LogManager.getLogger(ProductCategoryServiceImpl.class);
    public static final ProductCategoryServiceImpl INSTANCE = new ProductCategoryServiceImpl(ProductCategoryDao.INSTANCE);
    private final ProductCategoryDao productCategoryDao = ProductCategoryDao.INSTANCE;

    public static ProductCategoryServiceImpl getInstance() {
        ProductCategoryServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductCategoryServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ProductCategoryServiceImpl(ProductCategoryDao.INSTANCE);
                }
            }
        }
        return localInstance;
    }

        private ProductCategoryServiceImpl(ProductCategoryDao instance) {
        }


        public List<ProductCategory> findAllProductCategories() {
            try {
                return productCategoryDao.findAll();
            } catch (DaoException e) {
                logger.error("Failed to find all product types");
                throw new ServiceException(e);
            }
        }

        public Optional<ProductCategory> findProductCategoryById(Integer id) {
            return findProductCategoryByUniqueField(String.valueOf(id), ProductCategoryField.ID);
        }

        public Optional<ProductCategory> findProductCategoryByName(String productTypeName) {
            return findProductCategoryByUniqueField(productTypeName, ProductCategoryField.NAME);
        }

        public Optional<String> saveProductCategory(ProductCategory productCategory) {
            try {
                if (!findProductCategoryByName(productCategory.getCategoryName()).isPresent()) {
                    productCategoryDao.save(productCategory);
                    return Optional.empty();
                }
            } catch (DaoException e) {
                logger.error("Failed to create product type");
                throw new ServiceException(e);
            }
            return Optional.of("serverMessage.productTypeNameAlreadyTaken");
        }

        public Optional<String> editProductCategory(Integer id, String newFileName, String newProductName) {
            Optional<ProductCategory> productTypeOptional = findProductCategoryById(id);
            if (productTypeOptional.isPresent()) {
                ProductCategory productCategory = productTypeOptional.get();
                if (!productCategory.getCategoryName().equals(newProductName)
                        && findProductCategoryByName(newProductName).isPresent()) {
                    return Optional.of("serverMessage.productTypeNameAlreadyTaken");
                }
                ProductCategory editedProductType = ProductCategory.builder()
                        .withId(productCategory.getId())
                        .withCategoryName(newProductName)
                        .withImgFileName(newFileName)
                        .build();

                updateProductCategory(editedProductType);
                IOUtil.deleteData(productCategory.getImgFilename());
            } else {
                return Optional.of("serverMessage.productTypeNotFound");
            }
            return Optional.empty();
        }

        public void deleteProductCategory(ProductCategory productCategory) {
            try {
                productCategoryDao.delete(productCategory);
            } catch (DaoException e) {
                logger.error("Failed to delete product type with id = " + productCategory);
                throw new ServiceException(e);
            }
        }

        public Optional<ProductCategory> findProductCategoryByUniqueField(String searchableField, ProductCategoryField nameOfField) {
            List<ProductCategory> productCategories;
            try {
                productCategories = productCategoryDao.findByField(searchableField, nameOfField);
            } catch (DaoException e) {
                logger.error("Failed on a product type search with field = " + nameOfField);
                throw new ServiceException("Failed search product type by unique field", e);
            }
            return ((productCategories.size() > 0) ? Optional.of(productCategories.get(0)) : Optional.empty());
        }

        public void updateProductCategory(ProductCategory productCategory) {
            try {
                productCategoryDao.update(productCategory);
            } catch (DaoException e) {
                logger.error("Failed to update product type");
                throw new ServiceException(e);
            }
        }
}
