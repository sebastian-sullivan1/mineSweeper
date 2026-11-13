import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SolverGUI extends JFrame {
    private JPanel gridPanel;
    private JTextField[][] gridCells;
    private boolean[][] cellTouched; // Tracks which cells the user interacted with
    private JTextField sizeInput;
    private JLabel statusLabel;
    private int currentN = 0;
    private int[][] holdingOutPut;
    private boolean set;

    public SolverGUI() {
        setTitle("Grid Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
        holdingOutPut=null;
        set=false;
        // Top control panel
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Grid Size (N):"));
        sizeInput = new JTextField("4", 4);
        topPanel.add(sizeInput);

        JButton generateBtn = new JButton("Generate Grid");
        generateBtn.addActionListener(e -> generateGrid());
        topPanel.add(generateBtn);

        JButton solveBtn = new JButton("Find Target Coords");
        solveBtn.addActionListener(e -> executeSolve());
        topPanel.add(solveBtn);

        JButton inputBtn = new JButton("Send to code");
        inputBtn.addActionListener(e -> updateTiles());
        topPanel.add(inputBtn);

        add(topPanel, BorderLayout.NORTH);

        // Center grid panel
        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);

        // Status bar
        statusLabel = new JLabel("Enter N, build grid, then click cells to edit.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(statusLabel, BorderLayout.SOUTH);

        generateGrid();
    }


    private void updateTiles(){
        holdingOutPut=new int[gridCells.length][gridCells[0].length];
        for(int i=0;i<gridCells.length;i++){
            for(int j=0;j<gridCells[i].length;j++){
                if(cellTouched[i][j]){
                    try{
                    holdingOutPut[i][j]=Integer.parseInt(gridCells[i][j].getText().trim());
                    }catch (Exception e) {}
                }
            }
        }
        set=true;
    }

    public int[][] getOutputedSet(){
        while(set==false){
            try{
            Thread.sleep(10000);
            }catch(InterruptedException e){
                System.out.println("Sleeping for 30 seconds");
            }
        }
        return holdingOutPut;

    }
    private void generateGrid() {
        try {
            int n = Integer.parseInt(sizeInput.getText().trim());
            if (n <= 0) {
                JOptionPane.showMessageDialog(this, "N must be greater than 0.");
                return;
            }

            currentN = n;
            gridPanel.removeAll();
            gridPanel.setLayout(new GridLayout(n, n, 2, 2));
            
            gridCells = new JTextField[n][n];
            cellTouched = new boolean[n][n];

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    final int row = r;
                    final int col = c;

                    JTextField cell = new JTextField();
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    cell.setFont(new Font("SansSerif", Font.PLAIN, 18));

                    // Mark cell as touched when user clicks/focuses or edits it
                    cell.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            cellTouched[row][col] = true;
                        }
                    });

                    gridCells[r][c] = cell;
                    gridPanel.add(cell);
                }
            }

            gridPanel.revalidate();
            gridPanel.repaint();
            statusLabel.setText("Generated " + n + "x" + n + " grid. Click cells to edit values.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for N.");
        }
    }

    // 2. Extracts board values only for cells that were clicked/touched
    public int[][] getBoard() {
        return  holdingOutPut;
    }

    public boolean getSet(){return set;}

    private void executeSolve() {
        if (currentN == 0 || gridCells == null) return;

        int[][] board = getBoard();

        for (int r = 0; r < currentN; r++) {
            for (int c = 0; c < currentN; c++) {
                gridCells[r][c].setBackground(Color.WHITE);
            }
        }

        Point target = Run.findCoords(board); 

        if (target == null) {
            statusLabel.setText("No target coordinates found.");
            return;
        }

        if (target.x < 0 || target.x >= currentN || target.y < 0 || target.y >= currentN) {
            statusLabel.setText("Target coordinates (" + target.x + ", " + target.y + ") are out of bounds.");
            return;
        }
        set=false;
        gridCells[target.x][target.y].setBackground(Color.YELLOW);
        statusLabel.setText("Click on X: " + target.x + ", Y: " + target.y);
        for(int i=0;i<gridCells.length;i++){
            for(int j=0;j<gridCells[i].length;j++){
                gridCells[i][j]=new JTextField();
            }
        }
    }

    public static void main(String[] args) {
        SolverGUI frame = new SolverGUI();
        frame.setVisible(true);
        System.out.println(helper(frame.getOutputedSet()));
    }

    public static String helper(int[][] input){
        String constructor ="";
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[i].length;j++){
                constructor+=input[i][j]+" ";
            }
            constructor+="\n";
        }
        return constructor;
    }

}