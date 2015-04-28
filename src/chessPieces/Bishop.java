package chessPieces;

/**
 * 
 * @author Anna Genke
 *
 */
public class Bishop extends Pieces {
	
	public Bishop(char color)
	{
		this.setName(color + "B");
	}
	
	public boolean isValidMove(int[] currentLocation, int[] newLocation){
		
		//Bishop can only move diagonally.
		
		int largestrow = currentLocation[0] > newLocation[0] ? currentLocation[0]
				: newLocation[0];

		int smallestrow = currentLocation[0] < newLocation[0] ? currentLocation[0]
				: newLocation[0];

		int largestcolumn = currentLocation[1] > newLocation[1] ? currentLocation[1]
				: newLocation[1];

		int smallestcolumn = currentLocation[1] < newLocation[1] ? currentLocation[1]
				: newLocation[1];
		
		
		//This means the new and old location are diagonal from eachother
		if((largestrow - smallestrow) == largestcolumn - smallestcolumn){
			return true;
		}
		
		return false;
	}
	
	public boolean isValidCapture(int[] currentLocation, int[] newLocation){
		
		return this.isValidMove(currentLocation, newLocation);
	}

}
