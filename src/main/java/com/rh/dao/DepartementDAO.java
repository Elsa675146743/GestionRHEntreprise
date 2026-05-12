package com.rh.dao;

import com.rh.model.Departement;
import java.util.List;

public interface DepartementDAO {
	void create(Departement departement);
	Departement read(Long id);
	void update(Departement departement);
	void delete(Long id);
	List<Departement> findAll();

}
