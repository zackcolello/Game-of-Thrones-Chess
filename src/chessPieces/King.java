package chessPieces;

/**
 * 
 * @author Anna Genke
 *
 */
public class King extends Pieces {

	public King(char color) {
		this.setName(color + "K");
	}

	public boolean isValidMove(int[] currentLocation, int[] newLocation) {

		//TODO: Put special case here for castling.
		
		// if row is the same, make sure it is just one column over
		if (currentLocation[0] == newLocation[0]) {

			if (Math.abs(currentLocation[1] - newLocation[1]) == 1) {
				return true;
			}
			// If column the same, make sure it is just one row over
		} else if (currentLocation[1] == newLocation[1]) {

			if (Math.abs(currentLocation[0] - newLocation[0]) == 1) {
				return true;
			}
			// If both row and column are different, make sure King is just
			// moving one space
		} else {
			if ((Math.abs(currentLocation[0] - newLocation[0]) == 1)
					&& (Math.abs(currentLocation[1] - newLocation[1]) == 1)) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isValidCapture(int[] currentLocation, int[] newLocation) {

		//A kings capture will be valid if it's move is valid.
		return isValidMove(currentLocation, newLocation);
	}

}
