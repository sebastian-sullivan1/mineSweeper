import java.util.Random;


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
        this.revealed = new boolean[rows][cols];
        this.flagged = new boolean[rows][cols];
        this.gameOver = false;
        this.gameWon = false;

        generateMines();
        calculateNeighborCounts();
    }
    
    public boolean isValid(int row, int col) {
        if(row >= 0 && row < rows && col >= 0 && col < cols){
            return true;
        } else {
            return false;
        }
    }

    public void toggleFlag(int row, int col) {
        if (isValid(row, col) && !revealed[row][col] && !gameOver) {
            flagged[row][col] = !flagged[row][col];
        } else {
            System.out.println("Invalid coordinates or cell already revealed.");
        }
    }

    public void calculateNeighborCounts() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == -1) continue;
                int count = 0;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r + dr;
                        int nc = c + dc;
                        if (isValid(nr, nc) && grid[nr][nc] == -1) {
                            count++;
                        }
                    }
                }
                grid[r][c] = count;
            }
        }
    }

    public void revealCell(int row, int col) {
        if (!isValid(row, col) || revealed[row][col] || flagged[row][col] || gameOver) {
            return;
        }

        revealed[row][col] = true;

        // Hit a mine
        if (grid[row][col] == -1) {
            gameOver = true;
            gameWon = false;
            return;
        }

        // Auto-reveal surrounding cells if empty (no adjacent mines)
        if (grid[row][col] == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    revealCell(row + dr, col + dc);
                }
            }
        }

        checkWinCondition();
    }

    private void checkWinCondition() {
        int unrevealedSafe = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != -1 && !revealed[r][c]) {
                    unrevealedSafe++;
                }
            }
        }
        if (unrevealedSafe == 0) {
            gameOver = true;
            gameWon = true;
        }
    }

    public void generateMines() {
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
