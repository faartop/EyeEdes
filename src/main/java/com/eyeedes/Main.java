package com.eyeedes;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONObject;

import com.eyeedes.API.ApiConnection;
import com.eyeedes.Classes.Denuncia;
import com.eyeedes.Classes.Endereco;
import com.eyeedes.Classes.Login;
import com.eyeedes.Classes.TipoCadastro;
import com.eyeedes.Classes.Usuario;
import com.eyeedes.Classes.Vistoria;
import com.eyeedes.DAO.DenunciaDAO;
import com.eyeedes.DAO.EnderecoDAO;
import com.eyeedes.DAO.UsuarioDAO;
import com.eyeedes.DAO.VistoriaDAO;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        /* email e senha usuarios inativado, civil, vistoriador 
        teste@teste.com / teste 
        teste1@teste.com / teste1
        teste2@teste.com / teste2 */

        Usuario usuario = new Usuario();
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            boolean login = false;
            
            while (!login) {
                System.out.println("Bem vindo ao EyeEdes\nSelecione a opção desejada:\n1-Realizar Login\n2-Cadastrar usuario\n3-Sair");
                int opc = scanner.nextInt();
                scanner.nextLine();

                switch(opc) {
                    case 1:
                        System.out.println("Insira o email: ");
                        String email = scanner.nextLine();
                        System.out.println("Insira sua senha: ");
                        String senha = scanner.nextLine();

                        usuario = Login.realizaLogin(email, senha);

                        if(usuario != null) {
                            login = true;
                        }
                        
                        break;
                    case 2:
                        System.out.println("Insira seu nome: ");
                        String nome = scanner.nextLine();
                        System.out.println("Insira seu CPF (apenas números): ");
                        String cpf = scanner.nextLine();
                        System.out.println("Insira seu email: ");
                        email = scanner.nextLine();
                        System.out.println("Insira sua senha: ");
                        senha = scanner.nextLine();

                        while(true) {
                            System.out.println("Selecione o tipo de cadastro: \n1-Civil\n2-Vistoriador");
                            int opcc = scanner.nextInt();
                            scanner.nextLine();
                            if(opcc == 1) {
                                usuario = new Usuario(nome, cpf, email, senha, TipoCadastro.Civil);
                                break;
                            } else if(opcc == 2) {
                                usuario = new Usuario(nome, cpf, email, senha, TipoCadastro.Vistoriador);
                                break;
                            } else {
                                System.out.println("Tipo invalido!");
                            }
                        }

                        UsuarioDAO.novoUsuario(usuario);

                        if(usuario != null) {
                            login = true;
                        }

                        break;
                    case 3:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            }

            System.out.println("Usuario logado: " + usuario.getNome());
            boolean opera = true;

            while(opera) {
                int opc = 0;
                System.out.println("Selecione a opção desejada:");
                if(usuario.getTipoDeCadastro() == TipoCadastro.Civil) {
                    System.out.println("1-Cadastrar denuncia\n2-Acompanhar denuncia\n3-Alterar senha\n4-Sair");
                } else {
                    System.out.println("1-Cadastrar vistoria\n2-Acompanhar vistoria\n3-Alterar senha\n4-Sair");
                }

                opc = scanner.nextInt();
                scanner.nextLine();

                switch (opc) {
                    case 1:
                        if(usuario.getTipoDeCadastro() == TipoCadastro.Civil) {
                            boolean valida = false;
                            Endereco endereco = new Endereco();
                            while(!valida) {
                                System.out.println("Insira o cep da denúncia (apenas números)");
                                String cep = scanner.nextLine();

                                ApiConnection.printCepData(cep);
                                
                                System.out.println("O endereço está correto?\n1-Sim\n2-Não");
                                int val = scanner.nextInt();
                                scanner.nextLine();

                                if(val == 1) {
                                    System.out.println("Insira o número predial do endereço:");
                                    String num = scanner.nextLine();
                                    System.out.println("Insira o complemento (deixe em branco e pressione enter caso não exista): ");
                                    String comp = scanner.nextLine();
                                    
                                    JSONObject json = ApiConnection.validaUrl(cep);
                                    endereco = new Endereco(json, num, comp);

                                    EnderecoDAO.salvaEndereco(endereco);

                                    endereco = EnderecoDAO.setId();

                                    valida = true;
                                } else if(val == 2) {} else {
                                    System.out.println("Opção inválida");
                                }
                            }

                            System.out.println("Insira uma descrição sobre a denúncia");
                            String desc = scanner.nextLine();

                            Denuncia denuncia = new Denuncia(usuario, endereco, desc);
                            DenunciaDAO.novaDenuncia(denuncia);
                        } else {
                            DenunciaDAO.retornarDeununcia();
                            
                            System.out.println("Insira o código identificador da denúncia a qual foi feita a vistoria: ");
                            int val = scanner.nextInt();
                            scanner.nextLine();
                            Denuncia denuncia = DenunciaDAO.umaDenuncia(val);

                            System.out.println("Insira uma descrição sobre a vistoria: ");
                            String desc = scanner.nextLine();

                            Vistoria vistoria = new Vistoria(denuncia, usuario, desc);

                            VistoriaDAO.novaVistoria(vistoria);
                        }
                        break;
                    case 2: 
                        if(usuario.getTipoDeCadastro() == TipoCadastro.Civil) {
                            DenunciaDAO.retornarDeununcia();
                        } else {
                            VistoriaDAO.retornarVistoria();
                        }
                        break;
                    case 3:
                        System.out.println("Insira a nova senha: ");
                        String senha = scanner.nextLine();

                        UsuarioDAO.alterarSenha(usuario, senha);
                        break;
                    case 4:
                        opera = false;
                        break;
                    default:
                        System.out.println("Operação inválida!");    
                        
                }
                
            }

        }
        
        scanner.close();
    }
}