package com.eyeedes.DAO;

import com.eyeedes.Classes.CriptografiaSenha;
import com.eyeedes.Classes.Usuario;
import com.eyeedes.Global.Util;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsuarioDAO {
    public UsuarioDAO() {
    }

    public static void novoUsuario(Usuario usuario){
        
        String sql = "INSERT INTO Usuario (nome, email, cpf, dataCadastro, tipoCadastroId, hash, salt) VALUES (?,?,?,?,?,?,?)";
        String dataCadastro = Util.RegistraDataAtual();
        String cpfFormatado = Util.formataCPF(usuario.getCpf());

        try(Connection conectar = Util.getConnection();
            PreparedStatement pstmt = conectar.prepareStatement(sql)){
            pstmt.setString(1,usuario.getNome());
            pstmt.setString(2,usuario.getEmail());
            pstmt.setString(3,cpfFormatado);
            pstmt.setString(4,dataCadastro);
            pstmt.setInt(5,usuario.tipoDeCadastro.getId());
            pstmt.setString(6,usuario.getHash());
            pstmt.setString(7,usuario.getSalt());
            pstmt.executeUpdate();
            
            System.out.println("Usuario cadastrado com sucesso!");
        } catch(SQLException e){
            System.out.println("Código: " + e.getErrorCode() + "\n" + "Mensagem: " + e.getMessage());
        }
    }

    public static void inativarUsuario(Usuario usuario) {
        Util.inativarCadastro(usuario.getId(), "Usuario");
    }

    public static void listarUsuarios() {
        Util.consultaTabela("Usuario");
    }

    public static void alterarSenha(Usuario usuario, String novaSenha){
        String sql = "UPDATE Usuario SET hash = ?, salt = ? WHERE id = ?";

        try(PreparedStatement pstmt = Util.getConnection().prepareStatement(sql)){
            String atualizaSalt = CriptografiaSenha.gerarSalt();
            String atualizaHash = CriptografiaSenha.gerarHash(novaSenha, atualizaSalt);

            pstmt.setString(1, atualizaHash);
            pstmt.setString(2, atualizaSalt);
            pstmt.setInt(3, usuario.getId());
            pstmt.executeUpdate();

            System.out.println(("Atualizado!"));

        } catch(SQLException | NoSuchAlgorithmException e){
            System.out.println("Reveja o código:" + e.getMessage());
        }
    }
}