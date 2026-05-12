package com.rh.dao;
import com.rh.model.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	Utilisateur read(Long id);
	void update(Utilisateur utilisateur);
	void delete(Long id);
	Utilisateur findByLogin(String login);

}
