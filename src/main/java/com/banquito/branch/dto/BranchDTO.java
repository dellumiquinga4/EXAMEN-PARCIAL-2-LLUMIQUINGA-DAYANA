package com.banquito.branch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Branch Information")
public class BranchDTO {
    
    @Schema(description = "Branch ID", example = "507f1f77bcf86cd799439011")
    private String id;
    
    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Branch email address", example = "branch@banquito.com")
    private String emailAddress;
    
    @NotBlank(message = "Name is required")
    @Schema(description = "Branch name", example = "Main Branch")
    private String name;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Invalid phone number format")
    @Schema(description = "Branch phone number", example = "+593987654321")
    private String phoneNumber;
    
    @NotBlank(message = "State is required")
    @Schema(description = "Branch state", example = "ACTIVE")
    private String state;
    
    @Schema(description = "Branch creation date")
    private LocalDateTime creationDate;
    
    @Schema(description = "Branch last modified date")
    private LocalDateTime lastModifiedDate;
    
    @Schema(description = "Branch holidays")
    private List<BranchHolidayDTO> branchHolidays = new ArrayList<>();
} 