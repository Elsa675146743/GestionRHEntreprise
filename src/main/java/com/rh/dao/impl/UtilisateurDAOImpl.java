package com.rh.dao.impl;

import com.rh.dao.UtilisateurDAO;
import com.rh.model.Utilisateur;
import com.rh.util.DBConnection;
import java.sql.*;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    @Override
    public void create(Utilisateur u) {
        String sql = "INSERT INTO utilisateur (login, mdp_hash, role, employe_id, actif, premier_connexion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getMdpHash());
            ps.setString(3, u.getRole());
            ps.setLong(4, u.getEmployeId());
            ps.setBoolean(5, u.isActif());
            ps.setBoolean(6, true); // premier_connexion = true par défaut
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) u.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur read(Long id) {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Utilisateur u) {
        String sql = "UPDATE utilisateur SET login=?, mdp_hash=?, role=?, employe_id=?, actif=?, premier_connexion=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getMdpHash());
            ps.setString(3, u.getRole());
            ps.setLong(4, u.getEmployeId());
            ps.setBoolean(5, u.isActif());
            ps.setBoolean(6, u.isPremierConnexion());
            ps.setLong(7, u.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur findByLogin(String login) {
        String sql = "SELECT * FROM utilisateur WHERE login = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilisateur findByEmployeId(Long employeId) {
        String sql = "SELECT * FROM utilisateur WHERE employe_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateMotDePasse(Long id, String nouveauHash) {
        String sql = "UPDATE utilisateur SET mdp_hash=?, premier_connexion=0 WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nouveauHash);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Utilisateur mapUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur u = new Utilisateur();
        u.setId(rs.getLong("id"));
        u.setLogin(rs.getString("login"));
        u.setMdpHash(rs.getString("mdp_hash"));
        u.setRole(rs.getString("role"));
        u.setEmployeId(rs.getLong("employe_id"));
        if (rs.getObject("employe_id") == null) u.setEmployeId(null);
        u.setActif(rs.getBoolean("actif"));
        u.setPremierConnexion(rs.getBoolean("premier_connexion"));
        return u;
    }
}