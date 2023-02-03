package com.ancafra.ifoodclone.activity.autentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ancafra.ifoodclone.R;

public class RecuperaContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_conta);
        confgCliques();
        iniciaComponebte();
    }
    private void confgCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    public void iniciaComponebte() {
        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Recupera Cadastro");
    }
}