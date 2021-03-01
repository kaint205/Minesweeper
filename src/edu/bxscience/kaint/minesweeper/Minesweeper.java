package edu.bxscience.kaint.minesweeper;

public class Minesweeper {

    private int bombs, flags, moves, gameState;
    private int[][] numberGrid, statusGrid;
    private boolean[][] bombGrid;

    public Minesweeper(int rows, int columns, int bombs) {
        this.bombs = bombs;
        this.flags = bombs;
        moves = 0;
        gameState = 0;
        numberGrid = new int[rows][columns];
        statusGrid = new int[rows][columns];
        bombGrid = new boolean[rows][columns];
    }

    private void initBoard(int y, int x) {
        gameState = 1;
        // Puts (bombs) random bombs across the board everywhere except at (x, y)
        for (int a = 0; a < bombs; a++) {
            int bombRow = (int) (Math.random() * getY());
            int bombCol = (int) (Math.random() * getX());
            if (bombGrid[bombRow][bombCol] || bombRow >= y - 1 && bombRow <= y + 1 && bombCol >= x - 1 && bombCol <= x + 1)
                a--;
            else
                bombGrid[bombRow][bombCol] = true;
        }
        // Generates the appropriate numbers for each space based on adjacent bombs
        for (int b = 0; b < getY(); b++)
            for (int c = 0; c < getX(); c++)
                for (int d = -1; d < 2; d++)
                    for (int e = -1; e < 2; e++)
                        if (isValidSpace(b + d, c + e) && bombGrid[b + d][c + e])
                            numberGrid[b][c]++;
    }

    private boolean isValidSpace(int y, int x) {
        return (y >= 0 && y < getY() && x >= 0 && x < getX());
    }

    public void touch(int y, int x) {
        if (gameState == 0) {
            initBoard(y, x);
            clear(y, x);
        } else if (bombGrid[y][x])
            gameState = -1;
        else
            clear(y, x);
        moves++;
    }

    public void flag(int y, int x) {
        if (statusGrid[y][x] != 1) {
            switch (statusGrid[y][x]) {
                case 0 -> {
                    if (flags > 0) {
                        if (bombGrid[y][x])
                            bombs--;
                        if (bombs == 0)
                            gameState = 2;
                        flags--;
                        statusGrid[y][x] = 2;
                        moves++;
                    } else
                        System.out.println("\nYou're all out of flags!");
                }
                case 2 -> {
                    if (bombGrid[y][x])
                        bombs++;
                    flags++;
                    statusGrid[y][x] = 3;
                    moves++;
                }
                case 3 -> {
                    statusGrid[y][x] = 0;
                    moves++;
                }
                default -> throw new IllegalStateException("Unexpected value: Space status should be limited between 0 and 3");
            }
        } else
            System.out.println("\nThis space is already shown!");
    }

    private void clear(int y, int x) {
        statusGrid[y][x] = 1;
        if (numberGrid[y][x] == 0)
            for (int f = -1; f < 2; f++)
                for (int g = -1; g < 2; g++)
                    if (isValidSpace(y + f, x + g) && statusGrid[y + f][x + g] == 0)
                        clear(y + f, x + g);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("   ");
        for (int z = 0; z < getX(); z++) {
            str.append(z + 1).append(" ");
            if (z < 9)
                str.append(" ");
        }
        str.append("\n");
        for (int y = 0; y < getY(); y++) {
            str.append(y + 1).append(" ");
            if (y < 9)
                str.append(" ");
            for (int x = 0; x < getX(); x++) {
                switch (statusGrid[y][x]) {
                    case 1 -> {
                        if (numberGrid[y][x] == 0)
                            str.append("   ");
                        else
                            str.append(numberGrid[y][x]).append("  ");
                    }
                    case 2 -> str.append("F  ");
                    case 3 -> str.append("?  ");
                    default -> {
                        if (bombGrid[y][x] && gameState == -1)
                            str.append("X  ");
                        else
                            str.append("#  ");
                    }
                }
            }
            str.append("\n");
        }
        return str.toString();
    }

    public int getBombs() {
        return bombs;
    }

    public int getFlags() {
        return flags;
    }

    public int getMoves() {
        return moves;
    }

    public int getX() {
        return numberGrid[0].length;
    }

    public int getY() {
        return numberGrid.length;
    }

    public int getGameState() {
        return gameState;
    }

}
