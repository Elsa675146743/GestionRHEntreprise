package com.rh.dao.impl;

import com.rh.dao.CongeDAO;
import com.rh.model.Conge;
import com.rh.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CongeDAOImpl implements CongeDAO {

    @Override
    public void create(Conge c) {
        String sql = "INSERT INTO conge (employe_id, type_conge, date_debut, date_fin, nb_jours, motif, statut, approuve_par) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getEmployeId());
            ps.setString(2, c.getTypeConge());
            ps.setDate(3, Date.valueOf(c.getDateDebut()));
            ps.setDate(4, Date.valueOf(c.getDateFin()));
            ps.setInt(5, c.getNbJours());
            ps.setString(6, c.getMotif());
            ps.setString(7, c.getStatut());
            ps.setString(8, c.getApprouvePar());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Conge read(Long id) {
        String sql = "SELECT * FROM conge WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapConge(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Conge c) {
        String sql = "UPDATE conge SET employe_id=?, type_conge=?, date_debut=?, date_fin=?, nb_jours=?, motif=?, statut=?, approuve_par=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, c.getEmployeId());
            ps.setString(2, c.getTypeConge());
            ps.setDate(3, Date.valueOf(c.getDateDebut()));
            ps.setDate(4, Date.valueOf(c.getDateFin()));
            ps.setInt(5, c.getNbJours());
            ps.setString(6, c.getMotif());
            ps.setString(7, c.getStatut());
            ps.setString(8, c.getApprouvePar());
            ps.setLong(9, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM conge WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Conge> findAll() {
        List<Conge> list = new ArrayList<>();
        String sql = "SELECT * FROM conge ORDER BY date_debut DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapConge(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Conge> findByEmploye(Long employeId) {
        List<Conge> list = new ArrayList<>();
        String sql = "SELECT * FROM conge WHERE employe_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapConge(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Conge mapConge(ResultSet rs) throws SQLException {
        Conge c = new Conge();
        c.setId(rs.getLong("id"));
        c.setEmployeId(rs.getLong("employe_id"));
        c.setTypeConge(rs.getString("type_conge"));
        Date debut = rs.getDate("date_debut");
        if (debut != null) c.setDateDebut(debut.toLocalDate());
        Date fin = rs.getDate("date_fin");
        if (fin != null) c.setDateFin(fin.toLocalDate());
        c.setNbJours(rs.getInt("nb_jours"));
        c.setMotif(rs.getString("motif"));
        c.setStatut(rs.getString("statut"));
        c.setApprouvePar(rs.getString("approuve_par"));
        return c;
    }
}