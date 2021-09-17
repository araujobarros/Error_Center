package br.com.codenation.CentralDeErros.controller;

import br.com.codenation.CentralDeErros.DTO.ErrorEventLogDTO;
import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.mapper.MapStructMapper;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.repository.ErrorEventLogRepository;
import br.com.codenation.CentralDeErros.service.ErrorEventLogService;
import br.com.codenation.CentralDeErros.specification.ErrorEventLogSpec;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ErrorEventLogController {

    private MapStructMapper mapStructMapper;
    private ErrorEventLogService errorEventLogService;
    private ErrorEventLogRepository errorEventLogRepository;

    @Autowired
    public ErrorEventLogController(MapStructMapper mapStructMapper, ErrorEventLogService errorEventLogService, ErrorEventLogRepository errorEventLogRepository) {
        this.mapStructMapper = mapStructMapper;
        this.errorEventLogService = errorEventLogService;
        this.errorEventLogRepository = errorEventLogRepository;
    }

    @PostMapping
    @ApiOperation("Create an error log")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Event created successfully")})
    public ResponseEntity<ErrorEventLog> create(
            @Valid @RequestBody ErrorEventLog errorEventLog){
        return new ResponseEntity<ErrorEventLog>(
                this.errorEventLogService.save(errorEventLog), HttpStatus.CREATED);
    }

    @GetMapping(value = "/event/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Event not found"),
            @ApiResponse(code = 200, message = "Event found successfully")
    })
    public ResponseEntity<ErrorEventLogDTO> getById(
            @PathVariable(value = "id") Long id
    ) {
        return new ResponseEntity<>(
                mapStructMapper.errorEventLogToErrorEventLogDTO(
                        this.errorEventLogService.findById(id).get()), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "registeredAfter", value = "yyyy-mm-ddThh:mm:ss - The start date needs registeredBefore param", dataType = "string", paramType = "query" ),
            @ApiImplicitParam(name = "registeredBefore", value = "yyyy-mm-ddThh:mm:ss - The end date needs registeredAfter param", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "search for ignore-case term", dataType = "string", paramType = "query"),
    })
    @GetMapping("/events")
    @ApiOperation("It filters and sorts events, given attributes, the query can be cumulative")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event(s) found successfully")})
    public Page<ErrorEventLog> findEvents(
            ErrorEventLogSpec spec,
            @RequestParam(value = "log", required = false) String log,
            @RequestParam(value = "level", required = false) Levels level,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "quantity", required = false) Long quantity,
            @RequestParam(value = "sort", required = false) String sort,
            @PageableDefault(size = 10, direction = Sort.Direction.ASC) Pageable pageable) {
        return this.errorEventLogRepository.findAll(spec, pageable);
    }
}
