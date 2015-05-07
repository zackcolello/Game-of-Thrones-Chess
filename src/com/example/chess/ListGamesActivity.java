package com.example.chess;

import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ListGamesActivity extends Activity {

	private Button backbutton;
	private Button playbackbutton;
	private Spinner sortSpinner;
	private ListView list;
	private boolean sortbyTitle = false;
	ArrayAdapter<Node> nAdapter;
	int target = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listgameslayout);

		// Set up buttons
		this.playbackbutton = (Button) this.findViewById(R.id.playbackbutton);
		this.playbackbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ReplayGameActivity.moveList = MainActivity.gamesList
						.get(target).moves;
				startActivity(new Intent(getApplicationContext(),
						ReplayGameActivity.class));
			}
		});
		this.backbutton = (Button) this.findViewById(R.id.backbutton);
		this.backbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
			}
		});

		this.sortSpinner = (Spinner) this.findViewById(R.id.sortSpinner);
		ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this,
				R.array.sortBy, android.R.layout.simple_spinner_item);
		sortSpinner.setAdapter(sAdapter);

		sortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				TextView text = (TextView) view;
				if (position == 0) {
					// Sort by Date
					Collections.sort(MainActivity.gamesList,
							new Comparator<Node>() {

								@Override
								public int compare(Node lhs, Node rhs) {
									// TODO Auto-generated method stub
									return lhs.cal.compareTo(rhs.cal);
								}

							});
					nAdapter.notifyDataSetChanged();

				} else if (position == 1) {
					// Sort by Title
					Collections.sort(MainActivity.gamesList);
					nAdapter.notifyDataSetChanged();

				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// doesn't happen
			}

		});

		this.list = (ListView) findViewById(R.id.listView1);
		nAdapter = new ArrayAdapter<Node>(this,
				android.R.layout.simple_list_item_1, MainActivity.gamesList);
		list.setAdapter(nAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

				target = arg2;

			}

		});

	}

}
