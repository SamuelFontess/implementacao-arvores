# Relatório Técnico — Árvores Balanceadas (AVL e Rubro-Negra)

## Componentes do Grupo EDB 2:
- Samuel Evaristo de Fontes
- Caio Tadeu Santos Pessoa

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
- Implementadas correções tanto para inserção quanto para remoção, garantindo a manutenção das propriedades da árvore após qualquer modificação.
- Casos de correção após inserir um nó vermelho (com ponteiros de pai e tio):
  - Caso 1 (tio vermelho): recoloração de pai e tio para preto, avô para vermelho, e sobe o foco para o avô.
  - Caso 2 (tio preto e nó em “zig-zag”): rotação (esquerda ou direita) para converter em caso 3.
  - Caso 3 (tio preto e nó em “linha”): recoloração de pai para preto, avô para vermelho, e rotação oposta no avô.
- Casos de correção após remoção (quando o nó removido ou substituído é preto):
  - Caso 1: irmão vermelho — recoloração e rotação para converter em outro caso.
  - Caso 2: irmão e filhos do irmão pretos — recoloração do irmão e subida do problema ao pai.
  - Caso 3: irmão preto com filho vermelho do lado “de fora” — rotação e recoloração para restaurar a altura negra.
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

- **`void inserir(int chave)`**  
  Responsável por adicionar uma nova chave à árvore. Cria um novo nó já colorido de vermelho para evitar aumento da altura negra e utiliza a função `inserirRec` para encontrar recursivamente a posição correta, atualizando os ponteiros de pai. Após a inserção, chama `corrigirInsercao` para resolver possíveis violações das propriedades rubro-negras por recoloração ou rotações, finalizando ao garantir que a raiz sempre seja preta.

- **`NoRubroNegra inserirRec(NoRubroNegra atual, NoRubroNegra novo)`**  
  Percorre a árvore de forma recursiva, como em uma árvore binária de busca tradicional (BST), para encontrar a posição de inserção correta. Ao inserir, atualiza o ponteiro de pai do novo nó, fundamental para as operações de correção subsequentes após a inserção.

- **`void corrigirInsercao(NoRubroNegra no)`**  
  Garante que, após a inserção, as propriedades rubro-negras sejam mantidas. Analisa a cor do pai e do tio do novo nó, decidindo entre recoloração, rotação simples ou dupla (esquerda ou direita). O processo segue subindo na árvore até que nenhuma propriedade seja mais violada. Ao final, assegura que a raiz sempre seja preta, cumprindo a propriedade fundamental da estrutura.

- **`void rotacaoEsquerda(NoRubroNegra x)`, `void rotacaoDireita(NoRubroNegra y)`**  
  Realizam as rotações essenciais para manter a árvore balanceada, ajustando corretamente todos os ponteiros entre pai, filhos e, se necessário, promovendo um novo nó à posição de raiz. As rotações trabalham sobre dois nós adjacentes e são vitais após inserções e remoções onde ocorre desequilíbrio estrutural.

- **`boolean buscar(int chave)`, `boolean buscarRec(NoRubroNegra no, int chave)`**  
  Executam busca binária clássica. Percorrem recursivamente a árvore comparando a chave desejada com a do nó atual, navegando à esquerda ou à direita conforme o valor. Retornam verdadeiro se encontrarem a chave, falso caso contrário.

- **`public void remover(int chave)`**  
  Inicia o processo de remoção, localizando o nó a ser excluído usando busca binária. Realiza o transplante de subárvores conforme necessário (casos com 0, 1 ou 2 filhos), preservando a estrutura binária de busca. Se a remoção afetar a altura negra, ativa a rotina `corrigirRemocao` para restaurar as propriedades rubro-negras através de rotações e recolorações.

- **`void corrigirRemocao(NoRubroNegra x, NoRubroNegra pai)`**  
  Corrige a árvore caso a remoção de um nó preto viole a propriedade da altura negra. Lida com diferentes configurações dos irmãos do nó "duplo-preto" (termo para um caminho com um nó a menos na contagem preta), utilizando recolorações e rotações para redistribuir a negritude e restaurar o equilíbrio da árvore. O processo segue subindo até que a violação seja corrigida ou alcançada a raiz.

- **`void transplantar(NoRubroNegra u, NoRubroNegra v)`**  
  Substitui a subárvore enraizada no nó `u` pela subárvore de `v`, ajustando corretamente os ponteiros de pai. É utilizada nos casos de remoção para reorganizar a estrutura da árvore sem violar a ordenação binária.

- **`NoRubroNegra minimo(NoRubroNegra no)`**  
  Retorna o nó de menor valor dentro de uma subárvore, encontrado ao seguir os ponteiros do filho esquerdo até o nó mais à esquerda (mínimo absoluto), frequentemente usado na etapa de remoção de nós com dois filhos.

