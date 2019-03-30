package it.sevenbits.courses.practices.postgresql.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

/**
 * Model for task.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class Task {

    private final UUID id;
    private String text;
    private String status;
    private Date createdAt;

    /**
     * Constructor.
     *
     * @param id        - task id.
     * @param text      - task text.
     * @param status    - task status.
     * @param createdAt - task creation date.
     */
    @JsonCreator
    public Task(@JsonProperty("id") final UUID id,
                @JsonProperty("text") final String text,
                @JsonProperty("status") final String status,
                @JsonProperty("createdAt") final Date createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.status = status;
    }

    /**
     * Returns task id.
     *
     * @return task id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns task text.
     *
     * @return task text.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns task status.
     *
     * @return task status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns task creation date.
     *
     * @return task creation date.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets new task text.
     *
     * @param text - new text.
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * Sets new task status.
     *
     * @param status - new status.
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
