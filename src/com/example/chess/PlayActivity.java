package com.example.chess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import chessPieces.Bishop;
import chessPieces.Chess;
import chessPieces.King;
import chessPieces.Knight;
import chessPieces.Pawn;
import chessPieces.Pieces;
import chessPieces.Queen;
import chessPieces.Rook;

//Activity for when chess is being played. 
public class PlayActivity extends Activity {

	private static ArrayList<String> moveList = new ArrayList<String>();
	private static final String FILENAME = "game.txt";

	private static Button undoButton;
	private static Button aiButton;
	private static Button homeButton;
	private static Button drawButton;
	private static Button resignButton;
	private static TextView text;
	private static TextView toptext;

	private static Pieces lastMovedPiece;
	
	private static int[] prevSelectedPiece = new int[2];
	private static int[] selectedPiece = new int[2];
	private static boolean pieceSelected = false;
	static int[] endLoc = new int[2];

	static char isInCheck;

	// For chess board
	static String[][] board;
	static Pieces[][] pieces;
	static ArrayList<Pieces> graveyard;
	static int[] lastMove = new int[4];
	static boolean canUndo = false;
	static Pieces pieceTaken = null;
	static char whosTurn = 'r';

	// Chess board buttons
	private static Button b00;
	private static Button b01;
	private static Button b02;
	private static Button b03;
	private static Button b04;
	private static Button b05;
	private static Button b06;
	private static Button b07;
	private static Button b10;
	private static Button b11;
	private static Button b12;
	private static Button b13;
	private static Button b14;
	private static Button b15;
	private static Button b16;
	private static Button b17;
	private static Button b20;
	private static Button b21;
	private static Button b22;
	private static Button b23;
	private static Button b24;
	private static Button b25;
	private static Button b26;
	private static Button b27;
	private static Button b30;
	private static Button b31;
	private static Button b32;
	private static Button b33;
	private static Button b34;
	private static Button b35;
	private static Button b36;
	private static Button b37;
	private static Button b40;
	private static Button b41;
	private static Button b42;
	private static Button b43;
	private static Button b44;
	private static Button b45;
	private static Button b46;
	private static Button b47;
	private static Button b50;
	private static Button b51;
	private static Button b52;
	private static Button b53;
	private static Button b54;
	private static Button b55;
	private static Button b56;
	private static Button b57;
	private static Button b60;
	private static Button b61;
	private static Button b62;
	private static Button b63;
	private static Button b64;
	private static Button b65;
	private static Button b66;
	private static Button b67;
	private static Button b70;
	private static Button b71;
	private static Button b72;
	private static Button b73;
	private static Button b74;
	private static Button b75;
	private static Button b76;
	private static Button b77;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		moveList.clear();
		whosTurn = 'r';
		setContentView(R.layout.playlayout);

		// set last move to 0
		for (int i = 0; i < lastMove.length; i++) {
			lastMove[i] = -1;
		}

		this.undoButton = (Button) this.findViewById(R.id.undobutton);
		this.undoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Here it will undo the last move

				if (!canUndo) {
					pieceSelected = false;
					unselectAllPieces();
					toptext.setText("Invalid Move");
					return;
				}

