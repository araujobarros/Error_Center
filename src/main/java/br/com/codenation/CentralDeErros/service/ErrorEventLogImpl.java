package br.com.codenation.CentralDeErros.service;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.repository.ErrorEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorEventLogImpl implements ErrorEventLogService {

    @Autowired
    private ErrorEventLogRepository errorEventLogRepository;

//    @Autowired
//    private ErrorEventLogRepositoryId errorEventLogRepositoryId;


    @Override
    public List<ErrorEventLog> findAll(Pageable pageable) {
        return this.errorEventLogRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<ErrorEventLog> findById(Long id) {
        return errorEventLogRepository.findById(id);
    }

//    @Override
//    public Page<ErrorEventLog> findByLevel(Levels level, Pageable pageable) {
//        return this.errorEventLogRepository.findByLevel(level, pageable);
//    }
//
//    @Override
//    public Page<ErrorEventLog> findByLog(String log, Pageable pageable) {
//        return this.errorEventLogRepository.findByLog(log, pageable);
//    }
//
//    @Override
//    public Page<ErrorEventLog> findByOrigin(String origin, Pageable pageable) {
//        return this.errorEventLogRepository.findByOrigin(origin, pageable);
//    }
//
//    @Override
//    public Page<ErrorEventLog> findByDescriptionContaining(String description, Pageable pageable) {
//        return this.errorEventLogRepository.findByDescriptionContaining(description, pageable);
//    }
//
//    @Override
//    public Page<ErrorEventLog> findByQuantity(Long quantity, Pageable pageable) {
//        return this.errorEventLogRepository.findByQuantity(quantity, pageable);
//    }
//
//    @Override
//    public Page<ErrorEventLog> findByCreatedAt(LocalDateTime createdAt, Pageable pageable) {
//        return this.errorEventLogRepository.findByCreatedAt(createdAt, pageable);
//    }

    @Override
    public ErrorEventLog save(ErrorEventLog errorEventLog) {
        return this.errorEventLogRepository.save(errorEventLog);
    }
}
