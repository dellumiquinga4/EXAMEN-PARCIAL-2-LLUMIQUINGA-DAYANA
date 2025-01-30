package com.banquito.branch.mapper;

import com.banquito.branch.dto.BranchDTO;
import com.banquito.branch.dto.BranchHolidayDTO;
import com.banquito.branch.model.Branch;
import com.banquito.branch.model.BranchHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BranchMapper {
    
    BranchDTO toDto(Branch branch);
    
    Branch toEntity(BranchDTO branchDTO);
    
    BranchHolidayDTO toDto(BranchHoliday branchHoliday);
    
    BranchHoliday toEntity(BranchHolidayDTO branchHolidayDTO);
    
    List<BranchDTO> toDtoList(List<Branch> branches);
    
    void updateBranchFromDto(BranchDTO branchDTO, @MappingTarget Branch branch);
} 