package it.sevenbits.courses.homeworks.postgresql.web.model;

import it.sevenbits.courses.homeworks.postgresql.core.model.Task;

import java.util.List;

/**
 * Model for get tasks response.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */

public class GetTasksResponse {
    private Meta _meta;

    private List<Task> tasks;

    /**
     * Constructor.
     *
     * @param total - total amount of tasks.
     * @param page  - number of page.
     * @param size  - size of page.
     * @param next  - link to the next page.
     * @param prev  - link to the previous page.
     * @param first - link to the first page.
     * @param last  - link to the last page.
     * @param tasks - list of tasks.
     */
    public GetTasksResponse(final int total, final int page, final int size, final String next,
                            final String prev, final String first, final String last, final List<Task> tasks) {
        _meta = new Meta(total, page, size, next, prev, first, last);
        this.tasks = tasks;

    }

    /**
     * Model for get tasks response meta data.
     *
     * @author Daria Trofimova
     * @version 1.0
     * @since 2019-03-29
     */
    private class Meta {
        private int total;
        private int page;
        private int size;
        private String next;
        private String prev;
        private String first;
        private String last;

        /**
         * Constructor.
         *
         * @param total - total amount of tasks.
         * @param page  - number of page.
         * @param size  - size of page.
         * @param next  - link to the next page.
         * @param prev  - link to the previous page.
         * @param first - link to the first page.
         * @param last  - link to the last page.
         */
        Meta(final int total, final int page, final int size, final String next,
             final String prev, final String first, final String last) {
            this.total = total;
            this.page = page;
            this.size = size;
            this.next = next;
            this.prev = prev;
            this.first = first;
            this.last = last;
        }

        /**
         * Returns total tasks count.
         *
         * @return total tasks count.
         */
        public int getTotal() {
            return total;
        }

        /**
         * Returns page number.
         *
         * @return page number.
         */
        public int getPage() {
            return page;
        }

        /**
         * Returns page size.
         *
         * @return page size.
         */
        public int getSize() {
            return size;
        }

        /**
         * Returns next page link.
         *
         * @return next page link.
         */
        public String getNext() {
            return next;
        }

        /**
         * Returns previous page link.
         *
         * @return previous page link.
         */
        public String getPrev() {
            return prev;
        }

        /**
         * Returns first page link.
         *
         * @return first page link.
         */
        public String getFirst() {
            return first;
        }

        /**
         * Returns last page link.
         *
         * @return last page link.
         */
        public String getLast() {
            return last;
        }
    }

    /**
     * Returns response meta.
     *
     * @return response meta.
     */
    public Meta get_meta() {
        return _meta;
    }

    /**
     * Returns list of tasks.
     *
     * @return list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

}
