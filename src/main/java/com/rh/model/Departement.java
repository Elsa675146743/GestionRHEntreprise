package com.rh.model;

public class Departement {
	private Long id;
	private String nom;
	private String responsable;
	private double budgetSalaire;
	
	public Departement() {}
	
	public Departement(String nom, String responsable, double budgetSalaire) {
		this.nom = nom;
		this.responsable = responsable;
		this.budgetSalaire = budgetSalaire;
	}
	
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}
	
	public String getResponsable() {return responsable;}
	public void setResponsable(String responsable) {this.responsable = responsable;}
	
	public double getBudgetSalaire() {return budgetSalaire;}
	public void setBudgetSalaire(double budgetSalaire) {this.budgetSalaire = budgetSalaire;}
	

}
	
	





