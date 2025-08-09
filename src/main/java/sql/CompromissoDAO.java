package sql;

import service.Compromisso;
import service.CompromissoBuilder;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompromissoDAO {

    public void salvar(Compromisso c) {
        String sql = "INSERT INTO compromissos (descricao, diaSemana, gravidade, urgencia, tendencia, diaMes, mes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, c.getDescricao());
            pstmt.setInt(2, c.getDiaSemana());
            pstmt.setInt(3, c.getGravidade());
            pstmt.setInt(4, c.getUrgencia());
            pstmt.setInt(5, c.getTendencia());
            pstmt.setInt(6, c.getDiaMes());
            pstmt.setInt(7, c.getMes());

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao salvar compromisso no banco: " + e.getMessage());
        }
    }

    public List<Compromisso> listarTodos() {
        List<Compromisso> compromissos = new ArrayList<>();
        String sql = "SELECT * FROM compromissos";

        try (Connection conn = SQLiteConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Compromisso c = new CompromissoBuilder()
                        .builderId(rs.getInt("id")) // Lê o ID do banco
                        .builderDescricao(rs.getString("descricao"))
                        .builderDiaSemana(rs.getInt("diaSemana"))
                        .builderGravidade(rs.getInt("gravidade"))
                        .builderUrgencia(rs.getInt("urgencia"))
                        .builderTendencia(rs.getInt("tendencia"))
                        .builderDiaMes(rs.getInt("diaMes"))
                        .builderMes(rs.getInt("mes"))
                        .build();

                compromissos.add(c);
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar compromissos: " + e.getMessage());
        }

        return compromissos;
    }

    public void alterar(Compromisso c) {
        String sql = "UPDATE compromissos SET descricao = ?, diaSemana = ?, gravidade = ?, urgencia = ?, tendencia = ?, diaMes = ?, mes = ? WHERE id = ?";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, c.getDescricao());
            pstmt.setInt(2, c.getDiaSemana());
            pstmt.setInt(3, c.getGravidade());
            pstmt.setInt(4, c.getUrgencia());
            pstmt.setInt(5, c.getTendencia());
            pstmt.setInt(6, c.getDiaMes());
            pstmt.setInt(7, c.getMes());
            pstmt.setInt(8, c.getId()); // Usa o ID para localizar o registro

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao alterar compromisso no banco: " + e.getMessage());
        }
    }

    public void apagar(int id) {
        String sql = "DELETE FROM compromissos WHERE id = ?";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao apagar compromisso no banco: " + e.getMessage());
        }
    }
}
