package com.example.chess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayActivity extends Activity {
	
	private Button undoButton;
	private Button aiButton;
	private Button homeButton;
	private Button drawButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.playlayout);
		
		this.undoButton = (Button) this.findViewById(R.id.undobutton);
		this.undoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Here it will undo the last move somehow
			}
		});
		
		this.aiButton = (Button) this.findViewById(R.id.aibutton);
		this.aiButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Here it will do an AI move
			}
		});
		
		this.homeButton = (Button) this.findViewById(R.id.homebutton);
		this.homeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});
		
		this.drawButton = (Button) this.findViewById(R.id.draw);
		this.drawButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//this will start a draw
			}
		});
		
		
	}
}