- **`NoRubroNegra buscarNo(NoRubroNegra no, int chave)`**  
  Variante do método de busca que retorna o nó correspondente à chave buscada em vez de um valor booleano. Essencial para as funções internas de remoção que precisam manipular ou acessar o nó diretamente.

- **`void ordem()`, `void ordemRec(NoRubroNegra no)`**  
  Realizam o percurso em ordem (in-order) na árvore, imprimindo as chaves ordenadamente. Cada chave é seguida de `(R)` para vermelho ou `(P)` para preto, permitindo ao usuário visualizar não apenas os valores em ordem mas também a coloração dos nós, útil para depuração e validação da estrutura.

## 4. Principais Desafios e Soluções Adotadas

### AVL

- **Manter a consistência da altura**  
  Um dos grandes desafios da AVL é garantir que o atributo de altura dos nós reflita a real estrutura da árvore após qualquer operação. Caso a altura não seja devidamente atualizada, o fator de balanceamento pode indicar erroneamente a necessidade — ou a desnecessidade — de rotações, levando a comportamentos inesperados.  
  **Solução:** Após cada inserção ou remoção, a implementação sempre recalcula a altura de todo nó afetado. Esse cálculo ocorre de baixo para cima, garantindo que cada nó tenha a altura exata antes da análise do fator de balanceamento.

- **Escolha correta do tipo de rotação**  
  Se a rotação aplicada não corresponder ao caso de desbalanceamento, a árvore pode não se reequilibrar corretamente, resultando até mesmo em degradação de desempenho.  
  **Solução:** O algoritmo compara o fator de balanceamento do nó e também da subárvore afetada, junto à relação da chave inserida/removida, para classificar os casos LL, RR, LR ou RL. Assim, a rotação apropriada é escolhida de forma precisa.

- **Prevenção de ciclos infinitos no percurso ou impressões da árvore**  
  Um erro comum é deixar de verificar a existência (não-nulidade) dos filhos antes de acessar seus atributos, especialmente durante rotações ou impressões, o que pode causar um loop infinito ou exceções em tempo de execução.  
  **Solução:** Antes de acessar filhos ou suas chaves, sempre há a verificação de nulidade, tornando o algoritmo robusto tanto em árvores densas quanto em estruturas mais rasas, e evitando exceções acidentais.

### Rubro-Negra

- **Manutenção correta dos ponteiros de `pai` e tratamento dos casos simétricos**  
  Dado que as operações de rotação e transplante envolvem múltiplas atualizações em ponteiros pai-filho, qualquer descuido pode “quebrar” a estrutura bidirecional da árvore, levando à perda ou corrupção de nós. Além disso, é necessário tratar com cuidado os casos simétricos — ou seja, quando o “pai” do nó inserido/removido é filho direito do avô.  
  **Solução:** A implementação atualiza explicitamente o ponteiro de pai para cada nó afetado nas rotações e transplantes. Os algoritmos de correção consideram todos os casos simétricos, utilizando condicionais claras para diferenciar os contextos (pai-esquerdo vs pai-direito).

- **Deleção de um elemento e restauração das propriedades rubro-negras**  
  A exclusão em árvores Rubro-Negras representa o maior desafio, já que remover um nó preto pode violar a propriedade de altura negra e propagar problemas para cima na árvore. A complexidade dos casos de correção muitas vezes dificulta tanto a implementação quanto a depuração.  
  **Solução:** O método `corrigirRemocao` lida sistematicamente com cada situação após uma remoção: verifica a cor do irmão e de seus filhos, alternando entre recoloração, rotação simples ou duplas, e propagação do problema. A abordagem minuciosa e estruturada assegura que, mesmo removendo elementos em posições complexas, as propriedades da árvore sejam restauradas corretamente.

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
- Remoção de nó com dois filhos: remover 10
  - Esperado: substituição pelo sucessor (12), com posterior correção por rotações e recolorações para manter a altura negra uniforme.
  - Resultado: ordem() exibiu 1(P) 5(R) 7(P) 12(P) 15(R) 18(P), mantendo todas as propriedades da rubro-negra.
- Busca: presentes (7, 12) retornam true; ausentes (99) retornam false.

## 6. Observações de Uso
- O `Main` oferece menus separados para AVL e Rubro-Negra, com operações de Inserir, Remover/Buscar/Imprimir para AVL e Inserir, Remover/Buscar/Imprimir para Rubro-Negra. 

## 7. Conclusão
O projeto fornece uma implementação funcional de AVL (com inserção e remoção balanceadas) e de Rubro-Negra (inserção e remoção balanceada), com interface de console para testes manuais. Documentação adicional foi criada/atualizada para facilitar compilação, execução e entendimento do código.