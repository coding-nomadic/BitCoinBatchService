package com.example.batch.data.repository;

import com.example.batch.data.entity.BitcoinData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinDataRepository extends MongoRepository<BitcoinData, String> {
}
