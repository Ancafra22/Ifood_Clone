package com.ancafra.ifoodclone.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.helper.GetMask;
import com.ancafra.ifoodclone.activity.model.Produto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddMaisAdapter extends RecyclerView.Adapter<AddMaisAdapter.MyViewHolder> {

    private final List<Produto> produtoList;
    private final List<String> addMaisList;
    private final Context context;
    private final OnClickListener onClickListener;

    public AddMaisAdapter(List<Produto> produtoList, List<String> addMaisList, Context context, OnClickListener onClickListener) {
        this.produtoList = produtoList;
        this.addMaisList = addMaisList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_mais_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = produtoList.get(position);

        Picasso.get().load(produto.getUrlImagem()).into(holder.img_produto);
        holder.text_nome.setText(produto.getNome());
        holder.text_valor.setText(context.getString(R.string.text_valor, GetMask.getValor(Double.parseDouble(produto.getValor()))));

        holder.cb_status.setChecked(addMaisList.add(produto.getId()));
        holder.cb_status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            onClickListener.OnClick(produto.getId(), isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public interface OnClickListener {
        void OnClick(String idProduto, Boolean status);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_produto;
        TextView text_nome, text_valor;
        CheckBox cb_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_produto = itemView.findViewById(R.id.img_produto);
            text_nome = itemView.findViewById(R.id.text_nome);
            text_valor = itemView.findViewById(R.id.text_valor);
            cb_status = itemView.findViewById(R.id.cb_status);
        }
    }
}
