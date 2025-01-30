package com.banquito.branch.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "branches")
public class Branch {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String emailAddress;
    
    private String name;
    private String phoneNumber;
    private String state;
    
    @CreatedDate
    private LocalDateTime creationDate;
    
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
    private List<BranchHoliday> branchHolidays = new ArrayList<>();
} 