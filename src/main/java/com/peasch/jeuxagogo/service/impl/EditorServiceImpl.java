package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.EditorMapper;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.repository.EditorDao;
import com.peasch.jeuxagogo.service.EditorService;
import com.peasch.jeuxagogo.service.misc.Text;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorMapper mapper;

    @Autowired
    private EditorDao dao;


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
        this.validationOfUpdatingEditor(editorDto);
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
    private void constraintValidation(Set<ConstraintViolation<EditorDto>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            System.out.println(Text.INVALID_GAME);

            for (ConstraintViolation<EditorDto> contraintes : constraintViolations) {
                System.out.println(contraintes.getRootBeanClass().getSimpleName() +
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
            }
            throw new ValidationException(Text.INCORRECT_INFORMATION);
        }
    }

    private void validationOfNewEditor(EditorDto editorDto) throws ValidationException {
        Set<ConstraintViolation<EditorDto>> constraintViolations = validator.validate(editorDto);

        if (this.checkName(editorDto.getName())) {
            throw new ValidationException(Text.ALREADY_USED_GAME_NAME);
        }
        this.constraintValidation(constraintViolations);
    }

    private void validationOfUpdatingEditor(EditorDto editortoUpdateDto) throws ValidationException {
        Set<ConstraintViolation<EditorDto>> constraintViolations = validator.validate(editortoUpdateDto);
        this.constraintValidation(constraintViolations);
    }

}
