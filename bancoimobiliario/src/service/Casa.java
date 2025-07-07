package service;

public class Casa {
    public boolean isHipotecada() {
        return false;
    }

    public void setHipotecada(boolean b) {
    }

    public enum Tipo { INICIO, IMOVEL, IMPOSTO, RESTITUICAO, PRISAO, SORTE_REVES }
    private Tipo tipo;
    private String nome;
    private double preco, aluguel;
    private Casa proxima;
    private Jogador dono;

    public Casa(Tipo tipo, String nome, double preco, double aluguel) {
        this.tipo = tipo; this.nome = nome;
        this.preco = preco; this.aluguel = aluguel;
    }

    public Tipo getTipo() { return tipo; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public double getAluguel() { return aluguel; }
    public Casa getProxima() { return proxima; }
    public void setProxima(Casa p) { this.proxima = p; }
    public Jogador getDono() { return dono; }
    public void setDono(Jogador d) { this.dono = d; }
}

