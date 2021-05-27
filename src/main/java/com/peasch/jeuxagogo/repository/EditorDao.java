package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorDao extends JpaRepository<Editor,Integer> {

    Editor findByName(String name);
}
