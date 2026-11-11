import java.util.Random;

//we are making a minesweer model btw
public class Model {
    private int rows;
    private int cols;
    private int numMines;
    private int[][] grid;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private boolean gameOver;
    private boolean gameWon;
    

    public Model(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.grid = new int[rows][cols];
        generateMines();
    }
    
    public boolean isValid(int row, int col) {
        if(row <= 0 && row < rows && col <= 0 && col < cols){
            return true;
        } else {
            return false;
        }
    }

    private void toggleFlag(int row, int col) {
        if(isValid(row, col)){
            
        }
        
    }

    private void generateMines() {
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = (int) (Math.random() * rows);
            int col = (int) (Math.random() * cols);
            if (grid[row][col] != -1) { // -1 represents a mine
                grid[row][col] = -1;
                minesPlaced++;
            }
        }
    }

}
