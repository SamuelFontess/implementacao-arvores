import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o tipo de árvore para operar:");
        System.out.println("1. Árvore AVL");
        System.out.println("2. Árvore Rubro-Negra");
        System.out.print("Digite sua escolha (1 ou 2): ");
        int treeType = scanner.nextInt();

        if (treeType == 1) {
            AVL avl = new AVL();
            operacoesAVL(scanner, avl);
        } else if (treeType == 2) {
            // rubro negra

        } else {
            System.out.println("Escolha inválida. Encerrando o programa.");
        }

        scanner.close();
    }

    private static void operacoesAVL(Scanner scanner, AVL arvore) {
        int escolha;
        do {
            System.out.println("\nOperações da Árvore AVL:");
            System.out.println("1. Inserir elemento");
            System.out.println("2. Remover elemento");
            System.out.println("3. Buscar elemento");
            System.out.println("4. Imprimir (Inorder)");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Digite sua escolha: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.print("Digite o elemento a inserir: ");
                    int inserirChave = scanner.nextInt();
                    arvore.inserir(inserirChave);
                    System.out.println("Elemento " + inserirChave + " inserido.");
                    break;
                case 2:
                    System.out.print("Digite o elemento a remover: ");
                    int deletarChave = scanner.nextInt();
                    arvore.delete(deletarChave);
                    System.out.println("Elemento " + deletarChave + " removido (se existir).");
                    break;
                case 3:
                    System.out.print("Digite o elemento a buscar: ");
                    int buscarChave = scanner.nextInt();
                    if (arvore.busca(buscarChave)) {
                        System.out.println("Elemento " + buscarChave + " encontrado na árvore.");
                    } else {
                        System.out.println("Elemento " + buscarChave + " NÃO encontrado na árvore.");
                    }
                    break;
                case 4:
                    System.out.print("Elementos na árvore (Inorder): ");
                    arvore.ordem();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
            }
        } while (arvore != 0);
    }

    private static void operacoesrubroNegra(Scanner scanner, rubroNegra arvore) {
        int escolha;
        do {
            System.out.println("\nOperações da Árvore Rubro-Negra:");
            System.out.println("1. Inserir elemento");
            System.out.println("2. Remover elemento");
            System.out.println("3. Buscar elemento");
            System.out.println("4. Imprimir (Inorder)");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Digite sua escolha: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.print("Digite o elemento a inserir: ");
                    int inserirChave = scanner.nextInt();
                    arvore.inserir(inserirChave);
                    System.out.println("Elemento " + inserirChave + " inserido.");
                    break;
                case 2:
                    System.out.println("Remoção não implementada para Árvore Rubro-Negra neste momento.");
                    break;
                case 3:
                    System.out.print("Digite o elemento a buscar: ");
                    int buscarChave = scanner.nextInt();
                    if (arvore.buscar(buscarChave)) {
                        System.out.println("Elemento " + buscarChave + " encontrado na árvore.");
                    } else {
                        System.out.println("Elemento " + buscarChave + " NÃO encontrado na árvore.");
                    }
                    break;
                case 4:
                    System.out.print("Elementos na árvore (Inorder): ");
                    arvore.ordem();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }
 }
