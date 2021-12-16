package com.epam.training.jwd.online.shop.service.impl;

import com.epam.training.jwd.online.shop.dao.entity.Brand;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.ProductField;
import com.epam.training.jwd.online.shop.dao.impl.ProductDao;
import com.epam.training.jwd.online.shop.service.OrderService;
import com.epam.training.jwd.online.shop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The class provides a business logics withName {@link Product}.
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ProductServiceImpl implements ProductService {
    private static volatile ProductServiceImpl instance;
    private final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private static final ProductDao productDao = ProductDao.INSTANCE;

    public static ProductServiceImpl getInstance() {
        ProductServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ProductServiceImpl(ProductDao.INSTANCE);
                }
            }
        }
        return localInstance;
    }

    private ProductServiceImpl(ProductDao instance) {
    }

    /**
     * Find all products in the database.
     *
     * @return {@link List<Product>}
     */

    public List<Product> findAllProducts() {
        try {
            return productDao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to find all products");
            throw new ServiceException(e);
        }
    }

    /**
     * Find product in the database by id.
     *
     * @param productId id withName the product to be found
     * @return {@link Optional<Product>}<br> Empty optional if product does not exist
     */

    public Optional<Product> findProductById(Integer productId) {
        try {
            return Optional.ofNullable(productDao.findById(productId));
        } catch (DaoException e) {
            logger.error("Failed on a order search");
            throw new ServiceException("Failed search order by id", e);
        }
    }

    public Optional<Product> findProductByName(String name) {
        return findProductByUniqueField(name, ProductField.NAME);
    }

    /**
     * Find all products by product category id.
     *
     * @param categoryId id withName {@link ProductCategory}
     * @return {@link List<Product>}
     */

    public List<Product> findProductsByCategoryId(Integer categoryId) {
        try {
            return productDao.findByField(categoryId, ProductField.CATEGORY);
        } catch (DaoException e) {
            logger.error("Failed to find all products with category id = " + categoryId);
            throw new ServiceException(e);
        }
    }

    /**
     * Create new product
     *
     * @param product {@link Product} object to add to the database
     * @return {@link Optional<String>} - server message, if product name is already exist
     */

    public Optional<String> saveProduct(Product product) {
        try {
            Optional<Product> productOptional = findProductByName(product.getProductName());
            if (!productOptional.isPresent()) {
                productDao.save(product);
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Failed to create product");
            throw new ServiceException(e);
        }
        return Optional.of("serverMessage.productNameAlreadyTaken");
    }

    /**
     * Receive products from user cart.
     *
     * @param cart {@link Map} withName amount and id products
     * @return {@link Map} withName amount and {@link Product}
     */

    public Map<Product, Integer> receiveProducts(Map<Integer, Integer> cart) {
        Map<Product, Integer> productsInCart = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Optional<Product> productOptional = findProductById(entry.getKey());
            productOptional.ifPresent(product -> productsInCart.put(product, entry.getValue()));
        }
        return productsInCart;
    }

    /**
     * Calculate order cost
     *
     * @param productsInCart {@link Map} withName products and amount in user cart
     * @return {@link BigDecimal cost}
     */

    public BigDecimal calcOrderCost(Map<Product, Integer> productsInCart) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
            BigDecimal productPrice = entry.getKey().getPrice();
            BigDecimal amountOfProducts = BigDecimal.valueOf(entry.getValue());
            totalCost = totalCost.add(amountOfProducts.multiply(productPrice));
        }
        return totalCost;
    }


    /**
     * Edit product in the database.
     *
     * @param productId new product id
     * @param productName new product name
     * @param productDescription new product description
     * @param brand new product brand
     * @param price new product price
     * @param article new product article
     * @return {@link Optional<String>} - server message, if product name is already exist<br> or product not found
     */
    public Optional<String> editProduct(Integer productId, String productName, String productDescription, Brand brand, BigDecimal price, Integer article) {
        Optional<Product> productOptional = findProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (!product.getProductName().equals(productName)
                    && findProductByName(productName).isPresent()) {
                return Optional.of("serverMessage.productNameAlreadyTaken");
            }
            Product editedProduct = Product.builder()
                    .withId(productId)
                    .withProductName(productName)
                    .withBrand(brand)
                    .withProductCategory(product.getCategory())
                    .withProductName(productName)
                    .withPrice(price)
                    .withNameOfImage(product.getImgFileName())
                    .withProductDescription(productDescription)
                    .withArticle(article)
                    .build();
            updateProduct(editedProduct);
        } else {
            return Optional.of("serverMessage.productNotFound");
        }
        return Optional.empty();
    }

    /**
     * Delete product in database.
     *
     * @param product withName the {@link Product} object to be deleted
     */

    public void deleteProduct(Product product) {
        try {
            productDao.delete(product);
        } catch (DaoException e) {
            logger.error("Failed to delete product with id = " + product);
            throw new ServiceException(e);
        }
    }

    public void deleteAllProductsByCategoryId(Integer categoryId) {
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            List<Product> products = productDao.findByField(categoryId, ProductField.CATEGORY);
            for (Product product : products) {
                orderService.deleteProductFromOrders(product.getId());
                productDao.delete(product);
            }
        } catch (DaoException e) {
            logger.error("Failed to delete all products by category id");
            throw new ServiceException(e);
        }
    }

    public Optional<Product> findProductByUniqueField(String searchableField, ProductField nameOfField) {
        List<Product> products;
        try {
            products = productDao.findByField(searchableField, nameOfField);
        } catch (DaoException e) {
            logger.error("Failed on a product search by field = " + nameOfField.name());
            throw new ServiceException("Failed search user by unique field", e);
        }
        return ((products.size() > 0) ? Optional.of(products.get(0)) : Optional.empty());
    }

    public void updateProduct(Product product) {
        try {
            productDao.update(product);
        } catch (DaoException e) {
            logger.error("Failed to update product");
            throw new ServiceException(e);
        }
    }
}