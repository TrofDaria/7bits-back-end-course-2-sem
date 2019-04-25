package it.sevenbits.courses.homeworks.postgresql.web.model;

/**
 * Model for get tasks request.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class GetTasksRequest {
    private String status;
    private String order;

    private int pageNumber;
    private int size;

    /**
     * Constructor.
     *
     * @param status     - requested tasks status.
     * @param order      - requested order.
     * @param pageNumber - requested page number.
     * @param size       - requested size of page.
     */
    public GetTasksRequest(final String status, final String order,
                           final int pageNumber, final int size) {
        this.status = status;
        this.order = order;
        this.pageNumber = pageNumber;
        this.size = size;
    }

    /**
     * Returns page number.
     *
     * @return page number.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Returns status.
     *
     * @return status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns order.
     *
     * @return order.
     */
    public String getOrder() {
        return order;
    }

    /**
     * Returns size.
     *
     * @return size.
     */
    public int getSize() {
        return size;
    }
}
