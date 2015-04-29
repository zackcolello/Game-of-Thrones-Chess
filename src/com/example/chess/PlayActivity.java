package com.example.chess;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import chessPieces.Bishop;
import chessPieces.King;
import chessPieces.Knight;
import chessPieces.Pawn;
import chessPieces.Pieces;
import chessPieces.Queen;
import chessPieces.Rook;

//Activity for when chess is being played. 
public class PlayActivity extends Activity {

	private Button undoButton;
	private Button aiButton;
	private Button homeButton;
	private Button drawButton;
	private ImageView goldSquare;

	// For chess board
	static String[][] board;
	static Pieces[][] pieces;
	static ArrayList<Pieces> graveyard;
	static Pieces lastMove = null;
	static char isInCheck;

	//Chess board buttons
	private Button b00;
	private Button b01;
	private Button b02;
	private Button b03;
	private Button b04;
	private Button b05;
	private Button b07;
	private Button b10;
	private Button b11;
	private Button b12;
	private Button b13;
	private Button b14;
	private Button b15;
	private Button b16;
	private Button b17;
	private Button b20;
	private Button b21;
	private Button b22;
	private Button b23;
	private Button b24;
	private Button b25;
	private Button b26;
	private Button b27;
	private Button b30;
	private Button b31;
	private Button b32;
	private Button b33;
	private Button b34;
	private Button b35;
	private Button b36;
	private Button b37;
	private Button b40;
	private Button b41;
	private Button b42;
	private Button b43;
	private Button b44;
	private Button b45;
	private Button b46;
	private Button b47;
	private Button b50;
	private Button b51;
	private Button b52;
	private Button b53;
	private Button b54;
	private Button b55;
	private Button b56;
	private Button b57;
	private Button b60;
	private Button b61;
	private Button b62;
	private Button b63;
	private Button b64;
	private Button b65;
	private Button b66;
	private Button b67;
	private Button b70;
	private Button b71;
	private Button b72;
	private Button b73;
	private Button b74;
	private Button b75;
	private Button b76;
	private Button b77;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.playlayout);

		this.undoButton = (Button) this.findViewById(R.id.undobutton);
		this.undoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Here it will undo the last move somehow
			}
		});

		this.aiButton = (Button) this.findViewById(R.id.aibutton);
		this.aiButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Here it will do an AI move
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
			}
		});

		createChessBoard();
		updateBoard();
		printBoard();

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
	
	public void updateBoard(){

		
		/* goldSquare = (ImageView) this.findViewById(R.id.goldsquare);
		goldSquare.setVisibility(4);
		goldSquare.bringToFront(); */
		
		 b00= (Button) this.findViewById(R.id.b00); 
		this.b00.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				b00.setBackgroundResource(R.drawable.bbishop);
				
			}
		});
		

		
		
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
}