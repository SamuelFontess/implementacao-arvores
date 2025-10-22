public class AVL {

    private NoAVL raiz;

    private int altura(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    private void atualizaAltura(NoAVL no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        }
    }

    private int balanco(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;public class AVL {

            private NoAVL raiz;

            private int altura(NoAVL no) {
                if (no == null) {
                    return 0;
                }
                return no.altura;
            }

            private void atualizaAltura(NoAVL no) {
                if (no != null) {
                    no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
                }
            }

            private int balanco(NoAVL no) {
                if (no == null) {
                    return 0;
                }
                return altura(no.esquerda) - altura(no.direita);
            }

            private NoAVL rotacaoDireita(NoAVL y) {
                NoAVL x = y.esquerda;
                NoAVL T2 = x.direita;

                x.direita = y;
                y.esquerda = T2;

                atualizaAltura(y);
                atualizaAltura(x);

                return x;
            }

            private NoAVL rotacaoEsquerda(NoAVL x) {
                NoAVL y = x.direita;
                NoAVL T2 = y.esquerda;

                y.direita = x;
                x.esquerda = T2;

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

                if (balanco > 1 && chave < no.esquerda.chave) {
                    return rotacaoDireita(no);
                }

                if (balanco < -1 && chave > no.direita.chave) {
                    return rotacaoEsquerda(no);
                }

                if (balanco > 1 && chave > no.direita.chave) {
                    no.esquerda = rotacaoEsquerda(no.esquerda);
                    return rotacaoDireita(no);
                }

                if (balanco < -1 && chave < no.direita.chave) {
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

                if (chave < no.chave) {
                    return busca(no.esquerda, chave);
                } else {
                    return busca(no.direita, chave);
                }
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

            private NoAVL delete(NoAVL raiz, int chave) {
                if (raiz == null) {
                    return raiz;
                }

                if (chave < raiz.chave) {
                    raiz.esquerda = delete(raiz.esquerda, chave);
                } else if (chave > raiz.chave) {
                    raiz.direita = delete(raiz.direita, chave);
                } else {
                    if ((raiz.esquerda == null) || (raiz.direita == null)) {
                        NoAVL temp = null;
                        if (temp == raiz.esquerda) {
                            temp = raiz.direita;
                        } else {
                            temp = raiz.esquerda;
                        }

                        if (temp == null) {
                            raiz = null;
                        } else {
                            raiz = temp;
                        }
                    } else {
                        NoAVL temp = menorNo(raiz.direita);

                        raiz.chave = temp.chave;

                        raiz.direita = delete(raiz.direita, temp.chave);
                    }
                }

                if (raiz == null) {
                    return raiz;
                }

                atualizaAltura(raiz);

                int balanco = balanco(raiz);

                if (balanco > 1 && balanco(raiz.esquerda) >= 0) {
                    return rotacaoDireita(raiz);
                }

                if (balanco > 1 && balanco(raiz.esquerda) < 0) {
                    raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
                    return rotacaoDireita(raiz);
                }

                if (balanco < -1 && balanco(raiz.direita) <= 0) {
                    return rotacaoEsquerda(raiz);
                }

                if (balanco < -1 && balanco(raiz.direita) > 0) {
                    raiz.direita = rotacaoDireita(raiz.direita);
                    return rotacaoEsquerda(raiz);
                }

                return raiz;
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

        NoAVL T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        atualizaAltura(y);
        atualizaAltura(x);

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;

        y.direita = x;
        x.esquerda = T2;

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

        if (balanco > 1 && chave < no.esquerda.chave) {
            return rotacaoDireita(no);
        }

        if (balanco < -1 && chave > no.direita.chave) {
            return rotacaoEsquerda(no);
        }

        if (balanco > 1 && chave > no.direita.chave) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanco < -1 && chave < no.direita.chave) {
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

        if (chave < no.chave) {
            return busca(no.esquerda, chave);
        } else {
            return busca(no.direita, chave);
        }
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

    private NoAVL delete(NoAVL raiz, int chave) {
        if (raiz == null) {
            return raiz;
        }

        if (chave < raiz.chave) {
            raiz.esquerda = delete(raiz.esquerda, chave);
        } else if (chave > raiz.chave) {
            raiz.direita = delete(raiz.direita, chave);
        } else {
            if ((raiz.esquerda == null) || (raiz.direita == null)) {
                NoAVL temp = null;
                if (temp == raiz.esquerda) {
                    temp = raiz.direita;
                } else {
                    temp = raiz.esquerda;
                }

                if (temp == null) {
                    raiz = null;
                } else {
                    raiz = temp;
                }
            } else {
                NoAVL temp = menorNo(raiz.direita);

                raiz.chave = temp.chave;

                raiz.direita = delete(raiz.direita, temp.chave);
            }
        }

        if (raiz == null) {
            return raiz;
        }

        atualizaAltura(raiz);

        int balanco = balanco(raiz);

        if (balanco > 1 && balanco(raiz.esquerda) >= 0) {
            return rotacaoDireita(raiz);
        }

        if (balanco > 1 && balanco(raiz.esquerda) < 0) {
            raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
            return rotacaoDireita(raiz);
        }

        if (balanco < -1 && balanco(raiz.direita) <= 0) {
            return rotacaoEsquerda(raiz);
        }

        if (balanco < -1 && balanco(raiz.direita) > 0) {
            raiz.direita = rotacaoDireita(raiz.direita);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
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
