package com.iftm.newsLetter.services;

import java.util.List;
import java.util.stream.Collectors;

import com.iftm.newsLetter.mensages.RabbitMqSendLog;
import com.iftm.newsLetter.models.dtos.LogDTO;
import com.iftm.newsLetter.models.dtos.NewsDTO;
import com.iftm.newsLetter.repositories.NewsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<NewsDTO> update(NewsDTO newsDTO) {
        // validar NewsDTO
        if(newsDTO.getId() == null)
            return ResponseEntity.badRequest().build();

        var objectId = new ObjectId(String.valueOf(newsDTO.getId()));
        var dbNews = repository.findById(objectId);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();
        // atualizar
        var dbNewsObj = dbNews.get();
        dbNewsObj.setTitle(newsDTO.getTitle());
        dbNewsObj.setDate(newsDTO.getDate());
        dbNewsObj.setEditorName(newsDTO.getEditorName());
        dbNewsObj.getPosts();
        return ResponseEntity.ok(new NewsDTO(repository.save(dbNewsObj)));
    }

    //@Transactional
    public ResponseEntity<?> delete(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();



        repository.deleteById(id);

        var dbNews = repository.findById(id);
        if(dbNews.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();

        return ResponseEntity.ok().build();
    }




}
