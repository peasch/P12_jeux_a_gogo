package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.AdviceDto;

import java.util.List;

public interface AdviceService {
    List<AdviceDto> getAdvices();

    AdviceDto add(AdviceDto adviceDto, int id);

    void delete(int id);

    List<AdviceDto> getAllAdviceById(int id);

    Float getRatingOfGameId(int id);

    AdviceDto update(int id, AdviceDto adviceDto);

    List<AdviceDto> getAllAdviceByUsername(String username);

    AdviceDto getAdviceById(int id);
}
