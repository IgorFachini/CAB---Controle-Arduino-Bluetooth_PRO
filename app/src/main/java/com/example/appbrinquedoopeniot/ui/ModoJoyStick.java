package com.example.appbrinquedoopeniot.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbrinquedoopeniot.service.BluetoothThread;
import com.example.appbrinquedoopeniot.R;
import com.example.appbrinquedoopeniot.common.JoyStickClass;

@SuppressLint("WorldWriteableFiles")
public class ModoJoyStick extends FragmentActivity {
	RelativeLayout layout_joystick;
	String dadosUp_Low, dadosUp_Medium, dadosUp_High, dadosUpRight_Low, dadosUpRight_Medium, dadosUpRight_High,
			dadosRight_Low, dadosRight_Medium, dadosRight_High, dadosDownRight_Low, dadosDownRight_Medium,
			dadosDownRight_High, dadosDown_Low, dadosDown_Medium, dadosDown_High, dadosDownLeft_Low,
			dadosDownLeft_Medium, dadosDownLeft_High, dadosLeft_Low, dadosLeft_Medium, dadosLeft_High, dadosUpLeft_Low,
			dadosUpLeft_Medium, dadosUpLeft_High, conteudoAVoltar;
	ImageView image_joystick, image_border;
	TextView textView1, textView2, textView3, textView4, textView5, lblNivel;

	String x, y, z, a, b, c;
	Button btn1, btn2, btn3, btn4, btn5, btn6;
	TextView txtArduino01, txtArduino02, txtArduino03, txtArduino04, txtArduino05, txtArduino06, txtArduino07;
	View dados;
	SharedPreferences configValores;
	SharedPreferences.Editor editorValores;
	JoyStickClass js;
	FragmentManager fragmentoSobreApp = getSupportFragmentManager();
	public static final String PREFS_NAME_VALORES_JOY = "valoresJoy";
	public static final String PREFS_NAME_CLOSE = "inicializador";
	public static final String PREFS_NAME_BlUETOOTH = "device";
	Handler h;

	BluetoothThread btt;
	Handler writeHandler;
	Button btnConectar;
	Intent values_joystick;
	LinearLayout telaFundo;


	boolean velocidade, direcoes8,delimitador;

	private BluetoothAdapter bluetoothPadrao = null;

	private static final int REQUEST_ENABLE_BT = 1;
	public static final int SELECT_PAIRED_DEVICE = 2;
	public static final int VALORES = 4;

