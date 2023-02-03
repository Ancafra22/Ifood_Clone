package com.ancafra.ifoodclone.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.adapter.CuponsAdapter;
import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.ancafra.ifoodclone.activity.model.Categoria;
import com.ancafra.ifoodclone.activity.model.Cupons;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioCuponsActivity extends AppCompatActivity {

    private ImageView ib_voltar;
    private TextView text_toolbar;
    private SwipeableRecyclerView rv_cupons;
    private TextView text_info;
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private CuponsAdapter cuponsAdapter;
    private List<Cupons> cuponsList = new ArrayList<>();
    private Cupons cupomSelecionado;
    private int cuponsIndex = 0;
    private Boolean novoCupom = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cupons);
        iniciaComponentes();
        configCliques();
        recuperaCupons();
        configRv();

    }

    private void configRv() {
        rv_cupons.setLayoutManager(new LinearLayoutManager(this));
        rv_cupons.setHasFixedSize(true);
        cuponsAdapter = new CuponsAdapter(cuponsList, this::OnClick);
        rv_cupons.setAdapter(cuponsAdapter);
        rv_cupons.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                dialogRemoveCupom(cuponsList.get(position));
            }
        });
    }

    private void recuperaCupons() {
        DatabaseReference cuponsRef = FirebaseHelper.getDatabaseReference()
                .child("cupons")
                .child(FirebaseHelper.getIdFirebase());
        cuponsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Cupons cupons = ds.getValue(Cupons.class);
                        cuponsList.add(cupons);
                        text_info.setText("");
                    }
                }else{
                    text_info.setText("Nenhuma cupom cadastrado");
                }
                progressBar.setVisibility(View.GONE);
                Collections.reverse(cuponsList);
                cuponsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_cupom, null);
        builder.setView(view);

        EditText edt_cupom = view.findViewById(R.id.edt_cupom);
        Button btn_fechar = view.findViewById(R.id.btn_fechar);
        Button btn_salvar = view.findViewById(R.id.btn_salvar);

        if(!novoCupom){
            edt_cupom.setText(cupomSelecionado.getNome());
        }

        btn_salvar.setOnClickListener(v -> {
            String nomeCupom = edt_cupom.getText().toString().trim();
            if(!nomeCupom.isEmpty()){
                if(novoCupom){
                    Cupons cupons = new Cupons();
                    cupons.setNome(nomeCupom);
                    cupons.salvar();
                    cuponsList.add(cupons);

                }else{
                    cupomSelecionado.setNome(nomeCupom);
                    cuponsList.set(cuponsIndex, cupomSelecionado);
                    cupomSelecionado.salvar();
                }
                if(!cuponsList.isEmpty()) text_info.setText("");
                dialog.dismiss();
                cuponsAdapter.notifyDataSetChanged();
            }else{
                edt_cupom.setError("Informe uma categoria");
                edt_cupom.requestFocus();
            }
        });

        btn_fechar.setOnClickListener(v -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }

    private void dialogRemoveCupom(Cupons cupons){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover cupom");
        builder.setMessage("Deseja remover o cupom selecionado?");
        builder.setNegativeButton("Não", (dialog, which) -> {
            cuponsAdapter.notifyDataSetChanged();
        });
        builder.setPositiveButton("Sim", ((dialog, which) -> {
            cupons.remover();
            cuponsList.remove(cupons);

            if(cuponsList.isEmpty()) text_info.setText("Nenhum cupom cadastrado");

            cuponsAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void configCliques() {
        text_toolbar.setText("Meus Cupons");
        ib_voltar.setOnClickListener(v -> finish());
        findViewById(R.id.ib_add).setOnClickListener(v -> {
            novoCupom = true;
            showDialog();
        });
    }

    private void iniciaComponentes() {
        ib_voltar = findViewById(R.id.ib_voltar);
        text_toolbar = findViewById(R.id.text_toolbar);
        rv_cupons = findViewById(R.id.rv_cupons);
        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
    }

    public void OnClick(Cupons cupons, int position) {
        cupomSelecionado = cupons;
        cuponsIndex = position;
        novoCupom = false;

        showDialog();
    }

}