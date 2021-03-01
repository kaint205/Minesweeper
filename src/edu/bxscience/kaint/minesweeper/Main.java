package edu.bxscience.kaint.minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("""
                ___  ________ _   _ _____ _____ _    _ _____ ___________ ___________
                |  \\/  |_   _| \\ | |  ___/  ___| |  | |  ___|  ___| ___ \\  ___| ___ \\
                | .  . | | | |  \\| | |__ \\ `--.| |  | | |__ | |__ | |_/ / |__ | |_/ /
                | |\\/| | | | | . ` |  __| `--. \\ |/\\| |  __||  __||  __/|  __||    /
                | |  | |_| |_| |\\  | |___/\\__/ |  /\\  / |___| |___| |   | |___| |\\ \\
                \\_|  |_/\\___/\\_| \\_|____/\\____/ \\/  \\/\\____/\\____/\\_|   \\____/\\_| \\_|
                /-------------------------------------------------------------------\\
                |     Written by Thomas Kain for Ms. Qiu's 5th Period APCS Class    |
                |              Bronx High School of Science, Homeroom C20           |
                \\-------------------------------------------------------------------/
                
                HOW TO PLAY:
                
                You have a grid full of spaces. Touching a space will reveal adjacent spaces,
                but beware - some spaces have bombs. Numbered spaces have that # of bombs
                in the spaces adjacent to them (diagonally, too). Flag all the bombs to win.
                Use the flag function on a flagged space to mark it as unknown. Use the flag
                function again to unmark it. Don't touch a bomb space, or you lose!
                """);
        do {
            System.out.println();
            Minesweeper minesweeper = initMinesweeper(scan);
            System.out.println();
            gameLoop(minesweeper, scan);
            if (minesweeper.getGameState() == 2)
                System.out.println("You defused all the bombs!\nCongratulations! You earned it.\n");
            else
                System.out.println("You blew up a bomb!\nOh well. Can't win 'em all.\n");
            System.out.println("Bombs remaining: " + minesweeper.getBombs() + "\nFlags remaining: " + minesweeper.getFlags() + "\nMoves made: " + minesweeper.getMoves());
            System.out.print("Would you like to play again? [y/N] ");
        } while (scan.next().equalsIgnoreCase("y"));
        scan.close();
    }

    public static Minesweeper initMinesweeper(Scanner scan) {
        int x, y, bombs;
        switch (inputIntBounds(1, 5, "Please choose a difficulty setting:\n1. Easy (10x10, 10 bombs)\n2. Medium (15x15, 20 bombs)\n3. Hard (15x30, 30 bombs)\n4. Custom (you choose!)\nSelection: ", scan)) {
            case 1 -> {
                x = 10;
                y = 10;
                bombs = 5;
            }
            case 2 -> {
                x = 15;
                y = 15;
                bombs = 13;
            }
            case 3 -> {
                x = 15;
                y = 30;
                bombs = 30;
            }
            case 4 -> {
                x = inputIntBounds(10, 31, "Please enter the # of columns your board will have (between 10-30): ", scan);
                y = inputIntBounds(10, 31, "Please enter the # of rows your board will have (between 10-30): ", scan);
                bombs = inputIntBounds(10, 31, "Please enter the # of bombs your board will have (between 10-30): ", scan);
            }
            default -> throw new IllegalStateException("Unexpected value: Option should be limited between 1 and 4");
        }
        return new Minesweeper(y, x, bombs);
    }

    public static int inputIntBounds(int min, int max, String message, Scanner scan) {
        while (true) {
            System.out.print(message);
            int input = scan.nextInt();
            if (input >= min && input < max)
                return input;
            System.out.println("ERROR: Invalid input. Please try again.");
        }
    }

    public static void gameLoop(Minesweeper minesweeper, Scanner scan) {
        int x, y;
        while (minesweeper.getGameState() == 0 || minesweeper.getGameState() == 1) {
            System.out.println(minesweeper.toString());
            System.out.println("You've made " + minesweeper.getMoves() + " moves. You have " + minesweeper.getFlags() + " flags left to use.\n");
            x = inputIntBounds(1, minesweeper.getX() + 1, "Please enter the column of the point to interact with: ", scan);
            y = inputIntBounds(1, minesweeper.getY() + 1, "Please enter the row of the point to interact with: ", scan);
            System.out.println();
            switch (inputIntBounds(1, 3, "Please choose an option:\n1. Touch\n2. Flag\nSelection: ", scan)) {
                case 1 -> minesweeper.touch(y - 1, x - 1);
                case 2 -> minesweeper.flag(y - 1, x - 1);
                default -> throw new IllegalStateException("Unexpected value: Option should be limited between 1 and 2");
            }
            System.out.println();
        }
    }
}
