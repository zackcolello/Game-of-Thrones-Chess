package chessPieces;

/**
 * 
 * @author Zack Colello
 *
 */
public class Knight extends Pieces {
	
	public Knight(char color)
	{
		this.setName(color + "N");	
	}
	
	public boolean isValidMove(int[] currentLocation, int[] newLocation){
		
		//Knights moves are valid if it moves 2 in one direction, one in the other.
		
		//Up Up or Down down, then left/right
		if(Math.abs(currentLocation[0] - newLocation[0]) == 2){
			
			if (Math.abs(currentLocation[1] - newLocation[1]) == 1) {
				return true;
			}
		}
		
		//Left left or Right right, then up/down
		if(Math.abs(currentLocation[1] - newLocation[1]) == 2){
			if (Math.abs(currentLocation[0] - newLocation[0]) == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isValidCapture(int[] currentLocation, int[] newLocation){
		
		return isValidMove(currentLocation, newLocation);
	}

}
