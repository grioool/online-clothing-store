package com.epam.training.jwd.online.shop.controller.command;

import com.epam.training.jwd.online.shop.controller.command.impl.*;

/**
 * The class represent all {@link Command}'s
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public enum CommandManager {
    TO_LOGIN(new ToLoginCommand(), "to_login"),
    TO_REGISTRATION(new ToRegistrationCommand(), "to_registration"),
    TO_MAIN(new ToMainPageCommand(), "to_main"),
    TO_PROFILE(new ToProfileCommand(), "to_profile"),
    TO_USERS(new ToUsersCommand(), "to_users"),
    TO_CART(new ToCartCommand(), "to_cart"),
    TO_ORDERS(new ToOrdersCommand(), "to_orders"),
    TO_CATALOG(new ToCatalogCommand(), "to_catalog"),
    TO_ADD_PRODUCT_TYPE(new ToAddCategoryCommand(), "to_add_product_type"),
    TO_MENU_ITEM(new ToCatalogItemCommand(), "to_menu_item"),
    TO_ADD_PRODUCT(new ToAddProductCommand(), "to_add_product"),
    TO_CREATE_ORDER(new ToCreateOrderCommand(), "to_create_order"),
    TO_ACCESS_BLOCKED(new ToAccessBlockedCommand(), "to_access_blocked"),
    TO_MY_ORDERS(new ToMyOrdersCommand(), "to_my_orders"),
    LOGIN(new LoginCommand(), "login"),
    LOGOUT(new LogoutCommand(), "logout"),
    REGISTRATION(new RegistrationCommand(), "registration"),
    LOCALE_SWITCH(new ChangeLocaleCommand(), "locale_switch"),
    EDIT_PROFILE(new EditProfileCommand(), "edit_profile"),
    UPDATE_USER(new UpdateUserCommand(), "update_user"),
    UPDATE_ORDER(new UpdateOrderCommand(), "update_order"),
    ADD_PRODUCT_TYPE(new AddProductCategoryCommand(), "add_product_type"),
    ADD_PRODUCT(new AddProductCommand(), "add_product"),
    DELETE_PRODUCT_TYPE(new DeleteProductCategoryCommand(), "delete_product_type"),
    EDIT_PRODUCT_TYPE(new EditProductCategoryCommand(), "edit_product_type"),
    ADD_TO_CART(new AddProductToCartCommand(), "add_to_cart"),
    EDIT_PRODUCT(new EditProductCommand(), "edit_product"),
    DELETE_PRODUCT(new DeleteProductCommand(), "delete_product"),
    DELETE_PRODUCT_FROM_CART(new DeleteProductFromCartCommand(), "delete_product_from_cart"),
    CREATE_ORDER(new CreateOrderCommand(), "create_order");

    private final Command command;
    private final String commandName;

    CommandManager(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    static Command of(String name) {
        for (CommandManager action : CommandManager.values()) {
            if (action.getCommandName().equalsIgnoreCase(name)) {
                return action.command;
            }
        }
        return ToNotFoundPageCommand.INSTANCE;
    }
}
