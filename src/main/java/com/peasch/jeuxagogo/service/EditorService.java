package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.EditorDto;

import java.util.List;

public interface EditorService {

    List<EditorDto> getEditors();
    EditorDto findById(int id);
    EditorDto save(EditorDto editor);
    EditorDto update(EditorDto editorToUpdate);
    void delete(int id);
}
