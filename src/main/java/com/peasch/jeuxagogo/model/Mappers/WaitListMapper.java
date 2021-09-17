package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.WaitListDto;
import com.peasch.jeuxagogo.model.entities.Copy;
import com.peasch.jeuxagogo.model.entities.WaitList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GameMapper.class, UserMapper.class})
public interface WaitListMapper {

    WaitList fromDtoToWaitList(WaitListDto waitListDto);

    @Named("withGamesButDetails")
    @Mapping(target = "game",source = "game", qualifiedByName = "withoutDetails")
    @Mapping(target = "waiter",source = "waiter",qualifiedByName = "toDtoWithRoles")
    @Mapping(target = "returnDate",source = "returnDate")
    WaitListDto fromWaitListToDtoWithGame(WaitList waitList);
}