	@SuppressLint("ClickableViewAccessibility")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modo_joystick);
		bluetoothPadrao = BluetoothAdapter.getDefaultAdapter();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		values_joystick = new Intent(this, Values_Joystick.class);

		referenciarElementosTela();

	}

	public void referenciarElementosTela() {
		telaFundo = (LinearLayout)findViewById(R.id.FundoDados);
		dados = (View) findViewById(R.id.FundoDados);
		txtArduino01 = (TextView) findViewById(R.id.txtArduino01);
		txtArduino02 = (TextView) findViewById(R.id.txtArduino02);
		txtArduino03 = (TextView) findViewById(R.id.txtArduino03);
		txtArduino04 = (TextView) findViewById(R.id.txtArduino04);
		txtArduino05 = (TextView) findViewById(R.id.txtArduino05);
		txtArduino06 = (TextView) findViewById(R.id.txtArduino06);
		txtArduino07 = (TextView) findViewById(R.id.txtArduino07);
		btnConectar = (Button) findViewById(R.id.btnConectar);
		btn1 = (Button) findViewById(R.id.bt_x);
		btn2 = (Button) findViewById(R.id.bt_y);
		btn3 = (Button) findViewById(R.id.bt_z);
		btn4 = (Button) findViewById(R.id.bt_a);
		btn5 = (Button) findViewById(R.id.bt_b);
		btn6 = (Button) findViewById(R.id.bt_c);

	}

	@SuppressLint("ClickableViewAccessibility")
	private void acaoDoJoystick() {
		// textView1 = (TextView) findViewById(R.id.lblPosicaoX);
		// textView2 = (TextView) findViewById(R.id.lblPosicaoY);
		// textView3 = (TextView) findViewById(R.id.lblAngulo);
		//textView4 = (TextView) findViewById(R.id.lblDistancia);
		textView5 = (TextView) findViewById(R.id.lblDirecao);
		lblNivel = (TextView) findViewById(R.id.lblNivel);

		layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

		js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
		js.setStickSize(150, 150);
		js.setLayoutSize(500, 500);
		js.setLayoutAlpha(150);
		js.setStickAlpha(100);
		js.setOffset(90);
		js.setMinimumDistance(50);

		layout_joystick.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				js.drawStick(arg1);
				if (arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {
					// textView1.setText("X : " + String.valueOf(js.getX()));
					// textView2.setText("Y : " + String.valueOf(js.getY()));
					// textView3.setText("Angulo : " +
					// String.valueOf(js.getAngle()));
					//textView4.setText("Distancia : " + String.valueOf(js.getDistance()));
					int direction;
					if (direcoes8) {
						direction = js.get8Direction();
					} else {
						direction = js.get4Direction();
					}

					switch (direction) {
					case JoyStickClass.STICK_UP_LOW:
						textView5.setText(getResources().getString(R.string.direcao) +" Up");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosUp_Low);
						break;
					case JoyStickClass.STICK_UP_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Up");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosUp_Medium);
						break;
					case JoyStickClass.STICK_UP_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Up");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosUp_High);
						break;
					case JoyStickClass.STICK_UPRIGHT_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Up Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosUpRight_Low);
						break;
					case JoyStickClass.STICK_UPRIGHT_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Up Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosUpRight_Medium);
						break;
					case JoyStickClass.STICK_UPRIGHT_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Up Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosUpRight_High);
						break;
					case JoyStickClass.STICK_RIGHT_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosRight_Low);
						break;
					case JoyStickClass.STICK_RIGHT_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosRight_Medium);
						break;
					case JoyStickClass.STICK_RIGHT_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosRight_High);
						break;
					case JoyStickClass.STICK_DOWNRIGHT_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Down Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosDownRight_Low);
						break;
					case JoyStickClass.STICK_DOWNRIGHT_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Down Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosDownRight_Medium);
						break;
					case JoyStickClass.STICK_DOWNRIGHT_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Down Right");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosDownRight_High);
						break;
					case JoyStickClass.STICK_DOWN_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Down");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosDown_Low);
						break;
					case JoyStickClass.STICK_DOWN_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Down");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosDown_Medium);
						break;
					case JoyStickClass.STICK_DOWN_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Down");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosDown_High);
						break;
					case JoyStickClass.STICK_DOWNLEFT_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Down Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosDownLeft_Low);
						break;
					case JoyStickClass.STICK_DOWNLEFT_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Down Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosDownLeft_Medium);
						break;
					case JoyStickClass.STICK_DOWNLEFT_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Down Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosDownLeft_High);
						break;
					case JoyStickClass.STICK_LEFT_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosLeft_Low);
						break;
					case JoyStickClass.STICK_LEFT_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosLeft_Medium);
						break;
					case JoyStickClass.STICK_LEFT_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosLeft_High);
						break;
					case JoyStickClass.STICK_UPLEFT_LOW:
						textView5.setText(getResources().getString(R.string.direcao) + "Up Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.baixo));
						BotaoListener(dadosUpLeft_Low);
						break;
					case JoyStickClass.STICK_UPLEFT_MEDIUM:
						textView5.setText(getResources().getString(R.string.direcao) + "Up Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.medio));
						BotaoListener(dadosUpLeft_Medium);
						break;
					case JoyStickClass.STICK_UPLEFT_HIGH:
						textView5.setText(getResources().getString(R.string.direcao) + "Up Left");
						lblNivel.setText(getResources().getString(R.string.nivel) + getResources().getString(R.string.alto));
						BotaoListener(dadosUpLeft_High);
						break;

					case JoyStickClass.STICK_NONE:
						textView5.setText(getResources().getString(R.string.direcao) + "Center");
						lblNivel.setText(getResources().getString(R.string.nivel) + "Parado");
						BotaoListener(conteudoAVoltar);
						break;
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					// textView1.setText("X :");
					// textView2.setText("Y :");
					// textView3.setText("Angulo :");
					//textView4.setText("Distancia :");
                    textView5.setText(getResources().getString(R.string.direcao));
                    lblNivel.setText(getResources().getString(R.string.nivel));
					BotaoListener(conteudoAVoltar);
				}
				return true;
			}
		});

	}

	public void BotaoListener(String mensagem) {
		if (btt != null) {

			Message msg = Message.obtain();
			msg.obj = mensagem;
			writeHandler.sendMessage(msg);
		}
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
            btnConectar.setText(getResources().getString(R.string.conectar));
			btnConectar.setEnabled(true);
			btt.interrupt();
			btt.disconnect();
			btt = null;
		}

	}

	public void connectButtonPressed(View v) {

		if (bluetoothPadrao == null) {
            showTextWithColorRed(getResources().getString(R.string.dispostivoNaoPossuiBluetooth));
		} else {
			if (!bluetoothPadrao.isEnabled()) {

				Intent novoIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(novoIntent, REQUEST_ENABLE_BT);
			} else {

				listaDeDispositivos();
			}
		}

	}

	boolean imprimir = true;

	@SuppressLint("HandlerLeak")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		// Retrono do pedido de ativação do Bluetooth
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
				resgatarValoresBotoes();

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
                btt = new BluetoothThread(configDevice.getString("btDevAddress", ""), new Handler() {

                    @Override
                    public void handleMessage(Message message) {

                        String s = (String) message.obj;

                        String textoB[] = s.split(";");

                        // Do something with the message
                        switch (s) {
                            case "CONNECTED":
                                btnConectar.setText(getResources().getString(R.string.desconectar));
                                btnConectar.setEnabled(true);
                                showTextWithColorGreen(getResources().getString(R.string.conectado));
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

                btnConectar.setText(getResources().getString(R.string.conectando));
                btnConectar.setEnabled(false);
            }
            // break;

        } else {
            showToast(getResources().getString(R.string.nenhumDispositivoSelecionado));
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

	@SuppressLint("HandlerLeak")


	public void acaoDosBotoes() {
		btn1.setOnTouchListener(new BotaoListener(x));
		btn2.setOnTouchListener(new BotaoListener(y));
		btn3.setOnTouchListener(new BotaoListener(z));
		btn4.setOnTouchListener(new BotaoListener(a));
		btn5.setOnTouchListener(new BotaoListener(b));
		btn6.setOnTouchListener(new BotaoListener(c));
		telaFundo.setOnClickListener(new telaFundo());
	}

	@SuppressLint("ClickableViewAccessibility")
	public class BotaoListener implements OnTouchListener {

		private String mensagem;

		public BotaoListener(String mensagem) {
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
			} else {
				// Bluetooth nao conectado
			}
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

	public void resgatarValoresBotoes() {
        configValores = getSharedPreferences(PREFS_NAME_VALORES_JOY, MODE_PRIVATE);
        editorValores = configValores.edit();
		dadosUp_Low = configValores.getString("cima1", "w1");
		dadosUp_Medium = configValores.getString("cima2", "w2");
		dadosUp_High = configValores.getString("cima3", "w3");
		dadosUpRight_Low = configValores.getString("cimaDireita1", "e1");
		dadosUpRight_Medium = configValores.getString("cimaDireita2", "e2");
		dadosUpRight_High = configValores.getString("cimaDireita3", "e3");
		dadosRight_Low = configValores.getString("direita1", "d1");
		dadosRight_Medium = configValores.getString("direita2", "d2");
		dadosRight_High = configValores.getString("direita3", "d3");
		dadosDownRight_Low = configValores.getString("baixoDireita1", "c1");
		dadosDownRight_Medium = configValores.getString("baixoDireita2", "c2");
		dadosDownRight_High = configValores.getString("baixoDireita3", "c3");
		dadosDown_Low = configValores.getString("baixo1", "s1");
		dadosDown_Medium = configValores.getString("baixo2", "s2");
		dadosDown_High = configValores.getString("baixo3", "s3");
		dadosDownLeft_Low = configValores.getString("baixoEsquerda1", "z1");
		dadosDownLeft_Medium = configValores.getString("baixoEsquerda2", "z2");
		dadosDownLeft_High = configValores.getString("baixoEsquerda3", "z3");
		dadosLeft_Low = configValores.getString("esquerda1", "a1");
		dadosLeft_Medium = configValores.getString("esquerda2", "a2");
		dadosLeft_High = configValores.getString("esquerda3", "a3");
		dadosUpLeft_Low = configValores.getString("cimaEsquerda1", "q1");
		dadosUpLeft_Medium = configValores.getString("cimaEsquerda2", "q2");
		dadosUpLeft_High = configValores.getString("cimaEsquerda3", "q3");
		conteudoAVoltar = configValores.getString("conteudoAVoltar", "0");
		x = configValores.getString("x", "x");
		y = configValores.getString("y", "y");
		z = configValores.getString("z", "z");
		a = configValores.getString("a", "a");
		b = configValores.getString("b", "b");
		c = configValores.getString("c", "c");
		velocidade = configValores.getBoolean("velocidade", false);
		direcoes8 = configValores.getBoolean("direcoes8", false);
		delimitador = configValores.getBoolean("demiliter", false);
	}

	public void salvarValores() {
		editorValores.putString("cima1", dadosUp_Low);
		editorValores.putString("cima2", dadosUp_Medium);
		editorValores.putString("cima3", dadosUp_High);
		editorValores.putString("cimaDireita1", dadosUpRight_Low);
		editorValores.putString("cimaDireita2", dadosUpRight_Medium);
		editorValores.putString("cimaDireita3", dadosUpRight_High);
		editorValores.putString("direita1", dadosRight_Low);
		editorValores.putString("direita2", dadosRight_Medium);
		editorValores.putString("direita3", dadosRight_High);
		editorValores.putString("baixoDireita1", dadosDownRight_Low);
		editorValores.putString("baixoDireita2", dadosDownRight_Medium);
		editorValores.putString("baixoDireita3", dadosDownRight_High);
		editorValores.putString("baixo1", dadosDown_Low);
		editorValores.putString("baixo2", dadosDown_Medium);
		editorValores.putString("baixo3", dadosDown_High);
		editorValores.putString("baixoEsquerda1", dadosDownLeft_Low);
		editorValores.putString("baixoEsquerda2", dadosDownLeft_Medium);
		editorValores.putString("baixoEsquerda3", dadosDownLeft_High);
		editorValores.putString("esquerda1", dadosLeft_Low);
		editorValores.putString("esquerda2", dadosLeft_Medium);
		editorValores.putString("esquerda3", dadosLeft_High);
		editorValores.putString("cimaEsquerda1", dadosUpLeft_Low);
		editorValores.putString("cimaEsquerda2", dadosUpLeft_Medium);
		editorValores.putString("cimaEsquerda3", dadosUpLeft_High);
		editorValores.putString("x", x);
		editorValores.putString("y", y);
		editorValores.putString("z", z);
		editorValores.putString("a", a);
		editorValores.putString("b", b);
		editorValores.putString("c", c);
		editorValores.putString("conteudoAVoltar", conteudoAVoltar);
		editorValores.putBoolean("velocidade", velocidade);
		editorValores.putBoolean("direcoes8", direcoes8);
		editorValores.putBoolean("delimiter", delimitador);

		// Confirma a gravação dos dados
		editorValores.commit();

		SharedPreferences configInicializador = getSharedPreferences(PREFS_NAME_CLOSE, MODE_PRIVATE);
		SharedPreferences.Editor editorInicializador = configInicializador.edit();

		editorInicializador.putInt("inicializar", 2);
		editorInicializador.commit();
	}
	
	@Override
    public void onBackPressed() {
    	interromperBluetooth();
    	finish();
    };
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		interromperBluetooth();
	}

	@Override
	protected void onPause() {
		super.onPause();
		salvarValores();
	}

	@Override
	public void onResume() {
		super.onResume();
        acaoDoJoystick();
        acaoDosBotoes();
		resgatarValoresBotoes();
		setVeloDire8Check();

	}

	

	public void setVeloDire8Check() {
		if (velocidade) {
			js.velocidade = true;
		} else {
			js.velocidade = false;
		}
		if (direcoes8) {
			direcoes8 = true;
		} else {
			direcoes8 = false;
		}
		
		
		
	}

	private MenuItem btiMostrarDados;
	private MenuItem ckbDirecao8;
	private MenuItem ckbVelocidade;;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mn_modo_joystick, menu);

		btiMostrarDados = menu.findItem(R.id.actMostrarOcultar);
        btiMostrarDados.setTitle(getResources().getString(R.string.mostrar));
		ckbDirecao8 = menu.findItem(R.id.ckbDirecoes8);
		ckbVelocidade = menu.findItem(R.id.ckbVelocidade);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (direcoes8) {
			ckbDirecao8 = menu.findItem(R.id.ckbDirecoes8).setChecked(true);
		}
		if (velocidade) {
			ckbVelocidade = menu.findItem(R.id.ckbVelocidade).setChecked(true);
		}
		
		
		return true;
	}

	private boolean mostrarDados = true;

	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) {

		switch (item.getItemId()) {

		case R.id.ckbDirecoes8:
			item.setChecked(!item.isChecked());

			if (ckbDirecao8.isChecked()) {
				direcoes8 = true;
			} else {
				direcoes8 = false;
			}
			break;

		case R.id.ckbVelocidade:
			item.setChecked(!item.isChecked());
			if (ckbVelocidade.isChecked()) {
				js.velocidade = true;
				velocidade = true;
			} else {
				js.velocidade = false;
				velocidade = false;
			}
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
			startActivityForResult(values_joystick, VALORES);
			break;
		case R.id.actModoBotoes:
			Intent modoBotoes = new Intent(this, ModoBotoes.class);
			startActivity(modoBotoes);
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
