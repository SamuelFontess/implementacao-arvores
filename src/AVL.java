public class AVL {

    private NoAVL raiz;

    private int altura(NoAVL no) {
        return (no == null) ? 0 : no.altura;
    }

    private void atualizaAltura(NoAVL no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        }
    }

    private int balanco(NoAVL no) {
        return (no == null) ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = (x != null) ? x.direita : null;

        x.direita = y;
        y.esquerda = T2;

        atualizaAltura(y);
        atualizaAltura(x);

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = (y != null) ? y.esquerda : null;

        y.esquerda = x;
        x.direita = T2;

        atualizaAltura(x);
        atualizaAltura(y);

        return y;
    }

    public void inserir(int chave) {
        raiz = inserir(raiz, chave);
    }

    private NoAVL inserir(NoAVL no, int chave) {
        if (no == null) {
            return new NoAVL(chave);
        }

        if (chave < no.chave) {
            no.esquerda = inserir(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = inserir(no.direita, chave);
        } else {
            return no;
        }

        atualizaAltura(no);

        int balanco = balanco(no);

        // Esquerda-Esquerda (LL)
        if (balanco > 1 && no.esquerda != null && chave < no.esquerda.chave) {
            return rotacaoDireita(no);
        }

        // Direita-Direita (RR)
        if (balanco < -1 && no.direita != null && chave > no.direita.chave) {
            return rotacaoEsquerda(no);
        }

        // Esquerda-Direita (LR)
        if (balanco > 1 && no.esquerda != null && chave > no.esquerda.chave) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Direita-Esquerda (RL)
        if (balanco < -1 && no.direita != null && chave < no.direita.chave) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public boolean busca(int chave) {
        return busca(raiz, chave);
    }

    private boolean busca(NoAVL no, int chave) {
        if (no == null) {
            return false;
        }
        if (chave == no.chave) {
            return true;
        }
        return (chave < no.chave) ? busca(no.esquerda, chave) : busca(no.direita, chave);
    }

    private NoAVL menorNo(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }

    public void delete(int chave) {
        raiz = delete(raiz, chave);
    }

    private NoAVL delete(NoAVL no, int chave) {
        if (no == null) return no;

        if (chave < no.chave) {
            no.esquerda = delete(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = delete(no.direita, chave);
        } else {
            if (no.esquerda != null && no.direita != null) {
                NoAVL temp = menorNo(no.direita);
                no.chave = temp.chave;
                no.direita = delete(no.direita, temp.chave);
            } else if (no.esquerda != null) {
                no = no.esquerda;
            } else if (no.direita != null) {
                no = no.direita;
            } else {
                no = null;
            }
        }
        if (no == null) return no;

        atualizaAltura(no);
        int balanco = balanco(no);

        if (balanco > 1 && balanco(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }
        if (balanco > 1 && balanco(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        if (balanco < -1 && balanco(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }
        if (balanco < -1 && balanco(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }
        return no;
    }

    public void ordem() {
        ordem(raiz);
        System.out.println();
    }

    private void ordem(NoAVL no) {
        if (no != null) {
            ordem(no.esquerda);
            System.out.print(no.chave + " ");
            ordem(no.direita);
        }
    }
}