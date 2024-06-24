package com.eyeedes.DAO;

import com.eyeedes.Classes.Vistoria;
import com.eyeedes.Global.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VistoriaDAO {
    public VistoriaDAO() {}

    public static void novaVistoria(Vistoria vistoria) {
        String sql = "INSERT INTO Vistoria(id, denunciaId, vistoriadorId, enderecoId, descricao, dataCadastro) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vistoria.getID());
            pstmt.setInt(2, vistoria.getID_denuncia());
            pstmt.setInt(3, vistoria.getID_vistoriador());
            pstmt.setInt(4, vistoria.getEndereco());
            pstmt.setString(5, vistoria.getDescricao());
            pstmt.setString(6, vistoria.getDataVisita());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar nova vistoria: " + e.getMessage());
        }
    }

    public static void alterarVistoria(Vistoria vistoria) {
        String sql = "UPDATE Vistoria SET denunciaId = ?, vistoriadorId = ?, enderecoId = ?, descricao = ?, dataCadastro = ? WHERE id = ?";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vistoria.getID_denuncia());
            pstmt.setInt(2, vistoria.getID_vistoriador());
            pstmt.setInt(3, vistoria.getEndereco());
            pstmt.setString(4, vistoria.getDescricao());
            pstmt.setString(5, vistoria.getDataVisita());
            pstmt.setInt(6, vistoria.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar denúncia: " + e.getMessage());
        }
    }

    public static void inativarVistoria(Vistoria vistoria) {
        Util.inativarCadastro(vistoria.getID(), "Vistoria");
    }

    public static void retornarVistoria() {
        Util.consultaTabela("Vistoria");
    }
}
