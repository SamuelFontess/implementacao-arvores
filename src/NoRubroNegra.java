public class NoRubroNegra {
    int chave;
    NoRubroNegra esquerda, direita, pai;
    boolean cor; // true = vermelho, false = preto

    NoRubroNegra(int chave) {
        this.chave = chave;
        this.cor = true; // novos nós sempre iniciam como vermelhos
    }
}

