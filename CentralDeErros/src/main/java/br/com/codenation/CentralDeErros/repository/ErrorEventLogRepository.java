package br.com.codenation.CentralDeErros.repository;

import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ErrorEventLogRepository extends CrudRepository<ErrorEventLog, Long> {
    Page<ErrorEventLog> findAll(Pageable pageable);

    Optional<ErrorEventLog> findById(Long id);

    Page<ErrorEventLog> findByLevel(Levels level, Pageable pageable);

    Page<ErrorEventLog> findByLog(String log, Pageable pageable);

    Page<ErrorEventLog> findByOrigin(String origin, Pageable pageable);

    Page<ErrorEventLog> findByDescriptionContaining(String description, Pageable pageable);

    Page<ErrorEventLog> findByQuantity(Long quantity, Pageable pageable);

    Page<ErrorEventLog> findByCreatedAt(LocalDateTime createdAt, Pageable pageable);
}
