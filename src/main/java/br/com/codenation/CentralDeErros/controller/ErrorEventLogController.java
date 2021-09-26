package br.com.codenation.CentralDeErros.controller;

import br.com.codenation.CentralDeErros.DTO.ErrorEventLogDTO;
import br.com.codenation.CentralDeErros.DTO.PostErrorEventLogDTO;
import br.com.codenation.CentralDeErros.controller.advice.ResourceNotFoundException;
import br.com.codenation.CentralDeErros.enums.Levels;
import br.com.codenation.CentralDeErros.mapper.MapStructMapper;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.repository.ErrorEventLogRepository;
import br.com.codenation.CentralDeErros.service.ErrorEventLogService;
import br.com.codenation.CentralDeErros.specification.ErrorEventLogSpec;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Api (value = "Error event log", description = "Manage error event log", tags = {"Error event log"})
public class ErrorEventLogController {

    private MapStructMapper mapStructMapper;
    private ErrorEventLogService errorEventLogService;
    private ErrorEventLogRepository errorEventLogRepository;

    private static final Logger log =
            LoggerFactory.getLogger(ErrorEventLogController.class);

    @Autowired
    public ErrorEventLogController(
            MapStructMapper mapStructMapper,
            ErrorEventLogService errorEventLogService,
            ErrorEventLogRepository errorEventLogRepository) {
        this.mapStructMapper = mapStructMapper;
        this.errorEventLogService = errorEventLogService;
        this.errorEventLogRepository = errorEventLogRepository;
    }

    @PostMapping(value = "/event")
    @ApiOperation("Create an error log")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Event created successfully")})
    public ResponseEntity<ErrorEventLogDTO> create(
            @Valid @RequestBody PostErrorEventLogDTO postErrorEventLogDTO){
        try {
            ErrorEventLog saved = this.errorEventLogService.save(mapStructMapper.PostErrorEventLogDTOToerrorEventLog(postErrorEventLogDTO));

            return new ResponseEntity<ErrorEventLogDTO>(mapStructMapper.errorEventLogToErrorEventLogDTO(saved)
                    , HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("an internal error occurred, it was not possible to complete this request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/event/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Event not found"),
            @ApiResponse(code = 200, message = "Event found successfully")
    })
    public ResponseEntity<ErrorEventLogDTO> getById(
            @PathVariable(value = "id") Long id
    ) {

        return new ResponseEntity<ErrorEventLogDTO>(mapStructMapper.errorEventLogToErrorEventLogDTO(
                        this.errorEventLogService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event"))), HttpStatus.OK);
    }


    @GetMapping("/events")
    @ApiOperation("This endpoint filters and sorts events, given attributes, the query can be cumulative, if no events are found returns an empty list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event(s) found successfully")})
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "registeredAfter",
                    value = "yyyy-mm-ddThh:mm:ss - This field needs registeredBefore param",
                    dataType = "string",
                    paramType = "query" ),
            @ApiImplicitParam(
                    name = "registeredBefore",
                    value = "yyyy-mm-ddThh:mm:ss - This field needs registeredAfter param",
                    dataType = "string",
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "greaterThan",
                    value = "This field needs lessThan param",
                    paramType = "query" ),
            @ApiImplicitParam(
                    name = "lessThan",
                    value = "This field needs greaterThan param",
                    paramType = "query"),
            @ApiImplicitParam(name = "description", value = "search for ignore-case term", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "log", value = "search for ignore-case term", dataType = "string", paramType = "query")})
    public Page<ErrorEventLog> findEvents(
            ErrorEventLogSpec spec,
            @RequestParam(value = "level", required = false) Levels level,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "sort", required = false) String sort,
            @PageableDefault(size = 10, direction = Sort.Direction.ASC) Pageable pageable) {
        return this.errorEventLogRepository.findAll(spec, pageable);
    }
}
