package com.example.appbrinquedoopeniot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "HandlerLeak", "ClickableViewAccessibility", "WorldWriteableFiles", "NewApi" })
public class Extras_seguidor extends FragmentActivity {

	String frente, direita, esquerda, tras, x, y, z, a, b, c, conteudoAVoltar;
	Button btnParar, btnConectar, btnFrente, btnDireita, btnEsquerda, btnTras, btn1, btn2, btn3, btn4, btn5, btn6;
	TextView txtArduino01, txtArduino02, txtArduino03, txtArduino04, txtArduino05, txtArduino06, txtArduino07;
	View dados;
	EditText txtMaxInverse, txtProporcinalValue, txtMax, txtTempo, txtWeight1, txtWeight2, txtWeight3, txtWeight4,
			txtLinhaReta;
	EditText txtMaxInverse2, txtProporcinalValue2, txtMax2, txtTempo2, txtWeight12, txtWeight22, txtWeight32, txtWeight42,
	txtLinhaReta2;
	EditText txtMaxInverse3, txtProporcinalValue3, txtMax3, txtTempo3, txtWeight13, txtWeight23, txtWeight33, txtWeight43,
	txtLinhaReta3;

	public static final String PREFS_NAME_BlUETOOTH = "device";
	FragmentManager fragmentoSobreApp = getSupportFragmentManager();

	public static final String PREFS_NAME = "valoresSeguidor";
	public static final String PREFS_NAME_CLOSE = "inicializador";

	Intent Value_Bottons;

	BluetoothThread btt;
	Handler writeHandler;

	private static final int REQUEST_ENABLE_BT = 1;
	public static final int SELECT_PAIRED_DEVICE = 2;
	public static final int VALORES = 4;
	private BluetoothAdapter bluetoothPadrao = null;
	
	Chronometer m_chronometer;
	boolean isClickPause = false;
	long tempoQuandoParado = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		setContentView(R.layout.activity_extras_seguidor);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		Value_Bottons = new Intent(this, Values_bottons.class);
		bluetoothPadrao = BluetoothAdapter.getDefaultAdapter();

