package br.com.codenation.CentralDeErros.mapper;

import br.com.codenation.CentralDeErros.DTO.ErrorEventLogDTO;
import br.com.codenation.CentralDeErros.DTO.PostErrorEventLogDTO;
import br.com.codenation.CentralDeErros.DTO.PostUserDTO;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    ErrorEventLogDTO errorEventLogToErrorEventLogDTO(ErrorEventLog errorEventLog);

    ErrorEventLog PostErrorEventLogDTOToerrorEventLog(PostErrorEventLogDTO postErrorEventLogDTO);

    User PostUserDTOToUser(PostUserDTO postUserDTO);
}
