package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.entities.Borrowing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class,CopyMapper.class})
public interface BorrowingMapper {

    @Mapping(target = "borrower",source = "borrowerDto")
    @Mapping(target = "copy",source="copyDto")
    @Mapping(target = "returned",source = "returned")
    Borrowing fromDtoToBorrowing (BorrowingDto borrowingDto);

    @Named("toStrictDto")
    @Mapping(target = "copyDto", source = "copy",qualifiedByName ="withGames" )
    @Mapping(target = "borrowerDto",source = "borrower",qualifiedByName = "toDtoWithRoles")
    @Mapping(target = "returned",source = "returned")
    BorrowingDto fromBorrowingToDto (Borrowing borrowing);
}
