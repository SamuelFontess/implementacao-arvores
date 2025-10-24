# Relatório Técnico — Árvores Balanceadas (AVL e Rubro-Negra)

Este relatório descreve, em detalhes, as decisões de projeto, as estruturas de dados, as estratégias de balanceamento, as funções implementadas, os principais desafios enfrentados e os testes realizados no projeto de Árvores AVL e Rubro-Negra.

### Link repositório Github: [Implementação de Árvores em Java](https://github.com/SamuelFontess/implementacao-arvores)

## 1. Estruturas de Dados Escolhidas

### 1.1. Árvore AVL
- Nó: `NoAVL`
  - Campos: `int chave`, `int altura`, `NoAVL esquerda`, `NoAVL direita`.
  - A altura é mantida em cada nó para calcular o fator de balanceamento (altura(esquerda) − altura(direita)).
- Árvore: `AVL`
  - Campo: `NoAVL raiz`.

### 1.2. Árvore Rubro-Negra
- Nó: `NoRubroNegra`
  - Campos: `int chave`, `NoRubroNegra esquerda, direita, pai`, `boolean cor` (true = vermelho, false = preto).
  - O ponteiro para o pai facilita as correções (rotações/colorações) após inserções.
- Árvore: `RubroNegra`
  - Campo: `NoRubroNegra raiz`.

## 2. Estratégias de Balanceamento

### 2.1. AVL
- Definição: árvore binária de busca auto-balanceada que mantém o fator de balanceamento de cada nó em {−1, 0, +1}.
- Rebalanceamento ocorre após inserções e remoções.
- Rotações utilizadas:
  - Simples Direita (caso LL): quando o nó fica pesado para a esquerda e a chave foi inserida na subárvore esquerda-esquerda.
  - Simples Esquerda (caso RR): simétrico para a direita.
  - Dupla Esquerda-Direita (caso LR): primeiro rotação à esquerda no filho, depois rotação à direita no nó desbalanceado.
  - Dupla Direita-Esquerda (caso RL): primeiro rotação à direita no filho, depois rotação à esquerda no nó desbalanceado.
- Atualização da altura: após cada modificação estrutural, recalcula-se a altura do(s) nó(s) afetado(s).

### 2.2. Rubro-Negra
- Mantém as propriedades clássicas:
  1) Todo nó é vermelho ou preto; 2) Raiz é preta; 3) Folhas nulas são pretas; 4) Nó vermelho não pode ter filho vermelho; 5) Todo caminho da raiz a folha nula contém o mesmo número de nós pretos (altura negra).
- Implementada correção apenas para inserção (remoção não implementada no projeto atual).
- Casos de correção após inserir um nó vermelho (com ponteiros de pai e tio):
  - Caso 1 (tio vermelho): recoloração de pai e tio para preto, avô para vermelho, e sobe o foco para o avô.
  - Caso 2 (tio preto e nó em “zig-zag”): rotação (esquerda ou direita) para converter em caso 3.
  - Caso 3 (tio preto e nó em “linha”): recoloração de pai para preto, avô para vermelho, e rotação oposta no avô.
- Ao final, a raiz é sempre pintada de preto.

## 3. Descrição das Funções Implementadas

### 3.1. AVL (classe `AVL`)
- `void inserir(int chave)`: API pública que delega para `inserir(NoAVL, int)`.
- `NoAVL inserir(NoAVL no, int chave)`: insere recursivamente seguind o BST; atualiza alturas; detecta desbalanceamentos e aplica rotações LL, RR, LR, RL.
- `boolean busca(int chave)`: busca binária recursiva.
- `void delete(int chave)`: API pública que delega para `delete(NoAVL, int)`.
- `NoAVL delete(NoAVL no, int chave)`: remove como em BST (0, 1 ou 2 filhos); em caso de 2 filhos, substitui pela menor chave da subárvore direita; atualiza alturas e reequilibra (casos simétricos aos de inserção, usando fator de balanceamento do nó e de seus filhos).
- `NoAVL menorNo(NoAVL no)`: retorna o nó com a menor chave em uma subárvore (vai à esquerda até o fim).
- `int altura(NoAVL)`, `void atualizaAltura(NoAVL)`, `int balanco(NoAVL)`: utilitários para manutenção da altura e fator de balanceamento.
- `NoAVL rotacaoDireita(NoAVL)`, `NoAVL rotacaoEsquerda(NoAVL)`: implementações das rotações, atualizando ligações e alturas.
- `void ordem()`: percurso em-ordem com impressão das chaves (crescente), com quebra de linha ao final.

