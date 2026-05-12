package com.rh.dao;

import com.rh.model.Employe;
import java.util.List;

public interface EmployeDao {
    void create(Employe employe);
    Employe read(Long id);
    void update(Employe employe);
    void delete(Long id);
    List<Employe> findAll();
    List<Employe> findByDepartement(Long departementId);
    List<Employe> searchByKeyword(String keyword, int offset, int limit);
    int countSearch(String keyword);
    
    // Méthodes pour la pagination
    List<Employe> findAllPaginated(int offset, int recordsPerPage);
    int countAll();
}