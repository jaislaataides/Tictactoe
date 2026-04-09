# Tictactoe -- Jogo da Velha Invencível

**Um jogo da velha onde você nunca vai ganhar. E essa é exatamente a ideia.**

Na disciplina de Inteligência Artificial, chegamos em um momento que eu estava esperando desde o primeiro semestre: entender como um computador pode **tomar decisões sozinho**. Não com um monte de if/else -- qualquer um faz isso. Mas sim **raciocinando sobre o futuro**, olhando adiante, calculando todas as possibilidades e escolhendo a melhor. Foi assim que nasceu este jogo da velha: um oponente que nunca erra porque ele literalmente enxerga todos os futuros possíveis.

---

## O que esse programa faz

Um jogo da velha (Jogo da Velha / Tic-Tac-Toe) com interface gráfica onde você joga contra a máquina. Você é o **X**, ela é o **O**. Você faz sua jogada, e a máquina responde instantaneamente com a melhor jogada possível.

O resultado? **Ela nunca perde.** No máximo, empata.

A interface mostra a mensagem "Vença-me se for capaz!" no topo -- e não é bravata. Quando a máquina vence, as casas da linha vencedora ficam rosa. Quando dá velha, a tela reseta sozinha. E se por acaso você conseguir vencer, o programa te chama de "MENTE DE TITÂNIO" -- porque, honestamente, isso só é possível se a máquina cometer um erro, e esse algoritmo não comete.

---

## O coração do projeto: o algoritmo Minimax

É aqui que a mágica acontece. O **Minimax** é um algoritmo de busca competitiva usado em jogos de dois jogadores com informação completa (ambos veem o mesmo tabuleiro). A ideia é simples na teoria e poderosa na prática:

1. **A máquina imagina todas as jogadas possíveis** a partir do estado atual do tabuleiro
2. Para cada jogada, ela imagina **todas as respostas do oponente**
3. Para cada resposta, ela imagina **todas as contra-respostas**... e assim por diante, até chegar ao fim do jogo
4. Ela atribui valores: **+1** se ela vence, **-1** se o oponente vence, **0** se empata
5. Na sua vez, ela escolhe a jogada que **maximiza** o resultado; na vez do oponente, assume que ele vai **minimizar** (jogar o melhor possível contra ela)

O resultado é uma árvore de decisões que cobre **todos os estados possíveis do jogo**. O jogo da velha é pequeno o suficiente para que isso seja viável -- são no máximo 9! = 362.880 possibilidades, e muito menos se considerarmos simetrias e cortes por vitória antecipada.

```
        Estado atual
       /     |     \
   jogada1  jogada2  jogada3      <-- Máquina (maximiza)
    / \      |        / \
  j1  j2   j3  j4   j5  j6       <-- Oponente (minimiza)
  |   |    |   |    |   |
 +1  -1   0   +1   -1  +1        <-- Folhas (resultado final)

  A máquina escolhe a jogada que leva ao melhor pior cenário.
```

### Como o código implementa isso

O método `minimax(board, isMaximizingPlayer)` é recursivo:
- **Caso base**: alguém ganhou (+1 ou -1) ou o tabuleiro está cheio (0)
- **Vez da máquina (maximizando)**: testa todas as jogadas possíveis, chama minimax para cada uma assumindo que o oponente joga depois, e fica com o **maior** resultado
- **Vez do oponente (minimizando)**: testa todas as jogadas possíveis, chama minimax para cada uma assumindo que a máquina joga depois, e fica com o **menor** resultado

O método `bestAction()` simplesmente testa todas as jogadas iniciais disponíveis e retorna aquela cujo resultado do minimax é o mais alto. A máquina sempre joga a jogada ótima.

---

## O que eu aprendi fazendo esse projeto

### Inteligência Artificial não é mágica -- é busca

Antes dessa disciplina, "inteligência artificial" soava como algo misterioso. Depois de implementar o Minimax, ficou claro: IA muitas vezes é **buscar espaço de soluções de forma inteligente**. O computador não "pensa" como nós -- ele explora sistematicamente todas as possibilidades e escolhe a melhor segundo um critério. É poderoso, mas é mecânico.

### Jogos como laboratório perfeito

Jogos de tabuleiro são o cenário ideal para aprender IA porque:
- O **estado** é discreto e finito (o tabuleiro tem 9 casas)
- As **regras** são claras e determinísticas
- A **função de avaliação** é exata: venceu (+1), perdeu (-1), empatou (0)
- Não há incerteza nem informação oculta

### Recursão ganhou um novo significado

Eu já tinha usado recursão para fatorial e Fibonacci. Mas ver a recursão construindo uma **árvore de decisões inteira**, ramificando para cada possibilidade de jogo, foi quando realmente entendi o poder desse conceito. Cada chamada recursiva é um "e se?" -- e o computador responde a todos eles.

### Clonagem de estado

Um detalhe técnico crucial: antes de simular uma jogada, o código **clona o tabuleiro** com `cloneBoard()`. Sem isso, ao modificar o tabuleiro para simular uma jogada, você estaria alterando o estado real do jogo. Cada simulação precisa do seu próprio tabuleiro independente. Isso me ensinou sobre **imutabilidade** e o cuidado que se deve ter com estado compartilhado.

---

## Como os arquivos estão organizados

```
Tictactoe/
└── src/
    ├── Main.java                          # Ponto de entrada -- só cria a janela
    ├── Interface/
    │   ├── Janela.java                    # Interface gráfica (JFrame + grade 3x3 de botões)
    │   └── Board.java                     # Lógica do tabuleiro: inicializar, clonar, verificar vencedor
    └── competitiveSearch/
        └── Search.java                    # O cérebro: algoritmo Minimax + funções auxiliares
```

A separação entre `Interface/` e `competitiveSearch/` segue o mesmo princípio do MVC: a interface não sabe como a IA pensa, e a IA não sabe como a interface desenha. Só se comunicam pelos dados do tabuleiro.

---

## O fluxo de uma jogada

1. Você clica em uma casa vazia
2. `setButtonText(i, j)` registra seu "X" no tabuleiro
3. Se o tabuleiro não está cheio, chama `bestAction()` para encontrar a melhor jogada da máquina
4. `bestAction()` executa Minimax para cada jogada possível e retorna a ótima
5. O "O" da máquina é colocado no tabuleiro
6. `verifyWinner()` checa se alguém ganhou ou deu velha
7. Se a máquina ganhou, a linha vencedora fica rosa e a mensagem aparece
8. Após 1 segundo, o tabuleiro reseta para uma nova partida

---

## Como rodar

1. Abra o projeto no **IntelliJ IDEA** (o projeto já está configurado com os arquivos `.idea`)
2. Execute a classe `Main.java`
3. A janela do jogo abre -- clique em qualquer casa para começar!

---

## Requisitos

- **Java 8+**
- **IntelliJ IDEA** (recomendado)

---

## Sobre

Projeto da disciplina de **Inteligência Artificial**, 6º período do BCC. O objetivo era implementar um algoritmo de busca competitiva em um jogo de dois jogadores. Eu escolhi o jogo da velha pela simplicidade do domínio -- o que me permitiu focar inteiramente no algoritmo.

E esse foi o momento em que Inteligência Artificial deixou de ser uma palavra da moda e virou algo que eu podia construir, rodar e ver funcionando na minha frente. A máquina não está "pensando" -- mas o resultado é indistinguível de alguém que nunca erra. E isso, para mim, continua sendo impressionante.
