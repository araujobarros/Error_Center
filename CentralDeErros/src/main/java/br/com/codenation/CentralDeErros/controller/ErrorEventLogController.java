package br.com.codenation.CentralDeErros.controller;

import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.repository.ErrorEventLogRepository;
import br.com.codenation.CentralDeErros.service.ErrorEventLogService;
import br.com.codenation.CentralDeErros.specification.ErrorEventLogSpec;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ErrorEventLogController {

    @Autowired
    private ErrorEventLogService errorEventLogService;

    @Autowired
    private ErrorEventLogRepository errorEventLogRepository;

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


    @ApiImplicitParams({
            @ApiImplicitParam(name = "registeredAfter", value = "The start date needs registeredBefore param", dataType = "string", example = "yyyy-mm-ddThh:mm:ss", paramType = "query" ),
            @ApiImplicitParam(name = "registeredBefore", value = "The end date needs registeredAfter param", dataType = "string", example = "yyyy-mm-ddThh:mm:ss", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "search for ignore-case term", dataType = "string", paramType = "query"),
    })
    @GetMapping("/event")
    @ApiOperation("Filters events, given attributes, can be cumulative")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND"),
            @ApiResponse(code = 200, message = "SUCCESS")})
    public Page<ErrorEventLog> findEvents(
            ErrorEventLogSpec spec,
            @RequestParam(value = "log", required = false) String log,
            @RequestParam(value = "level", required = false) Levels level,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "quantity", required = false) Long quantity,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return this.errorEventLogRepository.findAll(spec, pageable);
    }
}
