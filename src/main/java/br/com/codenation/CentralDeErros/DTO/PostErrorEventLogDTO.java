package br.com.codenation.CentralDeErros.DTO;

import br.com.codenation.CentralDeErros.enums.Levels;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostErrorEventLogDTO {
    @JsonProperty("level")
    private Levels level;
    @JsonProperty("log")
    private String log;
    @JsonProperty("description")
    private String description;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("quantity")
    private Long quantity = 0L;
}
