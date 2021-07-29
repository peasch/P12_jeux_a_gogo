package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.CopyDto;
import java.util.List;

public interface CopyService {

    List<CopyDto> getAll ();
    List<CopyDto> getCopiesByGameId(int id);
    List<CopyDto> getAvailableCopiesByGameId(int id);
    CopyDto save(CopyDto copy,int id);
    CopyDto update(CopyDto copy);
    void deleteAllByGameId(int id);
    CopyDto setUnavailable(CopyDto copyDto);
    CopyDto setAvailable(CopyDto copyDto);
    CopyDto findById(int id);
}
