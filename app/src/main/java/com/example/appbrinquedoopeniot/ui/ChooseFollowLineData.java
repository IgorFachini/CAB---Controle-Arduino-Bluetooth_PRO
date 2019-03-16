package com.example.appbrinquedoopeniot.ui;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbrinquedoopeniot.R;
import com.example.appbrinquedoopeniot.model.FollowLineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChooseFollowLineData extends ListActivity {

	public static final String PREFS_NAME_BlUETOOTH = "device";

	private List<FollowLineModel> followLineData = new ArrayList<FollowLineModel>();
	private FollowLineModel follow;
	public static final String PREFS_NAME = "valoresSeguidor";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		/*
		 * Esse trecho n?o ? essencial, mas da um melhor visual a  lista.
		 * Adiciona um titulo a lista de dispositivos pareados utilizando o
		 * layout text_header.xml.
		 */
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		View header = inflater.inflate(R.layout.activity_list_bluetooth, lv, false);
		((TextView) header.findViewById(R.id.textView)).setText("\nDados\n");
		lv.addHeaderView(header, null, false);

		JSONArray array = new JSONArray();
		JSONObject obj;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

		try {
			follow = new FollowLineModel();
			array = new JSONArray(settings.getString("followLineData",follow.getStartFollowData()));

			for(int i = 0; i < array.length(); ++i) {
				obj = array.getJSONObject(i);

				follow = new FollowLineModel();

				follow.setNome(obj.getString("nome"));
				follow.setVts(obj.getString("vts"));
				follow.setWeight1(obj.getString("weight1"));
				follow.setWeight2(obj.getString("weight2"));
				follow.setWeight3(obj.getString("weight3"));
				follow.setWeight4(obj.getString("weight4"));
				follow.setMaxInverse(obj.getString("maxInverse"));
				follow.setProporcinalValue(obj.getString("proporcinalValue"));
				follow.setMax(obj.getString("max"));
				follow.setTempo(obj.getString("tempo"));
				follow.setReta(obj.getString("reta"));

				// O mesmo para as demais propriedades...

				followLineData.add(follow);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		if (followLineData.size() > 0) {
			for (FollowLineModel follow : followLineData) {
				adapter.add(follow.getNome() + "\n \n VTS: " + follow.getVts()
						+ "| LR: " + follow.getReta()
						+ "| PV: " + follow.getProporcinalValue()
						+ "| V: " + follow.getMax()
						+ "| T: " + follow.getTempo() + "\n W1: " + follow.getWeight1() + "| W2: "
						+ follow.getWeight2()+ "| W3: " + follow.getWeight3() + "| W4: " + follow.getWeight4());
			}
		}
	}

	/*
	 * Este metodo é executado quando o usuario seleciona um elemento da lista.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("followLineThis",position - 1);
        editor.apply();


		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mn_list_bluetooth, menu);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		return true;
	}

	public boolean onMenuItemSelected(int panel, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		}

		return true;
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.home) {

			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
