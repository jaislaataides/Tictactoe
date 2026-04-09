# Tictactoe -- Jogo da Velha Invencivel

**Um jogo da velha onde voce nunca vai ganhar. E essa e exatamente a ideia.**

Na disciplina de Inteligencia Artificial, chegamos em um momento que eu estava esperando desde o primeiro semestre: entender como um computador pode **tomar decisoes sozinho**. Nao com um monte de if/else -- qualquer um faz isso. Mas sim **raciocinando sobre o futuro**, olhando adiante, calculando todas as possibilidades e escolhendo a melhor. Foi assim que nasceu este jogo da velha: um oponente que nunca erra porque ele literalmente enxerga todos os futuros possiveis.

---

## O que esse programa faz

Um jogo da velha (Jogo da Velha / Tic-Tac-Toe) com interface grafica onde voce joga contra a maquina. Voce e o **X**, ela e o **O**. Voce faz sua jogada, e a maquina responde instantaneamente com a melhor jogada possivel.

O resultado? **Ela nunca perde.** No maximo, empata.

A interface mostra a mensagem "Venca-me se for capaz!" no topo -- e nao e bravata. Quando a maquina vence, as casas da linha vencedora ficam rosa. Quando da velha, a tela reseta sozinha. E se por acaso voce conseguir vencer, o programa te chama de "MENTE DE TITANIO" -- porque, honestamente, isso so e possivel se a maquina cometer um erro, e esse algoritmo nao comete.

---

## O coracao do projeto: o algoritmo Minimax

E aqui que a magica acontece. O **Minimax** e um algoritmo de busca competitiva usado em jogos de dois jogadores com informacao completa (ambos veem o mesmo tabuleiro). A ideia e simples na teoria e poderosa na pratica:

1. **A maquina imagina todas as jogadas possiveis** a partir do estado atual do tabuleiro
2. Para cada jogada, ela imagina **todas as respostas do oponente**
3. Para cada resposta, ela imagina **todas as contra-respostas**... e assim por diante, ate chegar ao fim do jogo
4. Ela atribui valores: **+1** se ela vence, **-1** se o oponente vence, **0** se empata
5. Na sua vez, ela escolhe a jogada que **maximiza** o resultado; na vez do oponente, assume que ele vai **minimizar** (jogar o melhor possivel contra ela)

O resultado e uma arvore de decisoes que cobre **todos os estados possiveis do jogo**. O jogo da velha e pequeno o suficiente para que isso seja viavel -- sao no maximo 9! = 362.880 possibilidades, e muito menos se considerarmos simetrias e cortes por vitoria antecipada.

```
        Estado atual
       /     |     \
   jogada1  jogada2  jogada3      <-- Maquina (maximiza)
    / \      |        / \
  j1  j2   j3  j4   j5  j6       <-- Oponente (minimiza)
  |   |    |   |    |   |
 +1  -1   0   +1   -1  +1        <-- Folhas (resultado final)

  A maquina escolhe a jogada que leva ao melhor pior cenario.
```

### Como o codigo implementa isso

O metodo `minimax(board, isMaximizingPlayer)` e recursivo:
- **Caso base**: alguem ganhou (+1 ou -1) ou o tabuleiro esta cheio (0)
- **Vez da maquina (maximizando)**: testa todas as jogadas possiveis, chama minimax para cada uma assumindo que o oponente joga depois, e fica com o **maior** resultado
- **Vez do oponente (minimizando)**: testa todas as jogadas possiveis, chama minimax para cada uma assumindo que a maquina joga depois, e fica com o **menor** resultado

O metodo `bestAction()` simplesmente testa todas as jogadas iniciais disponiveis e retorna aquela cujo resultado do minimax e o mais alto. A maquina sempre joga a jogada otima.

---

## O que eu aprendi fazendo esse projeto

### Inteligencia Artificial nao e magica -- e busca

Antes dessa disciplina, "inteligencia artificial" soava como algo misterioso. Depois de implementar o Minimax, ficou claro: IA muitas vezes e **buscar espaco de solucoes de forma inteligente**. O computador nao "pensa" como nos -- ele explora sistematicamente todas as possibilidades e escolhe a melhor segundo um criterio. E poderoso, mas e mecanico.

### Jogos como laboratorio perfeito

Jogos de tabuleiro sao o cenario ideal para aprender IA porque:
- O **estado** e discreto e finito (o tabuleiro tem 9 casas)
- As **regras** sao claras e deterministas
- A **funcao de avaliacao** e exata: venceu (+1), perdeu (-1), empatou (0)
- Nao ha incerteza nem informacao oculta

### Recursao ganhou um novo significado

Eu ja tinha usado recursao para fatorial e Fibonacci. Mas ver a recursao construindo uma **arvore de decisoes inteira**, ramificando para cada possibilidade de jogo, foi quando realmente entendi o poder desse conceito. Cada chamada recursiva e um "e se?" -- e o computador responde a todos eles.

### Clonagem de estado

Um detalhe tecnico crucial: antes de simular uma jogada, o codigo **clona o tabuleiro** com `cloneBoard()`. Sem isso, ao modificar o tabuleiro para simular uma jogada, voce estaria alterando o estado real do jogo. Cada simulacao precisa do seu proprio tabuleiro independente. Isso me ensinou sobre **imutabilidade** e o cuidado que se deve ter com estado compartilhado.

---

## Como os arquivos estao organizados

```
Tictactoe/
└── src/
    ├── Main.java                          # Ponto de entrada -- so cria a janela
    ├── Interface/
    │   ├── Janela.java                    # Interface grafica (JFrame + grade 3x3 de botoes)
    │   └── Board.java                     # Logica do tabuleiro: inicializar, clonar, verificar vencedor
    └── competitiveSearch/
        └── Search.java                    # O cerebro: algoritmo Minimax + funcoes auxiliares
```

A separacao entre `Interface/` e `competitiveSearch/` segue o mesmo principio do MVC: a interface nao sabe como a IA pensa, e a IA nao sabe como a interface desenha. So se comunicam pelos dados do tabuleiro.

---

## O fluxo de uma jogada

1. Voce clica em uma casa vazia
2. `setButtonText(i, j)` registra seu "X" no tabuleiro
3. Se o tabuleiro nao esta cheio, chama `bestAction()` para encontrar a melhor jogada da maquina
4. `bestAction()` executa Minimax para cada jogada possivel e retorna a otima
5. O "O" da maquina e colocado no tabuleiro
6. `verifyWinner()` checa se alguem ganhou ou deu velha
7. Se a maquina ganhou, a linha vencedora fica rosa e a mensagem aparece
8. Apos 1 segundo, o tabuleiro reseta para uma nova partida

---

## Como rodar

1. Abra o projeto no **IntelliJ IDEA** (o projeto ja esta configurado com os arquivos `.idea`)
2. Execute a classe `Main.java`
3. A janela do jogo abre -- clique em qualquer casa para comecar!

---

## Requisitos

- **Java 8+**
- **IntelliJ IDEA** (recomendado)

---

## Sobre

Projeto da disciplina de **Inteligencia Artificial**, 6o periodo do BCC. O objetivo era implementar um algoritmo de busca competitiva em um jogo de dois jogadores. Eu escolhi o jogo da velha pela simplicidade do dominio -- o que me permitiu focar inteiramente no algoritmo.

E esse foi o momento em que Inteligencia Artificial deixou de ser uma palavra da moda e virou algo que eu podia construir, rodar e ver funcionando na minha frente. A maquina nao esta "pensando" -- mas o resultado e indistinguivel de alguem que nunca erra. E isso, para mim, continua sendo impressionante.
