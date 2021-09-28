package br.com.codenation.CentralDeErros.service;

import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ErrorEventLogService {
    List<ErrorEventLog> findAll(Pageable pageable);

    Optional<ErrorEventLog> findById(Long id);

    ErrorEventLog save(ErrorEventLog errorEventLog);
}
