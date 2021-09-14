package br.com.codenation.CentralDeErros.repository;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ErrorEventLogRepository extends CrudRepository<ErrorEventLog, Long> {
    Page<ErrorEventLog> findAll(Pageable pageable);
}
