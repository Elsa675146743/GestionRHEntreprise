package com.rh.model;

public class Utilisateur {
	private Long id;
	private String login;
	private String mdpHash;
	private String role;
	private Long employeId;
	private boolean actif;
	
	public Utilisateur () {}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;} 
	
	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;} 
	
	public String getMdpHash() {return mdpHash;}
	public void setMdpHash(String mdpHash) {this.mdpHash = mdpHash;} 
	
	public String getRole() {return role;}
	public void setRole(String role) {this.role = role;} 
	
	public Long getEmployeId() {return employeId;}
	public void setEmployeId(Long employeId) {this.employeId = employeId;}
	
	public boolean isActif() {return actif;}
	public void setActif(boolean  actif) {this. actif =  actif;}
	
}
