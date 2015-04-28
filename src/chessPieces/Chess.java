package chessPieces;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 * @author Zack Colello (check/checkmate, pawn promotion, stalemate)
 * @author Anna Genke (castling, enpassant)
 *
 */
public class Chess {

	/**
	 * ASCII representation of empty board.
	 */
	static String[][] board;

	/**
	 * 2D array of pieces in play.
	 */
	static Pieces[][] pieces;
	/**
	 * Holds the pieces that have been captured.
	 */
	static ArrayList<Pieces> graveyard;

	/**
	 * Points to last piece moved.
	 */
	static Pieces lastMove = null;

	/**
	 * Holds w or b if in check.
	 */
	static char isInCheck;

	/**
	 * Creates and initializes chess board.
	 */
	public static void InitializeBoard() {

		board = new String[][] {
				{ "##", "  ", "##", "  ", "##", "  ", "##", "  ", "8" },
				{ "  ", "##", "  ", "##", "  ", "##", "  ", "##", "7" },
				{ "##", "  ", "##", "  ", "##", "  ", "##", "  ", "6" },
				{ "  ", "##", "  ", "##", "  ", "##", "  ", "##", "5" },
				{ "##", "  ", "##", "  ", "##", "  ", "##", "  ", "4" },
				{ "  ", "##", "  ", "##", "  ", "##", "  ", "##", "3" },
				{ "##", "  ", "##", "  ", "##", "  ", "##", "  ", "2" },
				{ "  ", "##", "  ", "##", "  ", "##", "  ", "##", "1" },
				{ " a", " b", " c", " d", " e", " f", " g", " h", "" } };

		pieces = new Pieces[8][8];
		graveyard = new ArrayList<Pieces>();

		pieces[0][0] = new Rook('b');
		pieces[0][1] = new Knight('b');
		pieces[0][2] = new Bishop('b');
		pieces[0][3] = new Queen('b');
		pieces[0][4] = new King('b');
		pieces[0][5] = new Bishop('b');
		pieces[0][6] = new Knight('b');
		pieces[0][7] = new Rook('b');
		pieces[1][0] = new Pawn('b');
		pieces[1][1] = new Pawn('b');
		pieces[1][2] = new Pawn('b');
		pieces[1][3] = new Pawn('b');
		pieces[1][4] = new Pawn('b');
		pieces[1][5] = new Pawn('b');
		pieces[1][6] = new Pawn('b');
		pieces[1][7] = new Pawn('b');

		pieces[7][0] = new Rook('w');
		pieces[7][1] = new Knight('w');
		pieces[7][2] = new Bishop('w');
		pieces[7][3] = new Queen('w');
		pieces[7][4] = new King('w');
		pieces[7][5] = new Bishop('w');
		pieces[7][6] = new Knight('w');
		pieces[7][7] = new Rook('w');
		pieces[6][0] = new Pawn('w');
		pieces[6][1] = new Pawn('w');
		pieces[6][2] = new Pawn('w');
		pieces[6][3] = new Pawn('w');
		pieces[6][4] = new Pawn('w');
		pieces[6][5] = new Pawn('w');
		pieces[6][6] = new Pawn('w');
		pieces[6][7] = new Pawn('w');
	}

