package service;

public abstract class Carta {
    protected String texto;
    public Carta(String texto) { this.texto = texto; }
    public String getTexto() { return texto; }
    public abstract void aplicar(Jogador j, ConfiguracaoJogo cfg, Jogo jogo);
}
