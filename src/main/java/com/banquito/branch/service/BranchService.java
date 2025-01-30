package com.banquito.branch.service;

import com.banquito.branch.dto.BranchDTO;
import com.banquito.branch.dto.BranchHolidayDTO;
import com.banquito.branch.mapper.BranchMapper;
import com.banquito.branch.model.Branch;
import com.banquito.branch.model.BranchHoliday;
import com.banquito.branch.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Transactional(readOnly = true)
    public List<BranchDTO> getAllBranches() {
        log.info("Getting all branches");
        List<Branch> branches = branchRepository.findAll();
        log.info("Found {} branches", branches.size());
        return branchMapper.toDtoList(branches);
    }

    @Transactional
    public BranchDTO createBranch(BranchDTO branchDTO) {
        log.info("Creating new branch with email: {}", branchDTO.getEmailAddress());
        if (branchRepository.existsByEmailAddress(branchDTO.getEmailAddress())) {
            throw new IllegalArgumentException("Branch with email " + branchDTO.getEmailAddress() + " already exists");
        }
        Branch branch = branchMapper.toEntity(branchDTO);
        branch = branchRepository.save(branch);
        log.info("Branch created successfully with id: {}", branch.getId());
        return branchMapper.toDto(branch);
    }

    @Transactional(readOnly = true)
    public BranchDTO getBranchById(String id) {
        log.info("Getting branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        log.info("Branch found successfully");
        return branchMapper.toDto(branch);
    }

    @Transactional
    public BranchDTO updateBranchPhone(String id, String phoneNumber) {
        log.info("Updating phone number for branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        branch.setPhoneNumber(phoneNumber);
        branch = branchRepository.save(branch);
        log.info("Branch phone number updated successfully");
        return branchMapper.toDto(branch);
    }

    @Transactional
    public BranchDTO addHolidays(String id, List<BranchHolidayDTO> holidays) {
        log.info("Adding holidays to branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        
        List<BranchHoliday> newHolidays = holidays.stream()
                .map(branchMapper::toEntity)
                .toList();
        
        branch.getBranchHolidays().addAll(newHolidays);
        branch = branchRepository.save(branch);
        log.info("Holidays added successfully");
        return branchMapper.toDto(branch);
    }

    @Transactional
    public BranchDTO removeHolidays(String id, List<LocalDate> holidayDates) {
        log.info("Removing holidays from branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        
        branch.getBranchHolidays().removeIf(holiday -> holidayDates.contains(holiday.getDate()));
        branch = branchRepository.save(branch);
        log.info("Holidays removed successfully");
        return branchMapper.toDto(branch);
    }

    @Transactional(readOnly = true)
    public List<BranchHolidayDTO> getBranchHolidays(String id) {
        log.info("Getting holidays for branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        
        return branch.getBranchHolidays().stream()
                .map(branchMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isHoliday(String id, LocalDate date) {
        log.info("Checking if date {} is holiday for branch with id: {}", date, id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        
        return branch.getBranchHolidays().stream()
                .anyMatch(holiday -> holiday.getDate().equals(date));
    }
} 