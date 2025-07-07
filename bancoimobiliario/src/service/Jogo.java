package service;

import java.util.List;
import java.util.Random;

public class Jogo {
    private ConfiguracaoJogo cfg;
    private Tabuleiro tabuleiro;
    private Random random = new Random();
    private int rodadaAtual = 1;

    public Jogo(ConfiguracaoJogo cfg) {
        this.cfg = cfg;
        this.tabuleiro = new Tabuleiro();
        cfg.casas.forEach(tabuleiro::adicionarCasa);
        cfg.jogadores.forEach(j -> j.setPosicaoAtual(tabuleiro.getInicio()));
    }

    public void iniciar() {
        while (rodadaAtual <= cfg.maxRodadas &&
                cfg.jogadores.stream().filter(j -> j.getSaldo() > 0).count() > 1) {

            for (Jogador j : cfg.jogadores) {
                if (j.getSaldo() <= 0) continue;
                turno(j);
                if (cfg.jogadores.stream().filter(x -> x.getSaldo() > 0).count() <= 1) break;
            }
            rodadaAtual++;
        }
    }

    public void turno(Jogador j) {
        if (j.isNaPrisao()) {
            int tentativa = j.getTurnosPrisao() + 1;
            int d1 = random.nextInt(6) + 1;
            int d2 = random.nextInt(6) + 1;
            System.out.printf("Prisão tentativa %d: %d e %d%n", tentativa, d1, d2);

            if (d1 == d2) {
                System.out.println("Saiu da prisão com duplos.");
                j.incrementaPrisao();
                mover(j, d1 + d2);
            } else {
                j.incrementaPrisao();
                if (!j.isNaPrisao()) {
                    System.out.println("Saiu da prisão, joga no próximo turno.");
                }
            }
            return;
        }

        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        mover(j, d1 + d2);
    }

    private void mover(Jogador j, int soma) {
        System.out.printf("%s rolou total: %d%n", j.getNome(), soma);
        Casa atual = j.getPosicaoAtual();

        for (int i = 0; i < soma; i++) {
            atual = atual.getProxima();
            if (atual.getTipo() == Casa.Tipo.INICIO) {
                j.setSaldo(j.getSaldo() + cfg.salarioPorVolta);
                System.out.println("Passou no Início! Recebeu salário.");
            }
        }
        j.setPosicaoAtual(atual);
        aplicarAcao(j, atual);
    }

    private void aplicarAcao(Jogador j, Casa c) {
        System.out.println("Parou em: " + c.getNome());
        switch (c.getTipo()) {
            case IMOVEL:
                if (c.getDono() == null) {
                    System.out.printf("Imóvel disponível: %s por R$ %.2f%n", c.getNome(), c.getPreco());
                    j.setEsperandoDecisao(true);
                } else if (c.getDono() != j) {
                    pagarAluguel(j, c);
                }
                break;

            case IMPOSTO:
                double taxa = j.calcularPatrimonio() * 0.05;
                j.setSaldo(j.getSaldo() - taxa);
                System.out.printf("Pagou imposto de R$ %.2f%n", taxa);
                break;
            case RESTITUICAO:
                double rest = cfg.salarioPorVolta * 0.10;
                j.setSaldo(j.getSaldo() + rest);
                System.out.printf("Recebeu restituição de R$ %.2f%n", rest);
                break;
            case PRISAO:
                j.enviarPrisao();
                System.out.println(j.getNome() + " foi para a prisão!");
                break;
            case SORTE_REVES:
                Carta carta = Baralho.getInstance().comprarCarta();
                System.out.println("Carta: " + carta.getTexto());
                carta.aplicar(j, cfg, this);
                break;
            default:
                break;
        }
    }

    private void comprarImovel(Jogador j, Casa c) {
        if (j.getSaldo() >= c.getPreco()) {
            j.setSaldo(j.getSaldo() - c.getPreco());
            j.getPropriedades().add(c);
            c.setDono(j);
            System.out.printf("%s comprou %s por R$ %.2f%n", j.getNome(), c.getNome(), c.getPreco());
        } else {
            System.out.println("Saldo insuficiente para comprar " + c.getNome());
        }
    }

    private void pagarAluguel(Jogador j, Casa c) {
        double al = c.getAluguel();
        j.setSaldo(j.getSaldo() - al);
        c.getDono().setSaldo(c.getDono().getSaldo() + al);
        System.out.printf("%s pagou aluguel R$ %.2f para %s%n", j.getNome(), al, c.getDono().getNome());
    }

    public boolean comprarImovelOpcional(Jogador j) {
        Casa c = j.getPosicaoAtual();
        if (c.getDono() == null && j.getSaldo() >= c.getPreco()) {
            j.setSaldo(j.getSaldo() - c.getPreco());
            j.getPropriedades().add(c);
            c.setDono(j);
            return true;
        }
        return false;
    }

    public boolean executarNegociacao(Jogador proponente, Jogador alvo,
                                      List<Casa> propsOfertadas, List<Casa> propsRecebidas,
                                      double dinheiroOfertado, double dinheiroRecebido) {
        if (proponente.getSaldo() < dinheiroOfertado || alvo.getSaldo() < dinheiroRecebido) return false;
        if (!proponente.getPropriedades().containsAll(propsOfertadas)) return false;
        if (!alvo.getPropriedades().containsAll(propsRecebidas)) return false;

        proponente.setSaldo(proponente.getSaldo() - dinheiroOfertado + dinheiroRecebido);
        alvo.setSaldo(alvo.getSaldo() - dinheiroRecebido + dinheiroOfertado);

        propsOfertadas.forEach(p -> {
            proponente.getPropriedades().remove(p);
            alvo.getPropriedades().add(p);
            p.setDono(alvo);
        });
        propsRecebidas.forEach(p -> {
            alvo.getPropriedades().remove(p);
            proponente.getPropriedades().add(p);
            p.setDono(proponente);
        });
        return true;
    }

}
