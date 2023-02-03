package com.ancafra.ifoodclone.activity.empresa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.MainActivity;
import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.ancafra.ifoodclone.activity.model.Empresa;
import com.ancafra.ifoodclone.activity.model.Login;
import com.ancafra.ifoodclone.activity.model.Usuario;
import com.ancafra.ifoodclone.activity.usuario.UsuarioHomeActivity;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EmpresaFinalizaCadastroActivity extends AppCompatActivity {

    private ImageView img_logo;
    private EditText edt_nome;
    private MaskEditText edt_cnpj;
    private EditText edt_rua;
    private EditText edt_bairro;
    private EditText edt_cidade;
    private EditText edt_estado;
    private MaskEditText edt_cep;
    private MaskEditText edt_telefone;
    private EditText edt_categoria;
    private CurrencyEditText edt_taxa_entrega;
    private CurrencyEditText edt_pedido_minimo;
    private EditText edt_tempo_minimo;
    private EditText edt_tempo_maximo;
    private ProgressBar progressBar;

    private Empresa empresa;
    private Login login;
    private String caminhoLogo = "";
    private final int REQUEST_GALERIA = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_finaliza_cadastro);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            empresa = (Empresa) bundle.getSerializable("empresa");
            login = (Login) bundle.getSerializable("login");
        }

        iniciaComponentes();
    }

    public void SelecionarLogo(View view) {
        verificaPermissaoGaleria();
    }

    public void validaDadosEmpresa(View view) {

        String nome = edt_nome.getText().toString().trim();
        String cnpj = edt_cnpj.getUnMasked();
        String rua = edt_rua.getText().toString().trim();
        String bairro = edt_bairro.getText().toString().trim();
        String cidade = edt_cidade.getText().toString().trim();
        String estado = edt_estado.getText().toString().trim();
        String cep = edt_cep.getText().toString().trim();
        String telefone = edt_telefone.getText().toString().trim();
        String categoria = edt_categoria.getText().toString().trim();
        double taxaEntrega = (double) edt_taxa_entrega.getRawValue() / 100;
        double pedidoMinimo = (double) edt_pedido_minimo.getRawValue() / 100;

        int tempoMinimo = 0;
        if(!edt_tempo_minimo.getText().toString().isEmpty())
            tempoMinimo = Integer.parseInt(edt_tempo_minimo.getText().toString());

        int tempoMaximo = 0;
        if(edt_tempo_maximo.getText().toString().isEmpty())
            tempoMaximo = Integer.parseInt(edt_tempo_maximo.getText().toString());

        if (!caminhoLogo.isEmpty()) {
            if (!nome.isEmpty()) {
                if (!cnpj.isEmpty()) {
                    if (!rua.isEmpty()) {
                        if (!bairro.isEmpty()) {
                            if (!cidade.isEmpty()) {
                                if (!estado.isEmpty()) {
                                    if (edt_cep.isDone()) {
                                        if (edt_telefone.isDone()) {
                                            if (!categoria.isEmpty()) {
                                                if (taxaEntrega >= 0) {
                                                    if (pedidoMinimo >= 0) {
                                                        if (tempoMinimo > 0) {
                                                            if (tempoMaximo >= 0) {

                                                                ocultarTeclado();
                                                                progressBar.setVisibility(View.VISIBLE);

                                                                empresa.setNome(nome);
                                                                empresa.setCnpj(cnpj);
                                                                empresa.setRua(rua);
                                                                empresa.setBairro(bairro);
                                                                empresa.setCidade(cidade);
                                                                empresa.setEstado(estado);
                                                                empresa.setCep(cep);
                                                                empresa.setTelefone(telefone);
                                                                empresa.setCategoria(categoria);
                                                                empresa.setTaxaEntrega(taxaEntrega);
                                                                empresa.setPedidoMinimo(pedidoMinimo);
                                                                empresa.setTempoMinEntrega(tempoMinimo);
                                                                empresa.setTempoMaxEntrega(tempoMaximo);
                                                                salvarImagemFirebase();

                                                            } else {
                                                                edt_tempo_maximo.requestFocus();
                                                                edt_tempo_maximo.setError("Preenchimento obrigatório");
                                                            }
                                                        } else {
                                                            edt_tempo_minimo.requestFocus();
                                                            edt_tempo_minimo.setError("Preenchimento obrigatório");
                                                        }
                                                    } else {
                                                        edt_pedido_minimo.requestFocus();
                                                        edt_pedido_minimo.setError("informe um valor válido");
                                                    }
                                                } else {
                                                    edt_taxa_entrega.requestFocus();
                                                    edt_taxa_entrega.setError("Informe um valor válido");
                                                }
                                            } else {
                                                edt_categoria.requestFocus();
                                                edt_categoria.setError("Preenchimento obrigatório");
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
                                    edt_estado.requestFocus();
                                    edt_estado.setError("Preenchimento obrigatório");
                                }
                            } else {
                                edt_cidade.requestFocus();
                                edt_cidade.setError("Preenchimento obrigatório");
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
                    edt_cnpj.requestFocus();
                    edt_cnpj.setError("Preenchimento obrigatório");
                }

            } else {
                edt_nome.requestFocus();
                edt_nome.setError("Preenchimento obrigatório");
            }

        } else {
            progressBar.setVisibility(View.GONE);
            ocultarTeclado();
            erroAutenticacao("Selecione uma logo para seu cadastro.");
        }
    }

    private void verificaPermissaoGaleria() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(EmpresaFinalizaCadastroActivity.this,
                        "Permissão negada!",
                        Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedTitle("Permissão negada.")
                .setDeniedMessage("Você negou as permissões para acessar a galeria do dispositivo, deseja permitir ?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    }

    private void salvarImagemFirebase() {
        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("perfil")
                .child(empresa.getId() + ".JPEG");
        UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoLogo));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

            login.setAcesso(true);
            login.salvar();

            empresa.setUrlLogo(task.getResult().toString());
            empresa.salvar();
            finish();
            startActivity(new Intent(getBaseContext(), EmpresaHomeActivity.class));
        })).addOnFailureListener(e -> erroAutenticacao(e.getMessage()));
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

    private void iniciaComponentes() {
        img_logo = findViewById(R.id.img_logo);
        edt_nome = findViewById(R.id.edt_nome);
        edt_cnpj = findViewById(R.id.edt_cnpj);
        edt_rua = findViewById(R.id.edt_rua);
        edt_bairro = findViewById(R.id.edt_bairro);
        edt_cidade = findViewById(R.id.edt_cidade);
        edt_estado = findViewById(R.id.edt_estado);
        edt_cep = findViewById(R.id.edt_cep);
        edt_telefone = findViewById(R.id.edt_telefone);
        edt_categoria = findViewById(R.id.edt_categoria);
        edt_taxa_entrega = findViewById(R.id.edt_taxa_entrega);
        edt_taxa_entrega.setLocale(new Locale("PT", "br"));
        edt_pedido_minimo = findViewById(R.id.edt_pedido_minimo);
        edt_pedido_minimo.setLocale(new Locale("PT", "br"));
        edt_tempo_minimo = findViewById(R.id.edt_tempo_minimo);
        edt_tempo_maximo = findViewById(R.id.edt_tempo_maximo);
        progressBar = findViewById(R.id.progressBar);
    }

    private void finalizaCadastro(String nome) {
        login.setAcesso(true);
        login.salvar();

        empresa.setNome(nome);
        empresa.salvar();

        finish();
        startActivity(new Intent(this, UsuarioHomeActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_GALERIA){
                Bitmap bitmap;
                Uri imagemSelecionada = data.getData();
                caminhoLogo = data.getData().toString();

                if(Build.VERSION.SDK_INT < 28){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagemSelecionada);
                        img_logo.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imagemSelecionada);
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source);
                        img_logo.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //método responsável por ocultar o teclado
    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                edt_nome.getWindowToken(), 0
        );
    }
}