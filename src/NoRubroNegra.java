public class NoRubroNegra {
    int chave;
    NoRubroNegra esquerda, direita, pai;
    boolean cor; 

    NoRubroNegra(int chave) {
        this.chave = chave;
        this.cor = true;
    }
}

