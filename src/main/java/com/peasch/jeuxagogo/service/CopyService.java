package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.CopyDto;
import java.util.List;

public interface CopyService {

    List<CopyDto> getAll ();
    List<CopyDto> getCopiesByGameId(int id);
    CopyDto save(CopyDto copy,int id);
    void deleteAllByGameId(int id);
}
