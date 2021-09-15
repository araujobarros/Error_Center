package br.com.codenation.CentralDeErros.controller;

import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.service.ErrorEventLogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

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

    @GetMapping("/event")
    @ApiOperation("Busca eventos por determinado atributo")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "NOT_FOUND"),
            @ApiResponse(code = 200, message = "SUCCESS")})
    public Page<ErrorEventLog> findEvents(
            @RequestParam(value = "level", required = false) Levels level,
            @RequestParam(value = "log", required = false) String log,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "quantity", required = false) Long quantity,
            @RequestParam(value = "created", required = false) LocalDateTime created,
            @PageableDefault(value = 100, page = 0, direction =
                    Sort.Direction.ASC,
                    sort = "id") Pageable pageable) {
        if (level != null) {
            return this.errorEventLogService.findByLevel(level, pageable);
        }
        return this.errorEventLogService.findByLog(log, pageable);
    }

}
