package com.banquito.branch.controller;

import com.banquito.branch.dto.BranchDTO;
import com.banquito.branch.dto.BranchHolidayDTO;
import com.banquito.branch.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @Operation(summary = "Get all branches",
            description = "Retrieves a list of all bank branches",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of branches retrieved successfully",
                            content = @Content(schema = @Schema(implementation = BranchDTO.class)))
            })
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PostMapping
    @Operation(summary = "Create a new branch",
            description = "Creates a new bank branch without holidays",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Branch created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            })
    public ResponseEntity<BranchDTO> createBranch(@Valid @RequestBody BranchDTO branchDTO) {
        return new ResponseEntity<>(branchService.createBranch(branchDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID",
            description = "Retrieves a specific branch by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Branch found"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
            })
    public ResponseEntity<BranchDTO> getBranchById(
            @Parameter(description = "Branch ID", required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @PatchMapping("/{id}/phone")
    @Operation(summary = "Update branch phone number",
            description = "Updates the phone number of a specific branch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Phone number updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
            })
    public ResponseEntity<BranchDTO> updateBranchPhone(
            @Parameter(description = "Branch ID", required = true)
            @PathVariable String id,
            @Parameter(description = "New phone number", required = true)
            @RequestParam String phoneNumber) {
        return ResponseEntity.ok(branchService.updateBranchPhone(id, phoneNumber));
    }

    @PostMapping("/{id}/holidays")
    @Operation(summary = "Add holidays to branch",
            description = "Adds a list of holidays to a specific branch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Holidays added successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
            })
    public ResponseEntity<BranchDTO> addHolidays(
            @Parameter(description = "Branch ID", required = true)
            @PathVariable String id,
            @Valid @RequestBody List<BranchHolidayDTO> holidays) {
        return ResponseEntity.ok(branchService.addHolidays(id, holidays));
    }

    @DeleteMapping("/{id}/holidays")
    @Operation(summary = "Remove holidays from branch",
            description = "Removes specified holidays from a branch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Holidays removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
            })
    public ResponseEntity<BranchDTO> removeHolidays(
            @Parameter(description = "Branch ID", required = true)
            @PathVariable String id,
            @Parameter(description = "Holiday dates to remove", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> dates) {
        return ResponseEntity.ok(branchService.removeHolidays(id, dates));
    }

    @GetMapping("/{id}/holidays")
    @Operation(summary = "Get branch holidays",
            description = "Retrieves all holidays for a specific branch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Holidays retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
            })
    public ResponseEntity<List<BranchHolidayDTO>> getBranchHolidays(
            @Parameter(description = "Branch ID", required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(branchService.getBranchHolidays(id));
    }

    @GetMapping("/{id}/holidays/check")
    @Operation(summary = "Check if date is holiday",
            description = "Checks if a specific date is a holiday for a branch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Check completed successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
            })
    public ResponseEntity<Boolean> isHoliday(
            @Parameter(description = "Branch ID", required = true)
            @PathVariable String id,
            @Parameter(description = "Date to check", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(branchService.isHoliday(id, date));
    }
} 