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
    public void remover(int chave) {
        NoRubroNegra z = buscarNo(raiz, chave);
        if (z == null) {
            System.out.println("Elemento não encontrado!");
            return;
        }

        NoRubroNegra y = z;
        boolean yCorOriginal = y.cor;
        NoRubroNegra x;

        NoRubroNegra filho;

        if (z.esquerda == null) {
            x = z.direita;
            transplantar(z, z.direita);
            filho = z.direita;
        } else if (z.direita == null) {
            x = z.esquerda;
            transplantar(z, z.esquerda);
            filho = z.esquerda;
        } else {
            y = minimo(z.direita);
            yCorOriginal = y.cor;
            x = y.direita;
            if (y.pai == z) {
                if (x != null) x.pai = y;
            } else {
                transplantar(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplantar(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
            filho = x;
        }

        if (yCorOriginal == PRETO) {
            corrigirRemocao(filho, z.pai);
        }
    }

    private void corrigirRemocao(NoRubroNegra x, NoRubroNegra pai) {
        while (x != raiz && (x == null || x.cor == PRETO)) {
            if (x == (pai != null ? pai.esquerda : null)) {
                NoRubroNegra w = (pai != null ? pai.direita : null);
                if (w != null && w.cor == VERMELHO) {
                    w.cor = PRETO;
                    pai.cor = VERMELHO;
                    rotacaoEsquerda(pai);
                    w = pai.direita;
                }
                if ((w == null) ||
                        ((w.esquerda == null || w.esquerda.cor == PRETO) &&
                                (w.direita == null || w.direita.cor == PRETO))) {
                    if (w != null) w.cor = VERMELHO;
                    x = pai;
                    pai = (x != null ? x.pai : null);
                } else {
                    if (w.direita == null || w.direita.cor == PRETO) {
                        if (w.esquerda != null) w.esquerda.cor = PRETO;
                        w.cor = VERMELHO;
                        rotacaoDireita(w);
                        w = pai.direita;
                    }
                    w.cor = pai.cor;
                    pai.cor = PRETO;
                    if (w.direita != null) w.direita.cor = PRETO;
                    rotacaoEsquerda(pai);
                    x = raiz;
                }
            } else {
                NoRubroNegra w = (pai != null ? pai.esquerda : null);
                if (w != null && w.cor == VERMELHO) {
                    w.cor = PRETO;
                    pai.cor = VERMELHO;
                    rotacaoDireita(pai);
                    w = pai.esquerda;
                }
                if ((w == null) ||
                        ((w.direita == null || w.direita.cor == PRETO) &&
                                (w.esquerda == null || w.esquerda.cor == PRETO))) {
                    if (w != null) w.cor = VERMELHO;
                    x = pai;
                    pai = (x != null ? x.pai : null);
                } else {
                    if (w.esquerda == null || w.esquerda.cor == PRETO) {
                        if (w.direita != null) w.direita.cor = PRETO;
                        w.cor = VERMELHO;
                        rotacaoEsquerda(w);
                        w = pai.esquerda;
                    }
                    w.cor = pai.cor;
                    pai.cor = PRETO;
                    if (w.esquerda != null) w.esquerda.cor = PRETO;
                    rotacaoDireita(pai);
                    x = raiz;
                }
            }
        }
        if (x != null) x.cor = PRETO;
    }

    private void transplantar(NoRubroNegra u, NoRubroNegra v) {
        if (u.pai == null) raiz = v;
        else if (u == u.pai.esquerda) u.pai.esquerda = v;
        else u.pai.direita = v;
        if (v != null) v.pai = u.pai;
    }

    private NoRubroNegra minimo(NoRubroNegra no) {
        while (no.esquerda != null) no = no.esquerda;
        return no;
    }

    private NoRubroNegra buscarNo(NoRubroNegra no, int chave) {
        if (no == null) return null;
        if (chave == no.chave) return no;
        if (chave < no.chave) return buscarNo(no.esquerda, chave);
        else return buscarNo(no.direita, chave);
    }
}




