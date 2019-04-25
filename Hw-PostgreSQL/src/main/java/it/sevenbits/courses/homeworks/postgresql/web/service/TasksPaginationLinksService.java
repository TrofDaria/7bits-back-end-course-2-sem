package it.sevenbits.courses.homeworks.postgresql.web.service;


/**
 * Service that provides pagination links for tasks.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class TasksPaginationLinksService {
    private String status;
    private String order;
    private int total;
    private int page;
    private int size;
    private int pagesTotal;

    /**
     * Constructor.
     *
     * @param status - tasks status.
     * @param order  - tasks order.
     * @param total  - total amount of tasks.
     * @param page   - number of page.
     * @param size   - size of page.
     */
    public TasksPaginationLinksService(final String status, final String order, final int total, final int page, final int size) {
        this.status = status;
        this.order = order;
        this.total = total;
        this.page = page;
        this.size = size;
        calculatePagesAmount();
    }

    /**
     * Returns possible amount of pages.
     *
     * @return amount of pages.
     */
    private void calculatePagesAmount() {
        pagesTotal = total / size;
        if (total % size != 0) {
            pagesTotal = pagesTotal + 1;
        }
    }

    /**
     * Returns next page link.
     *
     * @return next page link.
     */
    public String getNextPageLink() {
        if (page == pagesTotal) {
            return null;
        }
        return "/tasks?status=" + status + "&order=" + order + "&page=" + (page + 1) + "&size=" + size;
    }

    /**
     * Returns previous page link.
     *
     * @return previous page link.
     */
    public String getPrevPageLink() {
        if (page == 1) {
            return null;
        }
        return "/tasks?status=" + status + "&order=" + order + "&page=" + (page - 1) + "&size=" + size;
    }

    /**
     * Returns first page link.
     *
     * @return first page link.
     */
    public String getFirstPageLink() {
        return "/tasks?status=" + status + "&order=" + order + "&page=" + 1 + "&size=" + size;
    }

    /**
     * Returns last page link.
     *
     * @return last page link.
     */
    public String getLastPageLink() {

        return "/tasks?status=" + status + "&order=" + order + "&page=" + pagesTotal + "&size=" + size;
    }

}
