package com.ancafra.ifoodclone.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {
    private final List<Categoria> categoriaList;
    private final OnClickListener onClickListener;

    public CategoriaAdapter(List<Categoria> categoriaList, OnClickListener onClickListener) {
        this.categoriaList = categoriaList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categoria categoria = categoriaList.get(position);
        holder.text_categoria.setText(categoria.getNome());
        holder.itemView.setOnClickListener(v -> onClickListener.OnClick(categoria, position));
    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public interface OnClickListener {
        void OnClick(Categoria categoria, int position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_categoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_categoria = itemView.findViewById(R.id.text_categoria);
        }
    }
}
