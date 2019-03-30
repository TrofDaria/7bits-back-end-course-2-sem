package it.sevenbits.courses.practices.postgresql.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Model for add task request.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class AddTaskRequest {
    @NotNull
    @NotBlank
    private String text;

    /**
     * Constructor.
     *
     * @param text      - task text.
     */
    @JsonCreator
    public AddTaskRequest(@JsonProperty("text") final String text) {
        this.text = text;
    }

    /**
     * Returns requested task text.
     *
     * @return requested task text.
     */
    public String getText() {
        return text;
    }
}
