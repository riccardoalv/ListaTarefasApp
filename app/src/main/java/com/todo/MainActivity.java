package com.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todo.activity.TarefaActivity;
import com.todo.adapter.TarefaAdapter;
import com.todo.databinding.ActivityMainBinding;
import com.todo.helper.TarefaDAO;
import com.todo.model.Tarefa;
import com.todo.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> tarefaList = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa o RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Configura o RecyclerView para lidar com cliques e longos cliques
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        tarefaSelecionada = tarefaList.get(position);

                        Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        tarefaSelecionada = tarefaList.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Confirmar exclusão");
                        builder.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                if (tarefaDAO.deletar(tarefaSelecionada)) {
                                    carregarTarefas();
                                    Toast.makeText(getApplicationContext(), "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Não", null);
                        builder.create().show();
                    }
                }
        ));

        // Configura o FloatingActionButton para adicionar novas tarefas
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTarefas();
    }

    public void carregarTarefas() {
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefaList = tarefaDAO.listar();

        tarefaAdapter = new TarefaAdapter(tarefaList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);
        tarefaAdapter.notifyDataSetChanged();
    }
}
