package com.example.appbrinquedoopeniot.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbrinquedoopeniot.service.BluetoothThread;
import com.example.appbrinquedoopeniot.R;
import com.example.appbrinquedoopeniot.common.InputFilterMinMax;
import com.example.appbrinquedoopeniot.model.FollowLineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@SuppressLint({"HandlerLeak", "ClickableViewAccessibility", "WorldWriteableFiles", "NewApi"})
public class Extras_seguidor extends FragmentActivity {

    String frente, direita, esquerda, tras, x, y, z, a, b, c, conteudoAVoltar;
    Button btnParar, btnConectar, btnFrente, btnDireita, btnEsquerda, btnTras, btn1, btn2, btn3, btn4, btn5, btn6;
    TextView txtArduino01, txtArduino02, txtArduino03, txtArduino04, txtArduino05, txtArduino06, txtArduino07, txtNome;
    View dados;
    EditText txtVTS, txtMaxInverse, txtProporcinalValue, txtMax, txtTempo,txtTempod1,txtTempod2,txtTempod3,txtTempod4, txtWeight1, txtWeight2, txtWeight3, txtWeight4,
            txtLinhaReta;
    public static final String PREFS_NAME_BlUETOOTH = "device";
    FragmentManager fragmentoSobreApp = getSupportFragmentManager();
    LinearLayout telaFundo;

    public static final String PREFS_NAME = "valoresSeguidor";
    public static final String PREFS_NAME_CLOSE = "inicializador";

    private List<FollowLineModel> followLineData = new ArrayList<FollowLineModel>();
    private int followLineThis = 99;
    private FollowLineModel follow;

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
        assert ab != null;
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
        telaFundo = (LinearLayout)findViewById(R.id.FundoDados);
        dados = findViewById(R.id.FundoDados);
        txtArduino01 = (TextView) findViewById(R.id.txtArduino01);
        txtArduino02 = (TextView) findViewById(R.id.txtArduino02);
        txtArduino03 = (TextView) findViewById(R.id.txtArduino03);
        txtArduino04 = (TextView) findViewById(R.id.txtArduino04);
        txtArduino05 = (TextView) findViewById(R.id.txtArduino05);
        txtArduino06 = (TextView) findViewById(R.id.txtArduino06);
        txtArduino07 = (TextView) findViewById(R.id.txtArduino07);
        txtNome = (TextView) findViewById(R.id.txtNome);
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
        txtVTS = (EditText) findViewById(R.id.txtVTS);
        txtMaxInverse = (EditText) findViewById(R.id.txtMaxInverse);
        txtProporcinalValue = (EditText) findViewById(R.id.txtProporcionalValue);
        txtTempo = (EditText) findViewById(R.id.txtTempo);
        txtTempod1 = (EditText) findViewById(R.id.txtTempod1);
        txtTempod2 = (EditText) findViewById(R.id.txtTempod2);
        txtTempod3 = (EditText) findViewById(R.id.txtTempod3);
        txtTempod4 = (EditText) findViewById(R.id.txtTempod4);
        txtMax = (EditText) findViewById(R.id.txtMax);
        txtMax.setFilters(new InputFilter[]{new InputFilterMinMax("0", "254")});
        txtWeight1 = (EditText) findViewById(R.id.txtWeight1);
        txtWeight2 = (EditText) findViewById(R.id.txtWeight2);
        txtWeight3 = (EditText) findViewById(R.id.txtWeight3);
        txtWeight4 = (EditText) findViewById(R.id.txtWeight4);
        txtLinhaReta = (EditText) findViewById(R.id.txtLinhaReta);
        ///////////////////////////////

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

