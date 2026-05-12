package com.rh.dao.impl;

import com.rh.dao.FichePaieDAO;
import com.rh.model.FichePaie;
import com.rh.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichePaieDAOImpl implements FichePaieDAO {

    @Override
    public void create(FichePaie f) {
        String sql = "INSERT INTO fiche_paie (employe_id, mois, salaire_base, heures_sup, montant_heures_sup, primes, retenues, salaire_brut, salaire_net) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, f.getEmployeId());
            ps.setString(2, f.getMois());
            ps.setDouble(3, f.getSalaireBase());
            ps.setDouble(4, f.getHeuresSup());
            ps.setDouble(5, f.getMontantHeuresSup());
            ps.setDouble(6, f.getPrimes());
            ps.setDouble(7, f.getRetenues());
            ps.setDouble(8, f.getSalaireBrut());
            ps.setDouble(9, f.getSalaireNet());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) f.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FichePaie read(Long id) {
        String sql = "SELECT * FROM fiche_paie WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapFichePaie(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(FichePaie f) {
        String sql = "UPDATE fiche_paie SET employe_id=?, mois=?, salaire_base=?, heures_sup=?, montant_heures_sup=?, primes=?, retenues=?, salaire_brut=?, salaire_net=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, f.getEmployeId());
            ps.setString(2, f.getMois());
            ps.setDouble(3, f.getSalaireBase());
            ps.setDouble(4, f.getHeuresSup());
            ps.setDouble(5, f.getMontantHeuresSup());
            ps.setDouble(6, f.getPrimes());
            ps.setDouble(7, f.getRetenues());
            ps.setDouble(8, f.getSalaireBrut());
            ps.setDouble(9, f.getSalaireNet());
            ps.setLong(10, f.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM fiche_paie WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FichePaie> findAll() {
        List<FichePaie> list = new ArrayList<>();
        String sql = "SELECT * FROM fiche_paie ORDER BY mois DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapFichePaie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FichePaie> findByEmploye(Long employeId) {
        List<FichePaie> list = new ArrayList<>();
        String sql = "SELECT * FROM fiche_paie WHERE employe_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFichePaie(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FichePaie> findByMois(String mois) {
        List<FichePaie> list = new ArrayList<>();
        String sql = "SELECT * FROM fiche_paie WHERE mois = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mois);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFichePaie(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== MÉTHODES STATISTIQUES ==========

    @Override
    public double getMasseSalarialeTotale() {
        String sql = "SELECT SUM(salaire_net) as total FROM fiche_paie";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getMasseSalarialeParMois(String mois) {
        String sql = "SELECT SUM(salaire_net) as total FROM fiche_paie WHERE mois = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mois);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getMasseSalarialeParDepartement(Long departementId) {
        String sql = "SELECT SUM(fp.salaire_net) as total FROM fiche_paie fp " +
                     "JOIN employe e ON fp.employe_id = e.id " +
                     "WHERE e.departement_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, departementId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Object[]> getStatsParMois() {
        List<Object[]> stats = new ArrayList<>();
        String sql = "SELECT mois, COUNT(*) as nb_fiches, SUM(salaire_net) as total " +
                     "FROM fiche_paie GROUP BY mois ORDER BY mois DESC LIMIT 6";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getString("mois");
                row[1] = rs.getInt("nb_fiches");
                row[2] = rs.getDouble("total");
                stats.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    @Override
    public List<Object[]> getStatsParDepartement() {
        List<Object[]> stats = new ArrayList<>();
        String sql = "SELECT d.nom, COUNT(e.id) as nb_employes, SUM(fp.salaire_net) as total " +
                     "FROM departement d " +
                     "LEFT JOIN employe e ON d.id = e.departement_id " +
                     "LEFT JOIN fiche_paie fp ON e.id = fp.employe_id " +
                     "GROUP BY d.id ORDER BY total DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getString("nom");
                row[1] = rs.getInt("nb_employes");
                row[2] = rs.getDouble("total");
                stats.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    private FichePaie mapFichePaie(ResultSet rs) throws SQLException {
        FichePaie f = new FichePaie();
        f.setId(rs.getLong("id"));
        f.setEmployeId(rs.getLong("employe_id"));
        f.setMois(rs.getString("mois"));
        f.setSalaireBase(rs.getDouble("salaire_base"));
        f.setHeuresSup(rs.getDouble("heures_sup"));
        f.setMontantHeuresSup(rs.getDouble("montant_heures_sup"));
        f.setPrimes(rs.getDouble("primes"));
        f.setRetenues(rs.getDouble("retenues"));
        f.setSalaireBrut(rs.getDouble("salaire_brut"));
        f.setSalaireNet(rs.getDouble("salaire_net"));
        return f;
    }
}