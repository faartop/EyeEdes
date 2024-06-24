package com.eyeedes;

import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import com.eyeedes.Classes.Usuario;
import com.eyeedes.DAO.UsuarioDAO;
import com.eyeedes.API.ApiConnection;
import com.eyeedes.Classes.Endereco;
import com.eyeedes.DAO.EnderecoDAO;
import com.eyeedes.Classes.Login;
import com.eyeedes.Classes.TipoCadastro;



public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        /* email e senha usuarios inativado, civil, vistoriador 
        teste@teste.com / teste 
        teste1@teste.com / teste1
        teste2@teste.com / teste2

        Usuario usuario = new Usuario("Teste2", "00000000003", "teste2@teste.com", "teste2", TipoCadastro.Vistoriador);

        UsuarioDAO.novoUsuario(usuario); */

        JSONObject resposta = ApiConnection.validaUrl("85905180");

        Endereco endereco = new Endereco(resposta, "812", "Apto 1");

        EnderecoDAO.salvaEndereco(endereco);
    }
}