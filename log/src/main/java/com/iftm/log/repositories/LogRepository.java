package com.iftm.log.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iftm.log.models.Log;

public interface LogRepository extends MongoRepository<Log, String>{
    
}
