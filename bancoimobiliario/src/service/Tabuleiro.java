package service;

public class Tabuleiro {
    private Casa inicio;

    public void adicionarCasa(Casa nova) {
        if (inicio == null) {
            inicio = nova;
            inicio.setProxima(inicio);
        } else {
            Casa atual = inicio;
            while (atual.getProxima() != inicio) atual = atual.getProxima();
            atual.setProxima(nova);
            nova.setProxima(inicio);
        }
    }

    public Casa getInicio() { return inicio; }
}
