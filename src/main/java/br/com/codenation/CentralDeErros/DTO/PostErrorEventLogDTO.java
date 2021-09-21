package br.com.codenation.CentralDeErros.DTO;

import br.com.codenation.CentralDeErros.enums.Levels;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
