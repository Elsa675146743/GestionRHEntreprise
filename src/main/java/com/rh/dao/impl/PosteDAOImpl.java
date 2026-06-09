package com.rh.dao.impl;

import com.rh.dao.PosteDAO;
import com.rh.model.Poste;
import com.rh.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PosteDAOImpl implements PosteDAO {

    @Override
    public void create(Poste p) {
        String sql = "INSERT INTO poste (titre, description, departement_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getTitre());
            ps.setString(2, p.getDescription());
            ps.setLong(3, p.getDepartementId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByDepartementId(Long departementId) {
        String sql = "DELETE FROM poste WHERE departement_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, departementId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Poste> findByDepartementId(Long departementId) {
        List<Poste> list = new ArrayList<>();
        String sql = "SELECT * FROM poste WHERE departement_id = ? ORDER BY titre ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, departementId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Poste p = new Poste();
                    p.setId(rs.getLong("id"));
                    p.setTitre(rs.getString("titre"));
                    p.setDescription(rs.getString("description"));
                    p.setDepartementId(rs.getLong("departement_id"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}