package com.rh.model;

import java.util.Date;

public class Conge {
    private Long id;
    private Long employeId;
    private String typeConge; // ANNUEL, MALADIE, MATERNITE, PATERNITE, EXCEPTIONNEL
    private Date dateDebut;
    private Date dateFin;
    private int nbJours;
    private String motif;
    private String statut; // DEMANDE, APPROUVE, REFUSE
    private String approuvePar;

    public Conge() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeId() { return employeId; }
    public void setEmployeId(Long employeId) { this.employeId = employeId; }

    public String getTypeConge() { return typeConge; }
    public void setTypeConge(String typeConge) { this.typeConge = typeConge; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public int getNbJours() { return nbJours; }
    public void setNbJours(int nbJours) { this.nbJours = nbJours; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getApprouvePar() { return approuvePar; }
    public void setApprouvePar(String approuvePar) { this.approuvePar = approuvePar; }
}