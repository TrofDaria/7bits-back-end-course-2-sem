package it.sevenbits.courses.homeworks.postgresql.core.service;


import java.util.HashSet;
import java.util.Set;

/**
 * Validates tasks.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */

public class TasksValidationService {
    private Set<String> availableStatuses;
    private Set<String> availableOrders;
    private final int maxSize = 50;
    private final int minSize = 10;

    /** Default page size. */
    public static final int DEFAULT_SIZE = 25;
    /** Default page number. */
    public static final int DEFAULT_PAGE_NUMBER = 1;
    /** Default sorting order. */
    public static final String DEFAULT_ORDER = "desc";
    /** Default task status. */
    public static final String DEFAULT_STATUS = "inbox";

    /**
     * Constructor.
     */
    public TasksValidationService() {
        availableStatuses = new HashSet<>();
        availableStatuses.add("inbox");
        availableStatuses.add("done");
        availableOrders = new HashSet<>();
        availableOrders.add("desc");
        availableOrders.add("asc");
    }

    /**
     * Determines whether status is legit or not.
     *
     * @param status - status.
     * @return true if status legit, false otherwise.
     */
    public boolean isStatusLegit(final String status) {
        return availableStatuses.contains(status);
    }

    /**
     * Determines whether order is legit or not.
     *
     * @param order - order.
     * @return true if order legit, false otherwise.
     */
    public boolean isOrderLegit(final String order) {
        return availableOrders.contains(order);
    }

    /**
     * Determines whether page number is legit or not.
     *
     * @param pageNumber  - page.
     * @param pagesAmount - amount of pages.
     * @return true if page number legit, false otherwise.
     */
    public boolean isPageNumberLegit(final int pageNumber, final int pagesAmount) {
        return pageNumber <= pagesAmount;
    }

    /**
     * Determines whether size is legit or not.
     *
     * @param size - size.
     * @return true if size legit, false otherwise.
     */
    public boolean isSizeLegit(final int size) {
        return size <= maxSize && size >= minSize;
    }

    /**
     * Returns default task status.
     *
     * @return default task status.
     */
    public static String getDefaultStatus() {
        return DEFAULT_STATUS;
    }

    /**
     * Returns default page size.
     *
     * @return default page size.
     */
    public static int getDefaultSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Returns default tasks order.
     *
     * @return default tasks order.
     */
    public static String getDefaultOrder() {
        return DEFAULT_ORDER;
    }

    /**
     * Returns default page number.
     *
     * @return default page number.
     */
    public static int getDefaultPageNumber() {
        return DEFAULT_PAGE_NUMBER;
    }

    /**
     * Validates given status.
     *
     * @param status - status.
     * @return validated status.
     */
    public String validateStatus(final String status) {
        if (!isStatusLegit(status)) {
            return getDefaultStatus();
        }
        return status;
    }

    /**
     * Validates given tasks order.
     *
     * @param order - order.
     * @return validated tasks order.
     */
    public String validateOrder(final String order) {
        if (!isOrderLegit(order)) {
            return getDefaultOrder();
        }
        return order;
    }

    /**
     * Validates given page size.
     *
     * @param size - size.
     * @return validated page size.
     */
    public int validateSize(final int size) {
        if (!isSizeLegit(size)) {
            return getDefaultSize();
        }
        return size;
    }

    /**
     * Validates given page number.
     *
     * @param pageNumber  - pageNumber.
     * @param pagesAmount - amount of pages.
     * @return validated page number.
     */
    public int validatePageNumber(final int pageNumber, final int pagesAmount) {
        if (!isPageNumberLegit(pageNumber, pagesAmount)) {
            return getDefaultPageNumber();
        }
        return pageNumber;
    }

}