				if (lastMove[0] != -1) {
					pieces[lastMove[0]][lastMove[1]] = pieces[lastMove[2]][lastMove[3]];

					// put back old piece if there was one
					if (pieceTaken == null) {

						pieces[lastMove[2]][lastMove[3]] = null;

					} else {
						pieces[lastMove[2]][lastMove[3]] = pieceTaken;
					}

					// change turn
					if (whosTurn == 'r') {
						whosTurn = 'b';
						text.setTextColor(Color.parseColor("#0066CC"));
						text.setText("Blue's Move");
					} else {
						whosTurn = 'r';
						text.setTextColor(Color.parseColor("#E80000"));
						text.setText("Red's Move");
					}
					canUndo = false;
					pieceTaken = null;
					pieceSelected = false;
					unselectAllPieces();
					return;

				}
			}
		});

		this.aiButton = (Button) this.findViewById(R.id.aibutton);
		this.aiButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Here it will do an AI move

				// loop until we find a valid piece and move it
				while (true) {
					Random rand = new Random();
					int row = rand.nextInt(8);
					Random rand2 = new Random();
					int col = rand2.nextInt(8);

					int[] startLoc = new int[2];
					startLoc[0] = row;
					startLoc[1] = col;

					// Check to see if piece is the right color
					if (pieces[row][col] != null) {
						if (pieces[row][col].getName().charAt(0) == whosTurn) {

							// Check all pieces, see if valid move
							for (int i = 0; i < 8; i++) {
								for (int j = 0; j < 8; j++) {

									int[] endLoc = new int[2];
									endLoc[0] = i;
									endLoc[1] = j;

									// check to see if valid move and nobody is
									// there
									if ((pieces[i][j] == null)
											&& pieces[row][col].isValidMove(
													startLoc, endLoc)) {

										// see if no pieces in way
										if (!piecesInTheWay(startLoc, endLoc)) {

											// Make the move!
											pieceTaken = pieces[i][j];
											pieces[row][col].incrementMoves();
											pieces[i][j] = pieces[row][col];
											pieces[row][col] = null;

											if (isInCheck()
													&& isInCheck == whosTurn) {
												pieces[row][col] = pieces[i][j];
												pieces[i][j] = pieceTaken;
												continue;
											}

											// store last move
											lastMove[0] = row;
											lastMove[1] = col;
											lastMove[2] = i;
											lastMove[3] = j;

											moveList.add("" + row + "" + col + ":" + i + "" + j);
											
											// change turn
											if (whosTurn == 'r') {
												whosTurn = 'b';
												text.setTextColor(Color
														.parseColor("#0066CC"));
												text.setText("Blue's Move");
											} else {
												whosTurn = 'r';
												text.setTextColor(Color
														.parseColor("#E80000"));
												text.setText("Red's Move");
											}
											pieceSelected = false;
											canUndo = true;
											unselectAllPieces();
											return;
										}

										// or see if it can capture it
									} else if ((pieces[i][j] != null)
											&& (pieces[row][col]
													.isValidCapture(startLoc,
															endLoc))
											&& (pieces[row][col].getName()
													.charAt(0) != pieces[i][j]
													.getName().charAt(0))) {

										// see if no pieces in way
										if (!piecesInTheWay(startLoc, endLoc)) {

											// Make the move!
											pieces[row][col].incrementMoves();
											pieceTaken = pieces[i][j];
											pieces[i][j] = pieces[row][col];
											pieces[row][col] = null;

											if (isInCheck()
													&& isInCheck == whosTurn) {
												pieces[row][col] = pieces[i][j];
												pieces[i][j] = pieceTaken;
												continue;
											}

											// store last move
											lastMove[0] = row;
											lastMove[1] = col;
											lastMove[2] = i;
											lastMove[3] = j;

											moveList.add("" + row + "" + col + ":" + i + "" + j);
											
											// change turn
											if (whosTurn == 'r') {
												whosTurn = 'b';
												text.setTextColor(Color
														.parseColor("#0066CC"));
												text.setText("Blue's Move");
											} else {
												whosTurn = 'r';
												text.setTextColor(Color
														.parseColor("#E80000"));
												text.setText("Red's Move");
											}
											canUndo = true;
											pieceSelected = false;
											unselectAllPieces();
											return;
										}
									}
								}
							}

						}
					}
				}

			}
		});

		this.homeButton = (Button) this.findViewById(R.id.homebutton);
		this.homeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
			}
		});

		this.drawButton = (Button) this.findViewById(R.id.draw);
		this.drawButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// this will start a draw
				AlertDialog.Builder drawAlert = new AlertDialog.Builder(
						PlayActivity.this);

				drawAlert.setTitle("Draw?");
				if (whosTurn == 'r') {
					drawAlert
							.setMessage("Red has requested a draw. Blue, would you like to call it a draw?");
				} else {
					drawAlert
							.setMessage("Blue has requested a draw. Red, would you like to call it a draw?");
				}

				drawAlert.setNegativeButton(
						getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Do nothing

							}
						}).setIcon(android.R.drawable.ic_dialog_alert);

				drawAlert.setPositiveButton(
						getResources().getString(R.string.yesdraw),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								// Say game over
								AlertDialog.Builder tie = new AlertDialog.Builder(
										PlayActivity.this);

								tie.setTitle("Game Over");
								tie.setMessage("It's a draw!");
								tie.setPositiveButton(
										getResources().getString(R.string.savegame),
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int which) {

												// Second dialog box, enters name of game

												final Dialog commentDialog = new Dialog(
														PlayActivity.this);
												commentDialog.setTitle("Save Game");
												commentDialog.setContentView(R.layout.reply);
												Button okBtn = (Button) commentDialog
														.findViewById(R.id.ok);
												okBtn.setOnClickListener(new View.OnClickListener() {

													@Override
													public void onClick(View v) {
														
														//Create new node for game
														EditText et = (EditText) commentDialog.findViewById(R.id.body);
														Node newt = new Node(et.getText().toString(), moveList);
														MainActivity.gamesList.add(newt);
														
														startActivity(new Intent(
																getApplicationContext(),
																MainActivity.class));
														commentDialog.dismiss();
													}
												});
												Button cancelBtn = (Button) commentDialog
														.findViewById(R.id.cancel);
												cancelBtn
														.setOnClickListener(new View.OnClickListener() {

															@Override
															public void onClick(View v) {

																startActivity(new Intent(
																		getApplicationContext(),
																		MainActivity.class));
																commentDialog.dismiss();
															}
														});
												commentDialog.show();

											}
										});
								tie.setNegativeButton(
										getResources().getString(R.string.home),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												// Go to home screen
												startActivity(new Intent(
														getApplicationContext(),
														MainActivity.class));
											}
										}).setIcon(
										android.R.drawable.ic_dialog_alert);
								tie.show();

							}
						});
				drawAlert.show();

			}
		});

		this.resignButton = (Button) this.findViewById(R.id.resign);
		this.resignButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder resignAlert = new AlertDialog.Builder(
						PlayActivity.this);

				resignAlert.setTitle("Game Over");
				if (whosTurn == 'r') {
					resignAlert
							.setMessage("Red has resigned. Blue wins! Do you want to save this game?");
				} else {
					resignAlert
							.setMessage("Blue has resigned. Red wins! Do you want to save this game?");
				}

				resignAlert.setNegativeButton(
						getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Go to home screen
								startActivity(new Intent(
										getApplicationContext(),
										MainActivity.class));

							}
						}).setIcon(android.R.drawable.ic_dialog_alert);
				resignAlert.setPositiveButton(
						getResources().getString(R.string.savegame),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								// Second dialog box, enters name of game

								final Dialog commentDialog = new Dialog(
										PlayActivity.this);
								commentDialog.setTitle("Save Game");
								commentDialog.setContentView(R.layout.reply);
								Button okBtn = (Button) commentDialog
										.findViewById(R.id.ok);
								okBtn.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										
										//Create new node for game
										EditText et = (EditText) commentDialog.findViewById(R.id.body);
										Node newt = new Node(et.getText().toString(), moveList);
										MainActivity.gamesList.add(newt);
										
										startActivity(new Intent(
												getApplicationContext(),
												MainActivity.class));
										commentDialog.dismiss();
									}
								});
								Button cancelBtn = (Button) commentDialog
										.findViewById(R.id.cancel);
								cancelBtn
										.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View v) {

												startActivity(new Intent(
														getApplicationContext(),
														MainActivity.class));
												commentDialog.dismiss();
											}
										});
								commentDialog.show();

							}
						});
				resignAlert.show();

			}
		});

		this.text = (TextView) this.findViewById(R.id.text);
		text.setTextColor(Color.parseColor("#E80000"));
		this.text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		this.toptext = (TextView) this.findViewById(R.id.toptext);
		toptext.setTextColor(Color.parseColor("#ffffff"));
		this.toptext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		
		createChessBoard();
		updateBoard();
		// printBoard();
		

	}

	public static void writeFile(String data) throws Exception{
		
		
	}

	public void createChessBoard() {
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

		pieces[7][0] = new Rook('r');
		pieces[7][1] = new Knight('r');
		pieces[7][2] = new Bishop('r');
		pieces[7][3] = new Queen('r');
		pieces[7][4] = new King('r');
		pieces[7][5] = new Bishop('r');
		pieces[7][6] = new Knight('r');
		pieces[7][7] = new Rook('r');
		pieces[6][0] = new Pawn('r');
		pieces[6][1] = new Pawn('r');
		pieces[6][2] = new Pawn('r');
		pieces[6][3] = new Pawn('r');
		pieces[6][4] = new Pawn('r');
		pieces[6][5] = new Pawn('r');
		pieces[6][6] = new Pawn('r');
		pieces[6][7] = new Pawn('r');

	}

	public void updateBoard() {

		b00 = (Button) this.findViewById(R.id.b00);
		assignPiece(b00, 0, 0);
		PlayActivity.b00.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 0;
				selectPiece(b00, 0, 0);

			}
		});
		b01 = (Button) this.findViewById(R.id.b01);
		assignPiece(b01, 0, 1);
		PlayActivity.b01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 1;
				selectPiece(b01, 0, 1);

			}
		});
		b02 = (Button) this.findViewById(R.id.b02);
		assignPiece(b02, 0, 2);
		PlayActivity.b02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 2;
				selectPiece(b02, 0, 2);

			}
		});
		b03 = (Button) this.findViewById(R.id.b03);
		assignPiece(b03, 0, 3);
		PlayActivity.b03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 3;
				selectPiece(b03, 0, 3);

			}
		});
		b04 = (Button) this.findViewById(R.id.b04);
		assignPiece(b04, 0, 4);
		PlayActivity.b04.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 4;
				selectPiece(b04, 0, 4);

			}
		});
		b05 = (Button) this.findViewById(R.id.b05);
		assignPiece(b05, 0, 5);
		PlayActivity.b05.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 5;
				selectPiece(b05, 0, 5);

			}
		});
		b06 = (Button) this.findViewById(R.id.b06);
		assignPiece(b06, 0, 6);
		PlayActivity.b06.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 6;
				selectPiece(b06, 0, 6);

			}
		});
		b07 = (Button) this.findViewById(R.id.b07);
		assignPiece(b07, 0, 7);
		PlayActivity.b07.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 0;
				selectedPiece[1] = 7;
				selectPiece(b07, 0, 7);

			}
		});
		b10 = (Button) this.findViewById(R.id.b10);
		assignPiece(b10, 1, 0);
		PlayActivity.b10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 0;
				selectPiece(b10, 1, 0);

			}
		});
		b11 = (Button) this.findViewById(R.id.b11);
		assignPiece(b11, 1, 1);
		PlayActivity.b11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 1;
				selectPiece(b11, 1, 1);

			}
		});
		b12 = (Button) this.findViewById(R.id.b12);
		assignPiece(b12, 1, 2);
		PlayActivity.b12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 2;
				selectPiece(b12, 1, 2);

			}
		});
		b13 = (Button) this.findViewById(R.id.b13);
		assignPiece(b13, 1, 3);
		PlayActivity.b13.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 3;
				selectPiece(b13, 1, 3);

			}
		});
		b14 = (Button) this.findViewById(R.id.b14);
		assignPiece(b14, 1, 4);
		PlayActivity.b14.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 4;
				selectPiece(b14, 1, 4);

			}
		});
		b15 = (Button) this.findViewById(R.id.b15);
		assignPiece(b15, 1, 5);
		PlayActivity.b15.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 5;
				selectPiece(b15, 1, 5);

			}
		});
		b16 = (Button) this.findViewById(R.id.b16);
		assignPiece(b16, 1, 6);
		PlayActivity.b16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 6;
				selectPiece(b16, 1, 6);

			}
		});
		b17 = (Button) this.findViewById(R.id.b17);
		assignPiece(b17, 1, 7);
		PlayActivity.b17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 1;
				selectedPiece[1] = 7;
				selectPiece(b17, 1, 7);

			}
		});
		b20 = (Button) this.findViewById(R.id.b20);
		assignPiece(b20, 2, 0);
		PlayActivity.b20.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 0;
				selectPiece(b20, 2, 0);

			}
		});
		b21 = (Button) this.findViewById(R.id.b21);
		assignPiece(b21, 2, 1);
		PlayActivity.b21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 1;
				selectPiece(b21, 2, 1);

			}
		});
		b22 = (Button) this.findViewById(R.id.b22);
		assignPiece(b22, 2, 2);
		PlayActivity.b22.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 2;
				selectPiece(b22, 2, 2);

			}
		});
		b23 = (Button) this.findViewById(R.id.b23);
		assignPiece(b23, 2, 3);
		PlayActivity.b23.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 3;
				selectPiece(b23, 2, 3);

			}
		});
		b24 = (Button) this.findViewById(R.id.b24);
		assignPiece(b24, 2, 4);
		PlayActivity.b24.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 4;
				selectPiece(b24, 2, 4);

			}
		});
		b25 = (Button) this.findViewById(R.id.b25);
		assignPiece(b25, 2, 5);
		PlayActivity.b25.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 5;
				selectPiece(b25, 2, 5);

			}
		});
		b26 = (Button) this.findViewById(R.id.b26);
		assignPiece(b26, 2, 6);
		PlayActivity.b26.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 6;
				selectPiece(b26, 2, 6);

			}
		});
		b27 = (Button) this.findViewById(R.id.b27);
		assignPiece(b27, 2, 7);
		PlayActivity.b27.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 2;
				selectedPiece[1] = 7;
				selectPiece(b27, 2, 7);

			}
		});
		b30 = (Button) this.findViewById(R.id.b30);
		assignPiece(b30, 3, 0);
		PlayActivity.b30.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 0;
				selectPiece(b30, 3, 0);

			}
		});
		b31 = (Button) this.findViewById(R.id.b31);
		assignPiece(b31, 3, 1);
		PlayActivity.b31.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 1;
				selectPiece(b31, 3, 1);

			}
		});
		b32 = (Button) this.findViewById(R.id.b32);
		assignPiece(b32, 3, 2);
		PlayActivity.b32.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 2;
				selectPiece(b32, 3, 2);

			}
		});
		b33 = (Button) this.findViewById(R.id.b33);
		assignPiece(b33, 3, 3);
		PlayActivity.b33.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 3;
				selectPiece(b33, 3, 3);

			}
		});
		b34 = (Button) this.findViewById(R.id.b34);
		assignPiece(b34, 3, 4);
		PlayActivity.b34.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 4;
				selectPiece(b34, 3, 4);

			}
		});
		b35 = (Button) this.findViewById(R.id.b35);
		assignPiece(b35, 3, 5);
		PlayActivity.b35.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 5;
				selectPiece(b35, 3, 5);

			}
		});
		b36 = (Button) this.findViewById(R.id.b36);
		assignPiece(b36, 3, 6);
		PlayActivity.b36.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 6;
				selectPiece(b36, 3, 6);

			}
		});
		b37 = (Button) this.findViewById(R.id.b37);
		assignPiece(b37, 3, 7);
		PlayActivity.b37.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 3;
				selectedPiece[1] = 7;
				selectPiece(b37, 3, 7);

			}
		});
		b40 = (Button) this.findViewById(R.id.b40);
		assignPiece(b40, 4, 0);
		PlayActivity.b40.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 0;
				selectPiece(b40, 4, 0);

			}
		});
		b41 = (Button) this.findViewById(R.id.b41);
		assignPiece(b41, 4, 1);
		PlayActivity.b41.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 1;
				selectPiece(b41, 4, 1);

			}
		});
		b42 = (Button) this.findViewById(R.id.b42);
		assignPiece(b42, 4, 2);
		PlayActivity.b42.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 2;
				selectPiece(b42, 4, 2);

			}
		});
		b43 = (Button) this.findViewById(R.id.b43);
		assignPiece(b43, 4, 3);
		PlayActivity.b43.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 3;
				selectPiece(b43, 4, 3);

			}
		});
		b44 = (Button) this.findViewById(R.id.b44);
		assignPiece(b44, 4, 4);
		PlayActivity.b44.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 4;
				selectPiece(b44, 4, 4);

			}
		});
		b45 = (Button) this.findViewById(R.id.b45);
		assignPiece(b45, 4, 5);
		PlayActivity.b45.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 5;
				selectPiece(b45, 4, 5);

			}
		});
		b46 = (Button) this.findViewById(R.id.b46);
		assignPiece(b46, 4, 6);
		PlayActivity.b46.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 6;
				selectPiece(b46, 4, 6);

			}
		});
		b47 = (Button) this.findViewById(R.id.b47);
		assignPiece(b47, 4, 7);
		PlayActivity.b47.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 4;
				selectedPiece[1] = 7;
				selectPiece(b47, 4, 7);

			}
		});
		b50 = (Button) this.findViewById(R.id.b50);
		assignPiece(b50, 5, 0);
		PlayActivity.b50.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 0;
				selectPiece(b50, 5, 0);

			}
		});
		b51 = (Button) this.findViewById(R.id.b51);
		assignPiece(b51, 5, 1);
		PlayActivity.b51.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 1;
				selectPiece(b51, 5, 1);

			}
		});
		b52 = (Button) this.findViewById(R.id.b52);
		assignPiece(b52, 5, 2);
		PlayActivity.b52.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 2;
				selectPiece(b52, 5, 2);

			}
		});
		b53 = (Button) this.findViewById(R.id.b53);
		assignPiece(b53, 5, 3);
		PlayActivity.b53.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 3;
				selectPiece(b53, 5, 3);

			}
		});
		b54 = (Button) this.findViewById(R.id.b54);
		assignPiece(b54, 5, 4);
		PlayActivity.b54.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 4;
				selectPiece(b54, 5, 4);

			}
		});
		b55 = (Button) this.findViewById(R.id.b55);
		assignPiece(b55, 5, 5);
		PlayActivity.b55.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 5;
				selectPiece(b55, 5, 5);

			}
		});
		b56 = (Button) this.findViewById(R.id.b56);
		assignPiece(b56, 5, 6);
		PlayActivity.b56.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 6;
				selectPiece(b56, 5, 6);

			}
		});
		b57 = (Button) this.findViewById(R.id.b57);
		assignPiece(b57, 5, 7);
		PlayActivity.b57.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 5;
				selectedPiece[1] = 7;
				selectPiece(b57, 5, 7);

			}
		});
		b60 = (Button) this.findViewById(R.id.b60);
		assignPiece(b60, 6, 0);
		PlayActivity.b60.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 0;
				selectPiece(b60, 6, 0);

			}
		});
		b61 = (Button) this.findViewById(R.id.b61);
		assignPiece(b61, 6, 1);
		PlayActivity.b61.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 1;
				selectPiece(b61, 6, 1);

			}
		});
		b62 = (Button) this.findViewById(R.id.b62);
		assignPiece(b62, 6, 2);
		PlayActivity.b62.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 2;
				selectPiece(b62, 6, 2);

			}
		});
		b63 = (Button) this.findViewById(R.id.b63);
		assignPiece(b63, 6, 3);
		PlayActivity.b63.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 3;
				selectPiece(b63, 6, 3);

			}
		});
		b64 = (Button) this.findViewById(R.id.b64);
		assignPiece(b64, 6, 4);
		PlayActivity.b64.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 4;
				selectPiece(b64, 6, 4);

			}
		});
		b65 = (Button) this.findViewById(R.id.b65);
		assignPiece(b65, 6, 5);
		PlayActivity.b65.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 5;
				selectPiece(b65, 6, 5);

			}
		});
		b66 = (Button) this.findViewById(R.id.b66);
		assignPiece(b66, 6, 6);
		PlayActivity.b66.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 6;
				selectPiece(b66, 6, 6);

			}
		});
		b67 = (Button) this.findViewById(R.id.b67);
		assignPiece(b67, 6, 7);
		PlayActivity.b67.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 6;
				selectedPiece[1] = 7;
				selectPiece(b67, 6, 7);

			}
		});
		b70 = (Button) this.findViewById(R.id.b70);
		assignPiece(b70, 7, 0);
		PlayActivity.b70.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 0;
				selectPiece(b70, 7, 0);

			}
		});
		b71 = (Button) this.findViewById(R.id.b71);
		assignPiece(b71, 7, 1);
		PlayActivity.b71.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 1;
				selectPiece(b71, 7, 1);

			}
		});
		b72 = (Button) this.findViewById(R.id.b72);
		assignPiece(b72, 7, 2);
		PlayActivity.b72.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 2;
				selectPiece(b72, 7, 2);

			}
		});
		b73 = (Button) this.findViewById(R.id.b73);
		assignPiece(b73, 7, 3);
		PlayActivity.b73.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 3;
				selectPiece(b73, 7, 3);

			}
		});
		b74 = (Button) this.findViewById(R.id.b74);
		assignPiece(b74, 7, 4);
		PlayActivity.b74.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 4;
				selectPiece(b74, 7, 4);

			}
		});
		b75 = (Button) this.findViewById(R.id.b75);
		assignPiece(b75, 7, 5);
		PlayActivity.b75.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 5;
				selectPiece(b75, 7, 5);

			}
		});
		b76 = (Button) this.findViewById(R.id.b76);
		assignPiece(b76, 7, 6);
		PlayActivity.b76.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 6;
				selectPiece(b76, 7, 6);

			}
		});
		b77 = (Button) this.findViewById(R.id.b77);
		assignPiece(b77, 7, 7);
		PlayActivity.b77.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				;
				;
				;
				selectedPiece[0] = 7;
				selectedPiece[1] = 7;
				selectPiece(b77, 7, 7);

			}
		});

	}

	public static void assignPiece(Button b, int row, int col) {		
		
		try {
			writeFile("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (pieces[row][col] != null) {
			String name = pieces[row][col].getName();
			switch (name) {
			case "bR":
				b.setBackgroundResource(R.drawable.brook);
				break;
			case "bK":
				b.setBackgroundResource(R.drawable.bking);
				break;
			case "bN":
				b.setBackgroundResource(R.drawable.bknight);
				break;
			case "bp":
				b.setBackgroundResource(R.drawable.bpawn);
				break;
			case "bQ":
				b.setBackgroundResource(R.drawable.bqueen);
				break;
			case "bB":
				b.setBackgroundResource(R.drawable.bbishop);
				break;
			case "rR":
				b.setBackgroundResource(R.drawable.rrook);
				break;
			case "rK":
				b.setBackgroundResource(R.drawable.rking);
				break;
			case "rN":
				b.setBackgroundResource(R.drawable.rknight);
				break;
			case "rp":
				b.setBackgroundResource(R.drawable.rpawn);
				break;
			case "rQ":
				b.setBackgroundResource(R.drawable.rqueen);
				break;
			case "rB":
				b.setBackgroundResource(R.drawable.rbishop);
				break;
			}

		} else {
			b.setBackground(null);
			b.setBackgroundColor(Color.TRANSPARENT);
		}

	}

	public static void selectPiece(Button b, int row, int col) {

		// user has clicked an end location
		if (pieceSelected) {

			endLoc[0] = row;
			endLoc[1] = col;

			// Check to see if the move is valid
			if (pieces[prevSelectedPiece[0]][prevSelectedPiece[1]].isValidMove(
					prevSelectedPiece, endLoc)) {

				// check to see if there is a piece at the end location
				if (pieces[endLoc[0]][endLoc[1]] != null) {

					// if there is a piece of different color, check if it can
					// capture it
					if (pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
							.isValidCapture(prevSelectedPiece, endLoc)
							&& pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
									.getName().charAt(0) != pieces[endLoc[0]][endLoc[1]]
									.getName().charAt(0)) {

						// check to make sure there are no pieces in the way.
						if (!piecesInTheWay(prevSelectedPiece, endLoc)) {

							pieceTaken = pieces[endLoc[0]][endLoc[1]];
							// remove pieces pieces
							pieces[endLoc[0]][endLoc[1]] = pieces[prevSelectedPiece[0]][prevSelectedPiece[1]];
							pieces[prevSelectedPiece[0]][prevSelectedPiece[1]] = null;

							if (isInCheck() && isInCheck == whosTurn) {
								toptext.setText("Invalid Move");
								pieces[prevSelectedPiece[0]][prevSelectedPiece[1]] = pieces[endLoc[0]][endLoc[1]];
								pieces[endLoc[0]][endLoc[1]] = pieceTaken;
								return;
							}

							moveList.add(prevSelectedPiece[0] + ""
									+ prevSelectedPiece[1] + ":" + endLoc[0]
									+ "" + endLoc[1]);
							
							// store last move
							lastMove[0] = prevSelectedPiece[0];
							lastMove[1] = prevSelectedPiece[1];
							lastMove[2] = endLoc[0];
							lastMove[3] = endLoc[1];

							if (whosTurn == 'r') {
								whosTurn = 'b';
								text.setTextColor(Color.parseColor("#0066CC"));
								text.setText("Blue's Move");
							} else {
								whosTurn = 'r';
								text.setTextColor(Color.parseColor("#E80000"));
								text.setText("Red's Move");
							}

							// There are pieces in the way
						} else {
							pieceSelected = false;
							unselectAllPieces();
							toptext.setText("Invalid Move");
							return;
						}
					} else {
						pieceSelected = false;
						unselectAllPieces();
						toptext.setText("Invalid Move");
						return;

					}

					// there is not a piece in new location
				} else {
					if (!piecesInTheWay(prevSelectedPiece, endLoc)) {
						// remove pieces pieces
						pieceTaken = pieces[endLoc[0]][endLoc[1]];
						pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
								.incrementMoves();
						pieces[endLoc[0]][endLoc[1]] = pieces[prevSelectedPiece[0]][prevSelectedPiece[1]];
						pieces[prevSelectedPiece[0]][prevSelectedPiece[1]] = null;

						if (isInCheck() && isInCheck == whosTurn) {
							toptext.setText("Invalid Move");
							pieces[prevSelectedPiece[0]][prevSelectedPiece[1]] = pieces[endLoc[0]][endLoc[1]];
							pieces[endLoc[0]][endLoc[1]] = pieceTaken;
							return;
						}

						moveList.add(prevSelectedPiece[0] + "" + prevSelectedPiece[1] + ":" + endLoc[0] + "" + endLoc[1]);
						
						// store last move
						lastMove[0] = prevSelectedPiece[0];
						lastMove[1] = prevSelectedPiece[1];
						lastMove[2] = endLoc[0];
						lastMove[3] = endLoc[1];

						if (whosTurn == 'r') {
							whosTurn = 'b';
							text.setTextColor(Color.parseColor("#0066CC"));
							text.setText("Blue's Move");
						} else {
							whosTurn = 'r';
							text.setTextColor(Color.parseColor("#E80000"));
							text.setText("Red's Move");
						}

						// There are pieces in the way
					} else {
						pieceSelected = false;
						unselectAllPieces();
						toptext.setText("Invalid Move");
						return;
					}

				}

				// not valid move, could be valid capture by a pawn
			} else {
				// TODO: pawn check thing
				if (pieces[endLoc[0]][endLoc[1]] == null) {
					
					if((whosTurn=='r')&&(pieces[prevSelectedPiece[0]][prevSelectedPiece[1]].getName().charAt(1)=='p')){
						
						if((pieces[endLoc[0] + 1][endLoc[1]] != null)
								&& (pieces[endLoc[0] + 1][endLoc[1]] == lastMovedPiece)
								&& (pieces[endLoc[0] + 1][endLoc[1]]
										.getMoves() == 1)
								&& (pieces[endLoc[0] + 1][endLoc[1]]
										.getName().equals("bp"))){
							//en Passat red
						}
						
					}
				}else if (pieces[endLoc[0]][endLoc[1]] == null) {
					if((whosTurn=='b')&&(pieces[prevSelectedPiece[0]][prevSelectedPiece[1]].getName().charAt(1)=='p')){
						if((pieces[endLoc[0] + 1][endLoc[1]] != null)
								&& (pieces[endLoc[0] - 1][endLoc[1]] == lastMovedPiece)
								&& (pieces[endLoc[0] - 1][endLoc[1]]
										.getMoves() == 1)
								&& (pieces[endLoc[0] - 1][endLoc[1]]
										.getName().equals("rp"))){
							//en Passat blue
						}
					}else{

				// no piece there, nothing to capture
				
					pieceSelected = false;
					unselectAllPieces();
					toptext.setText("Invalid Move");
					return;
					}
				}

				// Before checking if it can capture the piece, make sure it
				// is a different color
				else if (pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
						.getName().charAt(0) == pieces[endLoc[0]][endLoc[1]]
						.getName().charAt(0)) {
					pieceSelected = false;
					unselectAllPieces();
					toptext.setText("Invalid Move");
					return;
				}

				else if (pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
						.isValidCapture(prevSelectedPiece, endLoc)
						&& pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
								.getName().charAt(0) != pieces[endLoc[0]][endLoc[1]]
								.getName().charAt(0)) {
					// Add captured piece to graveyard, move piece there

					pieceTaken = pieces[endLoc[0]][endLoc[1]];

					pieces[prevSelectedPiece[0]][prevSelectedPiece[1]]
							.incrementMoves();
					pieces[endLoc[0]][endLoc[1]] = pieces[prevSelectedPiece[0]][prevSelectedPiece[1]];
					pieces[prevSelectedPiece[0]][prevSelectedPiece[1]] = null;

					if (isInCheck() && isInCheck == whosTurn) {
						toptext.setText("Invalid Move");
						pieces[prevSelectedPiece[0]][prevSelectedPiece[1]] = pieces[endLoc[0]][endLoc[1]];
						pieces[endLoc[0]][endLoc[1]] = pieceTaken;
						return;
					}

					// store last move
					lastMove[0] = prevSelectedPiece[0];
					lastMove[1] = prevSelectedPiece[1];
					lastMove[2] = endLoc[0];
					lastMove[3] = endLoc[1];

					// change piece
					if (whosTurn == 'r') {
						whosTurn = 'b';
						text.setTextColor(Color.parseColor("#0066CC"));
						text.setText("Blue's Move");
					} else {
						whosTurn = 'r';
						text.setTextColor(Color.parseColor("#E80000"));
						text.setText("Red's Move");
					}

				} else {
					canUndo = true;
					pieceSelected = false;
					unselectAllPieces();
					toptext.setText("Invalid Move");
					return;
				}

			}
			canUndo = true;
			toptext.setText("");
			pieceSelected = false;
			unselectAllPieces();

			if (isInCheck()) {
				toptext.setText("Check!");

				pieceSelected = false;
				unselectAllPieces();
				return;

			}

			return;

			// user has clicked a start location
		} else {

			// make sure user is selecting the right color piece & not null char
			if (pieces[selectedPiece[0]][selectedPiece[1]] == null) {
				return;
			}
			if (pieces[selectedPiece[0]][selectedPiece[1]].getName().charAt(0) != whosTurn) {
				return;
			}
		}

		// if a piece is selected, unselect all
		unselectAllPieces();

		prevSelectedPiece[0] = row;
		prevSelectedPiece[1] = col;
		// selecting another piece
		if (pieces[row][col] != null) {
			String name = pieces[row][col].getName();
			switch (name) {
			case "bR":
				b.setBackgroundResource(R.drawable.selbrook);
				pieceSelected = true;
				break;
			case "bK":
				b.setBackgroundResource(R.drawable.selbking);
				pieceSelected = true;
				break;
			case "bN":
				b.setBackgroundResource(R.drawable.selbknight);
				pieceSelected = true;
				break;
			case "bp":
				b.setBackgroundResource(R.drawable.selbpawn);
				pieceSelected = true;
				break;
			case "bQ":
				b.setBackgroundResource(R.drawable.selbqueen);
				pieceSelected = true;
				break;
			case "bB":
				b.setBackgroundResource(R.drawable.selbbishop);
				pieceSelected = true;
				break;
			case "rR":
				b.setBackgroundResource(R.drawable.selrrook);
				pieceSelected = true;
				break;
			case "rK":
				b.setBackgroundResource(R.drawable.selrking);
				pieceSelected = true;
				break;
			case "rN":
				b.setBackgroundResource(R.drawable.selrknight);
				pieceSelected = true;
				break;
			case "rp":
				b.setBackgroundResource(R.drawable.selrpawn);
				pieceSelected = true;
				break;
			case "rQ":
				b.setBackgroundResource(R.drawable.selrqueen);
				pieceSelected = true;
				break;
			case "rB":
				b.setBackgroundResource(R.drawable.selrbishop);
				pieceSelected = true;
				break;
			}

		} else {
			b.setBackground(null);
			b.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public static void unselectAllPieces() {

		assignPiece(b00, 0, 0);
		assignPiece(b01, 0, 1);
		assignPiece(b02, 0, 2);
		assignPiece(b03, 0, 3);
		assignPiece(b04, 0, 4);
		assignPiece(b05, 0, 5);
		assignPiece(b06, 0, 6);
		assignPiece(b07, 0, 7);
		assignPiece(b10, 1, 0);
		assignPiece(b11, 1, 1);
		assignPiece(b12, 1, 2);
		assignPiece(b13, 1, 3);
		assignPiece(b14, 1, 4);
		assignPiece(b15, 1, 5);
		assignPiece(b16, 1, 6);
		assignPiece(b17, 1, 7);
		assignPiece(b20, 2, 0);
		assignPiece(b21, 2, 1);
		assignPiece(b22, 2, 2);
		assignPiece(b23, 2, 3);
		assignPiece(b24, 2, 4);
		assignPiece(b25, 2, 5);
		assignPiece(b26, 2, 6);
		assignPiece(b27, 2, 7);
		assignPiece(b30, 3, 0);
		assignPiece(b31, 3, 1);
		assignPiece(b32, 3, 2);
		assignPiece(b33, 3, 3);
		assignPiece(b34, 3, 4);
		assignPiece(b35, 3, 5);
		assignPiece(b36, 3, 6);
		assignPiece(b37, 3, 7);
		assignPiece(b40, 4, 0);
		assignPiece(b41, 4, 1);
		assignPiece(b42, 4, 2);
		assignPiece(b43, 4, 3);
		assignPiece(b44, 4, 4);
		assignPiece(b45, 4, 5);
		assignPiece(b46, 4, 6);
		assignPiece(b47, 4, 7);
		assignPiece(b50, 5, 0);
		assignPiece(b51, 5, 1);
		assignPiece(b52, 5, 2);
		assignPiece(b53, 5, 3);
		assignPiece(b54, 5, 4);
		assignPiece(b55, 5, 5);
		assignPiece(b56, 5, 6);
		assignPiece(b57, 5, 7);
		assignPiece(b60, 6, 0);
		assignPiece(b61, 6, 1);
		assignPiece(b62, 6, 2);
		assignPiece(b63, 6, 3);
		assignPiece(b64, 6, 4);
		assignPiece(b65, 6, 5);
		assignPiece(b66, 6, 6);
		assignPiece(b67, 6, 7);
		assignPiece(b70, 7, 0);
		assignPiece(b71, 7, 1);
		assignPiece(b72, 7, 2);
		assignPiece(b73, 7, 3);
		assignPiece(b74, 7, 4);
		assignPiece(b75, 7, 5);
		assignPiece(b76, 7, 6);
		assignPiece(b77, 7, 7);
	}

	public static void printBoard() {

		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 9; j++) {

				if (j != 8 && i != 8 && pieces[i][j] != null) {
					Log.i("Chess", pieces[i][j].getName() + " ");
				} else {
					Log.i("Chess", board[i][j] + " ");
				}

			}
			Log.i("Chess", "");
		}
	}

	public static boolean isInCheck() {

		// Loop through pieces array, check if king would be a valid capture

		int[] rkLocation = new int[2];
		int[] bKLocation = new int[2];
		int[] targetKing = new int[2]; // used to know which king to search for

		// Get location of both kings to be used
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] != null) {

					if (pieces[i][j].getName().equals("rK")) {
						rkLocation[0] = i;
						rkLocation[1] = j;
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

					if (pieces[i][j].getName().charAt(0) == 'r') {
						targetKing = bKLocation;
					} else {
						targetKing = rkLocation;
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

	public static boolean piecesInTheWay(int[] startLocation, int[] endLoc) {

		// Vertical move, columns the same, rows different
		if (startLocation[1] == endLoc[1]) {

			int largest = startLocation[0] > endLoc[0] ? startLocation[0]
					: endLoc[0];

			int smallest = startLocation[0] < endLoc[0] ? startLocation[0]
					: endLoc[0];

			for (int i = smallest + 1; i < largest; i++) {
				if (pieces[i][startLocation[1]] != null) {
					return true;
				}
			}
			// Horizontal move, row the same, columns different
		} else if (startLocation[0] == endLoc[0]) {

			int largest = startLocation[1] > endLoc[1] ? startLocation[1]
					: endLoc[1];

			int smallest = startLocation[1] < endLoc[1] ? startLocation[1]
					: endLoc[1];

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

			int largestrow = startLocation[0] > endLoc[0] ? startLocation[0]
					: endLoc[0];

			if (largestrow == startLocation[0]) {
				goUp = true;
			} else {
				goUp = false;
			}

			int largestcolumn = startLocation[1] > endLoc[1] ? startLocation[1]
					: endLoc[1];

			boolean goLeft;

			if (largestcolumn == startLocation[1]) {
				goLeft = true;
			} else {
				goLeft = false;
			}

			// Check for diagonal pieces

			if (goUp && goLeft) {

				for (int i = startLocation[0] - 1; i > endLoc[0]; i--) {
					for (int j = startLocation[1] - 1; j > endLoc[1]; i--, j--) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			} else if (goUp && !goLeft) {

				for (int i = startLocation[0] - 1; i > endLoc[0]; i--) {
					for (int j = startLocation[1] + 1; j < endLoc[1]; i--, j++) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			} else if (!goUp && goLeft) {

				for (int i = startLocation[0] + 1; i < endLoc[0]; i++) {
					for (int j = startLocation[1] - 1; j > endLoc[1]; i++, j--) {

						if (i < 0 || j < 0) {
							return false;
						}

						if (pieces[i][j] != null) {
							return true;
						}
					}
				}
			} else {
				for (int i = startLocation[0] + 1; i < endLoc[0]; i++) {
					for (int j = startLocation[1] + 1; j < endLoc[1]; i++, j++) {

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

}