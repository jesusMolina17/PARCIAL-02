package com.example.parcialmovil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListadoDeCanciones  extends RecyclerView.Adapter<ListadoDeCanciones.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView info;
        private LinearLayout layout;
        private Context context;
        private int posicion = 0;
        private List<Clase_Cancion> canciones = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            info = (TextView) itemView.findViewById(R.id.cancion_txt);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_cancion);

        }

        void setOnClickListener(List<Clase_Cancion> lista, int position) {
            canciones = lista;
            posicion = position;
            layout.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {


            }
        }

    }

    public List<Clase_Cancion> lista;
    public Context contexto;
    public int posi;

    public ListadoDeCanciones(List<Clase_Cancion> lista, Context contexto) {
        this.lista = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_canciones, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        posi = position;
        holder.info.setText(lista.get(position).titulo +" - "+ lista.get(position).artista +" - "+ lista.get(position).album +" - "+ lista.get(position).duracion );
        holder.setOnClickListener(lista, position);
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }


}
