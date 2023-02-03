package com.ancafra.ifoodclone.activity.autentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.empresa.EmpresaFinalizaCadastroActivity;
import com.ancafra.ifoodclone.activity.empresa.EmpresaHomeActivity;
import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.ancafra.ifoodclone.activity.model.Empresa;
import com.ancafra.ifoodclone.activity.model.Login;
import com.ancafra.ifoodclone.activity.model.Usuario;
import com.ancafra.ifoodclone.activity.usuario.UsuarioFinalizaCadastroActivity;
import com.ancafra.ifoodclone.activity.usuario.UsuarioHomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_email;
    private EditText edt_senha;
    private ProgressBar progressBar;
    private Login login;
    private ImageView ib_voltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        confgCliques();
        iniciaComponentes();

    }

    public void validaDados(View view) {
        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                ocultarTeclado();

                progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);


            }else{
                edt_senha.requestFocus();
                edt_senha.setError("Preenchimento obrigatório!");
            }

        }else{
            edt_email.requestFocus();
            edt_email.setError("Preenchimento obrigatório!");
        }

    }

    public void logar(String email, String senha){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        verificaCadastro(task.getResult().getUser().getUid());
                    }else{
                        progressBar.setVisibility(View.GONE);
                        erroAutenticacao(FirebaseHelper.validaErros(task.getException().getMessage()));
                    }
        });
    }

    private void verificaCadastro(String idUser){
        DatabaseReference loginRef = FirebaseHelper.getDatabaseReference()
                .child("login")
                .child(idUser);
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                login = snapshot.getValue(Login.class);

                verificaAcesso(login);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verificaAcesso(Login login) {

        if(login != null){
            if(login.getTipo().equals("U")){
                if(login.getAcesso()){
                    finish();
                    startActivity(new Intent(getBaseContext(), UsuarioHomeActivity.class));
                }else{
                    recuperaUsuario();
                }
            }else{
                if(login.getAcesso()){
                    finish();
                    startActivity(new Intent(getBaseContext(), EmpresaHomeActivity.class));
                }else{
                    recuperaEmpresa();
                }
            }

        }

    }

    private void recuperaEmpresa() {
        DatabaseReference empresaRef = FirebaseHelper.getDatabaseReference()
                .child("empresas")
                .child(login.getId());
        empresaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Empresa empresa = snapshot.getValue(Empresa.class);
                if(empresa != null){
                    finish();
                    Intent intent = new Intent(getBaseContext(), EmpresaFinalizaCadastroActivity.class);
                    intent.putExtra("login", login);
                    intent.putExtra("empresa", empresa);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recuperaUsuario() {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(login.getId());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                if(usuario != null){
                    finish();
                    Intent intent = new Intent(getBaseContext(), UsuarioFinalizaCadastroActivity.class);
                    intent.putExtra("login", login);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void erroAutenticacao(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void  iniciaComponentes() {
        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Login");

        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        progressBar = findViewById(R.id.progressBar);
        ib_voltar = findViewById(R.id.ib_voltar);

    }

    private void confgCliques() {

        findViewById(R.id.text_criar_conta).setOnClickListener(v ->
                startActivity(new Intent(this, CriarContaActivity.class)));
        findViewById(R.id.text_recuperar_conta).setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());

    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                edt_email.getWindowToken(), 0
        );
    }

}