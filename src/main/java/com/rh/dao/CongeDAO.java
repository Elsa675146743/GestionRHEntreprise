package com.rh.dao;

import com.rh.model.Conge;
import java.util.List;

public interface CongeDAO {
    void create(Conge conge);
    Conge read(Long id);
    void update(Conge conge);
    void delete(Long id);
    List<Conge> findAll();
    List<Conge> findByEmploye(Long employeId);
}