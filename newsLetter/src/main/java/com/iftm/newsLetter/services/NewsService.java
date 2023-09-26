package com.iftm.newsLetter.services;

import java.util.List;

import com.iftm.newsLetter.models.dtos.LogDTO;
import com.iftm.newsLetter.models.dtos.NewsDTO;
import com.iftm.newsLetter.repositories.NewsRepository;
import com.netflix.discovery.converters.Auto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsService {
    
    @Autowired
    private NewsRepository repository;

    @Autowired
    private RabbitMqSendLog rabbitMqSendLog;


    public ResponseEntity<List<NewsDTO>> findAll() {
        var dbNews = repository.findAll();
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();

        var newsDtos = dbNews.stream().map(news -> {
            return new NewsDTO(news);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(newsDtos);
    }

    public ResponseEntity<NewsDTO> findById(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();
        var dbNews = repository.findById(id);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new NewsDTO(dbNews.get()));
    }

    // public ResponseEntity<List<NewsDTO>> findEmployeesWithWageAboveValue(double value) {
    //     var dbNews = repository.findEmployeesWithWageAboveValue(value);
    //     if(dbNews.isEmpty())
    //         return ResponseEntity.notFound().build();

    //     var NewsDTOs = dbNews.get().stream().map(employee -> {
    //         return new NewsDTO(employee);
    //     }).collect(Collectors.toList());

    //     return ResponseEntity.ok(NewsDTOs);
    // }

    public ResponseEntity<NewsDTO> save(NewsDTO NewsDTO) {
        // validar NewsDTO
        if(NewsDTO.getDate().isBlank() 
                || NewsDTO.getEditorName().isBlank() 
                || NewsDTO.getTitle().isBlank())
            return ResponseEntity.badRequest().build();
        var dbNews = repository.save(NewsDTO.toNews());

        rabbitMqSendLog.sendLog(new LogDTO<NewsDTO>("created", new NewsDTO(dbNews)));

        return ResponseEntity.ok(new NewsDTO(dbNews));
    }

    public ResponseEntity<NewsDTO> update(NewsDTO NewsDTO) {
        // validar NewsDTO
        if(NewsDTO.getId() == null)
            return ResponseEntity.badRequest().build();

        var objectId = new ObjectId(NewsDTO.getId());
        var dbEmployee = repository.findById(objectId);
        if(dbEmployee.isEmpty())
            return ResponseEntity.notFound().build();
        // atualizar
        var dbNewsObj = dbNews.get();
        dbNewsObj.setDate(NewsDTO.getDate());
        dbNewsObj.setEditorName(NewsDTO.getEditorName());
        dbNewsObj.setTitle(NewsDTO.getTitle());
        dbNewsObj.getPosts(NewsDTO.getPosts());
        return ResponseEntity.ok(new NewsDTO(repository.save(dbNewsObj)));
    }

    //@Transactional
    public ResponseEntity<?> delete(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();

        var sector = sectorRepository.findSectorByEmployeeId(id);

        repository.deleteById(id);

        var dbEmployee = repository.findById(id);
        if(dbEmployee.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();

        if(!sector.isEmpty()) {
            sector.get().getEmployees().removeIf(employee -> employee.getId().toString().equals(id.toString()));
            sectorRepository.save(sector.get());
        }

        return ResponseEntity.ok().build();
    }




}