    public void reconect(View v) {

        if (!bluetoothPadrao.isEnabled()) {
            showToast(getResources().getString(R.string.ativeBluetooth));
        } else {
            btnConectar.setText(getResources().getString(R.string.conectando));
            btnConectar.setEnabled(false);
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

    public void cronometroParar() {
        if (!isClickPause) { //entra para false;
            tempoQuandoParado = m_chronometer.getBase() - SystemClock.elapsedRealtime();
        }
        m_chronometer.stop();
        isClickPause = true;

    }

    public void cronometroIniciar() {
        m_chronometer.stop();
        m_chronometer.setText(getResources().getString(R.string.timer));
        tempoQuandoParado = 0;

        if (isClickPause) {
            m_chronometer.setBase(SystemClock.elapsedRealtime() + tempoQuandoParado);
            m_chronometer.start();
            tempoQuandoParado = 0;
            isClickPause = false;
        } else {
            m_chronometer.setBase(SystemClock.elapsedRealtime());
            m_chronometer.start();
            tempoQuandoParado = 0;
        }
    }

    public void listarDadosSeguidor() {
        Intent novoIntent = new Intent(this, ChooseFollowLineData.class);
        startActivity(novoIntent);
    }

    public void addFollowData() {
        showChangeLangDialog(true);
    }

    public void editFollowData() {
        showChangeLangDialog(false);
    }

    public void showChangeLangDialog(boolean add) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        if (!add) {
            edt.setText(txtNome.getText().toString());
        }

        follow = new FollowLineModel();
        follow.setVts(txtVTS.getText().toString());
        follow.setWeight1(txtWeight1.getText().toString());
        follow.setWeight2(txtWeight2.getText().toString());
        follow.setWeight3(txtWeight3.getText().toString());
        follow.setWeight4(txtWeight4.getText().toString());
        follow.setMaxInverse(txtMaxInverse.getText().toString());
        follow.setProporcinalValue(txtProporcinalValue.getText().toString());
        follow.setMax(txtMax.getText().toString());
        follow.setTempo(txtTempo.getText().toString());
        follow.setTempod1(txtTempod1.getText().toString());
        follow.setTempod2(txtTempod2.getText().toString());
        follow.setTempod3(txtTempod3.getText().toString());
        follow.setTempod4(txtTempod4.getText().toString());
        follow.setReta(txtLinhaReta.getText().toString());

        dialogBuilder.setTitle("Nome do seguidor");
        dialogBuilder.setMessage("Entre com o nome do seguir");
        if (add) {
            dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    follow.setNome(edt.getText().toString());
                    followLineData.add(follow);
                    followLineThis = followLineData.size() -1;
                    txtNome.setText(edt.getText().toString());
                    showTextWithColorGreen(getResources().getString(R.string.mudanca_salvas));
                }
            });
        } else {
            dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    follow.setNome(edt.getText().toString());
                    followLineData.set(followLineThis,follow);
                    txtNome.setText(edt.getText().toString());
                    showTextWithColorGreen(getResources().getString(R.string.mudanca_salvas));
                }
            });
        }
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void iniciar(View v) {
        String mensagem = "i:" + txtMaxInverse.getText().toString() + "&s:" + txtVTS.getText().toString()
                + "&p:" + txtProporcinalValue.getText().toString()
                + "&v:" + txtMax.getText().toString() + "&t:" + txtTempo.getText().toString()
                + "&h:" + txtTempod1.getText().toString()
                + "&j:" + txtTempod2.getText().toString()
                + "&k:" + txtTempod3.getText().toString()
                + "&o:" + txtTempod4.getText().toString()+ "&l:"
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


    public void acaoDosBotoes() {
        btnFrente.setOnTouchListener(new BotaoListener(frente, false));
        btnEsquerda.setOnTouchListener(new BotaoListener(esquerda, false));
        btnDireita.setOnTouchListener(new BotaoListener(direita, false));
        btnTras.setOnTouchListener(new BotaoListener(tras, false));
        btn1.setOnTouchListener(new BotaoListener(x, false));
        btn2.setOnTouchListener(new BotaoListener(y, false));
        btn3.setOnTouchListener(new BotaoListener(z, false));
        btn4.setOnTouchListener(new BotaoListener(a, false));
        btn5.setOnTouchListener(new BotaoListener(b, false));
        btn6.setOnTouchListener(new BotaoListener(c, false));
        btnParar.setOnTouchListener(new BotaoListener("s", true));
        telaFundo.setOnClickListener(new telaFundo());
    }

      public class BotaoListener implements OnTouchListener {

        private String mensagem;
        private boolean parar = false;

        BotaoListener(String mensagem, boolean parar) {
            super();
            this.mensagem = mensagem;
            this.parar = parar;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (parar) {
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
            }
//            else {
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

            // Retrono do pedido de ativa��o do Bluetooth
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
            if (btt != null) {
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

    private MenuItem btiMostrarDados;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.mn_extras_seguidor, menu);
        btiMostrarDados = menu.findItem(R.id.actMostrarOcultar);
        btiMostrarDados.setTitle(getResources().getString(R.string.mostrar));
        return true;
    }

    private boolean mostrarDados = true;

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                showToast(getResources().getString(R.string.sair));
                interromperBluetooth();
                //this.finishAndRemoveTask();
                finish();

                break;

            case R.id.actDadosSeguidorListar:
                listarDadosSeguidor();
                break;
            case R.id.actDadosSeguidorAdicionar:
                addFollowData();
                break;
            case R.id.actDadosSeguidorEditar:
                editFollowData();
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


        followLineData.clear();

        JSONArray array = new JSONArray();
        JSONObject obj;


        followLineThis = settings.getInt("followLineThis", 0);
        array = null;
        try {
            follow = new FollowLineModel();
            array = new JSONArray(settings.getString("followLineData", follow.getStartFollowData()));

            for (int i = 0; i < array.length(); ++i) {
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
                follow.setTempod1(obj.getString("tempod1"));
                follow.setTempod2(obj.getString("tempod2"));
                follow.setTempod3(obj.getString("tempod3"));
                follow.setTempod4(obj.getString("tempod4"));
                follow.setReta(obj.getString("reta"));

                // O mesmo para as demais propriedades...

                followLineData.add(follow);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(followLineData, new Comparator<FollowLineModel>() {
            @Override
            public int compare(FollowLineModel followLineModel, FollowLineModel t1) {
                  return  followLineModel.getNome().compareTo(t1.getNome());
            }


        });
        txtNome.setText(followLineData.get(followLineThis).getNome());
        txtVTS.setText(followLineData.get(followLineThis).getVts());
        txtLinhaReta.setText(followLineData.get(followLineThis).getReta());
        txtWeight1.setText(followLineData.get(followLineThis).getWeight1());
        txtWeight2.setText(followLineData.get(followLineThis).getWeight2());
        txtWeight3.setText(followLineData.get(followLineThis).getWeight3());
        txtWeight4.setText(followLineData.get(followLineThis).getWeight4());
        txtMaxInverse.setText(followLineData.get(followLineThis).getMaxInverse());
        txtProporcinalValue.setText(followLineData.get(followLineThis).getProporcinalValue());
        txtMax.setText(followLineData.get(followLineThis).getMax());
        txtTempo.setText(followLineData.get(followLineThis).getTempo());
        txtTempod1.setText(followLineData.get(followLineThis).getTempod1());
        txtTempod2.setText(followLineData.get(followLineThis).getTempod2());
        txtTempod3.setText(followLineData.get(followLineThis).getTempod3());
        txtTempod4.setText(followLineData.get(followLineThis).getTempod4());


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


        editor.putInt("followLineThis", followLineThis);


        // Confirma a grava��o dos dados
        editor.apply();

        SharedPreferences configInicializador = getSharedPreferences(PREFS_NAME_CLOSE, MODE_PRIVATE);
        SharedPreferences.Editor editorInicializador = configInicializador.edit();

        editorInicializador.putInt("inicializar", 3);
        editorInicializador.apply();

        follow = new FollowLineModel();
        follow.setNome(txtNome.getText().toString());
        follow.setVts(txtVTS.getText().toString());
        follow.setWeight1(txtWeight1.getText().toString());
        follow.setWeight2(txtWeight2.getText().toString());
        follow.setWeight3(txtWeight3.getText().toString());
        follow.setWeight4(txtWeight4.getText().toString());
        follow.setMaxInverse(txtMaxInverse.getText().toString());
        follow.setProporcinalValue(txtProporcinalValue.getText().toString());
        follow.setMax(txtMax.getText().toString());
        follow.setTempo(txtTempo.getText().toString());
        follow.setTempod1(txtTempod1.getText().toString());
        follow.setTempod2(txtTempod2.getText().toString());
        follow.setTempod3(txtTempod3.getText().toString());
        follow.setTempod4(txtTempod4.getText().toString());
        follow.setReta(txtLinhaReta.getText().toString());
        followLineData.set(followLineThis, follow);

        JSONArray array = new JSONArray();
        JSONObject obj;

        for (FollowLineModel c : followLineData) {
            obj = new JSONObject();


            try {
                obj.put("nome", c.getNome());
                obj.put("weight1", c.getWeight1());
                obj.put("weight2", c.getWeight2());
                obj.put("weight3", c.getWeight3());
                obj.put("weight4", c.getWeight4());
                obj.put("maxInverse", c.getMaxInverse());
                obj.put("proporcinalValue", c.getProporcinalValue());
                obj.put("max", c.getMax());
                obj.put("tempo", c.getTempo());
                obj.put("tempod1", c.getTempod1());
                obj.put("tempod2", c.getTempod2());
                obj.put("tempod3", c.getTempod3());
                obj.put("tempod4", c.getTempod4());
                obj.put("reta", c.getReta());
                obj.put("vts", c.getVts());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Adicionar as demais propriedades...

            array.put(obj);
        }

        String arrayStr = array.toString();


        editor.putString("followLineData", arrayStr).commit();
    }

    @Override
    public void onBackPressed() {
        interromperBluetooth();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        salvarValores();
    }

    @Override
    public void onResume() {
        super.onResume();
        acaoDosBotoes();
        resgatarValoresBotoes();
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

    public void showToast(final String mensagem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Usado para mostras as mensagens em vermelho.
    public void showTextWithColorRed(final String mensagem) {
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
    public void showTextWithColorGreen(final String mensagem) {
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
