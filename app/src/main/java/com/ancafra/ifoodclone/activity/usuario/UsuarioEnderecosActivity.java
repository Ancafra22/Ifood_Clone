package com.ancafra.ifoodclone.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancafra.ifoodclone.R;

public class UsuarioEnderecosActivity extends AppCompatActivity {

    private ImageView ib_voltar;
    private TextView text_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_enderecos);
        iniciaComponentes();
        configCliques();

    }
    private void configCliques() {
        text_toolbar.setText("Meus endereços");
        ib_voltar.setOnClickListener(v -> finish());
    }

    private void iniciaComponentes() {
        ib_voltar = findViewById(R.id.ib_voltar);
        text_toolbar = findViewById(R.id.text_toolbar);
    }
}