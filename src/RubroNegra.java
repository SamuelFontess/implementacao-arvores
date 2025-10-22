public class RubroNegra {
    private NoRubroNegra raiz;
    private final boolean VERMELHO = true;
    private final boolean PRETO = false;

    public void inserir(int chave) {
        NoRubroNegra novo = new NoRubroNegra(chave);
        raiz = inserirRec(raiz, novo);
        corrigirInsercao(novo);
    }

    private NoRubroNegra inserirRec(NoRubroNegra atual, NoRubroNegra novo) {
        if (atual == null) {
            return novo;
        }
        if (novo.chave < atual.chave) {
            atual.esquerda = inserirRec(atual.esquerda, novo);
            atual.esquerda.pai = atual;
        } else if (novo.chave > atual.chave) {
            atual.direita = inserirRec(atual.direita, novo);
            atual.direita.pai = atual;
        }
        return atual;
    }

    private void corrigirInsercao(NoRubroNegra no) {
        while (no != raiz && no.pai.cor == VERMELHO) {
            if (no.pai == no.pai.pai.esquerda) {
                NoRubroNegra tio = no.pai.pai.direita;
                if (tio != null && tio.cor == VERMELHO) {
                    // caso 1: pai e tio vermelhos
                    no.pai.cor = PRETO;
                    tio.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    no = no.pai.pai;
                } else {
                    if (no == no.pai.direita) {
                        // caso 2: rotação esquerda
                        no = no.pai;
                        rotacaoEsquerda(no);
                    }
                    // caso 3: rotação direita
                    no.pai.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    rotacaoDireita(no.pai.pai);
                }
            } else {
                NoRubroNegra tio = no.pai.pai.esquerda;
                if (tio != null && tio.cor == VERMELHO) {
                    no.pai.cor = PRETO;
                    tio.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    no = no.pai.pai;
                } else {
                    if (no == no.pai.esquerda) {
                        no = no.pai;
                        rotacaoDireita(no);
                    }
                    no.pai.cor = PRETO;
                    no.pai.pai.cor = VERMELHO;
                    rotacaoEsquerda(no.pai.pai);
                }
            }
        }
        raiz.cor = PRETO;
    }

    private void rotacaoEsquerda(NoRubroNegra x) {
        NoRubroNegra y = x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != null) y.esquerda.pai = x;
        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }
        y.esquerda = x;
        x.pai = y;
    }

    private void rotacaoDireita(NoRubroNegra y) {
        NoRubroNegra x = y.esquerda;
        y.esquerda = x.direita;
        if (x.direita != null) x.direita.pai = y;
        x.pai = y.pai;
        if (y.pai == null) {
            raiz = x;
        } else if (y == y.pai.direita) {
            y.pai.direita = x;
        } else {
            y.pai.esquerda = x;
        }
        x.direita = y;
        y.pai = x;
    }

    public boolean buscar(int chave) {
        return buscarRec(raiz, chave);
    }

    private boolean buscarRec(NoRubroNegra no, int chave) {
        if (no == null) return false;
        if (chave == no.chave) return true;
        if (chave < no.chave) return buscarRec(no.esquerda, chave);
        else return buscarRec(no.direita, chave);
    }

    public void ordem() {
        ordemRec(raiz);
        System.out.println();
    }

    private void ordemRec(NoRubroNegra no) {
        if (no != null) {
            ordemRec(no.esquerda);
            System.out.print(no.chave + (no.cor ? "(R) " : "(P) "));
            ordemRec(no.direita);
        }
    }
}
