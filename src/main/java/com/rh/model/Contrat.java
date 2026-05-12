package com.rh.model;

import java.time.LocalDate;

public class Contrat {
    private Long id;
    private Long employeId;
    private String typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double salaire;
    private String avantages;
    
    public Contrat() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEmployeId() { return employeId; }
    public void setEmployeId(Long employeId) { this.employeId = employeId; }
    
    public String getTypeContrat() { return typeContrat; }
    public void setTypeContrat(String typeContrat) { this.typeContrat = typeContrat; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }
    
    public String getAvantages() { return avantages; }
    public void setAvantages(String avantages) { this.avantages = avantages; }
}