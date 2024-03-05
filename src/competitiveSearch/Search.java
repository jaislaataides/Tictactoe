package competitiveSearch;

import java.util.ArrayList;

import static Interface.Board.*;

public class Search {
    public static int[] bestAction() {
        ArrayList<int[]> moves = potentialMoves(board);
        int[] action = new int[2];
        int bestResult = Integer.MIN_VALUE;

        for (int[] move : moves) {
            String[][] newBoard = loadMove(n, board, "O");
            int result = minimax(newBoard, false);

            if (result > bestResult) {
                bestResult = result;
                action = move;
            }
        }
        return action;
    }

    private static int minimax(String[][] board, boolean isMaximizingPlayer) {
        ArrayList<int[]> moves = potentialMoves(board);
        int result = checkResult("O", board);
        int bestScore;
        if (result != 2) {
            return result;
        }

        // Se é a vez do player maximizador
        if (isMaximizingPlayer) {
            bestScore = Integer.MIN_VALUE;

            for (int[] move : moves) {
                String[][] newBoard = loadMove(move, board, "O");
                int score = minimax(newBoard, false);
                bestScore = Math.max(bestScore, score);
            }
        } else { // Se é a vez do player minimizador
            bestScore = Integer.MAX_VALUE;

            for (int[] move : moves) {
                String[][] newBoard = loadMove(move, board, "X");
                int score = minimax(newBoard, true);
                bestScore = Math.min(bestScore, score);
            }
        }
        return bestScore;
    }

    public static int checkResult(String player, String[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()){
                fillLinePositions(i, i, i);
                fillColumnPositions(0, 1, 2);
                return board[i][0].equals(player) ? 1 : -1;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]) && !board[0][j].isEmpty()) {
                fillLinePositions(0, 1, 2);
                fillColumnPositions(j, j, j);
                return board[0][j].equals(player) ? 1 : -1;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()){
            fillLinePositions(0, 1, 2);
            fillColumnPositions(0, 1 ,2);
            return board[0][0].equals(player) ? 1 : -1;
        }

        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()){
            fillLinePositions(0, 1, 2);
            fillColumnPositions(2, 1, 0);
            return board[0][2].equals(player) ? 1 : -1;
        }


        //jogo em andamento
        if(!isFull(board))
            return 2;

        //empate
        return 0;
    }

    private static ArrayList<int[]> potentialMoves(String[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    private static String[][] loadMove(int[] newMove, String[][] board, String player) {
        String[][] newBoard = cloneBoard(board);
        newBoard[newMove[0]][newMove[1]] = player;
        return newBoard;
    }
}
