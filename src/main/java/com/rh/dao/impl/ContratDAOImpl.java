package com.rh.dao.impl;

import com.rh.dao.ContratDAO;
import com.rh.model.Contrat;
import com.rh.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContratDAOImpl implements ContratDAO {

    @Override
    public void create(Contrat c) {
        String sql = "INSERT INTO contrat_employe (employe_id, type_contrat, date_debut, date_fin, salaire, avantages) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getEmployeId());
            ps.setString(2, c.getTypeContrat());
            ps.setDate(3, Date.valueOf(c.getDateDebut()));
            ps.setDate(4, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            ps.setDouble(5, c.getSalaire());
            ps.setString(6, c.getAvantages());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Contrat read(Long id) {
        String sql = "SELECT * FROM contrat_employe WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapContrat(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Contrat c) {
        String sql = "UPDATE contrat_employe SET employe_id=?, type_contrat=?, date_debut=?, date_fin=?, salaire=?, avantages=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, c.getEmployeId());
            ps.setString(2, c.getTypeContrat());
            ps.setDate(3, Date.valueOf(c.getDateDebut()));
            ps.setDate(4, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            ps.setDouble(5, c.getSalaire());
            ps.setString(6, c.getAvantages());
            ps.setLong(7, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM contrat_employe WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contrat> findAll() {
        List<Contrat> list = new ArrayList<>();
        String sql = "SELECT * FROM contrat_employe ORDER BY date_debut DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapContrat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Contrat> findByEmploye(Long employeId) {
        List<Contrat> list = new ArrayList<>();
        String sql = "SELECT * FROM contrat_employe WHERE employe_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapContrat(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Contrat mapContrat(ResultSet rs) throws SQLException {
        Contrat c = new Contrat();
        c.setId(rs.getLong("id"));
        c.setEmployeId(rs.getLong("employe_id"));
        c.setTypeContrat(rs.getString("type_contrat"));
        Date debut = rs.getDate("date_debut");
        if (debut != null) c.setDateDebut(debut.toLocalDate());
        Date fin = rs.getDate("date_fin");
        if (fin != null) c.setDateFin(fin.toLocalDate());
        c.setSalaire(rs.getDouble("salaire"));
        c.setAvantages(rs.getString("avantages"));
        return c;
    }
}