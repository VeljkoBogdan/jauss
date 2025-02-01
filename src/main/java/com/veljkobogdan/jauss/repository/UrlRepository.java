package com.veljkobogdan.jauss.repository;

import com.veljkobogdan.jauss.document.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
}
