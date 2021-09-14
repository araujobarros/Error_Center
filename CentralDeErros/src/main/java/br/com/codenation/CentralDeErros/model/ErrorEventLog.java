package br.com.codenation.CentralDeErros.model;


import br.com.codenation.CentralDeErros.enums.Levels;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ErrorEventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(length = 10)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Levels level;

    @Column(length = 300)
    @NotNull
    private String log;

    @Column(length = 500)
    @NotNull
    private String description;

    @Column(length = 100)
    @NotNull
    private String origin;

    @Column
    private Long quantity = 0L;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
}
