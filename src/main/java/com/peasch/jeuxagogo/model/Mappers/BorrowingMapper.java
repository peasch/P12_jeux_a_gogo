package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.entities.Borrowing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BorrowingMapper {

    Borrowing fromDtoToBorrowing (BorrowingDto borrowingDto);

    @Named("toStrictDto")
    @Mapping(target = "copyDto", source = "copy",qualifiedByName ="withoutGames" )
    @Mapping(target = "borrowerDto",source = "borrower",qualifiedByName = "toDtoWithRoles")
    BorrowingDto fromBorrowingToDto (Borrowing borrowing);
}
