package menus;

import service.ConfiguracaoJogo;
import service.Casa;

import java.util.Scanner;

public class MainMenu implements Menu {
    private ConfiguracaoJogo cfg;
    private Scanner sc = new Scanner(System.in);

    public MainMenu(ConfiguracaoJogo cfg) {
        this.cfg = cfg;
    }

    private void montarCasasPadrao() {
        var casas = cfg.casas;
        casas.clear();

        casas.add(new Casa(Casa.Tipo.INICIO, "Início", 0, 0));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Torres", 3000, 300));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Apartamento de Balneario Camboriu", 7000, 1000));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Estrela", 3500, 350));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Lajeado", 2900, 290));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Porto Alegre", 2700, 350));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Arroio do Sal", 2500, 250));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Capão da Canoa", 2000, 200));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Imbé", 2300, 230));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Xangri-lá", 2700, 270));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa de Osorio", 2800, 280));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Casa do Bosque", 2000, 200));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Apartamento Central", 3500, 180));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Vila das Flores", 4000, 220));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Pousada da Praia", 5000, 270));
        casas.add(new Casa(Casa.Tipo.IMOVEL, "Residência do Lago", 4500, 250));
        casas.add(new Casa(Casa.Tipo.PRISAO, "Prisão", 0, 0));
        casas.add(new Casa(Casa.Tipo.SORTE_REVES, "Sorte/Reveses", 0, 0));
        casas.add(new Casa(Casa.Tipo.IMPOSTO, "Imposto", 0, 0));
        casas.add(new Casa(Casa.Tipo.RESTITUICAO, "Restituição", 0, 0));
        // Se quiser, adicione mais imóveis até chegar a pelo menos 10 casas
    }

    public void executar() {
        int op;
        do {
            exibir();
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> new JogadorMenu(cfg).executar();
                case 2 -> new ImovelMenu(cfg).executar();
                case 3 -> new ConfiguracaoMenu(cfg).executar();
                case 4 -> {
                    montarCasasPadrao();
                    if (!cfg.podeIniciar()) {
                        System.out.println("Erro: necessário ≥2 jogadores e ≥10 imóveis.");
                        sc.nextLine();
                    } else {
                        System.out.println("Iniciando o jogo...");
                        new JogoMenu(cfg).executar();
                    }
                }
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Inválido!");
            }
        } while (op != 0);
    }

    public void exibir() {
        System.out.println("\n=== SIMULADOR DE JOGO DE BANCO IMOBILIÁRIO ===");
        System.out.println("1. Gerenciar Jogadores");
        System.out.println("2. Gerenciar Imóveis");
        System.out.println("3. Definir Configurações da Partida");
        System.out.println("4. Iniciar Jogo");
        System.out.println("0. Sair");
        System.out.print(">> ");
    }
}

