package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.EditorMapper;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.repository.EditorDao;
import com.peasch.jeuxagogo.service.EditorService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorMapper mapper;

    @Autowired
    private EditorDao dao;


    //----------------------------------Métier-------------------------------------------


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<EditorDto> getEditors() {
        return dao.findAll().stream().map(mapper::fromEditorToDtoWithoutGames).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EditorDto save(EditorDto editor) {
        this.validationOfNewEditor(editor);
        return mapper.fromEditorToDtoWithoutGames(dao.save(mapper.fromDtoToEditor(editor)));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EditorDto update(EditorDto editorToUpdate) {
        EditorDto editorDto = this.findById(editorToUpdate.getId());
        editorDto.setCountry(editorToUpdate.getCountry());
        editorDto.setName(editorToUpdate.getName());
        editorDto.setWebsite(editorToUpdate.getWebsite());
        editorDto.setGames(editorToUpdate.getGames());
        CustomConstraintValidation<EditorDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(editorToUpdate);
        return mapper.fromEditorToDtoWithoutGames(dao.save(mapper.fromDtoToEditor(editorDto)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        dao.delete(mapper.fromDtoToEditor(this.findById(id)));
    }

    //---------------------------------Finding Editors-----------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public EditorDto findById(int id) {
        return mapper.fromEditorToDtoWithoutGames(dao.findById(id).get());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public EditorDto findByName(String name) {
        return mapper.fromEditorToDtoWithoutGames(dao.findByName(name));
    }


    //-------------------------------------Checking fields -------------------------------

    private boolean checkName(String name) {
        return this.findByName(name) != null;
    }

    //-------------------------------------Validations------------------------------------


    private void validationOfNewEditor(EditorDto editorDto) throws ValidationException {;

        if (this.checkName(editorDto.getName())) {
            throw new ValidationException(Text_FR.ALREADY_USED_GAME_NAME);
        }
        CustomConstraintValidation<EditorDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(editorDto);
    }


}
