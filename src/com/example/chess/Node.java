package com.example.chess;

import java.util.ArrayList;
import java.util.Calendar;

public class Node implements Comparable<Node> {

	public String gameName;
	public Calendar cal;
	public ArrayList<String> moves = new ArrayList<String>();

	public Node(String game, ArrayList<String> movesList) {
		gameName = game;
		moves = movesList;
		cal= Calendar.getInstance();
		
		
	}
	
	public void setGameName(String game){
		this.gameName = game;
	}
	
	public void setMoves(ArrayList<String> moveList){
		this.moves = moveList;
	}
	public String toString(){
		return this.gameName;
	}

	@Override
	public int compareTo(Node another) {
		
		return this.gameName.compareTo(another.gameName);
	}
	

	
}
