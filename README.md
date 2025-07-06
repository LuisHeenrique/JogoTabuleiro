# JogoTabuleiro

# Simulador Banco Imobiliário

Simula uma partida do Banco Imobiliário em console, com compra de imóveis, aluguel, imposto, prisão e cartas de Sorte/Reveses.

---

## Descrição Geral

Aplicação escrita em Java que simula um jogo de tabuleiro, destacando:

- **Movimentação** com dados.
- **Estruturas de dados**: lista ligada circular para o tabuleiro, pilha para cartas, listas dinâmicas para jogadores e propriedades.
- **Casas especiais**: Prisão, Sorte/Reveses, Imposto, Restituição.
- **Interação por turno**, com opções de compra, hipoteca, negociação, ranking e desistência.

---

## Estruturas de Dados Utilizadas

- 📌 **Lista Ligada Circular** (`Tabuleiro` + `Casa`): permite circular pelo tabuleiro indefinidamente.
- 📌 **Pilha (Stack)** (`Baralho` + `Carta`): simula puxar e reembaralhar cartas do tipo Sorte/Reveses.
- 📌 **ArrayList** (`ConfiguracaoJogo.jogadores`, `Jogador.propriedades`): gerencia coleções de tamanho dinâmico de forma eficiente.

**Justificativas**:

- A lista ligada circular é ideal para o tabuleiro, pois permite o fluxo contínuo de rodadas.
- A pilha reflete fielmente o comportamento de um deck de cartas com retirada do topo e reembaralhamento.
- O ArrayList é ideal para manipular coleções de jogadores e propriedades com acesso aleatório.

---

## Como Compilar e Executar

### Requisitos

- Java JDK 17 ou superior instalado na máquina.
- IDE recomendada: IntelliJ IDEA ou similar.

### Pelo terminal

1. Navegue até a raiz do projeto (onde está `src/`).
2. Compile:

```bash
javac -d out src/service/*.java src/menus/*.java src/Main.java
