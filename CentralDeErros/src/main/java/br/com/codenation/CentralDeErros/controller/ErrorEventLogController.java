package br.com.codenation.CentralDeErros.controller;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.service.ErrorEventLogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ErrorEventLogController {

    @Autowired
    private ErrorEventLogService errorEventLogService;

    @PostMapping
    @ApiOperation("Cria um log de erro")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Log criado com sucesso")})
    public ResponseEntity<ErrorEventLog> create(@Valid @RequestBody ErrorEventLog errorEventLog){
        return new ResponseEntity<ErrorEventLog>(this.errorEventLogService.save(errorEventLog), HttpStatus.CREATED);
    }

//    A API não deve listar todos os logs, estou colocando este GET aqui só para testar
    @GetMapping
    @ApiOperation("Lista todos os logs")
    public Iterable<ErrorEventLog> findAll(Pageable pageable) {
        return this.errorEventLogService.findAll(pageable);
    }

}
