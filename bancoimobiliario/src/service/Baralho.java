package service;

import java.util.*;

public class Baralho {
    private Deque<Carta> deck = new ArrayDeque<>();
    private List<Carta> base = new ArrayList<>();
    private static Baralho inst;

    private Baralho() {
        base.add(new CartaDinheiro("Receba R$200", 200));
        base.add(new CartaDinheiro("Pague R$100", -100));
        base.add(new CartaPrisao("Vá para a prisão"));
        // adicione mais cartas aqui...
        embaralhar();
    }

    public static Baralho getInstance() {
        if (inst == null) inst = new Baralho();
        return inst;
    }

    public void embaralhar() {
        Collections.shuffle(base);
        deck.clear();
        deck.addAll(base);
    }

    public Carta comprarCarta() {
        if (deck.isEmpty()) embaralhar();
        return deck.pop();
    }
}
