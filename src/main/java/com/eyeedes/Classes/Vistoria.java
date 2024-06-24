package com.eyeedes.Classes;

import com.eyeedes.Global.Util;

public class Vistoria {
    private int ID;
    private int ID_denuncia;
    private int ID_vistoriador;
    private int endereco;
    private String descricao;
    private String dataVisita;
    private String dataInativacao;

    public Vistoria(Denuncia denuncia, Usuario vistoriador, String descricao) {
        this.ID_denuncia = denuncia.getId();
        this.ID_vistoriador = vistoriador.getId();
        this.endereco = denuncia.getEnderecoId();
        this.descricao = descricao;
        this.dataVisita = Util.RegistraDataAtual();
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_denuncia() {
        return this.ID_denuncia;
    }

    public void setID_denuncia(int ID_denuncia) {
        this.ID_denuncia = ID_denuncia;
    }

    public int getID_vistoriador() {
        return this.ID_vistoriador;
    }

    public void setID_vistoriador(int ID_vistoriador) {
        this.ID_vistoriador = ID_vistoriador;
    }

    public int getEndereco() {
        return this.endereco;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataVisita() {
        return this.dataVisita;
    }

    public void setDataVisita(String dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getDataInativacao() {
        return this.dataInativacao;
    }

    public void setDataInativacao(String dataInativacao) {
        this.dataInativacao = dataInativacao;
    }
}
