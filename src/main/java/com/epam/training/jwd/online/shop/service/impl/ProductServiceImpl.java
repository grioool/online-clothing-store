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

    public List<Product> findAllProducts() {
        try {
            return productDao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to find all products");
            throw new ServiceException(e);
        }
    }

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

    public List<Product> findProductsByCategoryId(Integer categoryId) {
        try {
            return productDao.findByField(categoryId, ProductField.CATEGORY);
        } catch (DaoException e) {
            logger.error("Failed to find all products with type id = " + categoryId);
            throw new ServiceException(e);
        }
    }

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

    public Map<Product, Integer> receiveProducts(Map<Integer, Integer> cart) {
        Map<Product, Integer> productsInCart = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Optional<Product> productOptional = findProductById(entry.getKey());
            productOptional.ifPresent(product -> productsInCart.put(product, entry.getValue()));
        }
        return productsInCart;
    }

    public BigDecimal calcOrderCost(Map<Product, Integer> productsInCart) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
            Double productPrice = entry.getKey().getPrice();
            BigDecimal amountOfProducts = BigDecimal.valueOf(entry.getValue());
            totalCost = totalCost.add(amountOfProducts.multiply(new BigDecimal(productPrice)));
        }
        return totalCost;
    }

    public Optional<String> editProduct(Integer productId, String productName, String productDescription, Brand brand, Double price, Integer article) {
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
                    .withNameOfImage(product.getNameOfImage())
                    .withProductDescription(productDescription)
                    .withArticle(article)
                    .build();
            updateProduct(editedProduct);
        } else {
            return Optional.of("serverMessage.productNotFound");
        }
        return Optional.empty();
    }

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
            logger.error("Failed to delete all products by type id");
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