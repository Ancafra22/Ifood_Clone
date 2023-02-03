package com.ancafra.ifoodclone.activity.model;

import android.text.Editable;

import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class Cupons {

    private String id;
    private String nome;

    public Cupons(){
        DatabaseReference categoriaRef = FirebaseHelper.getDatabaseReference();
        setId(categoriaRef.push().getKey());
    }

    public void salvar(){
        DatabaseReference cuponsRef = FirebaseHelper.getDatabaseReference()
                .child("cupons")
                .child(FirebaseHelper.getIdFirebase())
                .child(getId());
        cuponsRef.setValue(this);
    }

    public void remover(){
        DatabaseReference cuponsRef = FirebaseHelper.getDatabaseReference()
                .child("cupons")
                .child(FirebaseHelper.getIdFirebase())
                .child(getId());
        cuponsRef.removeValue();
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

}
