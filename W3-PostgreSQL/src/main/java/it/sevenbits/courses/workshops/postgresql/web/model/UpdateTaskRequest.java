package it.sevenbits.courses.workshops.postgresql.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.validation.Valid;

/**
 * Model for update task request.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class UpdateTaskRequest {
    @Nullable
    @Valid
    private String text;

    @Nullable
    @Valid
    private String status;

    /**
     * Constructor.
     *
     * @param text - task text.
     * @param status - task status.
     */
    @JsonCreator
    public UpdateTaskRequest(@JsonProperty("text") final String text,
                             @JsonProperty("status") final String status) {
        this.text = text;
        this.status = status;
    }

    /**
     * Returns requested task text.
     *
     * @return requested task text.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns requested task status.
     *
     * @return requested task status.
     */
    public String getStatus() {
        return status;
    }

}
