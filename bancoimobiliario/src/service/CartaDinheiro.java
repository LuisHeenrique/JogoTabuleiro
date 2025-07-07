package service;

public class CartaDinheiro extends Carta {
    private double valor;
    public CartaDinheiro(String texto, double valor) {
        super(texto); this.valor = valor;
    }
    @Override
    public void aplicar(Jogador j, ConfiguracaoJogo cfg, Jogo jogo) {
        j.setSaldo(j.getSaldo() + valor);
    }
}
