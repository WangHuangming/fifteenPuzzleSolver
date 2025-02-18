package com.huangmingwang.fifteenpuzzlesolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class FifteenPuzzle implements Cloneable{
    private static final int SIZE = 4;
    private String[][] board;
    private static final FifteenPuzzle samplePuzzle;
    private FifteenPuzzle previous;
    static{   
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
    }

    public String[][] getBoard(){
        return board;
    }

    public void setBoardValue(int row, int col, String value){
        board[row][col] = value;
    }
    
    public String getBoardValue(int row, int col){
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

    public void SearchAlg(String type) {
        switch (type) {
            case "BreadthFirst":
                System.out.println("Breadth First Search");
                break;
            case "A*":
                System.out.println("A* Search");
                break;
            default:
                System.out.println("Invalid search algorithm");
        }
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
    // private ArrayList<FifteenPuzzle> generateSuccessors() {
    //     int rowOfDash=-1;
    //     int colOfDash=-1;
    //     //find the position of the dash
    //     for(int i=0;i<SIZE;i++){
    //         for(int j=0;j<SIZE;j++){
    //             if(board[i][j].equals("-")){
    //                 rowOfDash=i;
    //                 colOfDash=j;
    //                 break;
    //             }
    //         }
    //     }
    //     //generate the successors
    //     ArrayList<FifteenPuzzle> successors=new ArrayList<FifteenPuzzle>();
    //     //move the switch dash and the tile above it
    //     if(rowOfDash>0){
    //         FifteenPuzzle successor=new FifteenPuzzle();
    //         successor.board=this.board.clone();
    //         successor.board[rowOfDash][colOfDash]=successor.board[rowOfDash-1][colOfDash];
    //         successor.board[rowOfDash-1][colOfDash]="-";
    //         successors.add(successor);
    //     }
    //     throw new UnsupportedOperationException("Unimplemented method 'generateSuccessors'");
    // }

    // private void breadthFirstSearch() {
    //     ArrayList<FifteenPuzzle> OPEN=new ArrayList<FifteenPuzzle>();
    //     ArrayList<FifteenPuzzle> CLOSED=new ArrayList<FifteenPuzzle>();
    //     OPEN.add(this); //add the initial state to OPEN
    //     while(!OPEN.isEmpty()){
    //         FifteenPuzzle current=OPEN.remove(0); //remove the state n from OPEN
    //         if(CLOSED.contains(current)){
    //             continue; //skip the rest of the loop
    //         }
    //         if(current.isGoalState()){
    //             System.out.println("Goal state found!");
    //             //print the path from the initial state to the goal state
    //             return;
    //         }
    //         ArrayList<FifteenPuzzle> successors=current.generateSuccessors();
    //         for(FifteenPuzzle successor:successors){
    //             if(!CLOSED.contains(successor)){
    //                 OPEN.add(successor);
    //             }
    //         }
    //         CLOSED.add(current);
    //     }
    //     System.out.println("Goal state not found!");
    // }
                
    
            
    public static void main(String[] args) {
        //welcome message
        System.out.println("Welcome to the 15 Puzzle Game!");
        System.out.println("Give me the puzzle board and I will solve it for you.");
        System.out.println("The puzzle board should be in the following format with in a txt file:");
        //give the sample puzzle board
        samplePuzzle.printBoard();
        System.out.println("If the order of the puzzle is different from the sample, I will solve it for you.");

        //aquire the puzzle board from the user
        System.out.println("Please enter the file name of your puzzle: ");
        FifteenPuzzle puzzle = new FifteenPuzzle();
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        puzzle.readBoardFromFile(fileName);
        System.out.println("Your puzzle board is: ");
        puzzle.printBoard();

        //choose the search algorithm


    }
}