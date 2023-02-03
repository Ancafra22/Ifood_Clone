package com.ancafra.ifoodclone.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.model.Login;
import com.ancafra.ifoodclone.activity.model.Usuario;
import com.santalu.maskara.widget.MaskEditText;

public class UsuarioFinalizaCadastroActivity extends AppCompatActivity {

    private EditText edt_nome;
    private MaskEditText edt_cpf;
    private EditText edt_rua;
    private EditText edt_bairro;
    private EditText edt_cidade;
    private EditText edt_estado;
    private MaskEditText edt_cep;
    private MaskEditText edt_telefone;
    private ProgressBar progressBar;
    private Usuario usuario;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_finaliza_cadastro);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuario = (Usuario) bundle.getSerializable("usuario");
            login = (Login) bundle.getSerializable("login");
        }

        iniciaComponentes();
    }

    public void validaDados(View view) {
        String nome = edt_nome.getText().toString().trim();
        String cpf = edt_cpf.getUnMasked();
        String rua = edt_rua.getText().toString().trim();
        String bairro = edt_bairro.getText().toString().trim();
        String cidade = edt_cidade.getText().toString().trim();
        String estado = edt_estado.getText().toString().trim();
        String cep = edt_cep.getUnMasked();
        String telefone = edt_telefone.getUnMasked();

        if (!nome.isEmpty()) {
            if (!cpf.isEmpty()) {
                if (!rua.isEmpty()) {
                    if (!bairro.isEmpty()) {
                        if (!cidade.isEmpty()) {
                            if (!estado.isEmpty()) {
                                if (!cep.isEmpty()) {
                                    if (!telefone.isEmpty()) {
                                        if (edt_telefone.isDone()) {

                                            ocultarTeclado();
                                            progressBar.setVisibility(View.VISIBLE);
                                            finalizaCadastro(nome, cpf, rua, bairro, cidade, estado, cep, telefone);

                                        } else {
                                            edt_telefone.requestFocus();
                                            edt_telefone.setError("Informe um telefone válido");
                                        }

                                    } else {
                                        edt_telefone.requestFocus();
                                        edt_telefone.setError("Preenchimento obrigatório");
                                    }
                                } else {
                                    edt_cep.requestFocus();
                                    edt_cep.setError("Preenchimento obrigatório");
                                }
                            } else {
                                edt_cidade.requestFocus();
                                edt_cidade.setError("Preenchimento obrigatório");
                            }
                        } else {
                            edt_estado.requestFocus();
                            edt_estado.setError("Preenchimento obrigatório");
                        }
                    } else {
                        edt_bairro.requestFocus();
                        edt_bairro.setError("Preenchimento obrigatório");
                    }
                } else {
                    edt_rua.requestFocus();
                    edt_rua.setError("Preenchimento obrigatório");
                }
            } else {
                edt_cpf.requestFocus();
                edt_cpf.setError("Preenchimento obrigatório");
            }
        } else {
            edt_nome.requestFocus();
            edt_nome.setError("Preenchimento obrigatório");
        }
    }

    private void finalizaCadastro
            (String nome, String cpf, String rua, String bairro,
             String cidade, String estado, String cep, String telefone) {
        login.setAcesso(true);
        login.salvar();

        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setRua(rua);
        usuario.setBairro(bairro);
        usuario.setCidade(cidade);
        usuario.setEstado(estado);
        usuario.setCep(cep);
        usuario.setTelefone(telefone);
        usuario.salvar();

        finish();
        startActivity(new Intent(this, UsuarioHomeActivity.class));
    }

    private void iniciaComponentes() {
        edt_nome = findViewById(R.id.edt_nome);
        edt_cpf = findViewById(R.id.edt_cpf);
        edt_rua = findViewById(R.id.edt_rua);
        edt_bairro = findViewById(R.id.edt_bairro);
        edt_cidade = findViewById(R.id.edt_cidade);
        edt_estado = findViewById(R.id.edt_estado);
        edt_cep = findViewById(R.id.edt_cep);
        edt_telefone = findViewById(R.id.edt_telefone);
        progressBar = findViewById(R.id.progressBar);
    }

    //método responsável por ocultar o teclado
    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                edt_nome.getWindowToken(), 0
        );
    }
}