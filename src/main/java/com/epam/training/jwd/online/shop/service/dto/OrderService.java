//package com.epam.training.jwd.online.shop.service.dto;
//
//import com.epam.training.jwd.online.shop.dao.entity.Order;
//import com.epam.training.jwd.online.shop.dao.entity.OrderStatus;
//import com.epam.training.jwd.online.shop.dao.entity.PaymentMethod;
//import com.epam.training.jwd.online.shop.dao.entity.User;
//import com.epam.training.jwd.online.shop.dao.exception.DaoException;
//import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
//import com.epam.training.jwd.online.shop.dao.field.OrderField;
//import com.epam.training.jwd.online.shop.dao.impl.OrderDao;
//import com.epam.training.jwd.online.shop.dao.impl.UserDao;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//public class OrderService {
//    private final Logger LOGGER = LogManager.getLogger(OrderService.class);
//    private static volatile OrderService instance;
//    private final OrderDao orderDao;
//
//    private OrderService(OrderDao orderDao) {
//        this.orderDao = orderDao;
//    }
//
//    public static OrderService getInstance() {
//        OrderService localInstance = instance;
//        if (localInstance == null) {
//            synchronized (OrderService.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    instance = localInstance = new OrderService(OrderDao.INSTANCE);
//                }
//            }
//        }
//        return localInstance;
//    }
//
//    public List<Order> findAllOrders() throws ServiceException {
//        try {
//            return orderDao.findAll();
//        } catch (DaoException e) {
//            LOGGER.error("Failed to find all orders");
//            throw new ServiceException(e);
//        }
//    }
//
//    public Optional<String> createOrder(Order order) throws ServiceException {
//        Integer loyaltyPointsPerDollar;
//        User orderUser = order.getUser();
//        UserService userService = UserService.getInstance();
//
//        if (orderUser.isBlocked()) {
//            return Optional.of("serverMessage.blockedAccount");
//        }
//        if (orderUser.getBalance().compareTo(order.getCost()) <= 0
//                && order.getPaymentMethod().equals(PaymentMethod.BALANCE)) {
//            return Optional.of("serverMessage.insufficientBalance");
//        }
//
//        try {
//            orderDao.save(order);
//            loyaltyPointsPerDollar = ApplicationConfig.getInstance().getLoyaltyPointsPerDollar();
//        } catch (DaoException | NumberFormatException e) {
//            LOGGER.error("Failed to create order");
//            throw new ServiceException(e);
//        }
//
//        User.Builder userBuilder = receiveUserBuilder(orderUser);
//        if (order.getPaymentMethod().equals(PaymentMethod.BALANCE)) {
//            userBuilder.withBalance(orderUser.getBalance().subtract(order.getCost()));
//        }
//        User user = userBuilder.build();
//        userService.updateUser(user);
//
//        return Optional.empty();
//    }
//
//    public Optional<Order> findOrderById(int orderId) throws ServiceException {
//        List<Order> orders;
//        try {
//            orders = orderDao.findByField(String.valueOf(orderId), OrderField.ID);
//        } catch (DaoException e) {
//            LOGGER.error("Failed on a order search");
//            throw new ServiceException("Failed search order by id", e);
//        }
//        return ((orders.size() > 0) ? Optional.of(orders.get(0)) : Optional.empty());
//    }
//
//    public List<Order> findAllOrdersByUserId(int userId) throws ServiceException {
//        try {
//            return orderDao.findByField(String.valueOf(userId), OrderField.USER);
//        } catch (DaoException e) {
//            LOGGER.error("Failed to find all orders by user id = " + userId);
//            throw new ServiceException(e);
//        }
//    }
//
//    public void updateOrder(Order order) throws ServiceException {
//        try {
//            orderDao.update(order);
//            updateUserBalanceAndLoyaltyPoints(order);
//        } catch (DaoException e) {
//            LOGGER.error("Failed to update order");
//            throw new ServiceException(e);
//        }
//    }
//
//    public void deleteProductFromOrders(int productId) throws ServiceException {
//        try {
//            orderDao.deleteOrderProductByProductId(productId);
//        } catch (DaoException e) {
//            LOGGER.error("Failed to delete product from orders");
//            throw new ServiceException(e);
//        }
//    }
//
//    private void updateUserBalanceAndLoyaltyPoints(Order order) throws ServiceException {
//        User.Builder userBuilder = receiveUserBuilder(order.getUser());
//        BigDecimal orderCost = order.getOrderCost();
//        PaymentMethod paymentMethod = order.getPaymentMethod();
//        OrderStatus orderStatus = order.getOrderStatus();
//        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
//        Integer pointsPerDollar = applicationConfig.getLoyaltyPointsPerDollar();
//
//        if (orderStatus.equals(OrderStatus.CANCELLED)) {
//            if (paymentMethod.equals(PaymentMethod.BALANCE)) {
//                userBuilder.withBalance((order.getUser().add(orderCost)));
//            }
//        }
//
//        UserService.getInstance().updateUser(userBuilder.build());
//    }
//
//    private User.Builder receiveUserBuilder(User user) {
//        return User.builder()
//                .withId(user.getId())
//                .withUsername(user.getUsername())
//                .withFirstName(user.getFirstName())
//                .withLastName(user.getLastName())
//                .withEmail(user.getEmail())
//                .withPassword(user.getPassword())
//                .withRole(user.getRole())
//                .withIsBlocked(user.isBlocked())
//                .withPhoneNumber(user.getPhoneNumber());
//    }
//}
