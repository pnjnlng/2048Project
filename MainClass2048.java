import java.util.*;
public class MainClass2048 {
	//Global variables for various uses.
	private int[][] grid;
    private final int grid_size = 4;
    private Random random;
    private int numOfMoves;
    private boolean gameEnded;
    //Initializing.
    public MainClass2048() {
        grid = new int[grid_size][grid_size];
        random = new Random();
    }
    //Method to print grid using for loops & prints space after each move to make the console look cleaner.
    private void printGrid() {
    	if(numOfMoves != 0) {
    		System.out.println("\n \n \n \n \n");
    		}
    	System.out.println("-" + "\t" + "-" + "\t\t" + "-" + "\t\t" + "-" + "\t\t" + "-" + "\t" + "-");
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                System.out.print("\t" + grid[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-" + "\t" + "-" + "\t\t" + "-" + "\t\t" + "-" + "\t\t" + "-" + "\t" + "-");
    }
    //Method to start game, adds two random tiles, and if not game over asks for user input for next move.
    public void startGame() {
        addRandomTile();
        addRandomTile();
        while (!isGameOver()) {
            printGrid();
            System.out.println("Enter a move: w,a,s,d");
            System.out.println("If you wish to quit: q");
            System.out.println("If you wish to restart: r");
            Scanner sc = new Scanner(System.in);
            String move = sc.nextLine();
            if (makeMove(move)) {
            	if(checkBoard()) {
            		quitGame();
            	}
                addRandomTile();
            }
        } 
    }
    //Method used to set game to over, also prints out final board, game over message, number of moves, and biggest number on the board.
    private void quitGame(){
    	gameEnded = true;
        printGrid();
        System.out.println("Game Over!");
        System.out.println("The number of moves was " + numOfMoves + ".");
        System.out.println("The biggest value you got was " + biggestValue() +".");
    }
    //Method check if game is over
    private boolean isGameOver() {
    	if(gameEnded == true) {
    		return true;
    	}
        return false;
    }
    //Method used to check for empty tiles through array list implementation. For loop to check if the array value equals zero, if yes then it gets added to the emptyTiles list.
    private List<Integer> getEmptyTiles() {
        List<Integer> emptyTiles = new ArrayList<>();
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (grid[i][j] == 0) {
                    emptyTiles.add(i * grid_size + j);
                }
            }
        }
        return emptyTiles;
    }
    //Method to check if board is full
    private boolean checkBoard() {
    	int winNum = 2048;
    	int boardCount = 0;
    	int totalBoardSpc = 16;
    	for(int i = 0; i < grid_size; i ++) {
    		for(int j = 0; j < grid_size; j++) {
    			if(grid[i][j] > 0) {
    				boardCount++;
    				if(boardCount == totalBoardSpc) {
    					return true;
    				}
    				if(boardCount == winNum) {
    					System.out.println("You won!! Congrats you made 2048!!");
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    //Method used to add the values 2 & 4 on empty spaces using the previous method to check for empty tiles
    private void addRandomTile() {
        List<Integer> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = random.nextInt(emptyTiles.size());
            int value = random.nextInt(10) == 0 ? 4 : 2;
            int tile = emptyTiles.get(index);
            int row = tile / grid_size;
            int col = tile % grid_size;
            grid[row][col] = value;
        }
    }
    //Method to check moves user inputs using switch cases, if invalid move key input is ignored from numOfMoves and returns a message to alert user.
    private boolean makeMove(String move) {
        boolean moved = false;
        switch (move) {
            case "w":
            	numOfMoves++;
            	mergeUp();
                moved = moveUp();
                System.out.println("Number of moves is " + numOfMoves);
                System.out.println("Biggest value on the board is " + biggestValue());
                break;
            case "a":
            	numOfMoves++;
            	mergeLeft();
                moved = moveLeft();
                System.out.println("Number of moves is " + numOfMoves);
                System.out.println("Biggest value on the board is " + biggestValue());
                break;
            case "s":
            	numOfMoves++;
            	mergeDown();
                moved = moveDown();
                System.out.println("Number of moves is " + numOfMoves);
                System.out.println("Biggest value on the board is " + biggestValue());
                break;
            case "d":
            	numOfMoves++;
            	mergeRight();
                moved = moveRight();
                System.out.println("Number of moves is " + numOfMoves);
                System.out.println("Biggest value on the board is " + biggestValue());
                break;
            case "q":
            	quitGame();
            	break;
            case "r":
            	MainClass2048 game = new MainClass2048();
            	game.startGame();
            	break;
            default:
                System.out.println("Invalid move. Try again.");
        }
        return moved;
    }
    //Method used to return biggest value on the board once games end
    private int biggestValue() {
    	int bigVal = grid[0][0];
    	for(int i = 0; i < grid_size; i++) {
    		for(int j = 0; j < grid_size; j++) {
    			if(grid[i][j] > bigVal) {
    				bigVal = grid[i][j];
    			}
    		}
    	}
    	return bigVal;
    }
    //Move up method
    private boolean moveUp() {
    	for(int col = 0; col < grid_size; col++) {
    		int c = 0;
    		for(int row = 0; row < grid_size; row++) {
    			if(this.grid[row][col] != 0) {
    				this.grid[0 + c][col] = this.grid[row][col];
    				if(0 + c!= row) {
    					this.grid[row][col] = 0;
    					}
    				c++;
    				}
    			}
    		}
    	return true;
    }
    //Move left method
    private boolean moveLeft() {
    	for(int row = 0; row < grid_size; row++) {
    		int c = 0;
    		for(int col = 0; col < grid_size; col++) {
    			if(this.grid[row][col] != 0) {
    				this.grid[row][0 + c] = this.grid[row][col];
    				if(0 + c!= col) {
    					this.grid[row][col] = 0;
    					}
    				c++;
    				}
    			}
    		}
    	return true;
    }
    //Move down method
    private boolean moveDown() {
        for(int col = 0;col < grid_size; col++){
            int c = 0;
            for(int row=grid_size - 1; row >= 0; row--)
            {
               if(this.grid[row][col]!=0){
                  this.grid[grid_size-1-c][col]=this.grid[row][col];
                  if(grid_size-1-c!=row){
                  this.grid[row][col]=0;
                    }
                  c++;
                }
            }
        }
        return true;
    }
    //Move right method
    private boolean moveRight() {
        for(int row = 0; row < grid_size; row++){
            int c=0;
            for(int col = grid_size - 1; col>=0; col--){
            	if(this.grid[row][col]!=0){
            		this.grid[row][grid_size-1-c]=this.grid[row][col];
            		if(grid_size-1-c!=col){
            			this.grid[row][col]=0;
            			}
            		c++;
            		}  
            	}
            }
        return true;
    }
    private void mergeDown() {
    	boolean merged;
    	for(int i = 0; i < grid_size; i++) {
    		for(int j = 0; j < grid_size; j++) {
    		if(i < 3 && grid[i][j] == 0) {
    			continue;
    			}
    		if(i < 3 && grid[i][j] == grid[i + 1][j]) {
    			merged = true;
    			grid[i + 1][j] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(i < 2 && grid[i][j] == grid[i + 2][j]) {
    			merged = true;
    			grid[i + 2][j] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(i < 1 && grid[i][j] == grid[i +3 ][j]) {
    			merged = true;
    			grid[i + 3][j] *= 2;
    			grid[i][j] = 0;
    			}
    		}
    	}
    }
    private void mergeUp() {
    	boolean merged;
    	for(int i = 0; i < grid_size; i++) {
    		for(int j = 0; j < grid_size; j++) {
    		if(i < 3 && grid[i][j] == 0) {
    			continue;
    			}
    		if(i < 3 && grid[i][j] == grid[i + 1][j]) {
    			merged = true;
    			grid[i + 1][j] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(i < 2 && grid[i][j] == grid[i + 2][j]) {
    			merged = true;
    			grid[i + 2][j] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(i < 1 && grid[i][j] == grid[i + 3][j]) {
    			merged = true;
    			grid[i + 3][j] *= 2;
    			grid[i][j] = 0;
    			}
    		}
    	}
    }
    private void mergeLeft() {
    	boolean merged;
    	for(int i = 0; i < grid_size; i++) {
    		for(int j = 0; j < grid_size; j++) {
    		if(j < 3 && grid[i][j] == 0) {
    			continue;
    			}
    		if(j < 3 && grid[i][j] == grid[i][j + 1]) {
    			merged = true;
    			grid[i][j + 1] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(j < 2 && grid[i][j] == grid[i][j + 2]) {
    			merged = true;
    			grid[i][j + 2] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(j < 1 && grid[i][j] == grid[i][j + 3]) {
    			merged = true;
    			grid[i][j + 3] *= 2;
    			grid[i][j] = 0;
    			}
    		}
    	}
    }
    private void mergeRight() {
    	boolean merged;
    	for(int i = 0; i < grid_size; i++) {
    		for(int j = 0; j < grid_size; j++) {
    		if(j < 3 && grid[i][j] == 0) {
    			continue;
    			}
    		if(j < 3 && grid[i][j] == grid[i][j + 1]) {
    			merged = true;
    			grid[i][j + 1] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(j < 2 && grid[i][j] == grid[i][j + 2]) {
    			merged = true;
    			grid[i][j + 2] *= 2;
    			grid[i][j] = 0;
    			if(merged == false) {
    				continue;
    			}
    			}
    		if(j < 1 && grid[i][j] == grid[i][j + 3]) {
    			merged = true;
    			grid[i][j + 3] *= 2;
    			grid[i][j] = 0;
    			}
    		}
    	}
    }
    //Main Class, calls game to start.
    public static void main(String[] args) {
        MainClass2048 game = new MainClass2048();
        game.startGame();
    }
}