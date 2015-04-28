package chessPieces;

/**
 * 
 * @author Anna Genke
 *
 */
public class Rook extends Pieces {

	public Rook(char color) {
		this.setName(color + "R");

	}

	public boolean isValidMove(int[] currentLocation, int[] newLocation) {

		// Move is valid if the currentLocation and endLocation have the same
		// row OR column.

		//Same row, different column
		if(currentLocation[0] == newLocation[0] && currentLocation[1] != newLocation[1]){
			return true;
			
		//Same column, different row	
		}else if(currentLocation[1] == newLocation[1] && currentLocation[0] != newLocation[0]){
			return true;
		}
		
		return false;
	}

	public boolean isValidCapture(int[] currentLocation, int[] newLocation) {

		return isValidMove(currentLocation, newLocation);
	}

}
