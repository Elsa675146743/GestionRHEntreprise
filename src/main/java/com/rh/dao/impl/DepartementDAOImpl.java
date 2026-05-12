package com.rh.dao.impl;

import com.rh.dao.DepartementDAO;
import com.rh.model.Departement;
import com.rh.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAOImpl implements DepartementDAO {

    @Override
    public void create(Departement d) {
        String sql = "INSERT INTO departement (nom, responsable, budget_masse_salaire) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getNom());
            ps.setString(2, d.getResponsable());
            ps.setDouble(3, d.getBudgetSalaire());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Departement read(Long id) {
        String sql = "SELECT * FROM departement WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Departement d = new Departement();
                    d.setId(rs.getLong("id"));
                    d.setNom(rs.getString("nom"));
                    d.setResponsable(rs.getString("responsable"));
                    d.setBudgetSalaire(rs.getDouble("budget_masse_salaire"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Departement d) {
        String sql = "UPDATE departement SET nom = ?, responsable = ?, budget_masse_salaire = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNom());
            ps.setString(2, d.getResponsable());
            ps.setDouble(3, d.getBudgetSalaire());
            ps.setLong(4, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM departement WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Departement> findAll() {
        List<Departement> list = new ArrayList<>();
        String sql = "SELECT * FROM departement ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Departement d = new Departement();
                d.setId(rs.getLong("id"));
                d.setNom(rs.getString("nom"));
                d.setResponsable(rs.getString("responsable"));
                d.setBudgetSalaire(rs.getDouble("budget_masse_salaire"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}