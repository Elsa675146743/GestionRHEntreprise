package com.rh.model;

public class FichePaie {
    private Long id;
    private Long employeId;
    private String mois;
    private double salaireBase;
    private double heuresSup;
    private double montantHeuresSup;
    private double primes;
    private double retenues;
    private double salaireBrut;
    private double salaireNet;

    public FichePaie() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeId() { return employeId; }
    public void setEmployeId(Long employeId) { this.employeId = employeId; }

    public String getMois() { return mois; }
    public void setMois(String mois) { this.mois = mois; }

    public double getSalaireBase() { return salaireBase; }
    public void setSalaireBase(double salaireBase) { this.salaireBase = salaireBase; }

    public double getHeuresSup() { return heuresSup; }
    public void setHeuresSup(double heuresSup) { this.heuresSup = heuresSup; }

    public double getMontantHeuresSup() { return montantHeuresSup; }
    public void setMontantHeuresSup(double montantHeuresSup) { this.montantHeuresSup = montantHeuresSup; }

    public double getPrimes() { return primes; }
    public void setPrimes(double primes) { this.primes = primes; }

    public double getRetenues() { return retenues; }
    public void setRetenues(double retenues) { this.retenues = retenues; }

    public double getSalaireBrut() { return salaireBrut; }
    public void setSalaireBrut(double salaireBrut) { this.salaireBrut = salaireBrut; }

    public double getSalaireNet() { return salaireNet; }
    public void setSalaireNet(double salaireNet) { this.salaireNet = salaireNet; }
}
