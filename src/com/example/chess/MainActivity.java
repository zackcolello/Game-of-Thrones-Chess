package com.example.chess;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private Button playbutton;
	private Button recbutton;
	public static ArrayList<Node> gamesList = new ArrayList<Node>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		System.out.println("game list:");
		for(Node n : gamesList){
			System.out.println(n.gameName);
		}
		
		this.playbutton = (Button) this.findViewById(R.id.playbutton);
		this.playbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				  startActivity(new Intent(getApplicationContext(), PlayActivity.class));
			}
		});

		this.recbutton = (Button) this.findViewById(R.id.recgame);
		this.recbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ListGamesActivity.class));
			}
		});
		
	}
	
	//Used for when Play Chess button is pressed
	public boolean playGameView(){
		RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        rlayout.setBackgroundResource(R.drawable.gotchess);
		recbutton.setVisibility(0x00000008);
		playbutton.setVisibility(0x00000008);
		
		return true;
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//

}
