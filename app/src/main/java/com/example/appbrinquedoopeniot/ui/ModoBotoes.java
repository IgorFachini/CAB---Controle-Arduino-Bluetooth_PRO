package com.example.appbrinquedoopeniot.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbrinquedoopeniot.service.BluetoothThread;
import com.example.appbrinquedoopeniot.R;

/**
 * Activity principal que cuida referencia botoes da tela principal, e manipula
 * os dados que são salvos.
 * 
 * @author Administrador
 */
@SuppressLint({ "HandlerLeak", "ClickableViewAccessibility", "WorldWriteableFiles", "NewApi" })
public class ModoBotoes extends FragmentActivity {

	String frente, direita, esquerda, tras, x, y, z, a, b, c, conteudoAVoltar;
	Button btnConectar, btnFrente, btnDireita, btnEsquerda, btnTras, btn1, btn2, btn3, btn4, btn5, btn6;
	TextView txtArduino01, txtArduino02, txtArduino03, txtArduino04, txtArduino05, txtArduino06, txtArduino07, txtStatus;
	View dados;

	public static final String PREFS_NAME_BlUETOOTH = "device";
	FragmentManager fragmentoSobreApp = getSupportFragmentManager();

	public static final String PREFS_NAME = "valoresBotoes";
	public static final String PREFS_NAME_CLOSE = "inicializador";
	
	Intent Value_Bottons;
	LinearLayout telaFundo;

	BluetoothThread btt;
	Handler writeHandler;

	// Requisição para Activity de ativação do Bluetooth
	// Se numero for maior > 0,este codigo sera devolvido em onActivityResult()
	private static final int REQUEST_ENABLE_BT = 1;
	// Requisição para Activity para inciar tela do aplicativos pareados,
	// que se houver ou nao aplicativo pareado retornara para onActivityResult()
	// e realizara as devidas ações conforme a resposta
	public static final int SELECT_PAIRED_DEVICE = 2;
	public static final int VALORES = 4;

	// BluetoothAdapter é comando de entrada padrão paras todads interações com
	private BluetoothAdapter bluetoothPadrao = null;

	// private static String address = "20:13:06:19:08:29";

	public String deviceName;


