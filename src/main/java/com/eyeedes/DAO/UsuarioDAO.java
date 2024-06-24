package com.eyeedes.DAO;

import com.eyeedes.Classes.CriptografiaSenha;
import com.eyeedes.Classes.Usuario;
import com.eyeedes.Global.Util;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public UsuarioDAO() {
    }

    public static void novoUsuario(Usuario usuario){
        
        String sql = "INSERT INTO Usuario (nome, email, cpf, dataCadastro, tipoCadastroId, hash, salt) VALUES (?,?,?,?,?,?,?)";
        String dataCadastro = Util.RegistraDataAtual();
        String cpfFormatado = Util.formataCPF(usuario.getCpf());

        try(Connection conectar = Util.getConnection(); PreparedStatement pstmt = conectar.prepareStatement(sql)){
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

    public static List<Usuario> listaUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario WHERE tipoCadastroid = ?";

        try (Statement stmt = Util.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }

        if (!usuarios.isEmpty()) {
            System.out.println("Lista de Usuários:");
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("Email: " + usuario.getEmail());
                System.out.println("--------------------");
            }
        } else {
            System.out.println("Nenhum usuário encontrado.");
        }

        return usuarios;
    }

    public static void inativarUsuario(Usuario usuario) {
        Util.inativarCadastro(usuario.getId(), "Usuario");
    }


    public static void alterarSenha(String email, String novaSenha){
        String sql = "UPDATE Usuario SET hash = ?, salt = ? WHERE email = ?";

        try(PreparedStatement pstmt = Util.getConnection().prepareStatement(sql)){
            String atualizaSalt = CriptografiaSenha.gerarSalt();
            String atualizaHash = CriptografiaSenha.gerarHash(novaSenha, atualizaSalt);

            pstmt.setString(1, atualizaHash);
            pstmt.setString(2, atualizaSalt);
            pstmt.setString(3, email);
            pstmt.executeUpdate();

            System.out.println(("Atualizado!"));

        } catch(SQLException | NoSuchAlgorithmException e){
            System.out.println("Reveja o código:" + e.getMessage());
        }
    }
}