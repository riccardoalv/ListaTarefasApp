package com.todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.todo.R;
import com.todo.helper.TarefaDAO;
import com.todo.model.Tarefa;

public class TarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;
    private Button btnSalvar, btnDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        // Inicializa os componentes do layout
        editTarefa = findViewById(R.id.textTarefa);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnDeletar = findViewById(R.id.btnDeletar);

        // Recupera a tarefa enviada pela Intent (se existir)
        Intent intent = getIntent();
        tarefaAtual = (Tarefa) intent.getSerializableExtra("tarefaSelecionada");

        // Se houver uma tarefa recebida, preenche o campo de texto e mostra o botão de deletar
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
            btnDeletar.setVisibility(Button.VISIBLE); // Mostra o botão de deletar
        }

        // Configura o clique no botão de salvar
        btnSalvar.setOnClickListener(view -> {
            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
            String nomeTarefa = editTarefa.getText().toString();

            if (!nomeTarefa.isEmpty()) {
                if (tarefaAtual != null) {
                    // Atualiza a tarefa existente
                    tarefaAtual.setNomeTarefa(nomeTarefa);
                    if (tarefaDAO.atualizar(tarefaAtual)) {
                        Toast.makeText(this, "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Salva uma nova tarefa
                    Tarefa novaTarefa = new Tarefa();
                    novaTarefa.setNomeTarefa(nomeTarefa);
                    if (tarefaDAO.salvar(novaTarefa)) {
                        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Preencha o nome da tarefa!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura o clique no botão de deletar
        btnDeletar.setOnClickListener(view -> {
            if (tarefaAtual != null) {
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                if (tarefaDAO.deletar(tarefaAtual)) {
                    Toast.makeText(this, "Tarefa deletada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erro ao deletar tarefa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
