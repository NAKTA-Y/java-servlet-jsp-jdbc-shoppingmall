package com.nhnacademy.shoppingmall.common.page;

public class Pagination {
    private static int DEFAULT_GROUP_SIZE = 10;
    public static int getTotalPage(long totalCount, int pageSize) {
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    public static int getStartPage(int page) {
        int pageGroupIndex = getPageGroupIndex(page);
        return (pageGroupIndex - 1) * DEFAULT_GROUP_SIZE + 1;
    }

    public static int getLastPage(long totalCount, int page, int pageSize) {
        int startPage = getStartPage(page);
        return Math.min(startPage + (DEFAULT_GROUP_SIZE - 1), getTotalPage(totalCount, pageSize));
    }

    private static int getPageGroupIndex(int page) {
        return ((page - 1) / DEFAULT_GROUP_SIZE) + 1;
    }
}
