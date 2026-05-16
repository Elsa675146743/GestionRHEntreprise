package com.rh.dao.impl;

import com.rh.dao.MessageDAO;
import com.rh.model.Message;
import com.rh.util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public void create(Message m) {
        String sql = "INSERT INTO message (expediteur_id, destinataire_id, sujet, contenu, date_envoi, lu, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, m.getExpediteurId());
            if (m.getDestinataireId() != null) {
                ps.setLong(2, m.getDestinataireId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.setString(3, m.getSujet());
            ps.setString(4, m.getContenu());
            ps.setTimestamp(5, Timestamp.valueOf(m.getDateEnvoi()));
            ps.setBoolean(6, m.isLu());
            ps.setString(7, m.getType());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message read(Long id) {
        String sql = "SELECT m.*, e1.nom as expediteur_nom, e2.nom as destinataire_nom " +
                     "FROM message m " +
                     "LEFT JOIN employe e1 ON m.expediteur_id = e1.id " +
                     "LEFT JOIN employe e2 ON m.destinataire_id = e2.id " +
                     "WHERE m.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMessage(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Message m) {
        String sql = "UPDATE message SET expediteur_id=?, destinataire_id=?, sujet=?, contenu=?, date_envoi=?, lu=?, type=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, m.getExpediteurId());
            if (m.getDestinataireId() != null) {
                ps.setLong(2, m.getDestinataireId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.setString(3, m.getSujet());
            ps.setString(4, m.getContenu());
            ps.setTimestamp(5, Timestamp.valueOf(m.getDateEnvoi()));
            ps.setBoolean(6, m.isLu());
            ps.setString(7, m.getType());
            ps.setLong(8, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM message WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> findByDestinataire(Long destinataireId) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT m.*, e1.nom as expediteur_nom, e2.nom as destinataire_nom " +
                     "FROM message m " +
                     "LEFT JOIN employe e1 ON m.expediteur_id = e1.id " +
                     "LEFT JOIN employe e2 ON m.destinataire_id = e2.id " +
                     "WHERE m.destinataire_id = ? OR (m.destinataire_id IS NULL AND m.type = 'GENERAL') " +
                     "ORDER BY m.date_envoi DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, destinataireId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapMessage(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Message> findByExpediteur(Long expediteurId) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT m.*, e1.nom as expediteur_nom, e2.nom as destinataire_nom " +
                     "FROM message m " +
                     "LEFT JOIN employe e1 ON m.expediteur_id = e1.id " +
                     "LEFT JOIN employe e2 ON m.destinataire_id = e2.id " +
                     "WHERE m.expediteur_id = ? ORDER BY m.date_envoi DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, expediteurId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapMessage(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Message> findNonLusParDestinataire(Long destinataireId) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT m.*, e1.nom as expediteur_nom, e2.nom as destinataire_nom " +
                     "FROM message m " +
                     "LEFT JOIN employe e1 ON m.expediteur_id = e1.id " +
                     "LEFT JOIN employe e2 ON m.destinataire_id = e2.id " +
                     "WHERE (m.destinataire_id = ? OR (m.destinataire_id IS NULL AND m.type = 'GENERAL')) AND m.lu = FALSE " +
                     "ORDER BY m.date_envoi DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, destinataireId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapMessage(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void marquerCommeLu(Long id) {
        String sql = "UPDATE message SET lu = TRUE WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countNonLus(Long destinataireId) {
        String sql = "SELECT COUNT(*) FROM message WHERE (destinataire_id = ? OR (destinataire_id IS NULL AND type = 'GENERAL')) AND lu = FALSE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, destinataireId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Message mapMessage(ResultSet rs) throws SQLException {
        Message m = new Message();
        m.setId(rs.getLong("id"));
        m.setExpediteurId(rs.getLong("expediteur_id"));
        m.setExpediteurNom(rs.getString("expediteur_nom"));
        Long destId = rs.getLong("destinataire_id");
        if (!rs.wasNull()) {
            m.setDestinataireId(destId);
            m.setDestinataireNom(rs.getString("destinataire_nom"));
        }
        m.setSujet(rs.getString("sujet"));
        m.setContenu(rs.getString("contenu"));
        Timestamp ts = rs.getTimestamp("date_envoi");
        if (ts != null) m.setDateEnvoi(ts.toLocalDateTime());
        m.setLu(rs.getBoolean("lu"));
        m.setType(rs.getString("type"));
        return m;
    }
}