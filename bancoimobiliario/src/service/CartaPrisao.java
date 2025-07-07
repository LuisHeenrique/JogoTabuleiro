package service;

public class CartaPrisao extends Carta {
    public CartaPrisao(String texto) { super(texto); }
    @Override
    public void aplicar(Jogador j, ConfiguracaoJogo cfg, Jogo jogo) {
        j.enviarPrisao();
    }
}
