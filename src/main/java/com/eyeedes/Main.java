package com.eyeedes;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import com.eyeedes.Classes.Login;
import com.eyeedes.Classes.TipoCadastro;
import com.eyeedes.Classes.Usuario;
import com.eyeedes.DAO.UsuarioDAO;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        /* email e senha usuarios inativado, civil, vistoriador 
        teste@teste.com / teste 
        teste1@teste.com / teste1
        teste2@teste.com / teste2 */

        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("Bem vindo ao EyeEdes\nSelecione a opção desejada:\n1-Realizar Login\n2-Cadastrar usuario\n3-Sair");
            int opc = scanner.nextInt();
            scanner.nextLine();

            switch(opc) {
                case 1:
                    System.out.println("Insira o email: ");
                    String email = scanner.nextLine();
                    System.out.println("Insira sua senha: ");
                    String senha = scanner.nextLine();

                    Login.realizaLogin(email, senha);
                    
                    break;
                case 2:
                    System.out.println("Insira seu nome: ");
                    String nome = scanner.nextLine();
                    System.out.println("Insira seu CPF (apenas números): ");
                    String cpf = scanner.nextLine();
                    System.out.println("Insira seu email: ");
                    String emaill = scanner.nextLine();
                    System.out.println("Insira sua senha: ");
                    String senhaa = scanner.nextLine();

                    while(true) {
                        System.out.println("Selecione o tipo de cadastro: \n1-Civil\n2-Vistoriador");
                        int opcc = scanner.nextInt();
                        scanner.nextLine();
                        if(opcc == 1) {
                            Usuario usuario = new Usuario(nome, cpf, emaill, senhaa, TipoCadastro.Civil);
                            UsuarioDAO.novoUsuario(usuario);
                            break;
                        } else if(opcc == 2) {
                            Usuario usuario = new Usuario(nome, cpf, emaill, senhaa, TipoCadastro.Vistoriador);
                            UsuarioDAO.novoUsuario(usuario);
                            break;
                        } else {
                            System.out.println("Tipo invalido!");
                        }
                    }
                case 3:
                    sair = true;
                    break;
            }
        }
        
        scanner.close();
    }
}