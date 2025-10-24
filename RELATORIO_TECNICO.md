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

A AVL é uma árvore binária de busca que garante que, após qualquer operação de inserção ou remoção, a estrutura permaneça balanceada. O critério de balanceamento é simples: **o fator de balanceamento de cada nó** (diferença entre as alturas das subárvores esquerda e direita) deve sempre estar entre -1 e +1. Esse controle evita que a árvore se degrade em uma lista ligada, mantendo assim a complexidade das operações em \(O(\log n)\).

#### Detecção de Desbalanceamento
Após qualquer modificação, a árvore verifica se algum nó ficou desbalanceado. Isso é feito recalculando o fator de balanceamento durante o retorno da recursão das operações. Se for detectado um fator de balanceamento fora dos limites permitidos, é aplicado um procedimento de reequilíbrio.

#### Tipos de Rotações Utilizadas

- **Rotação Simples à Direita (caso LL):**
  Ocorre quando o nó ficou "pesado" à esquerda, e a chave causadora da desbalanceamento foi inserida na subárvore esquerda-esquerda do nó desbalanceado. Exemplo: Inserção à esquerda do filho esquerdo.  
  Solução: uma rotação simples à direita sobre o nó desbalanceado restabelece o equilíbrio.

- **Rotação Simples à Esquerda (caso RR):**
  Situação simétrica ao caso LL, mas para o lado direito: o nó ficou "pesado" à direita devido a inserção na subárvore direita-direita.  
  Solução: rotação simples à esquerda sobre o nó desbalanceado.

- **Rotação Dupla Esquerda-Direita (caso LR):**
  Ocorre quando a subárvore esquerda tem um desequilíbrio à direita (inserção ocorrida à direita do filho esquerdo).  
  Solução: primeiro aplica-se uma rotação à esquerda no filho esquerdo, seguida de uma rotação à direita no nó desbalanceado.

- **Rotação Dupla Direita-Esquerda (caso RL):**
  Ocorre quando a subárvore direita está desbalanceada à esquerda (inserção à esquerda do filho direito).  
  Solução: executa-se uma rotação à direita no filho direito, seguida de uma rotação à esquerda no nó desbalanceado.

#### Atualização e Propagação das Alturas

Toda vez que ocorre uma inserção ou remoção, a altura de cada nó afetado é recalculada de baixo para cima recursivamente. Após qualquer rotação, as alturas dos nós envolvidos também são imediatamente ajustadas, garantindo consistência do fator de balanceamento em toda a subárvore afetada.

Essas estratégias garantem que a árvore AVL automaticamente se reequilibre após cada modificação, mantendo suas operações rápidas e eficientes mesmo após grande número de inserções e deleções.


### 2.2. Rubro-Negra
- Mantém as propriedades clássicas:
  1) Todo nó é vermelho ou preto; 2) Raiz é preta; 3) Folhas nulas são pretas; 4) Nó vermelho não pode ter filho vermelho; 5) Todo caminho da raiz a folha nula contém o mesmo número de nós pretos (altura negra).
- Implementada correção apenas para inserção (remoção não implementada no projeto atual).
- Casos de correção após inserir um nó vermelho (com ponteiros de pai e tio):
  - Caso 1 (tio vermelho): recoloração de pai e tio para preto, avô para vermelho, e sobe o foco para o avô.
  - Caso 2 (tio preto e nó em “zig-zag”): rotação (esquerda ou direita) para converter em caso 3.
  - Caso 3 (tio preto e nó em “linha”): recoloração de pai para preto, avô para vermelho, e rotação oposta no avô.
- Ao final, a raiz é sempre pintada de preto.

## 3. Descrição Detalhada das Funções Implementadas

### 3.1. Classe `AVL`
A classe `AVL` encapsula todas as operações de uma árvore binária de busca balanceada utilizando o algoritmo AVL, garantindo alta eficiência e manutenção automática do equilíbrio estrutural. Abaixo está uma explanação detalhada das principais funções implementadas:

- **`void inserir(int chave)`**  
  Método público para inserir uma nova chave. Internamente, chama `inserir(NoAVL no, int chave)`, que realiza a busca recursiva pelo local de inserção correto, atualiza alturas dos nós e detecta desbalanceamentos, aplicando rotações de acordo com os casos LL (Left-Left), RR (Right-Right), LR (Left-Right) e RL (Right-Left).

- **`NoAVL inserir(NoAVL no, int chave)`**  
  Realiza a inserção recursiva em conformidade com as regras de árvore binária de busca. Após adicionar o novo nó, recalcula a altura do nó pai, determina o fator de balanceamento e executa rotações se necessário, restaurando o equilíbrio da árvore AVL.

- **`boolean busca(int chave)`**  
  Implementa a busca binária recursiva por uma chave específica na árvore. Percorre os nós até encontrar o valor procurado ou determinar sua ausência.

- **`void delete(int chave)`**  
  Método público para remover uma chave da árvore. Delegada à função recursiva `delete(NoAVL no, int chave)`, que realiza a exclusão conforme o padrão BST e também reequilibra a árvore conforme o fator de balanceamento.

- **`NoAVL delete(NoAVL no, int chave)`**  
  Lida com a remoção propriamente dita:
    - Se o nó possui dois filhos, substitui sua chave pela menor chave da subárvore direita e remove esse nó posteriormente.
    - Após a remoção, atualiza alturas e reequilibra a árvore, aplicando rotações conforme o fator de balanceamento do nó e de seus filhos, mantendo a propriedade AVL.

- **`NoAVL menorNo(NoAVL no)`**  
  Localiza e retorna o nó com a menor chave dentro de uma subárvore, utilizado principalmente na etapa de remoção quando o nó tem dois filhos.

- **`int altura(NoAVL no)`**, **`void atualizaAltura(NoAVL no)`**, **`int balanco(NoAVL no)`**  
  Funções utilitárias para calcular e manter os atributos de altura e o fator de balanceamento dos nós, essenciais para detectar e corrigir desbalanceamentos por meio de rotações.

- **`NoAVL rotacaoDireita(NoAVL y)`**, **`NoAVL rotacaoEsquerda(NoAVL x)`**  
  Implementam as rotações simples necessárias para reequilibrar a árvore:
    - Rotação à direita corrige excesso de altura à esquerda.
    - Rotação à esquerda corrige excesso de altura à direita.  
      Ambas ajustam corretamente as ligações entre nós afetados e atualizam suas alturas.

- **`void ordem()`**  
  Realiza a travessia em ordem (in-order traversal), imprimindo as chaves dos nós em ordem crescente e finalizando com uma quebra de linha para facilitar a leitura.

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
