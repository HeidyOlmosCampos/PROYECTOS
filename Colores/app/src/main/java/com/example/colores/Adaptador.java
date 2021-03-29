package com.example.colores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
  List<Item> listaDeColores;

    public Adaptador(List<Item> listaDeColores) {
        this.listaDeColores = listaDeColores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Item item= listaDeColores.get(position);
     holder.color.setCardBackgroundColor(Color.parseColor(item.getColor()));
     holder.pueblo.setText(item.getPueblo());
     holder.Peso.setText(item.getPeso());
    }

    @Override
    public int getItemCount() {
        return listaDeColores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
      CardView color;
      TextView pueblo;
        TextView Peso;
      public ViewHolder(@NonNull View itemView) {
          super(itemView);
          color = itemView.findViewById(R.id.color);
          pueblo = itemView.findViewById(R.id.pueblo);
          Peso = itemView.findViewById(R.id.Peso);
      }
  }
}
