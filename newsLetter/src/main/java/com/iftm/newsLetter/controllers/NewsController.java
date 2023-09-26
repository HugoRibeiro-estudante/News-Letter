package com.iftm.newsLetter.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iftm.newsLetter.models.dtos.NewsDTO;
import com.iftm.newsLetter.services.NewsService;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {
    
@Autowired
private NewsService service;

@Value("${eureka.instance.instance-id}")
private String instaceId;

@GetMapping
public ResponseEntity<List<NewsDTO>> findAll() {
    return service.findAll();
}

@GetMapping("/id/{id}")
public ResponseEntity<NewsDTO> findById(@PathVariable("id") ObjectId id) {
    return service.findById(id);
}

// @GetMapping("/wage-above/{value}")
// public ResponseEntity<List<NewsDTO>> findById(@PathVariable("value") double value) {
//     return service.findEmployeesWithWageAboveValue(value);
// }

@GetMapping("/instance")
public String getInstanceId() {
    return instaceId;
}

@PostMapping
public ResponseEntity<NewsDTO> create(@RequestBody NewsDTO NewsDTO) {
    return service.save(NewsDTO);
}

@PutMapping
public ResponseEntity<NewsDTO> update(@RequestBody NewsDTO NewsDTO) {
    return service.update(NewsDTO);
}

@DeleteMapping("/id/{id}")
public ResponseEntity<?> delete(@PathVariable("id") ObjectId id) {
    return service.delete(id);
}


}