		interromperBluetooth();
		referenciarElementosTela();
		resgatarValoresBotoes();

	}

	public void referenciarElementosTela() {
		dados = (View) findViewById(R.id.FundoDados);
		txtArduino01 = (TextView) findViewById(R.id.txtArduino01);
		txtArduino02 = (TextView) findViewById(R.id.txtArduino02);
		txtArduino03 = (TextView) findViewById(R.id.txtArduino03);
		txtArduino04 = (TextView) findViewById(R.id.txtArduino04);
		txtArduino05 = (TextView) findViewById(R.id.txtArduino05);
		txtArduino06 = (TextView) findViewById(R.id.txtArduino06);
		txtArduino07 = (TextView) findViewById(R.id.txtArduino07);
		btnConectar = (Button) findViewById(R.id.btnConectar);
		btnFrente = (Button) findViewById(R.id.bt_frente);
		btnDireita = (Button) findViewById(R.id.bt_direita);
		btnEsquerda = (Button) findViewById(R.id.bt_esquerda);
		btnTras = (Button) findViewById(R.id.bt_tras);
		btn1 = (Button) findViewById(R.id.bt_x);
		btn2 = (Button) findViewById(R.id.bt_y);
		btn3 = (Button) findViewById(R.id.bt_z);
		btn4 = (Button) findViewById(R.id.bt_a);
		btn5 = (Button) findViewById(R.id.bt_b);
		btn6 = (Button) findViewById(R.id.bt_c);
		txtMaxInverse = (EditText) findViewById(R.id.txtMaxInverse);
		txtProporcinalValue = (EditText) findViewById(R.id.txtProporcionalValue);
		txtTempo = (EditText) findViewById(R.id.txtTempo);
		txtMax = (EditText) findViewById(R.id.txtMax);
		txtMax.setFilters(new InputFilter[] { new InputFilterMinMax("0", "254") });
		txtWeight1 = (EditText) findViewById(R.id.txtWeight1);
		txtWeight2 = (EditText) findViewById(R.id.txtWeight2);
		txtWeight3 = (EditText) findViewById(R.id.txtWeight3);
		txtWeight4 = (EditText) findViewById(R.id.txtWeight4);
		txtLinhaReta = (EditText) findViewById(R.id.txtLinhaReta);
		///////////////////////////////
		txtMaxInverse2 = (EditText) findViewById(R.id.txtMaxInverse2);
		txtProporcinalValue2 = (EditText) findViewById(R.id.txtProporcionalValue2);
		txtTempo2 = (EditText) findViewById(R.id.txtTempo2);
		txtMax2 = (EditText) findViewById(R.id.txtMax2);
		txtMax2.setFilters(new InputFilter[] { new InputFilterMinMax("0", "254") });
		txtWeight12 = (EditText) findViewById(R.id.txtWeight12);
		txtWeight22 = (EditText) findViewById(R.id.txtWeight22);
		txtWeight32 = (EditText) findViewById(R.id.txtWeight32);
		txtWeight42 = (EditText) findViewById(R.id.txtWeight42);
		txtLinhaReta2 = (EditText) findViewById(R.id.txtLinhaReta2);
		///////////////////////////////
		txtMaxInverse3 = (EditText) findViewById(R.id.txtMaxInverse3);
		txtProporcinalValue3 = (EditText) findViewById(R.id.txtProporcionalValue3);
		txtTempo3 = (EditText) findViewById(R.id.txtTempo3);
		txtMax3 = (EditText) findViewById(R.id.txtMax3);
		txtMax3.setFilters(new InputFilter[] { new InputFilterMinMax("0", "254") });
		txtWeight13= (EditText) findViewById(R.id.txtWeight13);
		txtWeight23 = (EditText) findViewById(R.id.txtWeight23);
		txtWeight33 = (EditText) findViewById(R.id.txtWeight33);
		txtWeight43 = (EditText) findViewById(R.id.txtWeight43);
		txtLinhaReta3 = (EditText) findViewById(R.id.txtLinhaReta3);
		btnParar = (Button) findViewById(R.id.btnParar);
		//
		m_chronometer = (Chronometer) findViewById(R.id.chronometer);

	}

	public void listaDeDispositivos() {
		if (bluetoothPadrao.isEnabled()) {
			if (btt == null) {
				Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
				startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
			} else {
				interromperBluetooth();
			}

		}
	}

	public void interromperBluetooth() {
		if (btt != null) {
			btnConectar.setText("Conectar");
			btnConectar.setEnabled(true);
			btt.interrupt();
			btt.disconnect();
			btt = null;

		}

	}

	public void connectButtonPressed(View v) {

		if (bluetoothPadrao == null) {
			Toast.makeText(getApplicationContext(), "Dispostivo nao possui Bluetooth", Toast.LENGTH_LONG).show();

		} else {
			if (!bluetoothPadrao.isEnabled()) {

				Intent novoIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(novoIntent, REQUEST_ENABLE_BT);
			} else {

				listaDeDispositivos();
			}
		}

	}

	public void reconect(View v) {

		if (!bluetoothPadrao.isEnabled()) {
			Toast.makeText(getApplicationContext(), "Ative o bluetooth", Toast.LENGTH_LONG).show();
		} else {
			btnConectar.setText("Conectando...");
			btnConectar.setEnabled(false);
			try {
				interromperBluetooth();
				Thread.sleep(1000);
				connectWithBluetooth(RESULT_OK);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public void cronometroParar(){
		if(isClickPause == false){ //entra para false;
			   tempoQuandoParado = m_chronometer.getBase() - SystemClock.elapsedRealtime();
			  }
			  m_chronometer.stop();
			  isClickPause = true;   

	}
	
	public void cronometroIniciar(){
		  m_chronometer.stop();
		  m_chronometer.setText(" (00:00)");
		  tempoQuandoParado = 0;
		
		if(isClickPause){ 
			   m_chronometer.setBase(SystemClock.elapsedRealtime() + tempoQuandoParado);
			   m_chronometer.start();
			   tempoQuandoParado = 0;
			   isClickPause = false;
			  }
			  else{
			   m_chronometer.setBase(SystemClock.elapsedRealtime());
			   m_chronometer.start();
			   tempoQuandoParado = 0;
			  }
	}

	public void iniciar(View v) {
		String mensagem = "i:" + txtMaxInverse.getText().toString() + "&p:" + txtProporcinalValue.getText().toString()
				+ "&v:" + txtMax.getText().toString() + "&t:" + txtTempo.getText().toString() + "&l:"
				+ txtLinhaReta.getText().toString() + "&1:" + txtWeight1.getText().toString() + "&2:"
				+ txtWeight2.getText().toString() + "&3:" + txtWeight3.getText().toString() + "&4:"
				+ txtWeight4.getText().toString() + "&g:g;";
		// int tamanho = mensagem.length();
		// conteudo.setText(mensagem + " : " + tamanho);
		cronometroIniciar();
		if (btt != null) {
			Message msg = Message.obtain();
			msg.obj = mensagem;
			writeHandler.sendMessage(msg);
		}

	}
	
	public void iniciar2(View v) {
		String mensagem = "i:" + txtMaxInverse2.getText().toString() + "&p:" + txtProporcinalValue2.getText().toString()
				+ "&v:" + txtMax2.getText().toString() + "&t:" + txtTempo2.getText().toString() + "&l:"
				+ txtLinhaReta2.getText().toString() + "&1:" + txtWeight12.getText().toString() + "&2:"
				+ txtWeight22.getText().toString() + "&3:" + txtWeight32.getText().toString() + "&4:"
				+ txtWeight42.getText().toString() + "&g:g;";
		// int tamanho = mensagem.length();
		// conteudo.setText(mensagem + " : " + tamanho);
		cronometroIniciar();
		if (btt != null) {
			Message msg = Message.obtain();
			msg.obj = mensagem;
			writeHandler.sendMessage(msg);
		}

	}
	
	public void iniciar3(View v) {
		String mensagem = "i:" + txtMaxInverse3.getText().toString() + "&p:" + txtProporcinalValue3.getText().toString()
				+ "&v:" + txtMax3.getText().toString() + "&t:" + txtTempo3.getText().toString() + "&l:"
				+ txtLinhaReta3.getText().toString() + "&1:" + txtWeight13.getText().toString() + "&2:"
				+ txtWeight23.getText().toString() + "&3:" + txtWeight33.getText().toString() + "&4:"
				+ txtWeight43.getText().toString() + "&g:g;";
		// int tamanho = mensagem.length();
		// conteudo.setText(mensagem + " : " + tamanho);
		cronometroIniciar();
		if (btt != null) {
			Message msg = Message.obtain();
			msg.obj = mensagem;
			writeHandler.sendMessage(msg);
		}

	}

	// public void parar(View v) {
	// if (btt != null) {
	// Message msg = Message.obtain();
	// msg.obj = "s";
	// writeHandler.sendMessage(msg);
	// }
	//
	// }

	public void acaoDosBotoes() {
		btnFrente.setOnTouchListener(new BotaoListener(frente,false));
		btnEsquerda.setOnTouchListener(new BotaoListener(esquerda,false));
		btnDireita.setOnTouchListener(new BotaoListener(direita,false));
		btnTras.setOnTouchListener(new BotaoListener(tras,false));
		btn1.setOnTouchListener(new BotaoListener(x,false));
		btn2.setOnTouchListener(new BotaoListener(y,false));
		btn3.setOnTouchListener(new BotaoListener(z,false));
		btn4.setOnTouchListener(new BotaoListener(a,false));
		btn5.setOnTouchListener(new BotaoListener(b,false));
		btn6.setOnTouchListener(new BotaoListener(c,false));
		btnParar.setOnTouchListener(new BotaoListener("s",true));
	}

	public class BotaoListener implements OnTouchListener {

		private String mensagem;
		private boolean parar = false;

		public BotaoListener(String mensagem,boolean parar) {
			super();
			this.mensagem = mensagem;
			this.parar = parar;
		}
		
		

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(parar){
				cronometroParar();
			}
			if (btt != null) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Message msg = Message.obtain();
					msg.obj = mensagem;
					writeHandler.sendMessage(msg);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Message msg = Message.obtain();
					msg.obj = conteudoAVoltar;
					writeHandler.sendMessage(msg);
				}
			} else {
				// Bluetooth nao conectado
			}
			return false;
		}

	}

	boolean imprimir = true;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		// Retrono do pedido de ativação do Bluetooth
		case REQUEST_ENABLE_BT:

			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(getApplicationContext(), "Bluetooth Ativado XD", Toast.LENGTH_LONG).show();
				listaDeDispositivos();
			} else {
				Toast.makeText(getApplicationContext(), "Voçe precisa ativar o bluetooth ", Toast.LENGTH_LONG).show();

			}
			break;
		case SELECT_PAIRED_DEVICE:
			connectWithBluetooth(resultCode);

			break;
		case VALORES:
			if (resultCode == RESULT_OK) {
				Toast.makeText(getApplicationContext(), "Mudanças salvas", Toast.LENGTH_LONG).show();
				frente = data.getStringExtra("frente");
				direita = data.getStringExtra("direita");
				esquerda = data.getStringExtra("esquerda");
				tras = data.getStringExtra("tras");
				x = data.getStringExtra("x");
				y = data.getStringExtra("y");
				z = data.getStringExtra("z");
				a = data.getStringExtra("a");
				b = data.getStringExtra("b");
				c = data.getStringExtra("c");
				conteudoAVoltar = data.getStringExtra("conteudoAVoltar");
				salvarValores();
			} else {
				Toast.makeText(getApplicationContext(), "Mudanças nao salvas", Toast.LENGTH_LONG).show();
			}

			break;
		}
	};

	private void connectWithBluetooth(int resultCode) {
		SharedPreferences configDevice = getSharedPreferences(PREFS_NAME_BlUETOOTH, MODE_PRIVATE);
		if (resultCode == RESULT_OK) {
			//btnConectar = (Button) findViewById(R.id.btnConectar);
			if (btt == null) {

				btt = new BluetoothThread(configDevice.getString("btDevAddress", ""), new Handler() {

					@Override
					public void handleMessage(Message message) {

						String s = (String) message.obj;

						String textoB[] = s.split(";");

						// Do something with the message
						if (s.equals("CONNECTED")) {
							btnConectar.setText("Desconectar");
							btnConectar.setEnabled(true);
							Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_LONG).show();
							// ativado = true;
						} else if (s.equals("DISCONNECTED")) {

							Toast.makeText(getApplicationContext(), "Desconectado", Toast.LENGTH_LONG).show();
							btnConectar.setText("Conectar");
							interromperBluetooth();
							//btt = null;
							btnConectar.setEnabled(true);
						} else if (s.equals("CONNECTION FAILED")) {
							Toast.makeText(getApplicationContext(), "Falha na conexao", Toast.LENGTH_LONG).show();
							btnConectar.setText("Conectar");
							interromperBluetooth();
							//btt = null;
							btnConectar.setEnabled(true);
						} else {

							loop: for (int i = 0; i < textoB.length; i++) {
								switch (i) {
								case 0:
									txtArduino01.setText(textoB[0]);
									break;
								case 1:
									txtArduino02.setText(textoB[1]);
									break;
								case 2:
									txtArduino03.setText(textoB[2]);
									break;
								case 3:
									txtArduino04.setText(textoB[3]);
									break;
								case 4:
									txtArduino05.setText(textoB[4]);
									break;
								case 5:
									txtArduino06.setText(textoB[5]);
									break;
								case 6:
									txtArduino07.setText(textoB[6]);
									break;
								default:
									if (imprimir) {
										Toast.makeText(getApplicationContext(),
												"Numeros maximos de linhas ultrapassado!!!!!!!", Toast.LENGTH_LONG)
												.show();
										imprimir = false;
									}

									break loop;
								}
							}
						}
					}
				});
			}

			if (btt != null) {
				// Get the handler that is used to send messages
				writeHandler = btt.getWriteHandler();

				// Run the thread
				btt.start();

				btnConectar.setText("Conectando...");
				btnConectar.setEnabled(false);
			}
			// break;

		} else {
			Toast.makeText(getApplicationContext(), "Nenhum dispositivo Selecionado", Toast.LENGTH_LONG).show();
		}

	}

	private MenuItem btiMostrarDados;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		getMenuInflater().inflate(R.menu.mn_extras_seguidor, menu);
		btiMostrarDados = menu.findItem(R.id.actMostrarOcultar);
		btiMostrarDados.setTitle("Mostrar");
		return true;
	}

	private boolean mostrarDados = true;

	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show();

			interromperBluetooth();
			//this.finishAndRemoveTask();
			finish();

			break;

		case R.id.actModoBotoes:
			Intent modoBotoes = new Intent(this, ModoBotoes.class);
			startActivity(modoBotoes);
			interromperBluetooth();
			finish();

			break;

		case R.id.actModoJoystick:
			Intent modoJoyStick = new Intent(this, ModoJoyStick.class);
			startActivity(modoJoyStick);
			interromperBluetooth();
			finish();
			break;

		case R.id.actValores:
			Value_Bottons.putExtra("frente", frente);
			Value_Bottons.putExtra("direita", direita);
			Value_Bottons.putExtra("esquerda", esquerda);
			Value_Bottons.putExtra("tras", tras);
			Value_Bottons.putExtra("x", x);
			Value_Bottons.putExtra("y", y);
			Value_Bottons.putExtra("z", z);
			Value_Bottons.putExtra("a", a);
			Value_Bottons.putExtra("b", b);
			Value_Bottons.putExtra("c", c);
			Value_Bottons.putExtra("conteudoAVoltar", conteudoAVoltar);
			startActivityForResult(Value_Bottons, VALORES);

			break;

		case R.id.actMostrarOcultar:
			if (mostrarDados) {
				dados.setVisibility(View.VISIBLE);
				txtArduino01.setVisibility(View.VISIBLE);
				txtArduino02.setVisibility(View.VISIBLE);
				txtArduino03.setVisibility(View.VISIBLE);
				txtArduino04.setVisibility(View.VISIBLE);
				txtArduino05.setVisibility(View.VISIBLE);
				txtArduino06.setVisibility(View.VISIBLE);
				txtArduino07.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "Dados visiveis", Toast.LENGTH_LONG).show();
				btiMostrarDados.setTitle("Ocultar");
				mostrarDados = false;

			} else {
				dados.setVisibility(View.INVISIBLE);
				txtArduino01.setVisibility(View.INVISIBLE);
				txtArduino02.setVisibility(View.INVISIBLE);
				txtArduino03.setVisibility(View.INVISIBLE);
				txtArduino04.setVisibility(View.INVISIBLE);
				txtArduino05.setVisibility(View.INVISIBLE);
				txtArduino06.setVisibility(View.INVISIBLE);
				txtArduino07.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "Dados invisiveis", Toast.LENGTH_LONG).show();
				btiMostrarDados.setTitle("Mostrar");
				mostrarDados = true;
			}
			break;

		case R.id.actSobreApp:
			InfoFragment dFragment = new InfoFragment();
			dFragment.show(fragmentoSobreApp, "Dialog Fragment");
			break;
		}

		return true;
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void resgatarValoresBotoes() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		frente = settings.getString("frente", "8");
		direita = settings.getString("direita", "6");
		esquerda = settings.getString("esquerda", "4");
		tras = settings.getString("tras", "2");
		x = settings.getString("x", "x");
		y = settings.getString("y", "y");
		z = settings.getString("z", "z");
		a = settings.getString("a", "a");
		b = settings.getString("b", "b");
		c = settings.getString("c", "c");
		conteudoAVoltar = settings.getString("conteudoAVoltar", "0");
		txtWeight1.setText(settings.getString("weight1", "1"));
		txtWeight2.setText(settings.getString("weight2", "2"));
		txtWeight3.setText(settings.getString("weight3", "6"));
		txtWeight4.setText(settings.getString("weight4", "8"));
		txtMaxInverse.setText(settings.getString("maxInverse", "100"));
		txtProporcinalValue.setText(settings.getString("proporcinalValue", "200"));
		txtMax.setText(settings.getString("max", "120"));
		txtTempo.setText(settings.getString("tempo", "0"));
		txtLinhaReta.setText(settings.getString("reta", "200"));
		///////
		txtWeight12.setText(settings.getString("weight12", "1"));
		txtWeight22.setText(settings.getString("weight22", "2"));
		txtWeight32.setText(settings.getString("weight32", "6"));
		txtWeight42.setText(settings.getString("weight42", "8"));
		txtMaxInverse2.setText(settings.getString("maxInverse2", "100"));
		txtProporcinalValue2.setText(settings.getString("proporcinalValue2", "200"));
		txtMax2.setText(settings.getString("max2", "120"));
		txtTempo2.setText(settings.getString("tempo2", "0"));
		txtLinhaReta2.setText(settings.getString("reta2", "200"));
		///////
		txtWeight13.setText(settings.getString("weight13", "1"));
		txtWeight23.setText(settings.getString("weight23", "2"));
		txtWeight33.setText(settings.getString("weight33", "6"));
		txtWeight43.setText(settings.getString("weight43", "8"));
		txtMaxInverse3.setText(settings.getString("maxInverse3", "100"));
		txtProporcinalValue3.setText(settings.getString("proporcinalValue3", "200"));
		txtMax3.setText(settings.getString("max3", "120"));
		txtTempo3.setText(settings.getString("tempo3", "0"));
		txtLinhaReta3.setText(settings.getString("reta3", "200"));
	}

	public void salvarValores() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("frente", frente);
		editor.putString("direita", direita);
		editor.putString("esquerda", esquerda);
		editor.putString("tras", tras);
		editor.putString("x", x);
		editor.putString("y", y);
		editor.putString("z", z);
		editor.putString("a", a);
		editor.putString("b", b);
		editor.putString("c", c);
		editor.putString("conteudoAVoltar", conteudoAVoltar);
		editor.putString("maxInverse", txtMaxInverse.getText().toString());
		editor.putString("proporcinalValue", txtProporcinalValue.getText().toString());
		editor.putString("max", txtMax.getText().toString());
		editor.putString("tempo", txtTempo.getText().toString());
		editor.putString("weight1", txtWeight1.getText().toString());
		editor.putString("weight2", txtWeight2.getText().toString());
		editor.putString("weight3", txtWeight3.getText().toString());
		editor.putString("weight4", txtWeight4.getText().toString());
		editor.putString("reta", txtLinhaReta.getText().toString());
		///////
		editor.putString("maxInverse2", txtMaxInverse2.getText().toString());
		editor.putString("proporcinalValue2", txtProporcinalValue2.getText().toString());
		editor.putString("max2", txtMax2.getText().toString());
		editor.putString("tempo2", txtTempo2.getText().toString());
		editor.putString("weight12", txtWeight12.getText().toString());
		editor.putString("weight22", txtWeight22.getText().toString());
		editor.putString("weight32", txtWeight32.getText().toString());
		editor.putString("weight42", txtWeight42.getText().toString());
		editor.putString("reta2", txtLinhaReta2.getText().toString());
		///////
		editor.putString("maxInverse3", txtMaxInverse3.getText().toString());
		editor.putString("proporcinalValue3", txtProporcinalValue3.getText().toString());
		editor.putString("max3", txtMax3.getText().toString());
		editor.putString("tempo3", txtTempo3.getText().toString());
		editor.putString("weight13", txtWeight13.getText().toString());
		editor.putString("weight23", txtWeight23.getText().toString());
		editor.putString("weight33", txtWeight33.getText().toString());
		editor.putString("weight43", txtWeight43.getText().toString());
		editor.putString("reta3", txtLinhaReta3.getText().toString());


		

		// Confirma a gravação dos dados
		editor.commit();

		SharedPreferences configInicializador = getSharedPreferences(PREFS_NAME_CLOSE, MODE_PRIVATE);
		SharedPreferences.Editor editorInicializador = configInicializador.edit();

		editorInicializador.putInt("inicializar", 3);
		editorInicializador.commit();
	}
	
	@Override
    public void onBackPressed() {
    	interromperBluetooth();
    	finish();
    };

	@Override
	protected void onPause() {
		super.onPause();

		salvarValores();
	}

	@Override
	public void onResume() {
		super.onResume();
		acaoDosBotoes();
		// resgatarValoresBotoes();
	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		interromperBluetooth();
	}

}
