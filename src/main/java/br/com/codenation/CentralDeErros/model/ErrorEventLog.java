package br.com.codenation.CentralDeErros.model;


import br.com.codenation.CentralDeErros.enums.Levels;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
//only to deploy
@Entity(name="error_event_log")
public class ErrorEventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column(length = 10)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Levels level;

    @Column(length = 300)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String log;

    @Column(length = 500)
    @NotNull
    private String description;

    @Column(length = 100)
    @NotNull
    private String origin;

    @Column
    private Long quantity = 0L;

    @Column(name = "created_at")
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
