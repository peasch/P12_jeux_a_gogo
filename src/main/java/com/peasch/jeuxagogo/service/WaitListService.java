package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.WaitListDto;

import java.util.List;

public interface WaitListService {

    WaitListDto addWaitListToGame(int id, String username);

    List<WaitListDto> getWaitlists();

    List<WaitListDto> getWaitlistByGameId(int Id);

    List<WaitListDto> getWaitlistByWaiterId(int Id);

    void contactFirstWaiterOfGameId(int id);

    WaitListDto getWaitlistByWaiterIdAndGameId(int id, String username);

    void deleteWaitList(int id);

    void deleteWaitListAndContactNext(int id);
}
