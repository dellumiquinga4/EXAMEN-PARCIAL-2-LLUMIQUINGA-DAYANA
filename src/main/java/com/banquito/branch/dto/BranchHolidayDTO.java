package com.banquito.branch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Branch Holiday Information")
public class BranchHolidayDTO {
    
    @NotNull(message = "Holiday date is required")
    @Schema(description = "Holiday date", example = "2024-01-01")
    private LocalDate date;
    
    @NotBlank(message = "Holiday name is required")
    @Schema(description = "Holiday name", example = "New Year's Day")
    private String name;
} 