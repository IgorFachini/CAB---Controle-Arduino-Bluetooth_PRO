package com.wickedbotz.cab.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.wickedbotz.cab.R;

public class Values_Joystick extends Activity {

	public static EditText dadosUp_Low, dadosUp_Medium, dadosUp_High, dadosUpRight_Low, dadosUpRight_Medium,
			dadosUpRight_High, dadosRight_Low, dadosRight_Medium, dadosRight_High, dadosDownRight_Low,
			dadosDownRight_Medium, dadosDownRight_High, dadosDown_Low, dadosDown_Medium, dadosDown_High,
			dadosDownLeft_Low, dadosDownLeft_Medium, dadosDownLeft_High, dadosLeft_Low, dadosLeft_Medium,
			dadosLeft_High, dadosUpLeft_Low, dadosUpLeft_Medium, dadosUpLeft_High,EditX, EditY, EditZ, EditA, EditB, EditC,
			EditConteudoAVoltar;
	public static final String PREFS_VALORES_JOY = "valoresJoy";
	public static final int VALORES = 4;
	
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_values_joystick);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		referenciarEditText();
		recuperarValoresDeTxt();
		
		
		
	}

	public void referenciarEditText() {
		dadosUp_Low = (EditText) findViewById(R.id.txtDadosCima1);
		dadosUp_Medium = (EditText) findViewById(R.id.txtDadosCima2);
		dadosUp_High = (EditText) findViewById(R.id.txtDadosCima3);
		dadosRight_Low = (EditText) findViewById(R.id.txtDadosDireita1);
		dadosRight_Medium = (EditText) findViewById(R.id.txtDadosDireita2);
		dadosRight_High = (EditText) findViewById(R.id.txtDadosDireita3);
		dadosDown_Low = (EditText) findViewById(R.id.txtDadosBaixo1);
		dadosDown_Medium = (EditText) findViewById(R.id.txtDadosBaixo2);
		dadosDown_High = (EditText) findViewById(R.id.txtDadosBaixo3);
		dadosLeft_Low = (EditText) findViewById(R.id.txtDadosEsquerda1);
		dadosLeft_Medium = (EditText) findViewById(R.id.txtDadosEsquerda2);
		dadosLeft_High = (EditText) findViewById(R.id.txtDadosEsquerda3);
		dadosUpRight_Low = (EditText) findViewById(R.id.txtDadosCimaDireita1);
		dadosUpRight_Medium = (EditText) findViewById(R.id.txtDadosCimaDireita2);
		dadosUpRight_High = (EditText) findViewById(R.id.txtDadosCimaDireita3);
		dadosDownRight_Low = (EditText) findViewById(R.id.txtDadosBaixoDireita1);
		dadosDownRight_Medium = (EditText) findViewById(R.id.txtDadosBaixoDireita2);
		dadosDownRight_High = (EditText) findViewById(R.id.txtDadosBaixoDireita3);
		dadosDownLeft_Low = (EditText) findViewById(R.id.txtDadosBaixoEsquerda1);
		dadosDownLeft_Medium = (EditText) findViewById(R.id.txtDadosBaixoEsquerda2);
		dadosDownLeft_High = (EditText) findViewById(R.id.txtDadosBaixoEsquerda3);
		dadosUpLeft_Low = (EditText) findViewById(R.id.txtDadosCimaEsquerda1);
		dadosUpLeft_Medium = (EditText) findViewById(R.id.txtDadosCimaEsquerda2);
		dadosUpLeft_High = (EditText) findViewById(R.id.txtDadosCimaEsquerda3);
		EditX = (EditText) findViewById(R.id.EditTextX);
		EditY = (EditText) findViewById(R.id.EditTextY);
		EditZ = (EditText) findViewById(R.id.EditTextZ);
		EditA = (EditText) findViewById(R.id.EditTextA);
		EditB = (EditText) findViewById(R.id.EditTextB);
		EditC = (EditText) findViewById(R.id.EditTextC);
		EditConteudoAVoltar = (EditText) findViewById(R.id.EditTextConteudoAVoltar);
	}

	public void recuperarValoresDeTxt() {
		
		SharedPreferences configValores = getSharedPreferences(PREFS_VALORES_JOY, MODE_PRIVATE);
		dadosUp_Low.setText(configValores.getString("cima1", "nada"));
		dadosUp_Medium.setText(configValores.getString("cima2", ""));
		dadosUp_High.setText(configValores.getString("cima3", ""));
		dadosUpRight_Low.setText(configValores.getString("cimaDireita1", ""));
		dadosUpRight_Medium.setText(configValores.getString("cimaDireita2", ""));
		dadosUpRight_High.setText(configValores.getString("cimaDireita3", ""));
	    dadosRight_Low.setText(configValores.getString("direita1", ""));
		dadosRight_Medium.setText(configValores.getString("direita2", ""));
		dadosRight_High.setText( configValores.getString("direita3", ""));
		dadosDownRight_Low.setText(configValores.getString("baixoDireita1", ""));
     	dadosDownRight_Medium.setText( configValores.getString("baixoDireita2", ""));
		dadosDownRight_High.setText(configValores.getString("baixoDireita3", ""));
		dadosDown_Low.setText(configValores.getString("baixo1", ""));
		dadosDown_Medium.setText(configValores.getString("baixo2", ""));
		dadosDown_High.setText(configValores.getString("baixo3", ""));
		dadosDownLeft_Low.setText(configValores.getString("baixoEsquerda1", ""));
		dadosDownLeft_Medium.setText(configValores.getString("baixoEsquerda2", ""));
		dadosDownLeft_High.setText(configValores.getString("baixoEsquerda3", ""));
		dadosLeft_Low.setText(configValores.getString("esquerda1", ""));
		dadosLeft_Medium.setText(configValores.getString("esquerda2", ""));
		dadosLeft_High.setText(configValores.getString("esquerda3", ""));
		dadosUpLeft_Low.setText(configValores.getString("cimaEsquerda1", ""));
		dadosUpLeft_Medium.setText(configValores.getString("cimaEsquerda2", ""));
		dadosUpLeft_High.setText(configValores.getString("cimaEsquerda3", ""));
		EditConteudoAVoltar.setText(configValores.getString("conteudoAVoltar", ""));
		EditX.setText(configValores.getString("x", ""));
		EditY.setText(configValores.getString("y", ""));
		EditZ.setText(configValores.getString("z", ""));
		EditA.setText(configValores.getString("a", ""));
		EditB.setText(configValores.getString("b", ""));
		EditC.setText(configValores.getString("c", ""));
	}

	public void salvarValores() {
		SharedPreferences configValores = getSharedPreferences(PREFS_VALORES_JOY, MODE_PRIVATE);
		SharedPreferences.Editor editorValores = configValores.edit();
		editorValores.putString("cima1", dadosUp_Low.getText().toString());
		editorValores.putString("cima2", dadosUp_Medium.getText().toString());
		editorValores.putString("cima3", dadosUp_High.getText().toString());
		editorValores.putString("cimaDireita1", dadosUpRight_Low.getText().toString());
		editorValores.putString("cimaDireita2", dadosUpRight_Medium.getText().toString());
		editorValores.putString("cimaDireita3", dadosUpRight_High.getText().toString());
		editorValores.putString("direita1", dadosRight_Low.getText().toString());
		editorValores.putString("direita2", dadosRight_Medium.getText().toString());
		editorValores.putString("direita3", dadosRight_High.getText().toString());
		editorValores.putString("baixoDireita1", dadosDownRight_Low.getText().toString());
		editorValores.putString("baixoDireita2", dadosDownRight_Medium.getText().toString());
		editorValores.putString("baixoDireita3", dadosDownRight_High.getText().toString());
		editorValores.putString("baixo1", dadosDown_Low.getText().toString());
		editorValores.putString("baixo2", dadosDown_Medium.getText().toString());
		editorValores.putString("baixo3", dadosDown_High.getText().toString());
		editorValores.putString("baixoEsquerda1", dadosDownLeft_Low.getText().toString());
		editorValores.putString("baixoEsquerda2", dadosDownLeft_Medium.getText().toString());
		editorValores.putString("baixoEsquerda3", dadosDownLeft_High.getText().toString());
		editorValores.putString("esquerda1", dadosLeft_Low.getText().toString());
		editorValores.putString("esquerda2", dadosLeft_Medium.getText().toString());
		editorValores.putString("esquerda3", dadosLeft_High.getText().toString());
		editorValores.putString("cimaEsquerda1", dadosUpLeft_Low.getText().toString());
		editorValores.putString("cimaEsquerda2", dadosUpLeft_Medium.getText().toString());
		editorValores.putString("cimaEsquerda3", dadosUpLeft_High.getText().toString());
		editorValores.putString("x", EditX.getText().toString());
		editorValores.putString("y", EditY.getText().toString());
		editorValores.putString("z", EditZ.getText().toString());
		editorValores.putString("a", EditA.getText().toString());
		editorValores.putString("b", EditB.getText().toString());
		editorValores.putString("c", EditC.getText().toString());
		editorValores.putString("conteudoAVoltar", EditConteudoAVoltar.getText().toString());

		
		// Confirma a gravação dos dados
		editorValores.commit();
	}

	public void valoresPadrao() {
		dadosUp_Low.setText("w1");
		dadosUp_Medium.setText("w2");
		dadosUp_High.setText("w3");
		dadosUpRight_Low.setText("e1");
		dadosUpRight_Medium.setText("e2");
		dadosUpRight_High.setText("e3");
		dadosRight_Low.setText("d1");
		dadosRight_Medium.setText("d2");
		dadosRight_High.setText("d3");
		dadosDownRight_Low.setText("c1");
		dadosDownRight_Medium.setText("c2");
		dadosDownRight_High.setText("c3");
		dadosDown_Low.setText("s1");
		dadosDown_Medium.setText("s2");
		dadosDown_High.setText("s3");
		dadosDownLeft_Low.setText("z1");
		dadosDownLeft_Medium.setText("z2");
		dadosDownLeft_High.setText("z3");
		dadosLeft_Low.setText("a1");
		dadosLeft_Medium.setText("a2");
		dadosLeft_High.setText("a3");
		dadosUpLeft_Low.setText("q1");
		dadosUpLeft_Medium.setText("q2");
		dadosUpLeft_High.setText("q3");
		EditX.setText("x");
		EditY.setText("y");
		EditZ.setText("z");
		EditA.setText("a");
		EditB.setText("b");
		EditC.setText("c");
		EditConteudoAVoltar.setText("0");

	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mn_values_joystick, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			setResult(RESULT_OK);
			salvarValores();

			finish();
			break;

		case android.R.id.home:
			finish();
			break;
		case R.id.valores_padrao:
			valoresPadrao();
			break;

		default:
			break;
		}

		return true;
	};
}
