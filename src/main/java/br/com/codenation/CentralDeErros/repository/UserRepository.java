package br.com.codenation.CentralDeErros.repository;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
}
