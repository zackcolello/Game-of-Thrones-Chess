package chessPieces;

/**
 * 
 * @author Zack Colello
 *
 */
public class Queen extends Pieces {

	public Queen(char color) {
		this.setName(color + "Q");
	}

	public boolean isValidMove(int[] currentLocation, int[] newLocation) {

		//For horizontal or vertical moves
		
		// Same row, different column
		if (currentLocation[0] == newLocation[0]
				&& currentLocation[1] != newLocation[1]) {
			return true;

			// Same column, different row
		} else if (currentLocation[1] == newLocation[1]
				&& currentLocation[0] != newLocation[0]) {
			return true;
		}

		//For diagonal moves

		int largestrow = currentLocation[0] > newLocation[0] ? currentLocation[0]
				: newLocation[0];

		int smallestrow = currentLocation[0] < newLocation[0] ? currentLocation[0]
				: newLocation[0];

		int largestcolumn = currentLocation[1] > newLocation[1] ? currentLocation[1]
				: newLocation[1];

		int smallestcolumn = currentLocation[1] < newLocation[1] ? currentLocation[1]
				: newLocation[1];

		// This means the new and old location are diagonal from each other
		if ((largestrow - smallestrow) == largestcolumn - smallestcolumn) {
			return true;
		}

		return false;
	}

	public boolean isValidCapture(int[] currentLocation, int[] newLocation) {

		return isValidMove(currentLocation, newLocation);
	}

}
