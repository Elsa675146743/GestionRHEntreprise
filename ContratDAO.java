package com.rh.dao;

import com.rh.model.Contrat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratDAO {
    private Connection connection;

    public ContratDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Contrat> getAll() throws SQLException {
        List<Contrat> liste = new ArrayList<>();
        String sql = "SELECT * FROM contrat_employe";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Contrat c = new Contrat();
            c.setId(rs.getLong("id"));
            c.setEmployeId(rs.getLong("employe_id"));
            c.setTypeContrat(rs.getString("type_contrat"));
            c.setDateDebut(rs.getDate("date_debut"));
            c.setDateFin(rs.getDate("date_fin"));
            c.setSalaire(rs.getDouble("salaire"));
            c.setAvantages(rs.getString("avantages"));
            liste.add(c);
        }
        return liste;
    }

    public void insert(Contrat c) throws SQLException {
        String sql = "INSERT INTO contrat_employe (employe_id, type_contrat, date_debut, date_fin, salaire, avantages) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, c.getEmployeId());
        ps.setString(2, c.getTypeContrat());
        ps.setDate(3, new java.sql.Date(c.getDateDebut().getTime()));
        ps.setDate(4, c.getDateFin() != null ? new java.sql.Date(c.getDateFin().getTime()) : null);
        ps.setDouble(5, c.getSalaire());
        ps.setString(6, c.getAvantages());
        ps.executeUpdate();
    }

    public void update(Contrat c) throws SQLException {
        String sql = "UPDATE contrat_employe SET employe_id=?, type_contrat=?, date_debut=?, date_fin=?, salaire=?, avantages=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, c.getEmployeId());
        ps.setString(2, c.getTypeContrat());
        ps.setDate(3, new java.sql.Date(c.getDateDebut().getTime()));
        ps.setDate(4, c.getDateFin() != null ? new java.sql.Date(c.getDateFin().getTime()) : null);
        ps.setDouble(5, c.getSalaire());
        ps.setString(6, c.getAvantages());
        ps.setLong(7, c.getId());
        ps.executeUpdate();
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM contrat_employe WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
    }

    public Contrat getById(Long id) throws SQLException {
        String sql = "SELECT * FROM contrat_employe WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Contrat c = new Contrat();
            c.setId(rs.getLong("id"));
            c.setEmployeId(rs.getLong("employe_id"));
            c.setTypeContrat(rs.getString("type_contrat"));
            c.setDateDebut(rs.getDate("date_debut"));
            c.setDateFin(rs.getDate("date_fin"));
            c.setSalaire(rs.getDouble("salaire"));
            c.setAvantages(rs.getString("avantages"));
            return c;
        }
        return null;
    }
}
