package com.ancafra.ifoodclone.activity.model;

import android.net.Uri;

import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Empresa implements Serializable {

    private String id;
    private String nome;
    private String cnpj;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String email;
    private String senha;
    private String urlLogo;
    private Boolean acesso;
    private double taxaEntrega;
    private double pedidoMinimo;
    private int tempoMaxEntrega;
    private int tempoMinEntrega;
    private String categoria;
    private Long dataCadastro;

    public Empresa() {
    }

    public void salvar() {
        DatabaseReference empresaRef = FirebaseHelper.getDatabaseReference()
                .child("empresas")//nome do nó
                .child(getId()); //pega informações atravéz do id
        empresaRef.setValue(this);//seta todos os valores da classe

        //usando o firebase user para guardar as informações do usuário
        FirebaseUser user = FirebaseHelper.getAuth().getCurrentUser();
        UserProfileChangeRequest perfil;
        if(getUrlLogo() == null){
            //crianado objeto UserProfileChangeRequest para atualizar os dados do usuário com as novas informações
            perfil = new UserProfileChangeRequest.Builder()
                    .setDisplayName(getNome())
                    .build();
        }else{
            //crianado objeto UserProfileChangeRequest para atualizar os dados do usuário com as novas informações
            perfil = new UserProfileChangeRequest.Builder()
                    .setDisplayName(getNome())
                    .setPhotoUri(Uri.parse(getUrlLogo()))
                    .build();
        }
        if(user != null) user.updateProfile(perfil);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Boolean getAcesso() {
        return acesso;
    }

    public void setAcesso(Boolean acesso) {
        this.acesso = acesso;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public double getPedidoMinimo() {
        return pedidoMinimo;
    }

    public void setPedidoMinimo(double pedidoMinimo) {
        this.pedidoMinimo = pedidoMinimo;
    }

    public int getTempoMaxEntrega() {
        return tempoMaxEntrega;
    }

    public void setTempoMaxEntrega(int tempoMaxEntrega) {
        this.tempoMaxEntrega = tempoMaxEntrega;
    }

    public int getTempoMinEntrega() {
        return tempoMinEntrega;
    }

    public void setTempoMinEntrega(int tempoMinEntrega) {
        this.tempoMinEntrega = tempoMinEntrega;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Long dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
