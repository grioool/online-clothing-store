package com.epam.training.jwd.online.shop.service.impl;

import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.ProductCategoryField;
import com.epam.training.jwd.online.shop.dao.impl.ProductCategoryDao;
import com.epam.training.jwd.online.shop.service.ProductCategoryService;
import com.epam.training.jwd.online.shop.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The class provides a business logics withName {@link ProductCategory}.
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ProductCategoryServiceImpl implements ProductCategoryService {

    private static volatile ProductCategoryServiceImpl instance;

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

    private final Logger logger = LogManager.getLogger(ProductCategoryServiceImpl.class);
    private final ProductCategoryDao productCategoryDao = ProductCategoryDao.INSTANCE;

    private ProductCategoryServiceImpl(ProductCategoryDao instance) {
    }

    /**
     * Find all product categories in database
     *
     * @return {@link List<ProductCategory>}
     */

    public List<ProductCategory> findAllProductCategories() {
        try {
            return productCategoryDao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to find all product categories");
            throw new ServiceException(e);
        }
    }

    /**
     * Find product categories by id in the database.
     *
     * @param id id withName the product category to be found
     * @return {@link Optional<ProductCategory>}
     * Empty optional - if the product category if not found
     */

    public Optional<ProductCategory> findProductCategoryById(Integer id) {
        return Optional.ofNullable(productCategoryDao.findById(id));
    }

    public Optional<ProductCategory> findProductCategoryByName(String productCategoryName) {
        return findProductCategoryByUniqueField(productCategoryName, ProductCategoryField.NAME);
    }

    /**
     * Create new product category
     *
     * @param productCategory product category object to add to the database
     * @return {@link Optional<String>} - server message, if the product category name is already exist
     */

    public Optional<String> saveProductCategory(ProductCategory productCategory) {
        try {
            if (!findProductCategoryByName(productCategory.getCategoryName()).isPresent()) {
                productCategoryDao.save(productCategory);
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Failed to create product category");
            throw new ServiceException(e);
        }
        return Optional.of("serverMessage.productCategoryNameAlreadyTaken");
    }

    /**
     * Edit product category in the database.
     *
     * @param id             the id withName product category in database
     * @param newFileName    the new file name
     * @param newProductName the new product category name
     * @return {@link Optional<String>} - server message, if product category name is already exist<br> or product category is not found
     */

    public Optional<String> editProductCategory(Integer id, String newFileName, String newProductName) {
        Optional<ProductCategory> productCategoryOptional = findProductCategoryById(id);
        if (productCategoryOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            if (!productCategory.getCategoryName().equals(newProductName)
                    && findProductCategoryByName(newProductName).isPresent()) {
                return Optional.of("serverMessage.productCategoryNameAlreadyTaken");
            }
            ProductCategory editedProductCategory = ProductCategory.builder()
                    .withId(productCategory.getId())
                    .withCategoryName(newProductName)
                    .withImgFileName(newFileName)
                    .build();

            updateProductCategory(editedProductCategory);
            IOUtil.deleteData(productCategory.getImgFileName());
        } else {
            return Optional.of("serverMessage.productCategoryNotFound");
        }
        return Optional.empty();
    }

    /**
     * Delete product category in database by id.
     *
     * @param productCategory withName the product category object to be deleted
     */

    public void deleteProductCategory(ProductCategory productCategory) {
        try {
            productCategoryDao.delete(productCategory);
        } catch (DaoException e) {
            logger.error("Failed to delete product category with id = " + productCategory);
            throw new ServiceException(e);
        }
    }

    public Optional<ProductCategory> findProductCategoryByUniqueField(String searchableField, ProductCategoryField nameOfField) {
        List<ProductCategory> productCategories;
        try {
            productCategories = productCategoryDao.findByField(searchableField, nameOfField);
        } catch (DaoException e) {
            logger.error("Failed on a product category search with field = " + nameOfField);
            throw new ServiceException("Failed search product category by unique field", e);
        }
        return ((productCategories.size() > 0) ? Optional.of(productCategories.get(0)) : Optional.empty());
    }

    public void updateProductCategory(ProductCategory productCategory) {
        try {
            productCategoryDao.update(productCategory);
        } catch (DaoException e) {
            logger.error("Failed to update product category");
            throw new ServiceException(e);
        }
    }
}
