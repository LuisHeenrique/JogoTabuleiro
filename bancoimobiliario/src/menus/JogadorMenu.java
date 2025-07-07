package menus;

import service.Casa;
import service.ConfiguracaoJogo;
import service.Jogador;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JogadorMenu implements Menu {
    private ConfiguracaoJogo cfg;
    private Scanner sc = new Scanner(System.in);

    public JogadorMenu(ConfiguracaoJogo cfg) { this.cfg = cfg; }

    public void exibir() {
        System.out.printf("\n--- Jogadores (%d/6) --- %n", cfg.jogadores.size());
        System.out.println("1. Adicionar");
        System.out.println("2. Listar");
        System.out.println("3. Remover");
        System.out.println("4. Hipotecar imóvel");
        System.out.println("5. Quitar hipoteca");
        System.out.println("0. Voltar");
        System.out.print(">> ");
    }

    public void executar() {
        int op;
        do {
            exibir();
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> {
                    if (cfg.jogadores.size() < 6) {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        cfg.jogadores.add(new Jogador(nome, cfg.saldoInicial, cfg.salarioPorVolta));
                        System.out.println("Jogador '" + nome + "' adicionado!");
                    } else System.out.println("Máximo atingido.");
                }
                case 2 -> cfg.jogadores.forEach(j -> System.out.println("- " + j.getNome()));
                case 3 -> {
                    System.out.print("Índice para remover: ");
                    int idx = sc.nextInt(); sc.nextLine();
                    if (idx >= 1 && idx <= cfg.jogadores.size()) {
                        System.out.println("Removido: " + cfg.jogadores.remove(idx - 1).getNome());
                    } else System.out.println("Índice inválido.");
                }
                case 4 -> hipotecarImovel();
                case 5 -> quitarHipoteca();
                case 0 -> { return; }
                default -> System.out.println("Inválido!");
            }
        } while (true);
    }

    private void hipotecarImovel() {
        Jogador j = selecionarJogador();
        var props = j.getPropriedades().stream()
                .filter(p -> !p.isHipotecada())
                .collect(Collectors.toList());
        if (props.isEmpty()) { System.out.println("Não há imóveis para hipoteca."); return; }
        System.out.println("Escolha imóvel para hipotecar:");
        for (int i = 0; i < props.size(); i++)
            System.out.printf("%d. %s (R$ %.2f)%n", i+1, props.get(i).getNome(), props.get(i).getPreco());
        int idx = sc.nextInt(); sc.nextLine();
        if (idx < 1 || idx > props.size()) return;
        Casa c = props.get(idx - 1);
        c.setHipotecada(true);
        double ganho = c.getPreco() * 0.5;
        j.setSaldo(j.getSaldo() + ganho);
        System.out.printf("Você recebeu R$ %.2f por hipotecar '%s'.%n", ganho, c.getNome());
    }

    private void quitarHipoteca() {
        Jogador j = selecionarJogador();
        var props = j.getPropriedades().stream()
                .filter(Casa::isHipotecada)
                .collect(Collectors.toList());
        if (props.isEmpty()) { System.out.println("Nenhum imóvel está hipotecado."); return; }
        System.out.println("Escolha imóvel para quitar:");
        for (int i = 0; i < props.size(); i++)
            System.out.printf("%d. %s (Custo: R$ %.2f)%n", i+1, props.get(i).getNome(), props.get(i).getPreco() * 0.55);
        int idx = sc.nextInt(); sc.nextLine();
        if (idx < 1 || idx > props.size()) return;
        Casa c = props.get(idx - 1);
        double custo = c.getPreco() * 0.55;
        if (j.getSaldo() >= custo) {
            c.setHipotecada(false);
            j.setSaldo(j.getSaldo() - custo);
            System.out.printf("Você pagou R$ %.2f e quitou '%s'.%n", custo, c.getNome());
        } else {
            System.out.println("Saldo insuficiente para quitar essa hipoteca.");
        }
    }

    private Jogador selecionarJogador() {
        System.out.println("Escolha um jogador:");
        for (int i = 0; i < cfg.jogadores.size(); i++)
            System.out.printf("%d. %s%n", i+1, cfg.jogadores.get(i).getNome());
        int idx = sc.nextInt(); sc.nextLine();
        return (idx >= 1 && idx <= cfg.jogadores.size())
                ? cfg.jogadores.get(idx - 1)
                : null;
    }




}
