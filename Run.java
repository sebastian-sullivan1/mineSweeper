import java.awt.Point;
/*
    bomb: -1 unrevealed: 9 
    maps are made left-right -- top-down
*/ 
public class Run{
    public static Point[] findCoords(){
        /*
            this returns the points that get possible configuratiosn agrees upon
        */
       int [][] map = SolverGUI.getOutputedSet();


       return null;
    }
    public int[][] getPossibleMineConfig(int [][] board){
        /*
            generates the possible mine maps that work with the mines
        */
       return null;
    }
    public boolean workingArray(int[][] board, int[] map){
        /*
            checks to see whether maps work
        */
        return true;
    }
    
    

    private boolean inbounds(int[][] board, int x, int y){
        if(x >= 0 && y >= 0 && x < board.length && y < board[x].length)
            return  true;
        return false;
    }
    

}
