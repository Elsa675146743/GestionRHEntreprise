package com.rh.model;

import java.time.LocalDate;

public class Conge {
    private Long id;
    private Long employeId;
    private String typeConge;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nbJours;
    private String motif;
    private String statut;
    private String approuvePar;
    
    public Conge() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEmployeId() { return employeId; }
    public void setEmployeId(Long employeId) { this.employeId = employeId; }
    
    public String getTypeConge() { return typeConge; }
    public void setTypeConge(String typeConge) { this.typeConge = typeConge; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public int getNbJours() { return nbJours; }
    public void setNbJours(int nbJours) { this.nbJours = nbJours; }
    
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public String getApprouvePar() { return approuvePar; }
    public void setApprouvePar(String approuvePar) { this.approuvePar = approuvePar; }
}