package service;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracaoJogo {
    public List<Casa> casas = new ArrayList<>();
    public List<Jogador> jogadores = new ArrayList<>();
    public double saldoInicial = 10000;
    public double salarioPorVolta = 1000;
    public int maxRodadas = 20;

    public boolean podeIniciar() {
        return jogadores.size() >= 2 && casas.size() >= 10;
    }
}

