package com.banquito.branch.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BranchHoliday {
    private LocalDate date;
    private String name;
} 