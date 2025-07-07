package service;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private double saldo;
    private double salario;
    private Casa posicaoAtual;
    private List<Casa> propriedades = new ArrayList<>();

    private boolean esperandoDecisao = false;
    public boolean isEsperandoDecisao() { return esperandoDecisao; }
    public void setEsperandoDecisao(boolean v) { esperandoDecisao = v; }
    private boolean estaNaPrisao = false;
    private int turnosPrisao = 0;
    private boolean livreNaProxima = false;

    public Jogador(String nome, double saldoInicial, double salarioPorVolta) {
        this.nome = nome; this.saldo = saldoInicial; this.salario = salarioPorVolta;
    }

    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double s) { saldo = s; }
    public double getSalario() { return salario; }
    public Casa getPosicaoAtual() { return posicaoAtual; }
    public void setPosicaoAtual(Casa c) { posicaoAtual = c; }
    public List<Casa> getPropriedades() { return propriedades; }

    public double calcularPatrimonio() {
        return saldo + propriedades.stream().mapToDouble(Casa::getPreco).sum();
    }

    public boolean isNaPrisao() { return estaNaPrisao; }
    public void enviarPrisao() { estaNaPrisao = true; turnosPrisao = 0; }
    public int getTurnosPrisao() { return turnosPrisao; }
    public void incrementaPrisao() {
        turnosPrisao++;
        if (turnosPrisao >= 3) { estaNaPrisao = false; livreNaProxima = true; }
    }
    public boolean isLivreNaProxima() { return livreNaProxima; }
    public void setLivreNaProxima(boolean v) { livreNaProxima = v; }
}

