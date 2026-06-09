package com.rh.dao;

import com.rh.model.Poste;
import java.util.List;

public interface PosteDAO {
    void create(Poste p);
    void deleteByDepartementId(Long departementId);
    List<Poste> findByDepartementId(Long departementId);
}