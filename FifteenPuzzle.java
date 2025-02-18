import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class FifteenPuzzle {
    private static final int SIZE = 4;
    private String[][] board;

    public FifteenPuzzle() {
        board = new String[SIZE][SIZE];
    }

    public void readBoardFromFile(String fileName) {
        String filePath = "./" + fileName;
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

    // public void SearchAlg(String type) {
    //     switch (type) {
    //         case "BFS":
    //             System.out.println("Breadth First Search");
    //             break;
    //         case "DFS":
    //             System.out.println("Depth First Search");
    //             break;
    //         case "A*":
    //             System.out.println("A* Search");
    //             break;
    //         default:
    //             System.out.println("Invalid search algorithm");
    //     }
    // }
    
    public static void main(String[] args) {
        //welcome message
        System.out.println("Welcome to the 15 Puzzle Game!");
        System.out.println("Give me the puzzle board and I will solve it for you.");
        System.out.println("The puzzle board should be in the following format with in a txt file:");
        //give the sample puzzle
        FifteenPuzzle samplePuzzle = new FifteenPuzzle();
        String sample="sample.txt";
        samplePuzzle.readBoardFromFile(sample);
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

        
    }
}