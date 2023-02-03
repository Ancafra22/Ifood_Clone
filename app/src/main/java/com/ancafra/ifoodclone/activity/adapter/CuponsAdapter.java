package com.ancafra.ifoodclone.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.model.Cupons;

import java.util.List;

public class CuponsAdapter extends RecyclerView.Adapter<CuponsAdapter.MyViewHolder> {
    private List<Cupons> cuponsList;
    private OnClickListener onClickListener;

    public CuponsAdapter(List<Cupons> cuponsList, OnClickListener onClickListener) {
        this.cuponsList = cuponsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cupom_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cupons cupons = cuponsList.get(position);
        holder.text_desconto.setText(cupons.getNome());
        holder.itemView.setOnClickListener(v -> onClickListener.OnClick(cupons, position));
    }

    @Override
    public int getItemCount() {
        return cuponsList.size();
    }

    public interface OnClickListener {
        void OnClick(Cupons cupons, int position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_desconto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_desconto = itemView.findViewById(R.id.text_desconto);
        }
    }
}
