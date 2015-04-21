package com.example.chess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ListGamesActivity extends Activity {
	
	private Button backbutton;
	private Button playbackbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listgameslayout);
		
		//Set up buttons
		this.playbackbutton = (Button) this.findViewById(R.id.playbackbutton);
		this.playbackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});
		this.backbutton = (Button) this.findViewById(R.id.backbutton);
		this.backbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});
		
	}
}
