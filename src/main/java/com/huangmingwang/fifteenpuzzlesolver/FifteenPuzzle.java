package com.huangmingwang.fifteenpuzzlesolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FifteenPuzzle implements Cloneable {
    private static final int SIZE = 4;
    private String[][] board;
    private static final FifteenPuzzle samplePuzzle;
    private FifteenPuzzle previous;
    private int heuristicValue;
    private int gValue; // cost from initial state to current state
    private int fValue;

    static {
        samplePuzzle = new FifteenPuzzle();
        samplePuzzle.board = new String[][] {
            {"A", "B", "C", "D"},
            {"E", "F", "G", "H"},
            {"I", "J", "K", "L"},
            {"M", "N", "O", "-"}
        };
    }

    public FifteenPuzzle() {
        board = new String[SIZE][SIZE];
        previous = null;
        heuristicValue = Integer.MAX_VALUE;
        fValue = Integer.MAX_VALUE;
        
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoardValue(int row, int col, String value) {
        board[row][col] = value;
    }

    public String getBoardValue(int row, int col) {
        return board[row][col];
    }

    @Override
    public FifteenPuzzle clone() {
        try {
            FifteenPuzzle cloned = (FifteenPuzzle) super.clone();
            cloned.board = new String[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                cloned.board[i] = this.board[i].clone();
            }
            cloned.previous = null;
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FifteenPuzzle other = (FifteenPuzzle) obj;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!this.board[i][j].equals(other.board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void readBoardFromFile(String fileName) {
        String filePath = "src/main/res/puzzle/" + fileName;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < SIZE) {
                String[] values = line.split("\\s+");
                if (values.length != SIZE) {
                    throw new RuntimeException("Invalid number of columns in row " + row);
                }
                System.arraycopy(values, 0, board[row], 0, SIZE);
                row++;
            }
            if (row != SIZE) {
                throw new RuntimeException("Invalid number of rows in the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void printPath(ArrayList<FifteenPuzzle> expandedNodes) {
        //arrange the path from the goal state to the initial state
        ArrayList<FifteenPuzzle> path = new ArrayList<>();
        FifteenPuzzle current = expandedNodes.get(expandedNodes.size() - 1);
        while (current != null) {
            path.add(0, current);
            current = current.previous;
        }

        //print the path
        System.out.println("The path from the initial state to the goal state is: ");
        for (FifteenPuzzle puzzle : path) {
            puzzle.printBoard();
            System.out.println("---------------------------");
        }
        System.out.println("The length of the path is: " + (path.size()-1));
        System.out.println("This algorithm expanded " + (expandedNodes.size() - 1)+" nodes");
    }

    public void searchAlgs(int type) {
        ArrayList<FifteenPuzzle> result = null;
        switch (type) {
            case 1:
                System.out.println("Running Breadth First Search...");
                result = breadthFirstSearch();
                break;
            case 2:
                System.out.println("Running A* Search...");
                result = aStarSearch();
                break;
            default:
                System.out.println("Invalid search algorithm");
                break;
        }
        if (result != null) {
            printPath(result);
        }
    }

    private ArrayList<FifteenPuzzle> breadthFirstSearch() {
        ArrayList<FifteenPuzzle> OPEN = new ArrayList<>();
        ArrayList<FifteenPuzzle> CLOSED = new ArrayList<>();
        OPEN.add(this); // add the initial state to OPEN
        while (!OPEN.isEmpty()) {
            FifteenPuzzle current = OPEN.remove(0); // remove the state n from OPEN
            if (CLOSED.contains(current)) {
                continue; // skip the rest of the loop
            }
            if (current.isGoalState()) {
                System.out.println("Goal state found!");
                //return all expanded nodes+goal state
                CLOSED.add(current);
                return CLOSED;
            }
            ArrayList<FifteenPuzzle> successors = current.generateSuccessors(0);
            for (FifteenPuzzle successor : successors) {
                if (!CLOSED.contains(successor)) {
                    OPEN.add(successor);
                }
            }
            CLOSED.add(current);
        }
        System.out.println("Goal state not found!");
        return null;
    }

    private ArrayList<FifteenPuzzle> aStarSearch() {
        ArrayList<FifteenPuzzle> OPEN = new ArrayList<>();
        ArrayList<FifteenPuzzle> CLOSED = new ArrayList<>();
        // Set up information for A* search
        int g = 0; // g is the cost from initial state to current state
        this.heuristicValue = heuristicFunction();
        this.fValue = g + this.heuristicValue;
        OPEN.add(this); // Add the initial state to OPEN

        while (!OPEN.isEmpty()) {
            // Select and remove the state n from OPEN (with the lowest f(n) value)
            Collections.sort(OPEN, (FifteenPuzzle p1, FifteenPuzzle p2) -> p1.fValue - p2.fValue);
            FifteenPuzzle current = OPEN.remove(0);
            if (CLOSED.contains(current)) {
                continue; // Skip the rest of the loop
            }
            if (current.isGoalState()) {
                System.out.println("Goal state found!");
                // Return all expanded nodes + goal state
                CLOSED.add(current);
                return CLOSED;
            }
            ArrayList<FifteenPuzzle> successors = current.generateSuccessors(g);
            for (FifteenPuzzle successor : successors) {
                successor.heuristicValue = successor.heuristicFunction(); // Calculate the heuristic value
                successor.fValue = successor.gValue + successor.heuristicValue; // Calculate the f value
                if (!CLOSED.contains(successor)) {
                    OPEN.add(successor);
                }
            }
            CLOSED.add(current);
        }
        System.out.println("Goal state not found!");
        return null;
    }

    private boolean isGoalState() {
        // Check if the current state is the goal state
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!this.board[i][j].equals(samplePuzzle.board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<FifteenPuzzle> generateSuccessors(int cost) {
        int rowOfDash = -1;
        int colOfDash = -1;
        // find the position of the dash
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].equals("-")) {
                    rowOfDash = i;
                    colOfDash = j;
                    break;
                }
            }
        }
        // generate the successors
        ArrayList<FifteenPuzzle> successors = new ArrayList<>();

        // move the switch dash up
        if (rowOfDash > 0) {
            FifteenPuzzle successorTop = this.clone();
            successorTop.previous = this;
            successorTop.setBoardValue(rowOfDash, colOfDash, getBoardValue(rowOfDash - 1, colOfDash));
            successorTop.board[rowOfDash - 1][colOfDash] = "-";
            successorTop.gValue+=cost;
            if (!successorTop.equals(this)) {
                successors.add(successorTop);
            }
        }

        // move the switch dash to the right
        if (colOfDash < SIZE - 1) {
            FifteenPuzzle successorRight = this.clone();
            successorRight.previous = this;
            successorRight.setBoardValue(rowOfDash, colOfDash, getBoardValue(rowOfDash, colOfDash + 1));
            successorRight.board[rowOfDash][colOfDash + 1] = "-";
            if (!successorRight.equals(this)) {
                successors.add(successorRight);
            }
        }

        // move the switch dash down
        if (rowOfDash < SIZE - 1) {
            FifteenPuzzle successorBottom = this.clone();
            successorBottom.previous = this;
            successorBottom.setBoardValue(rowOfDash, colOfDash, getBoardValue(rowOfDash + 1, colOfDash));
            successorBottom.board[rowOfDash + 1][colOfDash] = "-";
            if (!successorBottom.equals(this)) {
                successors.add(successorBottom);
            }
        }

        // move the switch dash to the left
        if (colOfDash > 0) {
            FifteenPuzzle successorLeft = this.clone();
            successorLeft.previous = this;
            successorLeft.setBoardValue(rowOfDash, colOfDash, getBoardValue(rowOfDash, colOfDash - 1));
            successorLeft.board[rowOfDash][colOfDash - 1] = "-";
            if (!successorLeft.equals(this)) {
                successors.add(successorLeft);
            }
        }
        return successors;
    }

    public int heuristicFunction() {
        int hValue=0;
        // Standard locations for letters A to O on the 4x4 board
        // A       B       C       D
        // E       F       G       H
        // I       J       K       L
        // M       N       O       -
        int[][] standardPositions = {
        {0, 0}, // A
        {0, 1}, // B
        {0, 2}, // C
        {0, 3}, // D
        {1, 0}, // E
        {1, 1}, // F
        {1, 2}, // G
        {1, 3}, // H
        {2, 0}, // I
        {2, 1}, // J
        {2, 2}, // K
        {2, 3}, // L
        {3, 0}, // M
        {3, 1}, // N
        {3, 2}  // O
        };

    // Calculate the heuristic function (Manhattan distance)
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            String value = board[i][j];
            if (!value.equals("-")) {
                int index = value.charAt(0) - 'A';
                int targetX = standardPositions[index][0];
                int targetY = standardPositions[index][1];
                hValue += Math.abs(i - targetX) + Math.abs(j - targetY);
            }
        }
    }
    return hValue;
    }

    public static void main(String[] args) {
        // welcome message
        System.out.println("Welcome to the 15 Puzzle Game!");
        System.out.println("Give me the puzzle board and I will solve it for you.");
        System.out.println("The puzzle board should be in the following format with in a txt file:");
        // give the sample puzzle board
        samplePuzzle.printBoard();
        System.out.println("If the order of the puzzle is different from the sample, I will solve it for you.");

        // acquire the puzzle board from the user
        System.out.println("Please enter the file name of your puzzle: ");
        FifteenPuzzle puzzle = new FifteenPuzzle();
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        puzzle.readBoardFromFile(fileName);
        System.out.println("Your puzzle board is: ");
        puzzle.printBoard();

        // choose the search algorithm
        System.out.println("Please choose the search algorithm: \n[1]BreadthFirst \n[2]A*");
        int chosenAlg = scanner.nextInt();
        puzzle.searchAlgs(chosenAlg);
        scanner.close();
    }
}