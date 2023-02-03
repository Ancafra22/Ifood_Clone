package com.ancafra.ifoodclone.activity.model;

import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String cpf;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String email;
    private String senha;

    public Usuario() {
    }

    public void salvar() {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")//nome do nó
                .child(getId()); //pega informações atravéz do id
        usuarioRef.setValue(this);//seta todos os valores da classe

        //usando o firebase user para guardar as informações do usuário
        FirebaseUser user = FirebaseHelper.getAuth().getCurrentUser();
        //crianado objeto UserProfileChangeRequest para atualizar os dados do usuário com as novas informações
        UserProfileChangeRequest perfil = new UserProfileChangeRequest.Builder()
                .setDisplayName(getNome())
                .build();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
}
