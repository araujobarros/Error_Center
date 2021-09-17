package br.com.codenation.CentralDeErros.repository;

import br.com.codenation.CentralDeErros.DTO.ErrorEventLogDTO;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.specification.ErrorEventLogSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ErrorEventLogRepository extends PagingAndSortingRepository<ErrorEventLog, Long>, JpaSpecificationExecutor<ErrorEventLog> {
}
