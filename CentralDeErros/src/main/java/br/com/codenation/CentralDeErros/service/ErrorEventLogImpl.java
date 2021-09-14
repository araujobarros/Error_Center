package br.com.codenation.CentralDeErros.service;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.repository.ErrorEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ErrorEventLogImpl implements ErrorEventLogService {

    @Autowired
    private ErrorEventLogRepository errorEventLogRepository;


    @Override
    public List<ErrorEventLog> findAll(Pageable pageable) {
        return this.errorEventLogRepository.findAll(pageable).getContent();
    }

    @Override
    public ErrorEventLog save(ErrorEventLog errorEventLog) {
        return this.errorEventLogRepository.save(errorEventLog);
    }
}
