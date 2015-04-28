package chessPieces;

/**
 * 
 * @author Anna Genke
 *
 */
public abstract class Pieces {
	
	/**
	 * Number of moves the piece has made.
	 */
	private int moves = 0;
	/**
	 * Name of the pieces.
	 */
	private String name = "";
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getMoves(){
		return moves;
	}
	
	public void incrementMoves(){
		moves++;
	}
	
	public void decrementMoves(){
		moves--;
	}
	
	/**
	 * @param currentLocation array the holds coordinates of current location of a piece.
	 * @param newLocation array the holds coordinates of intended location of a piece.
	 * @return true if the move is valid, false otherwise.
	 * Checks to see if the intended move is legal.
	 * Implemented differently depending on piece.
	 */
	public abstract boolean isValidMove(int[] currentLocation, int[] newLocation);
	
	/**
	 * @param currentLocation array the holds coordinates of current location of a piece.
	 * @param newLocation array the holds coordinates of intended location of a piece.
	 * @return true if the capture is valid, false otherwise.
	 * Checks to see if the intended move is legal.
	 * Implemented differently depending on piece.
	 */
	public abstract boolean isValidCapture(int[] currentLocation, int[] newLocation);
}
