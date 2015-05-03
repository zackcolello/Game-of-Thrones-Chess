package chessPieces;

/**
 * 
 * @author Zack Colello
 *
 */
public class Pawn extends Pieces {

	public Pawn(char color) {
		this.setName(color + "p");
	}

	public boolean isValidMove(int[] currentLocation, int[] newLocation) {

		// pawn can move one row up, but can move two rows up if numMoves = 0.
		// pawns also cannot go backwards.

		// make sure pawn is not trying to move backwards
		if (this.getName().charAt(0) == 'r') {

			if (newLocation[0] - currentLocation[0] >= 1) {
				return false;
			}

		} else {

			if (newLocation[0] - currentLocation[0] < 1) {
				return false;
			}
		}

		// if pawn has no moves yet, it can go two spaces
		if (this.getMoves() == 0) {
			if ((currentLocation[1] == newLocation[1])
					&& Math.abs(currentLocation[0] - newLocation[0]) <= 2) {
				return true;
			}
			// If pawn has moved, it can only go one space
		} else {

			if ((currentLocation[1] == newLocation[1])
					&& Math.abs(currentLocation[0] - newLocation[0]) == 1) {
				return true;
			}
		}

		return false;
	}

	public boolean isValidCapture(int[] currentLocation, int[] newLocation) {
		
		//If it is not exactly one column over, return false
		if(Math.abs(currentLocation[1] - newLocation[1]) != 1){
			return false;
		}
		
		// need to treat different colored pawns differently because they can't
				// go backwards
		
		//white pawn
		if (this.getName().charAt(0) == 'r') {
			
			if(currentLocation[0] - newLocation[0] == 1){
				return true;
			}
		//black pawn	
		}else{
			if(newLocation[0] - currentLocation[0] == 1){
				return true;
			}
		}

		return false;
	}

}
