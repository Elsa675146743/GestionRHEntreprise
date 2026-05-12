package com.rh.dao;

import com.rh.model.Conge;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongeDAO {
    private Connection connection;

    public CongeDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Conge> getAll() throws SQLException {
        List<Conge> liste = new ArrayList<>();
        String sql = "SELECT * FROM conge";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Conge c = new Conge();
            c.setId(rs.getLong("id"));
            c.setEmployeId(rs.getLong("employe_id"));
            c.setTypeConge(rs.getString("type_conge"));
            c.setDateDebut(rs.getDate("date_debut"));
            c.setDateFin(rs.getDate("date_fin"));
            c.setNbJours(rs.getInt("nb_jours"));
            c.setMotif(rs.getString("motif"));
            c.setStatut(rs.getString("statut"));
            c.setApprouvePar(rs.getString("approuve_par"));
            liste.add(c);
        }
        return liste;
    }

    public void insert(Conge c) throws SQLException {
        String sql = "INSERT INTO conge (employe_id, type_conge, date_debut, date_fin, nb_jours, motif, statut, approuve_par) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, c.getEmployeId());
        ps.setString(2, c.getTypeConge());
        ps.setDate(3, new java.sql.Date(c.getDateDebut().getTime()));
        ps.setDate(4, new java.sql.Date(c.getDateFin().getTime()));
        ps.setInt(5, c.getNbJours());
        ps.setString(6, c.getMotif());
        ps.setString(7, c.getStatut());
        ps.setString(8, c.getApprouvePar());
        ps.executeUpdate();
    }

    public void update(Conge c) throws SQLException {
        String sql = "UPDATE conge SET employe_id=?, type_conge=?, date_debut=?, date_fin=?, nb_jours=?, motif=?, statut=?, approuve_par=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, c.getEmployeId());
        ps.setString(2, c.getTypeConge());
        ps.setDate(3, new java.sql.Date(c.getDateDebut().getTime()));
        ps.setDate(4, new java.sql.Date(c.getDateFin().getTime()));
        ps.setInt(5, c.getNbJours());
        ps.setString(6, c.getMotif());
        ps.setString(7, c.getStatut());
        ps.setString(8, c.getApprouvePar());
        ps.setLong(9, c.getId());
        ps.executeUpdate();
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM conge WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
    }

    public Conge getById(Long id) throws SQLException {
        String sql = "SELECT * FROM conge WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Conge c = new Conge();
            c.setId(rs.getLong("id"));
            c.setEmployeId(rs.getLong("employe_id"));
            c.setTypeConge(rs.getString("type_conge"));
            c.setDateDebut(rs.getDate("date_debut"));
            c.setDateFin(rs.getDate("date_fin"));
            c.setNbJours(rs.getInt("nb_jours"));
            c.setMotif(rs.getString("motif"));
            c.setStatut(rs.getString("statut"));
            c.setApprouvePar(rs.getString("approuve_par"));
            return c;
        }
        return null;
    }
}