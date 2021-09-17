package br.com.codenation.CentralDeErros.repository;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;

import java.util.Optional;

public interface ErrorEventLogRepositoryId {
    Optional<ErrorEventLog> findById(Long id);
}
