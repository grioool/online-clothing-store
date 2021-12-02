package com.epam.training.jwd.online.shop.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is used to implement {@link com.epam.training.jwd.online.shop.controller.tag.PaginationTag}
 * @param <T> type withName pagination objects
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class PaginationContext<T> {
    private static final int PER_PAGE = 5;
    private final List<T> objectList;
    private final int page;
    private final int totalPages;

    public PaginationContext(List<T> objectList, int page) {
        this.objectList = receiveObjects(objectList, page);
        this.page = page;
        this.totalPages = (int) Math.ceil((double) objectList.size() / PER_PAGE);
    }

    public static int getPerPage() {
        return PER_PAGE;
    }

    public List<T> getObjectList() {
        return objectList;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    private List<T> receiveObjects(List<T> objectList, int page) {
        List<T> copyObjects = new ArrayList<>();

        int start = (page - 1) * PER_PAGE;
        int end = Math.min(page * PER_PAGE, objectList.size());

        for(int i = start; i < end; i++){
            copyObjects.add(objectList.get(i));
        }

        return copyObjects;
    }
}
