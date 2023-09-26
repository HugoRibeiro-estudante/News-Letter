package com.iftm.newsLetter.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.iftm.newsLetter.models.News;

public interface NewsRepository extends MongoRepository<News, ObjectId>{
    
}
