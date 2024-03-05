package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Interface.Board.*;
import static competitiveSearch.Search.bestAction;

public class Janela extends JFrame {

    private JButton buttons[][] = new JButton[3][3];
    private JPanel panel[] =  new JPanel[6];
    private JLabel label = new JLabel("Vença-me se for capaz!");


    public Janela(){
        //panel[0]: linha 1 (comando)
        //panel[1]: linha 2 (comporta os próximos 3)
        //panel[2]: vazio à esquerda
        //panel[3]: jogo da velha
        //panel[4]: vazio à direita
        //panel[5]: linha 3 (vazia)

        for (int i = 0; i < 6; i++) {
            panel[i] = new JPanel();
        }

        panel[0].add(label);
        panel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 45));
        panel[1].add(panel[2]);
        panel[1].add(panel[3]);
        panel[1].add(panel[4]);
        panel[1].setLayout(new GridLayout(1, 3));
        panel[1].setSize(new Dimension(300, 300));
        panel[3].setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                panel[3].add(buttons[i][j]);
            }
        }

        buttons[0][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(0, 0);
            }
        });
        buttons[0][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(0, 1);
            }
        });
        buttons[0][2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(0, 2);
            }
        });
        buttons[1][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(1, 0);
            }
        });
        buttons[1][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(1, 1);
            }
        });
        buttons[1][2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(1, 2);
            }
        });
        buttons[2][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(2, 0);
            }
        });
        buttons[2][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(2, 1);
            }
        });
        buttons[2][2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonText(2, 2);
            }
        });

        initializeBoard(buttons);

        this.setLayout(new GridLayout(3, 1));
        this.add(panel[0]);
        this.add(panel[1]);
        this.add(panel[5]);
        this.setTitle("Jogo da velha");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setButtonText(int i, int j){
        if (buttons[i][j].getText().isEmpty()) {
            setMove("X", i, j);
            if(!isFull(board)){
                int[] bestAction = bestAction();
                setMove("O", bestAction[0], bestAction[1]);
            }
            Board.verifyWinner(buttons);
        }
    }

    private void setMove(String player, int i, int j){
        board[i][j] = player;
        buttons[i][j].setText(player);
    }
}