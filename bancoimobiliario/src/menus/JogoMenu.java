package menus;

import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JogoMenu {
    private ConfiguracaoJogo cfg;
    private Jogo jogo;
    private Scanner sc = new Scanner(System.in);
    private int rodada = 1;

    public JogoMenu(ConfiguracaoJogo cfg) {
        this.cfg = cfg;
        this.jogo = new Jogo(cfg);
    }

    private void propNegociacaoAvancada(Jogador j) {
        List<Jogador> others = cfg.jogadores.stream()
                .filter(x -> x != j && x.getSaldo() > 0)
                .collect(Collectors.toList());
        if (others.isEmpty()) { System.out.println("Nenhum jogador disponível."); return; }

        System.out.println("Jogadores disponíveis:");
        for (int i = 0; i < others.size(); i++)
            System.out.printf("%d. %s%n", i + 1, others.get(i).getNome());
        System.out.print("Escolha jogador alvo (0 para voltar): ");
        int idx = sc.nextInt(); sc.nextLine();
        if (idx < 1 || idx > others.size()) return;
        Jogador alvo = others.get(idx - 1);

        System.out.print("Dinheiro que você oferece: ");
        double dinheiroOf = sc.nextDouble(); sc.nextLine();
        List<Casa> propsOf = selecionarPropriedades(j, "oferecer");

        System.out.print("Dinheiro que deseja receber: ");
        double dinheiroRec = sc.nextDouble(); sc.nextLine();
        List<Casa> propsRec = selecionarPropriedades(alvo, "receber");

        System.out.printf("Você oferece R$ %.2f + %d propriedades, quer R$ %.2f + %d propriedades.%n",
                dinheiroOf, propsOf.size(), dinheiroRec, propsRec.size());
        System.out.print("Alvo aceita? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            boolean ok = jogo.executarNegociacao(j, alvo, propsOf, propsRec, dinheiroOf, dinheiroRec);
            System.out.println(ok ? "Negociação realizada!" : "Negociação falhou.");
        } else {
            System.out.println("Negociação recusada.");
        }
    }

    private List<Casa> selecionarPropriedades(Jogador j, String acao) {
        List<Casa> props = new ArrayList<>();
        var list = j.getPropriedades();
        if (list.isEmpty()) return props;

        System.out.println("Selecione propriedades para " + acao + " (IDs separados por vírgula ou 0):");
        for (int i = 0; i < list.size(); i++)
            System.out.printf("%d. %s%n", i + 1, list.get(i).getNome());
        String[] ids = sc.nextLine().split(",");
        for (String s : ids) {
            try {
                int i = Integer.parseInt(s.trim());
                if (i > 0 && i <= list.size()) props.add(list.get(i-1));
            } catch (NumberFormatException ignored) {}
        }
        return props;
    }

    public void executar() {
        while (rodada <= cfg.maxRodadas &&
                cfg.jogadores.stream().filter(j -> j.getSaldo() > 0).count() > 1) {

            for (Jogador j : cfg.jogadores) {
                if (j.getSaldo() <= 0) continue;
                turno(j);
                if (cfg.jogadores.stream().filter(x -> x.getSaldo() > 0).count() <= 1) break;
            }
            rodada++;
        }
        fimDeJogo();
    }

    private void turno(Jogador j) {
        System.out.println("\n=========================================");
        System.out.printf("=== RODADA %d / %d - VEZ DE: %s ===%n",
                rodada, cfg.maxRodadas, j.getNome());
        System.out.printf("Posição: %s | Saldo: R$ %.2f%n",
                j.getPosicaoAtual().getNome(), j.getSaldo());

        int op;
        do {
            mostraMenuTurno();
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1 -> { jogo.turno(j);
// Bloco de decisão para compra de imóvel
                    if (j.isEsperandoDecisao()) {
                        System.out.println("--- Deseja comprar este imóvel? ---");
                        System.out.println("1. Sim    2. Não");
                        int r = sc.nextInt(); sc.nextLine();
                        if (r == 1) {
                            if (jogo.comprarImovelOpcional(j))
                                System.out.println("Imóvel comprado com sucesso!");
                            else
                                System.out.println("Não foi possível comprar (saldo insuficiente ou imóvel já foi adquirido).");
                        } else {
                            System.out.println("Você optou por não comprar.");
                        }
                        j.setEsperandoDecisao(false);
                    }

                    return;
                }
                case 2 -> mostrarStatus(j);
                case 3 -> gerenciarPropriedades(j);
                case 4 -> propNegociacaoAvancada(j);
                case 5 -> mostrarRanking();
                case 0 -> { j.setSaldo(0); System.out.println("Você desistiu."); return; }
                default -> System.out.println("Opção inválida!");
            }
        } while (true);
    }

    private void mostraMenuTurno() {
        System.out.println("\n--- O que deseja fazer? ---");
        System.out.println("1. Lançar Dados e Mover");
        System.out.println("2. Ver Meu Status");
        System.out.println("3. Gerenciar Propriedades");
        System.out.println("4. Propor Negociação");
        System.out.println("5. Ver Ranking");
        System.out.println("0. Desistir");
        System.out.print(">> ");
    }

    private void mostrarStatus(Jogador j) {
        System.out.printf("\n=== STATUS DE %s ===%n", j.getNome());
        System.out.printf("Saldo: R$ %.2f%n", j.getSaldo());
        System.out.println("Propriedades:");
        j.getPropriedades().forEach(c ->
                System.out.printf("- %s (R$ %.2f)%n", c.getNome(), c.getPreco()));
        System.out.println("Pressione Enter para continuar...");
        sc.nextLine();
    }

    private void gerenciarPropriedades(Jogador j) {
        var props = j.getPropriedades();
        if (props.isEmpty()) {
            System.out.println("Você não possui propriedades.");
        } else {
            System.out.println("\nSuas propriedades:");
            for (int i = 0; i < props.size(); i++)
                System.out.printf("%d. %s (R$ %.2f)%n", i + 1,
                        props.get(i).getNome(), props.get(i).getPreco());
            System.out.print("Escolha para hipotecar (0 p/ voltar): ");
            int idx = sc.nextInt(); sc.nextLine();
            if (idx >= 1 && idx <= props.size()) {
                Casa c = props.get(idx - 1);
                double valor = c.getPreco() * 0.5;
                j.setSaldo(j.getSaldo() + valor);
                props.remove(c);
                c.setDono(null);
                System.out.printf("Você hipotecou '%s' e recebeu R$ %.2f%n", c.getNome(), valor);
            }
        }
        System.out.println("Pressione Enter...");
        sc.nextLine();
    }

    private void proporNegociacao(Jogador j) {
        List<Jogador> others = cfg.jogadores.stream()
                .filter(x -> x != j && x.getSaldo() > 0)
                .toList();
        if (others.isEmpty()) {
            System.out.println("Nenhum jogador disponível.");
        } else {
            System.out.println("\nJogadores disponíveis:");
            for (int i = 0; i < others.size(); i++)
                System.out.printf("%d. %s%n", i + 1, others.get(i).getNome());
            System.out.print("Escolha jogador (0 p/ voltar): ");
            int idx = sc.nextInt(); sc.nextLine();
            if (idx >= 1 && idx <= others.size()) {
                Jogador o = others.get(idx - 1);
                System.out.print("Valor oferecido (R$): ");
                double valor = sc.nextDouble(); sc.nextLine();
                if (j.getSaldo() < valor) {
                    System.out.println("Saldo insuficiente.");
                } else if (o.getPropriedades().isEmpty()) {
                    System.out.println(o.getNome() + " não tem propriedades.");
                } else {
                    System.out.println("\nPropriedades de " + o.getNome() + ":");
                    var props = o.getPropriedades();
                    for (int i = 0; i < props.size(); i++)
                        System.out.printf("%d. %s%n", i + 1, props.get(i).getNome());
                    System.out.print("Escolha propriedade (0 p/ cancelar): ");
                    int pidx = sc.nextInt(); sc.nextLine();
                    if (pidx >= 1 && pidx <= props.size()) {
                        Casa c = props.get(pidx - 1);
                        System.out.printf("Negociar '%s' por R$ %.2f? (s/n): ", c.getNome(), valor);
                        if (sc.nextLine().equalsIgnoreCase("s")) {
                            j.setSaldo(j.getSaldo() - valor);
                            o.setSaldo(o.getSaldo() + valor);
                            o.getPropriedades().remove(c);
                            j.getPropriedades().add(c);
                            c.setDono(j);
                            System.out.println("Negociação concluída!");
                        } else {
                            System.out.println("Negociação cancelada.");
                        }
                    }
                }
            }
        }
        System.out.println("Pressione Enter...");
        sc.nextLine();
    }

    private void mostrarRanking() {
        RankingBST tree = new RankingBST();
        cfg.jogadores.forEach(tree::add);
        List<Jogador> ordenados = tree.toListDesc();

        System.out.println("\n--- RANKING DE JOGADORES ---");
        int pos = 1;
        for (Jogador j : ordenados) {
            System.out.printf("%d. %s - Patrimônio: R$ %.2f%s%n",
                    pos++, j.getNome(), j.calcularPatrimonio(),
                    j.getSaldo() <= 0 ? " (Falido)" : "");
        }
        System.out.println("-----------------------------");
    }


    private void fimDeJogo() {
        System.out.println("\n=========================================");
        System.out.println("========== FIM DE JOGO ==========");
        System.out.println("=========================================");
        mostrarRanking();

        System.out.println("\nO que deseja fazer?");
        System.out.println("1. Jogar Novamente (mesmas configurações)");
        System.out.println("2. Voltar ao Menu Principal");
        System.out.println("0. Encerrar Programa");
        System.out.print(">> ");
        int op = sc.nextInt(); sc.nextLine();

        switch (op) {
            case 1 -> {
                rodada = 1;             // Resetando rodada
                jogo = new Jogo(cfg);   // Nova instância do jogo
                executar();             // Recomeça o loop principal
            }
            case 2 -> new MainMenu(cfg).executar();
            default -> System.out.println("Obrigado por jogar!");
        }
    }

}