### 3.2. Rubro-Negra (classe `RubroNegra`)
- `void inserir(int chave)`: cria nó vermelho, insere recursivamente mantendo ponteiros de pai, e chama `corrigirInsercao`.
- `NoRubroNegra inserirRec(NoRubroNegra atual, NoRubroNegra novo)`: BST recursivo com manutenção de `pai`.
- `void corrigirInsercao(NoRubroNegra no)`: aplica os casos 1–3 usando tio, avô, rotações e recolorações; garante raiz preta ao final.
- `void rotacaoEsquerda(NoRubroNegra)`, `void rotacaoDireita(NoRubroNegra)`: atualizam apontadores pai/filhos e, se necessário, a `raiz`.
- `boolean buscar(int chave)`, `boolean buscarRec(...)`: busca binária recursiva.
- `void ordem()`, `void ordemRec(...)`: percurso em ordem imprimindo `chave` seguida de `(R)` ou `(P)` para vermelho/preto.
- Remoção: não implementada; o menu reporta a limitação ao usuário.

## 4. Principais Desafios e Soluções Adotadas
- Manter consistência da altura (AVL): solução - sempre atualizar alturas após alterações e antes de avaliar rotações.
- Escolha correta de rotação (AVL): solução - usar fator de balanceamento do nó e comparar a chave com a do filho para decidir LL/RR/LR/RL.
- Ciclo infinito no print da arvore (AVL): erro lógico nas condições de rotação, inserção e remoção lidam com ponteiros nulos para os filhos: solução - todas as condições de rotação consideram que os filhos existem antes de acessar a chave
- Manutenção de ponteiros `pai` e casos de correção (Rubro-Negra): solução - garantir atualização cuidadosa de `pai` nas rotações e considerar caso simétrico (quando o pai é filho direito do avô).
- Decisão de escopo: remoção na Rubro-Negra pode ser complexa e não foi incluída; a aplicação informa essa restrição no menu.

## 5. Testes Realizados e Resultados Observados

### 5.1. AVL
- Inserções: sequência 30, 20, 40, 10, 25, 50, 5
  - Esperado: reequilíbrio com rotações LL/LR conforme necessário.
  - Resultado: `ordem()` imprimiu `5 10 20 25 30 40 50`.
- Remoção de nó com dois filhos: remover 20
  - Esperado: substituição pelo sucessor (25) e reequilíbrio.
  - Resultado: inorder `5 10 25 30 40 50`.
- Busca: presentes (30, 25) retornam true; ausentes (99) retornam false.

### 5.2. Rubro-Negra
- Inserções: sequência 10, 5, 15, 1, 7, 12, 18
  - Esperado: correções mantêm propriedades; `ordem()` imprime chaves com marcação de cor, por exemplo `1(P) 5(R) 7(P) 10(P) 12(P) 15(R) 18(P)` (as cores podem variar conforme rotações/recolorações, mas invariantes são mantidas).
- Busca: presentes (7, 12) retornam true; ausentes (99) retornam false.
- Remoção: não aplicável (não implementada).

## 6. Observações de Uso
- O `Main` oferece menus separados para AVL e Rubro-Negra, com operações de Inserir, Remover/Buscar/Imprimir para AVL e Inserir/Buscar/Imprimir para Rubro-Negra. A remoção na Rubro-Negra informa que não está implementada.

## 7. Conclusão
O projeto fornece uma implementação funcional de AVL (com inserção e remoção balanceadas) e de Rubro-Negra (inserção balanceada), com interface de console para testes manuais. Documentação adicional foi criada/atualizada para facilitar compilação, execução e entendimento do código.
