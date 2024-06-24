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
}
