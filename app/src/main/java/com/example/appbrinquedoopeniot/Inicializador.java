package com.example.appbrinquedoopeniot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Inicializador extends Activity {

	int activityInicializador;
	public static final String PREFS_NAME_CLOSE = "inicializador";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences configInicializador = getSharedPreferences(PREFS_NAME_CLOSE, MODE_PRIVATE);
		activityInicializador = configInicializador.getInt("inicializar",1);
		

		if (activityInicializador == 1) {
			Intent modoBotoes = new Intent(this, ModoBotoes.class);
			startActivity(modoBotoes);
			finish();
		} 
		else if(activityInicializador == 2) {
			Intent modojoystick = new Intent(this, ModoJoyStick.class);
			startActivity(modojoystick);
			finish();
		}else if(activityInicializador == 3) {
			Intent seguidor = new Intent(this, Extras_seguidor.class);
			startActivity(seguidor);
			finish();
		}
	}
}
