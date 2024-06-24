package com.eyeedes;

import java.security.NoSuchAlgorithmException;

import com.eyeedes.Classes.Usuario;
import com.eyeedes.DAO.UsuarioDAO;
import com.eyeedes.Classes.Login;
import com.eyeedes.Classes.TipoCadastro;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        /* email e senha usuarios inativado, civil, vistoriador 
        teste@teste.com / teste 
        teste1@teste.com / teste1
        teste2@teste.com / teste2 */

        Usuario usuario = new Usuario("Teste2", "00000000003", "teste2@teste.com", "teste2", TipoCadastro.Vistoriador);

        UsuarioDAO.novoUsuario(usuario);
    }
}