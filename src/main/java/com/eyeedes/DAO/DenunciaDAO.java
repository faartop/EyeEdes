package com.eyeedes.DAO;

import com.eyeedes.Classes.Denuncia;
import com.eyeedes.Global.Util;

import java.sql.*;

public class DenunciaDAO {

    public static void novaDenuncia(Denuncia denuncia){
        String sql = "INSERT INTO Denuncia (denuncianteId, statusId, enderecoId, descricao, anexoId, dataCadastro) VALUES (?,?,?,?,?,?)";
        String dataAbertura = Util.RegistraDataAtual();

        try (Connection conectar = Util.getConnection();
             PreparedStatement pstmt = conectar.prepareStatement(sql)) {
            pstmt.setInt(1, denuncia.getDenuncianteId());
            pstmt.setInt(2, denuncia.getStatusId());
            pstmt.setInt(3, denuncia.getEnderecoId());
            pstmt.setString(4, denuncia.getDescricao());
            pstmt.setInt(5, denuncia.getAnexoId());
            pstmt.setString(6, dataAbertura);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir nova denúncia. Código: " + e.getErrorCode() + " Mensagem: " + e.getMessage());
        }
    }

    public static void alterarDenuncia(Denuncia denuncia) {
        String sql = "UPDATE Denuncia SET statusId = ?, enderecoId = ?, descricao = ? WHERE ID = ?";

        try (Connection conectar = Util.getConnection();
             PreparedStatement pstmt = conectar.prepareStatement(sql)) {
            pstmt.setInt(1, denuncia.getStatusId());
            pstmt.setInt(2, denuncia.getEnderecoId());
            pstmt.setString(3, denuncia.getDescricao());
            pstmt.setInt(4, denuncia.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar denúncia. Código: " + e.getErrorCode() + " Mensagem: " + e.getMessage());
        }
    }

    public static Denuncia umaDenuncia(int id) throws SQLException {
        String sql = "SELECT * FROM Denuncia WHERE id = ?";

        try(Connection conectar = Util.getConnection();
            PreparedStatement pstmt = conectar.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    Denuncia denuncia = new Denuncia();
                    denuncia.setId(rs.getInt("id"));
                    denuncia.setDenuncianteId(rs.getInt("denuncianteId"));
                    denuncia.setEnderecoId(rs.getInt("enderecoId"));
                    denuncia.setStatusId(rs.getInt("statusId"));
                    denuncia.setDescricao(rs.getString("descricao"));
                    denuncia.setDataCadastro(rs.getString("dataCadastro"));

                    return denuncia;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao retornar denuncia: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao retornar denuncia: " + e.getMessage());
        }
        return null; 
    }

    public static void inativarDenuncia(Denuncia denuncia) {
        Util.inativarCadastro(denuncia.getId(), "Denuncia");
    }

    public static void retornarDeununcia() {
        Util.consultaTabela("Denuncia");
    }
}
