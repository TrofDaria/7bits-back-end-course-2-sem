package it.sevenbits.courses.practices.base.postgresql.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Task {

    private final UUID id;
    private String text;
    private String status;
    private Date createdAt;
    private final String defaultStatus = "inbox";

    @JsonCreator
    public Task(@JsonProperty("id") UUID id,
                @JsonProperty("text") String text,
                @JsonProperty("status") String status,
                Date createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        if (status == null) {
            this.status = defaultStatus;
        } else {
            this.status = status;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
