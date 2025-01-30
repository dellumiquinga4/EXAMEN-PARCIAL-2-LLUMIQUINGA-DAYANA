package com.banquito.branch.controller;

import com.banquito.branch.dto.BranchDTO;
import com.banquito.branch.dto.BranchHolidayDTO;
import com.banquito.branch.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Tag(name = "Branch Management", description = "APIs for managing bank branches and their holidays")
public class BranchController {

    private final BranchService branchService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Get all branches",
        description = "Retrieves a list of all bank branches with their details including holidays"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "List of branches retrieved successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = BranchDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Create a new branch",
        description = "Creates a new bank branch without holidays. Email address must be unique."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Branch created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = BranchDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data or email already exists",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<BranchDTO> createBranch(
        @Parameter(
            description = "Branch details for creation",
            required = true,
            schema = @Schema(implementation = BranchDTO.class)
        )
        @Valid @RequestBody BranchDTO branchDTO
    ) {
        return new ResponseEntity<>(branchService.createBranch(branchDTO), HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Get branch by ID",
        description = "Retrieves detailed information of a specific branch by its ID"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Branch found successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = BranchDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Branch not found with the given ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<BranchDTO> getBranchById(
        @Parameter(
            description = "Branch ID (UUID format)",
            required = true,
            example = "507f1f77bcf86cd799439011"
        )
        @PathVariable String id
    ) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @PatchMapping(
        value = "/{id}/phone",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Update branch phone number",
        description = "Updates only the phone number of a specific branch"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Phone number updated successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = BranchDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid phone number format",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Branch not found with the given ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<BranchDTO> updateBranchPhone(
        @Parameter(
            description = "Branch ID",
            required = true,
            example = "507f1f77bcf86cd799439011"
        )
        @PathVariable String id,
        @Parameter(
            description = "New phone number (format: +[country code][number], 10-13 digits)",
            required = true,
            example = "+593987654321"
        )
        @RequestParam String phoneNumber
    ) {
        return ResponseEntity.ok(branchService.updateBranchPhone(id, phoneNumber));
    }

    @PostMapping(
        value = "/{id}/holidays",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Add holidays to branch",
        description = "Adds a list of holidays to a specific branch"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Holidays added successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = BranchDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid holiday data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Branch not found with the given ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<BranchDTO> addHolidays(
        @Parameter(
            description = "Branch ID",
            required = true,
            example = "507f1f77bcf86cd799439011"
        )
        @PathVariable String id,
        @Parameter(
            description = "List of holidays to add",
            required = true,
            schema = @Schema(implementation = BranchHolidayDTO.class)
        )
        @Valid @RequestBody List<BranchHolidayDTO> holidays
    ) {
        return ResponseEntity.ok(branchService.addHolidays(id, holidays));
    }

    @DeleteMapping(
        value = "/{id}/holidays",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Remove holidays from branch",
        description = "Removes specified holidays from a branch by their dates"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Holidays removed successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = BranchDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid date format",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Branch not found with the given ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<BranchDTO> removeHolidays(
        @Parameter(
            description = "Branch ID",
            required = true,
            example = "507f1f77bcf86cd799439011"
        )
        @PathVariable String id,
        @Parameter(
            description = "List of holiday dates to remove (ISO format: YYYY-MM-DD)",
            required = true,
            example = "2024-01-01"
        )
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> dates
    ) {
        return ResponseEntity.ok(branchService.removeHolidays(id, dates));
    }

    @GetMapping(
        value = "/{id}/holidays",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Get branch holidays",
        description = "Retrieves all holidays for a specific branch"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Holidays retrieved successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = BranchHolidayDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Branch not found with the given ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<List<BranchHolidayDTO>> getBranchHolidays(
        @Parameter(
            description = "Branch ID",
            required = true,
            example = "507f1f77bcf86cd799439011"
        )
        @PathVariable String id
    ) {
        return ResponseEntity.ok(branchService.getBranchHolidays(id));
    }

    @GetMapping(
        value = "/{id}/holidays/check",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Check if date is holiday",
        description = "Checks if a specific date is marked as a holiday for a branch"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Check completed successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Boolean.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid date format",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Branch not found with the given ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    public ResponseEntity<Boolean> isHoliday(
        @Parameter(
            description = "Branch ID",
            required = true,
            example = "507f1f77bcf86cd799439011"
        )
        @PathVariable String id,
        @Parameter(
            description = "Date to check (ISO format: YYYY-MM-DD)",
            required = true,
            example = "2024-01-01"
        )
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(branchService.isHoliday(id, date));
    }
} 