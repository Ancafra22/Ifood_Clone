package com.ancafra.ifoodclone.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancafra.ifoodclone.R;

public class UsuarioAjudaActivity extends AppCompatActivity {

    private ImageView ib_voltar;
    private TextView text_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ajuda);
        iniciaComponentes();
        configCliques();

    }
    private void configCliques() {
        text_toolbar.setText("Ajuda");
        ib_voltar.setOnClickListener(v -> finish());
    }

    private void iniciaComponentes() {
        ib_voltar = findViewById(R.id.ib_voltar);
        text_toolbar = findViewById(R.id.text_toolbar);
    }
}