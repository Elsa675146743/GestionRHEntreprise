package com.rh.dao;

import com.rh.model.Contrat;
import java.util.List;

public interface ContratDAO {
    void create(Contrat contrat);
    Contrat read(Long id);
    void update(Contrat contrat);
    void delete(Long id);
    List<Contrat> findAll();
    List<Contrat> findByEmploye(Long employeId);
}