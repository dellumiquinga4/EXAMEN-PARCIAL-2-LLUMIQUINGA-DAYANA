package com.banquito.branch.repository;

import com.banquito.branch.model.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {
    boolean existsByEmailAddress(String emailAddress);
} 