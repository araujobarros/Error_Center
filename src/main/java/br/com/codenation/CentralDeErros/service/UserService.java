package br.com.codenation.CentralDeErros.service;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    List<User> findAll(Pageable pageable);

    User save(User user);

    void deleteById(Long id);

}
