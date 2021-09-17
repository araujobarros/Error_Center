package br.com.codenation.CentralDeErros.mapper;

import br.com.codenation.CentralDeErros.DTO.ErrorEventLogDTO;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    ErrorEventLogDTO errorEventLogToErrorEventLogDTO(ErrorEventLog errorEventLog);
}
