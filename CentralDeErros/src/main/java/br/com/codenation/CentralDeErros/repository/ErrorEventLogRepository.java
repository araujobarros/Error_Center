package br.com.codenation.CentralDeErros.repository;

import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ErrorEventLogRepository extends PagingAndSortingRepository<ErrorEventLog, Long>, JpaSpecificationExecutor<ErrorEventLog> {

}
