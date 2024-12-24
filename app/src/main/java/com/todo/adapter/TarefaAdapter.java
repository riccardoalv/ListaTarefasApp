package com.todo.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.todo.model.Tarefa;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.todo.R;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> tarefaList;

    public TarefaAdapter(List<Tarefa> lista) {
        this.tarefaList = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa = tarefaList.get(position);
        holder.txtTarefa.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        return this.tarefaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTarefa = itemView.findViewById(R.id.txtTarefa);
        }
    }
}