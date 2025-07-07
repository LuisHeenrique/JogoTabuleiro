package menus;

import service.ConfiguracaoJogo;
import service.Casa;
import java.util.Scanner;

public class ImovelMenu implements Menu {
    private ConfiguracaoJogo cfg;
    private Scanner sc = new Scanner(System.in);

    public ImovelMenu(ConfiguracaoJogo cfg) { this.cfg = cfg; }

    public void exibir() {
        System.out.printf("\n--- Imóveis (%d/40) --- %n", cfg.casas.size());
        System.out.println("1. Adicionar");
        System.out.println("2. Listar");
        System.out.println("3. Remover");
        System.out.println("4. Voltar");
        System.out.print(">> ");
    }

    public void executar() {
        int op;
        do {
            exibir();
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> {
                    if (cfg.casas.size() < 40) {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Preço: ");
                        double p = sc.nextDouble();
                        System.out.print("Aluguel: ");
                        double a = sc.nextDouble(); sc.nextLine();
                        cfg.casas.add(new Casa(Casa.Tipo.IMOVEL, nome, p, a));
                        System.out.println("Imóvel '" + nome + "' adicionado!");
                    } else System.out.println("Limite atingido.");
                }
                case 2 -> {
                    for (Casa c : cfg.casas) {
                        System.out.printf("- %s (R$ %.2f | aluguel R$ %.2f)%n",
                                c.getNome(), c.getPreco(), c.getAluguel());
                    }
                }
                case 3 -> {
                    System.out.print("Índice para remover: ");
                    int idx = sc.nextInt(); sc.nextLine();
                    if (idx >= 1 && idx <= cfg.casas.size()) {
                        System.out.println("Removido: " + cfg.casas.remove(idx - 1).getNome());
                    } else System.out.println("Índice inválido.");
                }
                case 4 -> { return; }
                default -> System.out.println("Inválido!");
            }
        } while (true);
    }
}
