package com.example.chess;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

	private static Button undoButton;
	private static Button aiButton;
	private static Button homeButton;
	private static Button drawButton;

	private int[] selectedPiece = new int[2];

	// For chess board
	static String[][] board;
	static Pieces[][] pieces;
	static ArrayList<Pieces> graveyard;
	static Pieces lastMove = null;
	static char isInCheck;

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
	private static ArrayList<Button> buttons = new ArrayList<Button>();
	
	
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

		addButtons();
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

	public void updateBoard() {

		b00 = (Button) this.findViewById(R.id.b00);
		assignPiece(b00, 0, 0);
		this.b00.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 0;
				selectPiece(b00, 0, 0);

			}
		});
		b01 = (Button) this.findViewById(R.id.b01);
		assignPiece(b01, 0, 1);
		this.b01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 1;
				selectPiece(b01, 0, 1);

			}
		});
		b02 = (Button) this.findViewById(R.id.b02);
		assignPiece(b02, 0, 2);
		this.b02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 2;
				selectPiece(b02, 0, 2);

			}
		});
		b03 = (Button) this.findViewById(R.id.b03);
		assignPiece(b03, 0, 3);
		this.b03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 3;
				selectPiece(b03, 0, 3);

			}
		});
		b04 = (Button) this.findViewById(R.id.b04);
		assignPiece(b04, 0, 4);
		this.b04.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 4;
				selectPiece(b04, 0, 4);

			}
		});
		b05 = (Button) this.findViewById(R.id.b05);
		assignPiece(b05, 0, 5);
		this.b05.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 5;
				selectPiece(b05, 0, 5);

			}
		});
		b06 = (Button) this.findViewById(R.id.b06);
		assignPiece(b06, 0, 6);
		this.b06.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 6;
				selectPiece(b06, 0, 6);

			}
		});
		b07 = (Button) this.findViewById(R.id.b07);
		assignPiece(b07, 0, 7);
		this.b07.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 0;
				selectedPiece[1] = 7;
				selectPiece(b07, 0, 7);

			}
		});
		b10 = (Button) this.findViewById(R.id.b10);
		assignPiece(b10, 1, 0);
		this.b10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 0;
				selectPiece(b10, 1, 0);

			}
		});
		b11 = (Button) this.findViewById(R.id.b11);
		assignPiece(b11, 1, 1);
		this.b11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 1;
				selectPiece(b11, 1, 1);

			}
		});
		b12 = (Button) this.findViewById(R.id.b12);
		assignPiece(b12, 1, 2);
		this.b12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 2;
				selectPiece(b12, 1, 2);

			}
		});
		b13 = (Button) this.findViewById(R.id.b13);
		assignPiece(b13, 1, 3);
		this.b13.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 3;
				selectPiece(b13, 1, 3);

			}
		});
		b14 = (Button) this.findViewById(R.id.b14);
		assignPiece(b14, 1, 4);
		this.b14.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 4;
				selectPiece(b14, 1, 4);

			}
		});
		b15 = (Button) this.findViewById(R.id.b15);
		assignPiece(b15, 1, 5);
		this.b15.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 5;
				selectPiece(b15, 1, 5);

			}
		});
		b16 = (Button) this.findViewById(R.id.b16);
		assignPiece(b16, 1, 6);
		this.b16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 6;
				selectPiece(b16, 1, 6);

			}
		});
		b17 = (Button) this.findViewById(R.id.b17);
		assignPiece(b17, 1, 7);
		this.b17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 1;
				selectedPiece[1] = 7;
				selectPiece(b17, 1, 7);

			}
		});
		b20 = (Button) this.findViewById(R.id.b20);
		assignPiece(b20, 2, 0);
		this.b20.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 0;
				selectPiece(b20, 2, 0);

			}
		});
		b21 = (Button) this.findViewById(R.id.b21);
		assignPiece(b21, 2, 1);
		this.b21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 1;
				selectPiece(b21, 2, 1);

			}
		});
		b22 = (Button) this.findViewById(R.id.b22);
		assignPiece(b22, 2, 2);
		this.b22.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 2;
				selectPiece(b22, 2, 2);

			}
		});
		b23 = (Button) this.findViewById(R.id.b23);
		assignPiece(b23, 2, 3);
		this.b23.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 3;
				selectPiece(b23, 2, 3);

			}
		});
		b24 = (Button) this.findViewById(R.id.b24);
		assignPiece(b24, 2, 4);
		this.b24.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 4;
				selectPiece(b24, 2, 4);

			}
		});
		b25 = (Button) this.findViewById(R.id.b25);
		assignPiece(b25, 2, 5);
		this.b25.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 5;
				selectPiece(b25, 2, 5);

			}
		});
		b26 = (Button) this.findViewById(R.id.b26);
		assignPiece(b26, 2, 6);
		this.b26.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 6;
				selectPiece(b26, 2, 6);

			}
		});
		b27 = (Button) this.findViewById(R.id.b27);
		assignPiece(b27, 2, 7);
		this.b27.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 2;
				selectedPiece[1] = 7;
				selectPiece(b27, 2, 7);

			}
		});
		b30 = (Button) this.findViewById(R.id.b30);
		assignPiece(b30, 3, 0);
		this.b30.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 0;
				selectPiece(b30, 3, 0);

			}
		});
		b31 = (Button) this.findViewById(R.id.b31);
		assignPiece(b31, 3, 1);
		this.b31.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 1;
				selectPiece(b31, 3, 1);

			}
		});
		b32 = (Button) this.findViewById(R.id.b32);
		assignPiece(b32, 3, 2);
		this.b32.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 2;
				selectPiece(b32, 3, 2);

			}
		});
		b33 = (Button) this.findViewById(R.id.b33);
		assignPiece(b33, 3, 3);
		this.b33.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 3;
				selectPiece(b33, 3, 3);

			}
		});
		b34 = (Button) this.findViewById(R.id.b34);
		assignPiece(b34, 3, 4);
		this.b34.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 4;
				selectPiece(b34, 3, 4);

			}
		});
		b35 = (Button) this.findViewById(R.id.b35);
		assignPiece(b35, 3, 5);
		this.b35.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 5;
				selectPiece(b35, 3, 5);

			}
		});
		b36 = (Button) this.findViewById(R.id.b36);
		assignPiece(b36, 3, 6);
		this.b36.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 6;
				selectPiece(b36, 3, 6);

			}
		});
		b37 = (Button) this.findViewById(R.id.b37);
		assignPiece(b37, 3, 7);
		this.b37.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 3;
				selectedPiece[1] = 7;
				selectPiece(b37, 3, 7);

			}
		});
		b40 = (Button) this.findViewById(R.id.b40);
		assignPiece(b40, 4, 0);
		this.b40.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 0;
				selectPiece(b40, 4, 0);

			}
		});
		b41 = (Button) this.findViewById(R.id.b41);
		assignPiece(b41, 4, 1);
		this.b41.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 1;
				selectPiece(b41, 4, 1);

			}
		});
		b42 = (Button) this.findViewById(R.id.b42);
		assignPiece(b42, 4, 2);
		this.b42.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 2;
				selectPiece(b42, 4, 2);

			}
		});
		b43 = (Button) this.findViewById(R.id.b43);
		assignPiece(b43, 4, 3);
		this.b43.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 3;
				selectPiece(b43, 4, 3);

			}
		});
		b44 = (Button) this.findViewById(R.id.b44);
		assignPiece(b44, 4, 4);
		this.b44.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 4;
				selectPiece(b44, 4, 4);

			}
		});
		b45 = (Button) this.findViewById(R.id.b45);
		assignPiece(b45, 4, 5);
		this.b45.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 5;
				selectPiece(b45, 4, 5);

			}
		});
		b46 = (Button) this.findViewById(R.id.b46);
		assignPiece(b46, 4, 6);
		this.b46.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 6;
				selectPiece(b46, 4, 6);

			}
		});
		b47 = (Button) this.findViewById(R.id.b47);
		assignPiece(b47, 4, 7);
		this.b47.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 4;
				selectedPiece[1] = 7;
				selectPiece(b47, 4, 7);

			}
		});
		b50 = (Button) this.findViewById(R.id.b50);
		assignPiece(b50, 5, 0);
		this.b50.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 0;
				selectPiece(b50, 5, 0);

			}
		});
		b51 = (Button) this.findViewById(R.id.b51);
		assignPiece(b51, 5, 1);
		this.b51.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 1;
				selectPiece(b51, 5, 1);

			}
		});
		b52 = (Button) this.findViewById(R.id.b52);
		assignPiece(b52, 5, 2);
		this.b52.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 2;
				selectPiece(b52, 5, 2);

			}
		});
		b53 = (Button) this.findViewById(R.id.b53);
		assignPiece(b53, 5, 3);
		this.b53.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 3;
				selectPiece(b53, 5, 3);

			}
		});
		b54 = (Button) this.findViewById(R.id.b54);
		assignPiece(b54, 5, 4);
		this.b54.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 4;
				selectPiece(b54, 5, 4);

			}
		});
		b55 = (Button) this.findViewById(R.id.b55);
		assignPiece(b55, 5, 5);
		this.b55.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 5;
				selectPiece(b55, 5, 5);

			}
		});
		b56 = (Button) this.findViewById(R.id.b56);
		assignPiece(b56, 5, 6);
		this.b56.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 6;
				selectPiece(b56, 5, 6);

			}
		});
		b57 = (Button) this.findViewById(R.id.b57);
		assignPiece(b57, 5, 7);
		this.b57.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 5;
				selectedPiece[1] = 7;
				selectPiece(b57, 5, 7);

			}
		});
		b60 = (Button) this.findViewById(R.id.b60);
		assignPiece(b60, 6, 0);
		this.b60.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 0;
				selectPiece(b60, 6, 0);

			}
		});
		b61 = (Button) this.findViewById(R.id.b61);
		assignPiece(b61, 6, 1);
		this.b61.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 1;
				selectPiece(b61, 6, 1);

			}
		});
		b62 = (Button) this.findViewById(R.id.b62);
		assignPiece(b62, 6, 2);
		this.b62.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 2;
				selectPiece(b62, 6, 2);

			}
		});
		b63 = (Button) this.findViewById(R.id.b63);
		assignPiece(b63, 6, 3);
		this.b63.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 3;
				selectPiece(b63, 6, 3);

			}
		});
		b64 = (Button) this.findViewById(R.id.b64);
		assignPiece(b64, 6, 4);
		this.b64.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 4;
				selectPiece(b64, 6, 4);

			}
		});
		b65 = (Button) this.findViewById(R.id.b65);
		assignPiece(b65, 6, 5);
		this.b65.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 5;
				selectPiece(b65, 6, 5);

			}
		});
		b66 = (Button) this.findViewById(R.id.b66);
		assignPiece(b66, 6, 6);
		this.b66.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 6;
				selectPiece(b66, 6, 6);

			}
		});
		b67 = (Button) this.findViewById(R.id.b67);
		assignPiece(b67, 6, 7);
		this.b67.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 6;
				selectedPiece[1] = 7;
				selectPiece(b67, 6, 7);

			}
		});
		b70 = (Button) this.findViewById(R.id.b70);
		assignPiece(b70, 7, 0);
		this.b70.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 0;
				selectPiece(b70, 7, 0);

			}
		});
		b71 = (Button) this.findViewById(R.id.b71);
		assignPiece(b71, 7, 1);
		this.b71.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 1;
				selectPiece(b71, 7, 1);

			}
		});
		b72 = (Button) this.findViewById(R.id.b72);
		assignPiece(b72, 7, 2);
		this.b72.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 2;
				selectPiece(b72, 7, 2);

			}
		});
		b73 = (Button) this.findViewById(R.id.b73);
		assignPiece(b73, 7, 3);
		this.b73.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 3;
				selectPiece(b73, 7, 3);

			}
		});
		b74 = (Button) this.findViewById(R.id.b74);
		assignPiece(b74, 7, 4);
		this.b74.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 4;
				selectPiece(b74, 7, 4);

			}
		});
		b75 = (Button) this.findViewById(R.id.b75);
		assignPiece(b75, 7, 5);
		this.b75.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 5;
				selectPiece(b75, 7, 5);

			}
		});
		b76 = (Button) this.findViewById(R.id.b76);
		assignPiece(b76, 7, 6);
		this.b76.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 6;
				selectPiece(b76, 7, 6);

			}
		});
		b77 = (Button) this.findViewById(R.id.b77);
		assignPiece(b77, 7, 7);
		this.b77.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedPiece[0] = 7;
				selectedPiece[1] = 7;
				selectPiece(b77, 7, 7);

			}
		});

	}

	public static void assignPiece(Button b, int row, int col) {

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

		//if a piece is selected, unselect all
		unselectAllPieces();
		
		if (pieces[row][col] != null) {
			String name = pieces[row][col].getName();
			switch (name) {
			case "bR":
				b.setBackgroundResource(R.drawable.selbrook);
				break;
			case "bK":
				b.setBackgroundResource(R.drawable.selbking);
				break;
			case "bN":
				b.setBackgroundResource(R.drawable.selbknight);
				break;
			case "bp":
				b.setBackgroundResource(R.drawable.selbpawn);
				break;
			case "bQ":
				b.setBackgroundResource(R.drawable.selbqueen);
				break;
			case "bB":
				b.setBackgroundResource(R.drawable.selbbishop);
				break;
			case "rR":
				b.setBackgroundResource(R.drawable.selrrook);
				break;
			case "rK":
				b.setBackgroundResource(R.drawable.selrking);
				break;
			case "rN":
				b.setBackgroundResource(R.drawable.selrknight);
				break;
			case "rp":
				b.setBackgroundResource(R.drawable.selrpawn);
				break;
			case "rQ":
				b.setBackgroundResource(R.drawable.selrqueen);
				break;
			case "rB":
				b.setBackgroundResource(R.drawable.selrbishop);
				break;
			}

		} else {
			b.setBackground(null);
			b.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public static void unselectAllPieces(){
		
		int row = 0;
		int col = 0;
		
		for(Button b: buttons){
			System.out.println("assigning: " + row + " : " + col);
			assignPiece(b, row, col);
			
			col++;
			if(col == 8){
				col = 0;
				row++;
			}
			
		}
	}
	
	public static void addButtons(){
		buttons.add(b00);
		buttons.add( b01);
		buttons.add( b02);
		buttons.add( b03);
		buttons.add( b04);
		buttons.add( b05);
		buttons.add( b06);
		buttons.add( b07);
		buttons.add( b10);
		buttons.add( b11);
		buttons.add( b12);
		buttons.add( b13);
		buttons.add( b14);
		buttons.add( b15);
		buttons.add( b16);
		buttons.add( b17);
		buttons.add( b20);
		buttons.add( b21);
		buttons.add( b22);
		buttons.add( b23);
		buttons.add( b24);
		buttons.add( b25);
		buttons.add( b26);
		buttons.add( b27);
		buttons.add( b30);
		buttons.add( b31);
		buttons.add( b32);
		buttons.add( b33);
		buttons.add( b34);
		buttons.add( b35);
		buttons.add( b36);
		buttons.add( b37);
		buttons.add( b40);
		buttons.add( b41);
		buttons.add( b42);
		buttons.add( b43);
		buttons.add( b44);
		buttons.add( b45);
		buttons.add( b46);
		buttons.add( b47);
		buttons.add( b50);
		buttons.add( b51);
		buttons.add( b52);
		buttons.add( b53);
		buttons.add( b54);
		buttons.add( b55);
		buttons.add( b56);
		buttons.add( b57);
		buttons.add( b60);
		buttons.add( b61);
		buttons.add( b62);
		buttons.add( b63);
		buttons.add( b64);
		buttons.add( b65);
		buttons.add( b66);
		buttons.add( b67);
		buttons.add( b70);
		buttons.add( b71);
		buttons.add( b72);
		buttons.add( b73);
		buttons.add( b74);
		buttons.add( b75);
		buttons.add( b76);
		buttons.add( b77);
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