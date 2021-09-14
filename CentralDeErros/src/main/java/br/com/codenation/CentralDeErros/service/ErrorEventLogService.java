package br.com.codenation.CentralDeErros.service;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ErrorEventLogService {
    List<ErrorEventLog> findAll(Pageable pageable);

    ErrorEventLog save(ErrorEventLog errorEventLog);
}
