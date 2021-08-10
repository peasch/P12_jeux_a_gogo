package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.AdviceDto;

import java.util.List;

public interface AdviceService {
    List<AdviceDto> getAdvices();
    AdviceDto add(AdviceDto adviceDto,int id);
    void delete(int id);
    List<AdviceDto> getAllAdviceById(int id);
    float getRatingOfGameId(int id);
}
