package com.rh.dao.impl;

import com.rh.dao.EmployeDao;
import com.rh.model.Employe;
import com.rh.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeDaoImpl implements EmployeDao {

    @Override
    public void create(Employe e) {
        String sql = "INSERT INTO employe (matricule, nom, prenom, poste, departement_id, date_embauche, salaire_base, type_contrat, telephone, email, photo_filename, solde_conges_jours) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getMatricule());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getPrenom());
            ps.setString(4, e.getPoste());
            ps.setLong(5, e.getDepartementId());
            ps.setDate(6, Date.valueOf(e.getDateEmbauche()));
            ps.setDouble(7, e.getSalaireBase());
            ps.setString(8, e.getTypeContrat());
            ps.setString(9, e.getTelephone());
            ps.setString(10, e.getEmail());
            ps.setString(11, e.getPhotoFilename());
            ps.setInt(12, e.getSoldeCongesJours());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getLong(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Employe read(Long id) {
        String sql = "SELECT e.*, d.nom as departement_nom FROM employe e LEFT JOIN departement d ON e.departement_id = d.id WHERE e.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmploye(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Employe e) {
        String sql = "UPDATE employe SET matricule=?, nom=?, prenom=?, poste=?, departement_id=?, date_embauche=?, salaire_base=?, type_contrat=?, telephone=?, email=?, photo_filename=?, solde_conges_jours=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getMatricule());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getPrenom());
            ps.setString(4, e.getPoste());
            ps.setLong(5, e.getDepartementId());
            ps.setDate(6, Date.valueOf(e.getDateEmbauche()));
            ps.setDouble(7, e.getSalaireBase());
            ps.setString(8, e.getTypeContrat());
            ps.setString(9, e.getTelephone());
            ps.setString(10, e.getEmail());
            ps.setString(11, e.getPhotoFilename());
            ps.setInt(12, e.getSoldeCongesJours());
            ps.setLong(13, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM employe WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Employe> findAll() {
        List<Employe> list = new ArrayList<>();
        String sql = "SELECT e.*, d.nom as departement_nom FROM employe e LEFT JOIN departement d ON e.departement_id = d.id ORDER BY e.nom";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapEmploye(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Employe> findByDepartement(Long departementId) {
        List<Employe> list = new ArrayList<>();
        String sql = "SELECT e.*, d.nom as departement_nom FROM employe e LEFT JOIN departement d ON e.departement_id = d.id WHERE e.departement_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, departementId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEmploye(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Employe> searchByKeyword(String keyword, int offset, int limit) {
        List<Employe> list = new ArrayList<>();
        String sql = "SELECT e.*, d.nom as departement_nom FROM employe e LEFT JOIN departement d ON e.departement_id = d.id WHERE e.nom LIKE ? OR e.prenom LIKE ? OR e.matricule LIKE ? LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setInt(4, limit);
            ps.setInt(5, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEmploye(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public int countSearch(String keyword) {
        String sql = "SELECT COUNT(*) FROM employe WHERE nom LIKE ? OR prenom LIKE ? OR matricule LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // ==================== MÉTHODES DE PAGINATION ====================
    
    @Override
    public List<Employe> findAllPaginated(int offset, int recordsPerPage) {
        List<Employe> list = new ArrayList<>();
        String sql = "SELECT e.*, d.nom as departement_nom FROM employe e " +
                     "LEFT JOIN departement d ON e.departement_id = d.id " +
                     "ORDER BY e.nom LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordsPerPage);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEmploye(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM employe";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ==================== MAPPER ====================
    
    private Employe mapEmploye(ResultSet rs) throws SQLException {
        Employe e = new Employe();
        e.setId(rs.getLong("id"));
        e.setMatricule(rs.getString("matricule"));
        e.setNom(rs.getString("nom"));
        e.setPrenom(rs.getString("prenom"));
        e.setPoste(rs.getString("poste"));
        e.setDepartementId(rs.getLong("departement_id"));
        try {
            e.setDepartementNom(rs.getString("departement_nom"));
        } catch (SQLException ignored) {}
        Date date = rs.getDate("date_embauche");
        if (date != null) e.setDateEmbauche(date.toLocalDate());
        e.setSalaireBase(rs.getDouble("salaire_base"));
        e.setTypeContrat(rs.getString("type_contrat"));
        e.setTelephone(rs.getString("telephone"));
        e.setEmail(rs.getString("email"));
        e.setPhotoFilename(rs.getString("photo_filename"));
        e.setSoldeCongesJours(rs.getInt("solde_conges_jours"));
        return e;
    }
}