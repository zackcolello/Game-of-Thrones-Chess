package com.example.chess;

import java.util.ArrayList;

import chessPieces.Bishop;
import chessPieces.King;
import chessPieces.Knight;
import chessPieces.Pawn;
import chessPieces.Pieces;
import chessPieces.Queen;
import chessPieces.Rook;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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

	//Chess piece buttons
	private Button bB1;
	private Button bB2;
	private Button bN1;
	private Button bN2;
	private Button bQ;
	private Button bK;
	private Button bR1;
	private Button bR2;
	private Button bp1;
	private Button bp2;
	private Button bp3;
	private Button bp4;
	private Button bp5;
	private Button bp6;
	private Button bp7;
	private Button bp8;
	
	private Button rB1;
	private Button rB2;
	private Button rK;
	private Button rQ;
	private Button rN1;
	private Button rN2;
	private Button rR1;
	private Button rR2;
	private Button rp1;
	private Button rp2;
	private Button rp3;
	private Button rp4;
	private Button rp5;
	private Button rp6;
	private Button rp7;
	private Button rp8;
	
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
		placePieces();
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
	
	public void placePieces(){
		
		goldSquare = (ImageView) this.findViewById(R.id.goldsquare);
		goldSquare.setVisibility(4);
		goldSquare.bringToFront();
		
		bR1= (Button) this.findViewById(R.id.br);
		this.bR1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bR1.getLeft());
				goldSquare.setTop(bR1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bN1= (Button) this.findViewById(R.id.bn);
		this.bN1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bN1.getLeft());
				goldSquare.setTop(bN1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bB1= (Button) this.findViewById(R.id.bb);
		this.bB1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bB1.getLeft());
				goldSquare.setTop(bB1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bQ= (Button) this.findViewById(R.id.bq);
		this.bQ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bQ.getLeft());
				goldSquare.setTop(bQ.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bK= (Button) this.findViewById(R.id.bk);
		this.bK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bK.getLeft());
				goldSquare.setTop(bK.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bB2= (Button) this.findViewById(R.id.bb2);
		this.bB2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bB2.getLeft());
				goldSquare.setTop(bB2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bN2= (Button) this.findViewById(R.id.bn2);
		this.bN2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bN2.getLeft());
				goldSquare.setTop(bN2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bR2= (Button) this.findViewById(R.id.br2);
		this.bR2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bR2.getLeft());
				goldSquare.setTop(bR2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp1= (Button) this.findViewById(R.id.bp1);
		this.bp1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp1.getLeft());
				goldSquare.setTop(bp1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp2= (Button) this.findViewById(R.id.bp2);
		this.bp2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp2.getLeft());
				goldSquare.setTop(bp2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp3= (Button) this.findViewById(R.id.bp3);
		this.bp3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp3.getLeft());
				goldSquare.setTop(bp3.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp4= (Button) this.findViewById(R.id.bp4);
		this.bp4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp4.getLeft());
				goldSquare.setTop(bp4.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp5= (Button) this.findViewById(R.id.bp5);
		this.bp5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp5.getLeft());
				goldSquare.setTop(bp5.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp6= (Button) this.findViewById(R.id.bp6);
		this.bp6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp6.getLeft());
				goldSquare.setTop(bp6.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp7= (Button) this.findViewById(R.id.bp7);
		this.bp7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp7.getLeft());
				goldSquare.setTop(bp7.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		bp8= (Button) this.findViewById(R.id.bp8);
		this.bp8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(bp8.getLeft());
				goldSquare.setTop(bp8.getTop());
				goldSquare.setVisibility(0);
			}
		});
		
		
		//Red pieces
		
		rB1= (Button) this.findViewById(R.id.rb1);
		this.rB1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rB1.getLeft());
				goldSquare.setTop(rB1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		//Red pieces
		rB2= (Button) this.findViewById(R.id.rb2);
		this.rB2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rB2.getLeft());
				goldSquare.setTop(rB2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rN1= (Button) this.findViewById(R.id.rn1);
		this.rN1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rN1.getLeft());
				goldSquare.setTop(rN1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rN2= (Button) this.findViewById(R.id.rn2);
		this.rN2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rN2.getLeft());
				goldSquare.setTop(rN2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rR1= (Button) this.findViewById(R.id.rr1);
		this.rR1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rR1.getLeft());
				goldSquare.setTop(rR1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rR2= (Button) this.findViewById(R.id.rr2);
		this.rR2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rR2.getLeft());
				goldSquare.setTop(rR2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rQ= (Button) this.findViewById(R.id.rq);
		this.rQ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rQ.getLeft());
				goldSquare.setTop(rQ.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rK= (Button) this.findViewById(R.id.rk);
		this.rK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rK.getLeft());
				goldSquare.setTop(rK.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp1= (Button) this.findViewById(R.id.rp1);
		this.rp1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp1.getLeft());
				goldSquare.setTop(rp1.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp2= (Button) this.findViewById(R.id.rp2);
		this.rp2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp2.getLeft());
				goldSquare.setTop(rp2.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp3= (Button) this.findViewById(R.id.rp3);
		this.rp3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp3.getLeft());
				goldSquare.setTop(rp3.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp4= (Button) this.findViewById(R.id.rp4);
		this.rp4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp4.getLeft());
				goldSquare.setTop(rp4.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp5= (Button) this.findViewById(R.id.rp5);
		this.rp5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp5.getLeft());
				goldSquare.setTop(rp5.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp6= (Button) this.findViewById(R.id.rp6);
		this.rp6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp6.getLeft());
				goldSquare.setTop(rp6.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp7= (Button) this.findViewById(R.id.rp7);
		this.rp7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp7.getLeft());
				goldSquare.setTop(rp7.getTop());
				goldSquare.setVisibility(0);
			}
		});
		rp8= (Button) this.findViewById(R.id.rp8);
		this.rp8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goldSquare.setLeft(rp8.getLeft());
				goldSquare.setTop(rp8.getTop());
				goldSquare.setVisibility(0);
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