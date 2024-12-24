# Relatório de Alterações no Projeto

Este documento descreve as alterações realizadas no projeto padrão, focando na melhoria da interface de usuário para melhor usabilidade e visualização. Foram adicionados dois novos botões: um botão de salvar, centralizado para maior destaque, e um botão de deletar, estilizado com a cor vermelha para maior identificação de sua funcionalidade.

---

## Alterações Realizadas

### 1. Adição de um Botão Centralizado (Salvar)
- Um botão de salvar foi adicionado ao centro da tela de **Adicionar/Editar Tarefa**.
- **Objetivo:** Melhorar a visibilidade e usabilidade, destacando a principal ação do usuário.
- **Detalhes Técnicos:**
  - Botão foi posicionado abaixo do campo de texto usando o atributo `android:layout_gravity="center_horizontal"`.
  - Implementado o evento de clique no botão para salvar novas tarefas ou atualizar tarefas existentes.

### 2. Adição de um Botão de Deletar
- Um botão de deletar foi adicionado na tela **Adicionar/Editar Tarefa**, posicionado abaixo do botão de salvar.
- **Objetivo:** Permitir a exclusão de tarefas existentes diretamente da tela de edição.
- **Estilo do Botão:**
  - Cor de fundo vermelha (`@android:color/holo_red_light`).
  - Texto branco (`@android:color/white`) para contraste.
- **Visibilidade Condicional:**
  - O botão só é exibido quando uma tarefa existente é carregada para edição.
  - Para tarefas novas, o botão permanece oculto.

---

## Layout Atualizado

### Tela **Adicionar/Editar Tarefa**

- **Campo de Texto:** Entrada para o nome da tarefa.
- **Botão Salvar:** Localizado abaixo do campo de texto, centralizado horizontalmente.
- **Botão Deletar:** Localizado abaixo do botão de salvar, visível apenas ao editar tarefas.

---

## Código Adicionado

### Exemplo de Lógica para os Botões

#### Botão de Salvar
O botão de salvar captura o texto do campo de entrada e salva uma nova tarefa ou atualiza uma existente.

```java
btnSalvar.setOnClickListener(view -> {
    String nomeTarefa = editTarefa.getText().toString();

    if (!nomeTarefa.isEmpty()) {
        if (tarefaAtual != null) {
            tarefaAtual.setNomeTarefa(nomeTarefa);
            tarefaDAO.atualizar(tarefaAtual);
        } else {
            Tarefa novaTarefa = new Tarefa();
            novaTarefa.setNomeTarefa(nomeTarefa);
            tarefaDAO.salvar(novaTarefa);
        }
    } else {
        Toast.makeText(this, "Preencha o nome da tarefa!", Toast.LENGTH_SHORT).show();
    }
});
```

#### Botão de Deletar
O botão de deletar permite excluir uma tarefa existente. Ele é exibido somente quando uma tarefa é carregada para edição.

```java
btnDeletar.setOnClickListener(view -> {
    if (tarefaAtual != null) {
        tarefaDAO.deletar(tarefaAtual);
        finish();
    } else {
        Toast.makeText(this, "Erro ao deletar tarefa!", Toast.LENGTH_SHORT).show();
    }
});
```

---

## Considerações Finais

As alterações realizadas melhoraram a interface de usuário ao oferecer:
1. Maior destaque para a ação principal de salvar, com um botão centralizado.
2. Uma forma clara e direta de excluir tarefas existentes, destacada por um botão vermelho.

Estas mudanças tornam o aplicativo mais intuitivo e funcional para os usuários.
