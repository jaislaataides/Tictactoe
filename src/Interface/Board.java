package Interface;

import competitiveSearch.Search;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Board {
    public static String board[][] = new String[3][3];
    public static int[] linePositions = {-1, -1, -1};
    public static int[] columnPositions = {-1, -1, -1};

    public static void initializeBoard(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.white);
            }
        }
    }

    public static String[][] cloneBoard(String[][] board) {
        String[][] newBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    public static boolean isFull(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty())
                    return false;
            }
        }
        return true;
    }

    public static void verifyWinner(JButton[][] buttons) {
        switch (Search.checkResult("O", board)) {
            case 1:
                highlightWin(buttons);
                JOptionPane.showMessageDialog(null,
                        "A máquina venceu :) \n Tente mais");
                sleep(buttons);
                initializeBoard(buttons);
                break;
            case 0:
                JOptionPane.showMessageDialog(null, " Deu velha :/");
                sleepOneSecond();
                initializeBoard(buttons);
                break;
            case -1:
                highlightWin(buttons);
                JOptionPane.showMessageDialog(null,
                        "Quer dizer que vc ganha de bot? :O\n MENTE DE TITÂNIO!!");
                sleep(buttons);
                initializeBoard(buttons);
                break;
        }
    }

    private static void sleepOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1); // Pausa por 1 segundo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void highlightWin(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            buttons[linePositions[i]][columnPositions[i]].setBackground(Color.PINK);
        }
    }

    public static void fillLinePositions(int l1, int l2, int l3) {
        linePositions[0] = l1;
        linePositions[1] = l2;
        linePositions[2] = l3;
    }

    public static void fillColumnPositions(int c1, int c2, int c3) {
        columnPositions[0] = c1;
        columnPositions[1] = c2;
        columnPositions[2] = c3;
    }

    private static void lockBoard(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private static void unlockBoard(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private static void sleep(JButton[][] buttons) {
        lockBoard(buttons);
        sleepOneSecond();
        unlockBoard(buttons);
        fillColumnPositions(-1, -1, -1);
        fillLinePositions(-1, -1, -1);
    }
}