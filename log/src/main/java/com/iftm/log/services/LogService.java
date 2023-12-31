package com.iftm.log.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.iftm.log.models.dtos.LogDTO;
import com.iftm.log.models.dtos.NewsDTO;
import com.iftm.log.repositories.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository repository;

    public ResponseEntity<List<LogDTO<NewsDTO>>> findAll() {
        var dbLogs = repository.findAll();
        if(dbLogs == null || dbLogs.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var dbLogsDTO = dbLogs.stream().map(log -> {
            return new LogDTO<NewsDTO>(log);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(dbLogsDTO, HttpStatus.OK);
    }

    public ResponseEntity<LogDTO<NewsDTO>> save(LogDTO logDTO) {
        if(logDTO.getAction().isBlank() || logDTO.getObject() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var dbLog = repository.save(logDTO.toLog());
        return new ResponseEntity<>(new LogDTO(dbLog), HttpStatus.CREATED);
    }
}
