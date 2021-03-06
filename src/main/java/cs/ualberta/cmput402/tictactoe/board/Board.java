package cs.ualberta.cmput402.tictactoe.board;

import cs.ualberta.cmput402.tictactoe.board.exceptions.InvalidMoveException;

/**
 * Created by snadi on 2018-07-16.
 */
public class Board {

    public enum Player {X, O, NONE};
    private Player currentPlayer;
    private Player winner;
    private Player board[][];
    private int scoreBoard[][];
    private boolean draw;//draw is to detect draw


    public Board(){
        board = new Player[3][3];
        scoreBoard = new int[2][3];
        initBoard();
    }

    public void initBoard(){
        winner = null;
        draw = false;
        currentPlayer = Player.X;
        for (int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = Player.NONE;

    }

    public void playMove(int row, int col) throws InvalidMoveException {

        if(row < 0 || row >= 3 || col <0 || col >=3)
            throw new InvalidMoveException("Input coordinates are outside the board. Try again");

        if(!isSquareAvailable(row, col)){
            //the given coordinates already contain a played move
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid Move: Square ");
            stringBuilder.append(row);
            stringBuilder.append(",");
            stringBuilder.append(col);
            stringBuilder.append(" already contains ");
            stringBuilder.append(getSymbol(board[row][col]));
            throw new InvalidMoveException(stringBuilder.toString());
        }else{
            board[row][col] = currentPlayer;

            if (hasWon(row, col))
                winner = currentPlayer;
            else if(hasDraw())
                draw = true;
            else if(currentPlayer == Player.X)
                currentPlayer = Player.O;
            else
                currentPlayer = Player.X;
        }

    }


    private boolean isSquareAvailable(int row, int col){
        return (board[row][col] != Player.X && board[row][col] != Player.O);
    }

    public String getSymbol(Player player){
        switch(player){
            case X:
                return "X";
            case O:
                return "O";
            case NONE:
                return " ";
            default:
                return "UNKNOWN SYMBOL";
        }
    }

    public boolean hasWon(int lastColPlayed, int lastRowPlayed){

        //check horizontal
        if (board[lastColPlayed][0].equals(board[lastColPlayed][1]) && board[lastColPlayed][1].equals(board[lastColPlayed][2])){
            return true;
        }
        //check vertical
        else if(board[0][lastRowPlayed].equals(board[1][lastRowPlayed]) && board[1][lastRowPlayed].equals(board[2][lastRowPlayed])){
            return true;
        }
        //check diagonal
        else{
            if(isOnRightDiag(lastColPlayed, lastRowPlayed) && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
                return true;
            else if (isOnLeftDiag(lastColPlayed, lastRowPlayed) && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
                return true;
        }

        return false;
    }

    public boolean hasDraw(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == Player.NONE)
                    return false;
            }
        }
        return true;
    }

    private boolean isOnRightDiag(int col, int row){
        return (col == 0 && row == 0) || (col == 1 && row == 1) || (col == 2 & row == 2);
    }

    private boolean isOnLeftDiag(int col, int row){
        return (col == 0 && row == 2) || (col == 1 && row == 1) || (col == 2 & row == 0);
    }


    public void printBoard(){
        for(int i  = 0; i < 3; i++){
            for(int j = 0 ; j < 3; j++){

                System.out.print(getSymbol(board[i][j]));

                if (j == 2)
                    System.out.println("");
                else
                    System.out.print(" | ");
            }
            System.out.println("----------");
        }
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isDraw() {
        return draw;
    }

    public Player getPlayerAtPos(int row, int col){
        return board[row][col];
    }
    
    public void incrementNumOfWin(Player player) {
    	switch(player){
        	case X:
        		scoreBoard[0][0] += 1;
        		break;
        	case O:
        		scoreBoard[1][0] += 1;
        		break;
        	default:
    			break;
    	}
	}
	
	public void incrementNumOfLoses(Player player) {
		switch(player){
    		case X:
    			scoreBoard[0][1] += 1;
    			break;
    		case O:
    			scoreBoard[1][1] += 1;
    			break;
    		default:
    			break;
		}
	}
	
	public void incrementNumOfDraws(Player player) {
		switch(player){
    		case X:
    			scoreBoard[0][2] += 1;
    			break;
    		case O:
    			scoreBoard[1][2] += 1;
    			break;
    		default:
    			break;
		}
	}
	
	public void printScoreBoard() {
		System.out.println("Score:");
		System.out.println("Wins  (X, 0): " + this.scoreBoard[0][0] + ", " + this.scoreBoard[1][0] + "; ");
		System.out.println("Loses (X, 0): " + this.scoreBoard[0][1] + ", " + this.scoreBoard[1][1] + "; ");
		System.out.println("Draws (X, 0): " + this.scoreBoard[0][2] + ", " + this.scoreBoard[1][2] + ";");
	}


}