	/**
	 * Prints ASCII representation of board.
	 */
	public static void printBoard() {

		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 9; j++) {

				if (j != 8 && i != 8 && pieces[i][j] != null) {
					System.out.print(pieces[i][j].getName() + " ");
				} else {
					System.out.print(board[i][j] + " ");
				}

			}
			System.out.println();
		}
	}

	/**
	 * Checks to see if the game is in check.
	 * 
	 * @return true if the last move put the game in check, false otherwise.
	 */
	public static boolean isInCheck() {

		// Loop through pieces array, check if king would be a valid capture

		int[] wKLocation = new int[2];
		int[] bKLocation = new int[2];
		int[] targetKing = new int[2]; // used to know which king to search for

		// Get location of both kings to be used
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] != null) {

					if (pieces[i][j].getName().equals("wK")) {
						wKLocation[0] = i;
						wKLocation[1] = j;
					}
					if (pieces[i][j].getName().equals("bK")) {
						bKLocation[0] = i;
						bKLocation[1] = j;
					}
				}
			}
		}

		// Loop through board, see if a piece can capture a king.
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (pieces[i][j] != null) {

					if (pieces[i][j].getName().charAt(0) == 'w') {
						targetKing = bKLocation;
					} else {
						targetKing = wKLocation;
					}

					int[] temp = new int[2];
					temp[0] = i;
					temp[1] = j;

					if (pieces[i][j].isValidCapture(temp, targetKing)
							&& !piecesInTheWay(temp, targetKing)) {
						isInCheck = pieces[targetKing[0]][targetKing[1]]
								.getName().charAt(0);
						return true;
					}

				}
			}
		}

		return false;
	}

	/**
	 * Checks if the game is in check mate. When true, game ends.
	 * 
	 * @return true if game is in check mate, false otherwise.
	 */
	public static boolean isInCheckMate() {

		// To see if checkmate is true:
		// -Call isInCheckMate if the piece is in check after every time a piece
		// is moved, so isInCheckMate assumes isInCheck is true
		// -Have to check every piece of color in checkmate and see if any
		// possible move they can make would result in isInCheck now returning
		// false
		// If every possible move of all pieces for the color that is in check
		// still
		// Results in check, then it is checkmate.

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// make sure piece is there and it is the same color of the king
				// that is in check

				if (pieces[i][j] != null
						&& pieces[i][j].getName().charAt(0) == isInCheck) {

					// for all pieces, need to see if moving them would result
					// in not check. temporarily move every piece to every spot
					// that is a valid move, see if it is still in check. After,
					// move it back to its original spot.

					int[] temp = new int[2];
					temp[0] = i;
					temp[1] = j;

					int[] temp2 = new int[2];

					for (int k = 0; k < 8; k++) {
						for (int l = 0; l < 8; l++) {

							temp2[0] = k;
							temp2[1] = l;

							if (pieces[k][l] == null
									&& pieces[i][j].isValidMove(temp, temp2)) {

								// temporarily store current piece at new spot
								pieces[k][l] = pieces[i][j];
								pieces[i][j] = null;

								if (!isInCheck()) {

									// reset pieces for next iteration
									pieces[i][j] = pieces[k][l];
									pieces[k][l] = null;

									return false;
								}

								// reset pieces for next iteration
								pieces[i][j] = pieces[k][l];
								pieces[k][l] = null;

								// Could get out of checkmate with a capture.
								// Check that potential capture is not same
								// color, and it is a valid capture.
							} else if (pieces[k][l] != null
									&& pieces[i][j].getName().charAt(0) != pieces[k][l]
											.getName().charAt(0)
									&& pieces[i][j].isValidCapture(temp, temp2)) {

								Pieces tempPiece = pieces[k][l];

								// temporarily store current piece at new spot
								pieces[k][l] = pieces[i][j];
								pieces[i][j] = null;

								if (!isInCheck()) {

									// reset pieces for next iteration
									pieces[i][j] = pieces[k][l];
									pieces[k][l] = tempPiece;

									return false;
								}

								// reset pieces for next iteration
								pieces[i][j] = pieces[k][l];
								pieces[k][l] = tempPiece;

							}

						}
					}

				}
			}
		}

		return true;
	}

	/**
	 * @param color
	 *            char representing the player whose turn it is.
	 * @return true if in stale mate, false otherwise.
	 */
	public static boolean isInStaleMate(char color) {

		// Stalemate occurs when not in check, but any move will put you in
		// check.

		if (isInCheck()) {
			return false;
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				// make sure piece is there and it is the same color that we are
				// checking
				if (pieces[i][j] != null
						&& pieces[i][j].getName().charAt(0) == color) {

					// for all pieces, need to see if moving them would result
					// in check. temporarily move every piece to every spot
					// that is a valid move, see if it is in check. After,
					// move it back to its original spot.

					int[] temp = new int[2];
					temp[0] = i;
					temp[1] = j;

					int[] temp2 = new int[2];

					for (int k = 0; k < 8; k++) {
						for (int l = 0; l < 8; l++) {

							temp2[0] = k;
							temp2[1] = l;

							if (pieces[k][l] == null
									&& pieces[i][j].isValidMove(temp, temp2)
									&& !piecesInTheWay(temp, temp2)) {

								// temporarily store current piece at new spot
								pieces[k][l] = pieces[i][j];
								pieces[i][j] = null;

								if (!isInCheck()) {

									pieces[i][j] = pieces[k][l];
									pieces[k][l] = null;

									return false;
								}

								// reset pieces for next iteration
								pieces[i][j] = pieces[k][l];
								pieces[k][l] = null;

								// Could get out of checkmate with a capture.
								// Check that potential capture is not same
								// color, and it is a valid capture.
							} else if (pieces[k][l] != null
									&& pieces[i][j].getName().charAt(0) != pieces[k][l]
											.getName().charAt(0)
									&& pieces[i][j].isValidCapture(temp, temp2)
									&& !piecesInTheWay(temp, temp2)) {

								Pieces tempPiece = pieces[k][l];

								// temporarily store current piece at new spot
								pieces[k][l] = pieces[i][j];
								pieces[i][j] = null;

								if (!isInCheck()) {

									// reset pieces for next iteration
									pieces[i][j] = pieces[k][l];
									pieces[k][l] = tempPiece;

									return false;
								}

								// reset pieces for next iteration
								pieces[i][j] = pieces[k][l];
								pieces[k][l] = tempPiece;

							}

						}
					}

				}
			}
		}

		return true;

	}

	/**
	 * @param startLocation
	 *            array holding the coordinates of the current piece
	 * @param endLocation
	 *            array holding the coordinates of the attempted move.
	 * @return true if pieces are between the two locations, false otherwise
	 *         This method assumes that a piece is making a valid move. Checks
	 *         if there are any pieces in the way from start to end location.
	 *         (Does not check startLocation or endLocation, only pieces in
	 *         between)
	 */
	public static boolean piecesInTheWay(int[] startLocation, int[] endLocation) {

		// Vertical move, columns the same, rows different
		if (startLocation[1] == endLocation[1]) {

			int largest = startLocation[0] > endLocation[0] ? startLocation[0]
					: endLocation[0];

			int smallest = startLocation[0] < endLocation[0] ? startLocation[0]
					: endLocation[0];

			for (int i = smallest + 1; i < largest; i++) {
				if (pieces[i][startLocation[1]] != null) {
					return true;
				}
			}
			// Horizontal move, row the same, columns different
		} else if (startLocation[0] == endLocation[0]) {

			int largest = startLocation[1] > endLocation[1] ? startLocation[1]
					: endLocation[1];

			int smallest = startLocation[1] < endLocation[1] ? startLocation[1]
					: endLocation[1];

			for (int i = smallest + 1; i < largest; i++) {
				if (pieces[startLocation[0]][i] != null) {
					return true;
				}
			}
			// Diagonal move or knight move. Both row and column different
		} else {

			// if starting piece is Knight, it can jump over pieces, return
			// false
			if (pieces[startLocation[0]][startLocation[1]] instanceof Knight) {
				return false;
			}

			boolean goUp; // used to know if piece is moving up or down board

			int largestrow = startLocation[0] > endLocation[0] ? startLocation[0]
					: endLocation[0];

			if (largestrow == startLocation[0]) {
				goUp = true;
			} else {
				goUp = false;
			}

			int largestcolumn = startLocation[1] > endLocation[1] ? startLocation[1]
					: endLocation[1];

			boolean goLeft;

			if (largestcolumn == startLocation[1]) {
				goLeft = true;
			} else {
				goLeft = false;
			}

			// Check for diagonal pieces

			if (goUp && goLeft) {

				for (int i = startLocation[0] - 1; i > endLocation[0]; i--) {
					for (int j = startLocation[1] - 1; j > endLocation[1]; i--, j--) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			} else if (goUp && !goLeft) {

				for (int i = startLocation[0] - 1; i > endLocation[0]; i--) {
					for (int j = startLocation[1] + 1; j < endLocation[1]; i--, j++) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			} else if (!goUp && goLeft) {

				for (int i = startLocation[0] + 1; i < endLocation[0]; i++) {
					for (int j = startLocation[1] - 1; j > endLocation[1]; i++, j--) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			} else {
				for (int i = startLocation[0] + 1; i < endLocation[0]; i++) {
					for (int j = startLocation[1] + 1; j < endLocation[1]; i++, j++) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			}

		}
		return false;
	}

	/**
	 * @param input
	 *            String typed by player.
	 * @return array of integers that are coordinates on board. this method
	 *         converts an input (ex: a4) to the index in the 2d arrays board
	 *         and pieces. Returns int[] with [row], [column] of piece
	 */
	public static int[] inputToIndex(String input) {

		if (input.length() != 2) {
			return null;
		}

		int output[] = new int[2];
		output[0] = -1;
		output[1] = -1;

		char firstIndex = (char) input.charAt(0);
		int secondIndex = (char) input.charAt(1);

		switch (firstIndex) {
		case 'a':
			output[1] = 0;
			break;
		case 'b':
			output[1] = 1;
			break;
		case 'c':
			output[1] = 2;
			break;
		case 'd':
			output[1] = 3;
			break;
		case 'e':
			output[1] = 4;
			break;
		case 'f':
			output[1] = 5;
			break;
		case 'g':
			output[1] = 6;
			break;
		case 'h':
			output[1] = 7;
			break;
		default:
			return null;
		}

		secondIndex = (int) Integer.parseInt(String.valueOf(input.charAt(1)));

		output[0] = 8 - secondIndex;

		return output;
	}

	/**
	 * @param location
	 *            int[] of location.
	 * @param color
	 *            char representing whose turn it is.
	 * @param promotion
	 *            char representation of desired promotion.
	 * @return true on success, false on failure.
	 */
	public static boolean promotePawn(int[] location, char color, char promotion) {

		// Use d for default if no promotion piece was specified.
		// Queen by default

		if (promotion == 'd') {

			Queen queen = new Queen(color);
			pieces[location[0]][location[1]] = queen;

			// queen was not specified by default. Find piece
		} else {

			switch (promotion) {
			case 'B':
				Bishop bishop = new Bishop(color);
				pieces[location[0]][location[1]] = bishop;
				break;
			case 'N':
				Knight knight = new Knight(color);
				pieces[location[0]][location[1]] = knight;
				break;
			case 'Q':
				Queen queen = new Queen(color);
				pieces[location[0]][location[1]] = queen;
				break;
			case 'R':
				Rook rook = new Rook(color);
				pieces[location[0]][location[1]] = rook;
				break;
			default:
				return false;
			}
		}

		return true;

	}

	public static void main(String[] args) {

		InitializeBoard();
		Pieces lastMove = null;

		boolean drawSetThisTurn = false; // used to see when draw was set
		boolean potentialDraw = false; // used to see if draw has been requested
		char whosTurn = 'w'; // Keeps track of whos turn it is
		Scanner sc = new Scanner(System.in);

		while (true) {

			printBoard();

			if (isInCheck()) {
				if (isInCheckMate()) {

					System.out.println("Checkmate");

					// game over, print who won based on who was just in check
					if (isInCheck == 'w') {
						System.out.println("Black wins");
					} else {
						System.out.println("White wins");
					}
					return;

				} else {
					System.out.println("Check");
				}

				// check to see if stalemate
			} else {
				if (isInStaleMate(whosTurn)) {
					System.out.println("Stalemate\nDraw");
					return;
				}
			}

			if (whosTurn == 'w') {
				System.out.print("White's move: ");
			} else {
				System.out.print("Black's move: ");
			}

			// read in User's move
			ArrayList<String> input = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(sc.nextLine());

			while (st.hasMoreTokens()) {
				input.add(st.nextToken());
			}

			if (input.size() == 3) { // potential draw or pawn promotion

				// make sure first two arguments are valid moves

				if (inputToIndex(input.get(0)) == null
						|| inputToIndex(input.get(1)) == null) {
					System.out.println("Illegal move, try again");
					continue;
				}

				if (input.get(2).equals("draw?")) {
					potentialDraw = true;
					drawSetThisTurn = true;
				} else {
					System.out.println("Invalid number of arguments.");
					continue;
				}

			} else if (input.size() > 3 || input.size() == 0) {
				System.out.println("Invalid number of arguments.");
				continue;
			} else if (input.size() == 1) { // check if draw or resign

				if (input.get(0).equals("resign")) {

					if (whosTurn == 'w') {
						System.out.println("Black wins");
						return;
					} else {
						System.out.println("White wins");
						return;
					}

				} else if (input.get(0).equals("draw")) {

					if (potentialDraw) {
						System.out.println("Draw");
						return;
					} else {
						System.out.println("A draw has not been offered.");
						continue;
					}

				} else {
					System.out.println("Invalid number of arguments.");
					continue;
				}

			}

			if (!drawSetThisTurn) {
				if (potentialDraw) {
					potentialDraw = false;
				}
			}

			drawSetThisTurn = false;

			// Used for debugging for now
			if (input.get(0).equals("quit")) {
				break;
			}

			int[] startLocation = inputToIndex(input.get(0));
			int[] endLocation = inputToIndex(input.get(1));

			if (startLocation == null || endLocation == null) {
				System.out.println("Illegal move, try again");
				continue;
			}

			// make sure indices are in bounds
			if (startLocation[0] < 0 || startLocation[0] > 8
					|| startLocation[1] < 0 || startLocation[1] > 8
					|| endLocation[0] < 0 || endLocation[0] > 8
					|| endLocation[1] < 0 || endLocation[1] > 8) {
				System.out.println("Illegal move, try again");
				continue;
			}

			Pieces currentPiece = (pieces[startLocation[0]][startLocation[1]]);

			// check if piece is at location
			if (currentPiece == null) {
				System.out
						.println("No piece at location " + input.get(0) + ".");
				continue;
				// check to make sure the right color is moving depending on
				// whose turn it is
			} else if (currentPiece.getName().charAt(0) != whosTurn) {

				System.out.print("Error: Trying to move a ");
				if (whosTurn == 'w') {
					System.out.println("Black piece when it is White's turn.");
				} else {
					System.out.println("White piece when it is Black's turn.");
				}
				continue;
			}

			// Check to see if it's a valid move. If not, check if it's a valid
			// capture.
			if (currentPiece.isValidMove(startLocation, endLocation)) {
				// check if there is a piece in new location. If so, check if
				// valid capture.
				if (pieces[endLocation[0]][endLocation[1]] != null) {

					// Before checking if it can capture the piece, make sure it
					// is a different color
					if (currentPiece.getName().charAt(0) == pieces[endLocation[0]][endLocation[1]]
							.getName().charAt(0)) {
						System.out.println("Illegal move, try again");
						continue;
					}

					// if there is a piece of different color, check if it can
					// capture it
					if (currentPiece.isValidCapture(startLocation, endLocation)) {

						// check to make sure there are no pieces in the way.
						if (!piecesInTheWay(startLocation, endLocation)) {

							Pieces tempPiece = pieces[endLocation[0]][endLocation[1]];

							// Add captured piece to graveyard, move piece there
							graveyard
									.add(pieces[endLocation[0]][endLocation[1]]);
							pieces[endLocation[0]][endLocation[1]] = currentPiece;
							lastMove = currentPiece;
							pieces[startLocation[0]][startLocation[1]] = null;

							// make sure this does not result in a check

							if (isInCheck() && isInCheck == whosTurn) {
								System.out.println("Illegal move, try again");
								pieces[startLocation[0]][startLocation[1]] = currentPiece;
								pieces[endLocation[0]][endLocation[1]] = tempPiece;
								continue;
							}

							// Do this check to see if pawn promotion is
							// happening

							if (currentPiece instanceof Pawn) {

								// can do this check since pawns can't move
								// backwards
								if (endLocation[0] == 7 || endLocation[0] == 0) {

									// promotion has happened, check if default
									// or not

									if (input.size() == 3) {

										if (input.get(2).length() != 1) {
											System.out
													.println("Illegal move, try again");
											continue;
										}

										if (promotePawn(endLocation,
												currentPiece.getName()
														.charAt(0), input
														.get(2).charAt(0))) {
											// pawn has been promoted, do
											// nothing
										} else {
											System.out
													.println("Illegal move, try again");
										}

										// no promotion has happened, pass
										// default 'd'
									} else {

										if (promotePawn(endLocation,
												currentPiece.getName()
														.charAt(0), 'd')) {
											// pawn has been promoted, do
											// nothing
										} else {
											System.out
													.println("Illegal move, try again");
										}
									}
								}
							}

						} else {
							System.out.println("Illegal move, try again");
							continue;
						}

					} else {
						// I believe this will only happen when a pawn moves and
						// a piece is in the way

						System.out.println("Illegal move, try again");
						continue;
					}

					// No piece there. Check to make sure no pieces in the way
				} else {
					// If no pieces in the way, move the piece there
					if (!piecesInTheWay(startLocation, endLocation)) {

						pieces[startLocation[0]][startLocation[1]]
								.incrementMoves();
						pieces[endLocation[0]][endLocation[1]] = currentPiece;
						lastMove = currentPiece;
						pieces[startLocation[0]][startLocation[1]] = null;

						// make sure this does not result in a check

						if (isInCheck() && isInCheck == whosTurn) {
							System.out.println("Illegal move, try again");
							pieces[startLocation[0]][startLocation[1]] = currentPiece;
							pieces[endLocation[0]][endLocation[1]] = null;
							continue;
						}

						// Do this check to see if pawn promotion is
						// happening

						if (currentPiece instanceof Pawn) {

							// can do this check since pawns can't move
							// backwards
							if (endLocation[0] == 7 || endLocation[0] == 0) {

								// promotion has happened, check if default
								// or not

								if (input.size() == 3) {

									if (input.get(2).length() != 1) {
										System.out
												.println("Illegal move, try again");
										continue;
									}

									if (promotePawn(endLocation, currentPiece
											.getName().charAt(0), input.get(2)
											.charAt(0))) {
										// pawn has been promoted, do nothing
									} else {
										System.out
												.println("Illegal move, try again");
									}

									// no promotion has happened, pass default
									// 'd'
								} else {

									if (promotePawn(endLocation, currentPiece
											.getName().charAt(0), 'd')) {
										// pawn has been promoted, do nothing
									} else {
										System.out
												.println("Illegal move, try again");
									}
								}
							}
						}

					} else {
						System.out.println("Illegal move, try again");
						continue;
					}
				}

				// Not a valid move, but could be valid capture. This happens
				// with pawns.
			} else {

				if ((currentPiece.getName().charAt(1) == 'K')
						&& (currentPiece.getMoves() == 0)) {
					if ((startLocation[0] == endLocation[0])
							&& (Math.abs(startLocation[1] - endLocation[1]) == 2)) {
						// attempting castling
						Pieces last = lastMove;
						if(isInCheck()){
							if(isInCheck==whosTurn){
								System.out
								.println("Illegal Move (cannot use castling to get out of check), try again");
								continue;
							}
						}
						if (whosTurn == 'w') {
							int[] king = { 7, 4 };
							if (endLocation[1] == 6) {
								int[] rook = { 7, 7 };
								if ((pieces[7][7] != null)
										&& (pieces[7][7].getMoves() == 0)
										&& (!piecesInTheWay(king, rook))) {
									pieces[7][6] = pieces[7][4];
									pieces[7][5] = pieces[7][7];
									pieces[7][7] = null;
									pieces[7][4] = null;
									pieces[7][6].incrementMoves();
									lastMove = pieces[7][6];

									if (isInCheck()) {
										if(isInCheck==whosTurn){
										System.out
												.println("Illegal Move (puts king in check), try again");
										pieces[7][4] = pieces[7][6];
										pieces[7][7] = pieces[7][5];
										pieces[7][5] = null;
										pieces[7][7] = null;
										pieces[7][4].decrementMoves();
										lastMove = last;
										}
									}
									continue;
								}
							}
							if (endLocation[1] == 2) {
								int[] rook = { 7, 0 };
								if ((pieces[7][0] != null)
										&& (pieces[7][0].getMoves() == 0)
										&& (!piecesInTheWay(king, rook))) {
									pieces[7][2] = pieces[7][4];
									pieces[7][3] = pieces[7][0];
									pieces[7][0] = null;
									pieces[7][4] = null;
									pieces[7][2].incrementMoves();
									lastMove = pieces[7][2];

									if (isInCheck()) {
										if(isInCheck==whosTurn){
										System.out
												.println("Illegal Move (puts king in check), try again");
										pieces[7][0] = pieces[7][3];
										pieces[7][4] = pieces[7][2];
										pieces[7][2] = null;
										pieces[7][3] = null;
										pieces[7][4].decrementMoves();
										lastMove = last;
										}
									}
									continue;
								}
							}
						}
						if (whosTurn == 'b') {
							int[] king = { 0, 4 };
							if (endLocation[1] == 6) {
								int[] rook = { 0, 7 };
								if ((pieces[0][7] != null)
										&& (pieces[0][7].getMoves() == 0)
										&& (!piecesInTheWay(king, rook))) {
									pieces[0][6] = pieces[0][4];
									pieces[0][5] = pieces[0][7];
									pieces[0][7] = null;
									pieces[0][4] = null;
									pieces[0][6].incrementMoves();
									lastMove = pieces[0][6];

									if (isInCheck()) {
										if(isInCheck==whosTurn){
										System.out
												.println("Illegal Move (puts king in check), try again");
										pieces[0][4] = pieces[7][6];
										pieces[0][7] = pieces[7][5];
										pieces[0][5] = null;
										pieces[0][7] = null;
										pieces[0][4].decrementMoves();
										lastMove = last;
										}

									}
									continue;
								}
							}
							if (endLocation[1] == 2) {
								int[] rook = { 0, 0 };
								if ((pieces[0][0] != null)
										&& (pieces[0][0].getMoves() == 0)
										&& (!piecesInTheWay(king, rook))) {
									pieces[0][2] = pieces[0][4];
									pieces[0][3] = pieces[0][0];
									pieces[0][0] = null;
									pieces[0][4] = null;
									pieces[0][2].incrementMoves();
									lastMove = pieces[0][2];

									if (isInCheck()) {
										if(isInCheck==whosTurn){
										System.out
												.println("Illegal Move (puts king in check), try again");
										pieces[0][0] = pieces[0][3];
										pieces[0][4] = pieces[0][2];
										pieces[0][2] = null;
										pieces[0][3] = null;
										pieces[0][4].decrementMoves();
										lastMove = last;
										}
									}
									continue;
								}
							}
						}
					}
				}

				// If piece is not there, nothing to capture
				if (pieces[endLocation[0]][endLocation[1]] == null) {
					// check for en Passant
					if ((whosTurn == 'w')
							&& (currentPiece.getName().charAt(1) == 'p')) {

						if ((pieces[endLocation[0] + 1][endLocation[1]] != null)
								&& (pieces[endLocation[0] + 1][endLocation[1]] == lastMove)
								&& (pieces[endLocation[0] + 1][endLocation[1]]
										.getMoves() == 1)
								&& (pieces[endLocation[0] + 1][endLocation[1]]
										.getName().equals("bp"))) {
							Pieces temp = pieces[endLocation[0] + 1][endLocation[1]];
							Pieces last = lastMove;
							graveyard
									.add(pieces[endLocation[0] + 1][endLocation[1]]);
							pieces[startLocation[0]][startLocation[1]]
									.incrementMoves();
							pieces[endLocation[0]][endLocation[1]] = currentPiece;
							pieces[endLocation[0] + 1][endLocation[1]] = null;
							lastMove = currentPiece;
							pieces[startLocation[0]][startLocation[1]] = null;

							if (isInCheck()) {
								if (isInCheck == whosTurn) {
									System.out
											.println("Illeal move (put's king in check), try again");
									pieces[startLocation[0]][startLocation[1]] = currentPiece;
									pieces[endLocation[0] + 1][endLocation[1]] = temp;
									graveyard.remove(temp);
									currentPiece.decrementMoves();
									lastMove = last;
								}

							}

							continue;
						}
					}
					if ((whosTurn == 'b')
							&& (currentPiece.getName().charAt(1) == 'p')) {

						if ((pieces[endLocation[0] - 1][endLocation[1]] != null)
								&& (pieces[endLocation[0] - 1][endLocation[1]] == lastMove)
								&& (pieces[endLocation[0] - 1][endLocation[1]]
										.getMoves() == 1)) {
							Pieces temp = pieces[endLocation[0] - 1][endLocation[1]];
							Pieces last = lastMove;
							graveyard
									.add(pieces[endLocation[0] - 1][endLocation[1]]);
							pieces[startLocation[0]][startLocation[1]]
									.incrementMoves();
							pieces[endLocation[0]][endLocation[1]] = currentPiece;
							pieces[endLocation[0] - 1][endLocation[1]] = null;
							lastMove = currentPiece;
							pieces[startLocation[0]][startLocation[1]] = null;

							if (isInCheck()) {
								if (isInCheck == whosTurn) {
									System.out
											.println("Illeal move (put's king in check), try again");
									pieces[startLocation[0]][startLocation[1]] = currentPiece;
									pieces[endLocation[0] - 1][endLocation[1]] = temp;
									graveyard.remove(temp);
									currentPiece.decrementMoves();
									lastMove = last;
								}
							}
							continue;
						}
					}

					System.out.println("Illegal move, try again");
					continue;
				}

				// Before checking if it can capture the piece, make sure it
				// is a different color
				if (currentPiece.getName().charAt(0) == pieces[endLocation[0]][endLocation[1]]
						.getName().charAt(0)) {
					System.out.println("Illegal move, try again");
					continue;
				}

				if (currentPiece.isValidCapture(startLocation, endLocation)) {
					// Add captured piece to graveyard, move piece there

					Pieces tempPiece = pieces[endLocation[0]][endLocation[1]];

					graveyard.add(pieces[endLocation[0]][endLocation[1]]);
					pieces[startLocation[0]][startLocation[1]].incrementMoves();
					pieces[endLocation[0]][endLocation[1]] = currentPiece;
					lastMove = currentPiece;
					pieces[startLocation[0]][startLocation[1]] = null;

					// make sure this does not result in a check
					if (isInCheck() && isInCheck == whosTurn) {
						System.out.println("Illegal move, try again");
						pieces[startLocation[0]][startLocation[1]] = currentPiece;
						pieces[endLocation[0]][endLocation[1]] = tempPiece;
						continue;
					}

					// Do this check to see if pawn promotion is
					// happening

					if (currentPiece instanceof Pawn) {

						// can do this check since pawns can't move
						// backwards
						if (endLocation[0] == 7 || endLocation[0] == 0) {

							// promotion has happened, check if default
							// or not

							if (input.size() == 3) {

								if (input.get(2).length() != 1) {
									System.out
											.println("Illegal move, try again");
									continue;
								}

								if (promotePawn(endLocation, currentPiece
										.getName().charAt(0), input.get(2)
										.charAt(0))) {
									// pawn has been promoted, do nothing
								} else {
									System.out
											.println("Illegal move, try again");
								}

								// no promotion has happened, pass default 'd'
							} else {

								if (promotePawn(endLocation, currentPiece
										.getName().charAt(0), 'd')) {
									// pawn has been promoted, do nothing
								} else {
									System.out
											.println("Illegal move, try again");
								}
							}
						}
					}

				} else {
					// not valid move or capture.
					System.out.println("Illegal move, try again");
					continue;
				}
			}

			// alternate boolean character for whose turn it is
			if (whosTurn == 'w') {
				whosTurn = 'b';
			} else {
				whosTurn = 'w';
			}
		}

		sc.close();
	}
}
