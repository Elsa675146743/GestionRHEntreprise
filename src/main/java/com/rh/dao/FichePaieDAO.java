package com.rh.dao;

import com.rh.model.FichePaie;
import java.util.List;

public interface FichePaieDAO {
    void create(FichePaie fichePaie);
    FichePaie read(Long id);
    void update(FichePaie fichePaie);
    void delete(Long id);
    List<FichePaie> findAll();
    List<FichePaie> findByEmploye(Long employeId);
    List<FichePaie> findByMois(String mois);
    
    // Méthodes statistiques
    double getMasseSalarialeTotale();
    double getMasseSalarialeParMois(String mois);
    double getMasseSalarialeParDepartement(Long departementId);
    List<Object[]> getStatsParMois();
    List<Object[]> getStatsParDepartement();
}