	/**
	 * Criação da tela
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		setContentView(R.layout.activity_modo_botoes);
		ActionBar ab = getActionBar();
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
		}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Value_Bottons = new Intent(this, Values_bottons.class);

		// Obtem o bluetooth padrao do aparelho celular
		bluetoothPadrao = BluetoothAdapter.getDefaultAdapter();

		// Vereficamos se o aparelho possui adaptador Bluetooth

        referenciarElementosTela();
		resgatarValoresBotoes();

		txtStatus.setText(getResources().getString(R.string.state) + " " + getResources().getString(R.string.desconectado));



	}

	public void referenciarElementosTela() {
		telaFundo = (LinearLayout)findViewById(R.id.FundoDados);
		dados = findViewById(R.id.FundoDados);
		txtArduino01 = (TextView) findViewById(R.id.txtArduino01);
		txtArduino02 = (TextView) findViewById(R.id.txtArduino02);
		txtArduino03 = (TextView) findViewById(R.id.txtArduino03);
		txtArduino04 = (TextView) findViewById(R.id.txtArduino04);
		txtArduino05 = (TextView) findViewById(R.id.txtArduino05);
		txtArduino06 = (TextView) findViewById(R.id.txtArduino06);
		txtArduino07 = (TextView) findViewById(R.id.txtArduino07);
		txtStatus = findViewById(R.id.txtBtStatus);

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
		
		
	}

	

	public void listaDeDispositivos() {
		if (bluetoothPadrao.isEnabled()) {
			if (btt == null) {
				Intent searchPairedDevicesIntent = new Intent(this, ListBluetooth.class);
				startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
			} else {
				interromperBluetooth();
			}

		}
	}
	
	public void interromperBluetooth(){
		if(btt != null){
			btnConectar.setText(getResources().getString(R.string.conectar));
			btt.interrupt();
			btt.disconnect();
			btt = null;
			txtStatus.setText(getResources().getString(R.string.state)
					+ " "
					+ getResources().getString(R.string.desconectado)
					+	" de " +
					deviceName);
		}
		
	}

	public void connectButtonPressed(View v) {

		if (bluetoothPadrao == null) {
			showTextWithColorRed(getResources().getString(R.string.dispostivoNaoPossuiBluetooth));
		} else {
			if(btt != null){
				interromperBluetooth();
			}else {
				if (!bluetoothPadrao.isEnabled()) {

					Intent novoIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(novoIntent, REQUEST_ENABLE_BT);
				} else {

					listaDeDispositivos();
				}
			}
		}

	}


	public void reconect(View v) {

		if (!bluetoothPadrao.isEnabled()) {
			showToast(getResources().getString(R.string.ativeBluetooth));
		} else {
			btnConectar.setText(getResources().getString(R.string.cancel));
			try {
				interromperBluetooth();
//				Thread.sleep(1000);
				connectWithBluetooth(RESULT_OK);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void acaoDosBotoes() {
		btnFrente.setOnTouchListener(new BotaoListener(frente));
		btnEsquerda.setOnTouchListener(new BotaoListener(esquerda));
		btnDireita.setOnTouchListener(new BotaoListener(direita));
		btnTras.setOnTouchListener(new BotaoListener(tras));
		btn1.setOnTouchListener(new BotaoListener(x));
		btn2.setOnTouchListener(new BotaoListener(y));
		btn3.setOnTouchListener(new BotaoListener(z));
		btn4.setOnTouchListener(new BotaoListener(a));
		btn5.setOnTouchListener(new BotaoListener(b));
		btn6.setOnTouchListener(new BotaoListener(c));
		telaFundo.setOnClickListener(new telaFundo());
	}

	public class BotaoListener implements OnTouchListener {

		private String mensagem;

		BotaoListener(String mensagem) {
			super();
			this.mensagem = mensagem;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
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
			}
//			else {
//				// Bluetooth nao conectado
//			}
			return false;
		}

	}

	public class telaFundo implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			mostrarEsconderTelaFundo();
		}
	}
	public void mostrarEsconderTelaFundo(){
		if (mostrarDados) {
			dados.setBackgroundColor(Color.BLACK);
			txtArduino01.setVisibility(View.VISIBLE);
			txtArduino02.setVisibility(View.VISIBLE);
			txtArduino03.setVisibility(View.VISIBLE);
			txtArduino04.setVisibility(View.VISIBLE);
			txtArduino05.setVisibility(View.VISIBLE);
			txtArduino06.setVisibility(View.VISIBLE);
			txtArduino07.setVisibility(View.VISIBLE);
			showToast(getResources().getString(R.string.dadosVisiveis));
			btiMostrarDados.setTitle(getResources().getString(R.string.ocultar));
			mostrarDados = false;

		} else {
			dados.setBackgroundColor(Color.TRANSPARENT);
			txtArduino01.setVisibility(View.INVISIBLE);
			txtArduino02.setVisibility(View.INVISIBLE);
			txtArduino03.setVisibility(View.INVISIBLE);
			txtArduino04.setVisibility(View.INVISIBLE);
			txtArduino05.setVisibility(View.INVISIBLE);
			txtArduino06.setVisibility(View.INVISIBLE);
			txtArduino07.setVisibility(View.INVISIBLE);
			showToast(getResources().getString(R.string.dadosInvisivel));
			btiMostrarDados.setTitle(getResources().getString(R.string.mostrar));
			mostrarDados = true;
		}
	}

	boolean imprimir = true;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

			// Retorno do pedido de ativação do Bluetooth
			case REQUEST_ENABLE_BT:

				if (resultCode == Activity.RESULT_OK) {
					showTextWithColorGreen(getResources().getString(R.string.bluetooth_ativado));
					listaDeDispositivos();
				} else {
					showTextWithColorRed(getResources().getString(R.string.precisa_ativar_bluetooth));
				}
				break;
			case SELECT_PAIRED_DEVICE:
				connectWithBluetooth(resultCode);
				break;
			case VALORES:
				if (resultCode == RESULT_OK) {
					showTextWithColorGreen(getResources().getString(R.string.mudanca_salvas));
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
					showTextWithColorRed(getResources().getString(R.string.mudancas_nao_Salvas));
				}

				break;
		}
	}





	private void connectWithBluetooth(int resultCode) {
		SharedPreferences configDevice = getSharedPreferences(PREFS_NAME_BlUETOOTH, MODE_PRIVATE);
		if (resultCode == RESULT_OK) {
			if (btt == null) {
				deviceName = configDevice.getString("btDevName","");
				txtStatus.setText(getResources().getString(R.string.state)
						+ " "
						+ getResources().getString(R.string.conectando)
						+	" " +
						deviceName);

				btt = new BluetoothThread(this,configDevice.getString("btDevAddress", ""), new Handler() {

					@Override
					public void handleMessage(Message message) {

						String s = (String) message.obj;

						String textoB[] = s.split(";");

						// Do something with the message
                        switch (s) {
                            case "CONNECTED":
                                btnConectar.setText(getResources().getString(R.string.desconectar));
                                showTextWithColorGreen(getResources().getString(R.string.conectado));
								txtStatus.setText("Conectado em: " + deviceName);

								break;
                            case "DISCONNECTED":
                                showToast(getResources().getString(R.string.desconectado));
                                interromperBluetooth();
                                break;
                            case "CONNECTION FAILED":
                                showTextWithColorRed(getResources().getString(R.string.falhaNaConexao));
                                interromperBluetooth();
                                break;
                            default:

                                loop:
                                for (int i = 0; i < textoB.length; i++) {
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
                                                showTextWithColorRed(getResources().getString(R.string.numerosMaximosDeLinhasUltrapassado));
                                                imprimir = false;
                                            }

                                            break loop;
                                    }
                                }
                                break;
                        }
					}
				});
			}
            if(btt != null){
				// Get the handler that is used to send messages
				writeHandler = btt.getWriteHandler();

				// Run the thread
				btt.start();

				btnConectar.setText(getResources().getString(R.string.cancel));
			}
			// break;

		} else {
			showToast(getResources().getString(R.string.nenhumDispositivoSelecionado));
		}
	}
	private MenuItem btiMostrarDados;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mn_modo_botoes, menu);
		btiMostrarDados = menu.findItem(R.id.actMostrarOcultar);
		btiMostrarDados.setTitle(getResources().getString(R.string.mostrar));
		return true;
	}

	private boolean mostrarDados = true;

	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) {

		switch (item.getItemId()) {

			case R.id.actReconnect:
				reconect(null);
				break;
		case android.R.id.home:
			showToast(getResources().getString(R.string.sair));

			interromperBluetooth();
			//this.finishAndRemoveTask();
			finish();

			break;
			
		case R.id.actSeguidor:
			Intent seguidor = new Intent(this, Extras_seguidor.class);
			startActivity(seguidor);
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
		 case R.id.actModoJoystick:
			 Intent modoJoyStick =  new Intent(this, ModoJoyStick.class);
			 startActivity(modoJoyStick);
			 interromperBluetooth();
			 finish();
		 break;
		case R.id.actMostrarOcultar:
			mostrarEsconderTelaFundo();
			break;

		case R.id.actSobreApp:
			InfoFragment dFragment = new InfoFragment();
			dFragment.show(fragmentoSobreApp, "Dialog Fragment");
			break;
		}

		return true;
	}

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

	private void salvarValores() {

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

		// Confirma a gravação dos dados
		editor.apply();

		SharedPreferences configInicializador = getSharedPreferences(PREFS_NAME_CLOSE, MODE_PRIVATE);
		SharedPreferences.Editor editorInicializador = configInicializador.edit();

		editorInicializador.putInt("inicializar", 1);
		editorInicializador.apply();
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
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		salvarValores();
	}
	
	@Override
    public void onBackPressed() {
    	interromperBluetooth();
    	finish();
    }

    @Override
	public void onResume() {
		super.onResume();
		acaoDosBotoes();
		resgatarValoresBotoes();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		interromperBluetooth();
	}

	public void showToast(final String mensagem){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_SHORT).show();
			}
		});
	}

	//Usado para mostras as mensagens em vermelho.
	public void showTextWithColorRed(final String mensagem){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT);
				TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
				v.setTextColor(Color.RED);
				toast.show();
			}
		});

	}
	//Usado para mostras as mensagens em verde.
	public void showTextWithColorGreen(final String mensagem){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT);
				TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
				v.setTextColor(Color.GREEN);
				toast.show();
			}
		});
	}

}
