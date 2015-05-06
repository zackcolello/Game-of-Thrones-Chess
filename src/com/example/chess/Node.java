package com.example.chess;

import java.util.ArrayList;

public class Node {

	public String gameName;
	public ArrayList<String> moves = new ArrayList<String>();

	public Node(String game, ArrayList<String> movesList) {
		gameName = game;
		moves = movesList;
		
	}
	
	public void setGameName(String game){
		this.gameName = game;
	}
	
	public void setMoves(ArrayList<String> moveList){
		this.moves = moveList;
	}
}
