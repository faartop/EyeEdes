package com.eyeedes.DAO;

import com.eyeedes.Classes.Endereco;
import com.eyeedes.Global.Util;

import java.sql.*;

public class EnderecoDAO {

    public static void salvaEndereco(Endereco endereco) {
        String sql = "INSERT INTO Endereco (cep, logradouro, numero, bairro, localidade, uf, complemento) VALUES (?,?,?,?,?,?,?)";

        try(Connection conectar = Util.getConnection();
            PreparedStatement pstmt = conectar.prepareStatement(sql)) {
            pstmt.setString(1, endereco.getCep());
            pstmt.setString(2, endereco.getLogradouro());
            pstmt.setString(3, endereco.getNumero());
            pstmt.setString(4, endereco.getBairro());
            pstmt.setString(5, endereco.getLocalidade());
            pstmt.setString(6, endereco.getUf());
            pstmt.setString(7, endereco.getComplemento());
            pstmt.executeUpdate();

            System.out.println("Endereço cadastrado com sucesso!");
        } catch(SQLException e){
            System.out.println("Código: " + e.getErrorCode() + "\n" + "Mensagem: " + e.getMessage());
        }
    }

    public static Endereco setId() throws SQLException {
        String sql = "SELECT * FROM Endereco WHERE id = (SELECT MAX(id) FROM Endereco)";

        try(Connection conectar = Util.getConnection();
            PreparedStatement pstmt = conectar.prepareStatement(sql)) {
                        
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(rs.getInt("id"));
                    endereco.setCep(rs.getString("cep"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setNumero(rs.getString("numero"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setLocalidade(rs.getString("localidade"));
                    endereco.setUf(rs.getString("uf"));
                    endereco.setComplemento(rs.getString("complemento"));

                    return endereco;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao retornar denuncia: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao retornar denuncia: " + e.getMessage());
        }
        return null; 
    }
}
