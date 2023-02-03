package com.ancafra.ifoodclone.activity.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.adapter.CategoriaAdapter;
import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.ancafra.ifoodclone.activity.model.Categoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmpresaCategoriaActivity extends AppCompatActivity implements CategoriaAdapter.OnClickListener {
    private SwipeableRecyclerView rv_categorias;
    private TextView text_info;
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> categoriaList = new ArrayList<>();
    private Categoria categoriaSelecionada;
    private int categoriaIndex = 0;
    private Boolean novaCategoria = true;
    private int acesso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_categoria);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            acesso = bundle.getInt("acesso");
        }

        iniciaComponentes();
        configCliques();
        recuperaCategorias();
        configRv();
    }

    private void configRv() {
        rv_categorias.setLayoutManager(new LinearLayoutManager(this));
        rv_categorias.setHasFixedSize(true);
        categoriaAdapter = new CategoriaAdapter(categoriaList, this);
        rv_categorias.setAdapter(categoriaAdapter);
        rv_categorias.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                dialogRemoveCategoria(categoriaList.get(position));
            }
        });
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
        findViewById(R.id.ib_add).setOnClickListener(v -> {
            novaCategoria = true;
            showDialog();
        });
    }

    private void recuperaCategorias() {
        DatabaseReference categoriasRef = FirebaseHelper.getDatabaseReference()
                .child("categorias")
                .child(FirebaseHelper.getIdFirebase());
        categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Categoria categoria = ds.getValue(Categoria.class);
                        categoriaList.add(categoria);
                    }
                    text_info.setText("");
                }else{
                    text_info.setText("Nenhuma categoria cadastrada");
                }
                progressBar.setVisibility(View.GONE);
                Collections.reverse(categoriaList);
                categoriaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_categoria, null);
        builder.setView(view);

        EditText edt_categoria = view.findViewById(R.id.edt_categoria);
        Button btn_fechar = view.findViewById(R.id.btn_fechar);
        Button btn_salvar = view.findViewById(R.id.btn_salvar);

        if(!novaCategoria){
            edt_categoria.setText(categoriaSelecionada.getNome());
        }

        btn_salvar.setOnClickListener(v -> {
            String nomeCategoria = edt_categoria.getText().toString().trim();
            if(!nomeCategoria.isEmpty()){
                if(novaCategoria){
                    Categoria categoria = new Categoria();
                    categoria.setNome(nomeCategoria);
                    categoria.salvar();
                    categoriaList.add(categoria);

            }else{
                    categoriaSelecionada.setNome(nomeCategoria);
                    categoriaList.set(categoriaIndex, categoriaSelecionada);
                    categoriaSelecionada.salvar();
                }
                if(!categoriaList.isEmpty()) text_info.setText("");
                dialog.dismiss();
                categoriaAdapter.notifyDataSetChanged();
            }else{
                edt_categoria.setError("Informe uma categoria");
                edt_categoria.requestFocus();
            }
        });

        btn_fechar.setOnClickListener(v -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }

    private void dialogRemoveCategoria(Categoria categoria){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover categoria");
        builder.setMessage("Deseja remover a categoria selecionada?");
        builder.setNegativeButton("Não", (dialog, which) -> {
            categoriaAdapter.notifyDataSetChanged();
        });
        builder.setPositiveButton("Sim", ((dialog, which) -> {
            categoria.remover();
            categoriaList.remove(categoria);

            if(categoriaList.isEmpty()) text_info.setText("Nenhuma categoria cadastrada");

            categoriaAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponentes() {
        TextView text_toobar = findViewById(R.id.text_toolbar);
        text_toobar.setText("Categorias");
        rv_categorias = findViewById(R.id.rv_categorias);
        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void OnClick(Categoria categoria, int position) {
        if(acesso == 0){
            categoriaSelecionada = categoria;
            categoriaIndex = position;
            novaCategoria = false;

            showDialog();
        }else if(acesso == 1){
            Intent intent = new Intent();
            intent.putExtra("categoriaSelecionada", categoria);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}