package it.sevenbits.courses.homeworks.base.spring.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Task {

    private final UUID id;
    @NotNull
    @NotBlank
    private String text;
    @Nullable
    private String status;

    @JsonCreator
    public Task(@JsonProperty("id") UUID id,
                @JsonProperty("text") String text,
                @JsonProperty("status") String status) {
        this.id = id;
        this.text = text;
        this.status = status;
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
}
