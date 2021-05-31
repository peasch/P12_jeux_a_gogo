package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.CopiesDto;
import java.util.List;

public interface CopyService {

    List<CopiesDto> getAll ();
    List<CopiesDto> getCopiesByGameId(int id);
}
