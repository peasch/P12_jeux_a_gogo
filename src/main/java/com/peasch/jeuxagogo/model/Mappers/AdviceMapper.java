package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.AdviceDto;
import com.peasch.jeuxagogo.model.entities.Advice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {UserMapper.class})
public interface AdviceMapper {

    Advice fromDtoToAdvice(AdviceDto adviceDto);

    @Mapping(target = "user", source = "user", qualifiedByName = "toDtoWithRoles")
    AdviceDto fromAdviceToDto(Advice advice);
}
