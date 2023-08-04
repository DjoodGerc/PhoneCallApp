package com.example.phonecallapp.mappers;

import com.example.phonecallapp.repository.CallDTO;
import com.example.phonecallapp.repository.CallEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MyMapper {
    CallEntity toEntity(CallDTO callDTO);
    CallDTO toDAO(CallEntity callEntity);
    List<CallDTO> toDAOList(List<CallEntity> dtos);
}
