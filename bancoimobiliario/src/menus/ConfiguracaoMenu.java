package menus;

import service.ConfiguracaoJogo;
import java.util.Scanner;

public class ConfiguracaoMenu implements Menu {
    private ConfiguracaoJogo cfg;
    private Scanner sc = new Scanner(System.in);

    public ConfiguracaoMenu(ConfiguracaoJogo cfg) { this.cfg = cfg; }

    public void exibir() {
        System.out.println("\n--- Configurações ---");
        System.out.printf("1. Saldo inicial (%.2f)%n", cfg.saldoInicial);
        System.out.printf("2. Salário по volta (%.2f)%n", cfg.salarioPorVolta);
        System.out.printf("3. Nº de rodadas (%d)%n", cfg.maxRodadas);
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
                    System.out.print("Novo saldo inicial: ");
                    cfg.saldoInicial = sc.nextDouble(); sc.nextLine();
                    System.out.println("Atualizado!");
                }
                case 2 -> {
                    System.out.print("Novo salário por volta: ");
                    cfg.salarioPorVolta = sc.nextDouble(); sc.nextLine();
                    System.out.println("Atualizado!");
                }
                case 3 -> {
                    System.out.print("Máx rodadas: ");
                    cfg.maxRodadas = sc.nextInt(); sc.nextLine();
                    System.out.println("Atualizado!");
                }
                case 4 -> { return; }
                default -> System.out.println("Inválido!");
            }
        } while (true);
    }
}
