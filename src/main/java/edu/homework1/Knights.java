package edu.homework1;

public class Knights {
    private static final int n = 8;
    private static final int m = 8;

    private Knights() {}

    public static boolean knightBoardCapture(int[][] board) throws IllegalArgumentException {
        if (board == null || board.length != n) {
            throw new IllegalArgumentException("Null or incorrect size board!");
        }

        for (int[] line : board) {
            if (line == null || line.length != m) {
                throw new IllegalArgumentException("Null or incorrect size board!");
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 0) {
                    continue;
                }

                if (isBeaten(i - 1, j - 2, board)
                    || isBeaten(i - 1, j + 2, board)
                    || isBeaten(i - 2, j - 1, board)
                    || isBeaten(i - 2, j + 1, board)
                    || isBeaten(i + 1, j - 2, board)
                    || isBeaten(i + 1, j + 2, board)
                    || isBeaten(i + 2, j - 1, board)
                    || isBeaten(i + 2, j + 1, board)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isBeaten(int x, int y, int[][]board) {
        return (isOnBoard(x, y) && board[x][y] == 1);
    }

    private static boolean isOnBoard(int x, int y) {
        return (x >= 0 && x < n && y >= 0 & y < m);
    }
}
