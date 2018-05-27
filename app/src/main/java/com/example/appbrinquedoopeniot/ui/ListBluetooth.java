package com.example.appbrinquedoopeniot.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appbrinquedoopeniot.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class ListBluetooth extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter = null;

    private ProgressBar progressBar;

    private ArrayList<String> bluetoothPareados;
    private ArrayList<String> bluetoothEncontrados;

    private ArrayList<BluetoothDevice> bluetoothDispositivosPareados, bluetoothDispositivosEncontrados;

    private ArrayAdapter<String> adapterPareados;
    private ArrayAdapter<String> adapterEncontrados;

    public static final String PREFS_NAME_BlUETOOTH = "device";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!bluetoothAdapter.isEnabled()){
            Intent solicita = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(solicita, 1);
        }

        registerReceiver(receberInfo, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(receberInfo, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

        progressBar = findViewById(R.id.progressBar);
        ListView lvBluetoothEncontrados = findViewById(R.id.lv_bluetooth_encontrados);
        ListView lvBluetoothPareados = findViewById(R.id.lv_bluetooth_pareados);

        progressBar.setVisibility(View.INVISIBLE);

        bluetoothDispositivosPareados = new ArrayList<>();
        bluetoothDispositivosEncontrados = new ArrayList<>();

        bluetoothPareados = new ArrayList<>();
        bluetoothEncontrados = new ArrayList<>();

        adapterEncontrados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bluetoothEncontrados);
        adapterPareados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bluetoothPareados);

        lvBluetoothEncontrados.setAdapter(adapterEncontrados);
        lvBluetoothPareados.setAdapter(adapterPareados);


        if (!bluetoothAdapter.isEnabled()) {
            ligarBluetooth();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothAdapter.isEnabled()) {
                    iniciarBusca();
                } else {
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.precisa_ativar_bluetooth), Toast.LENGTH_LONG).show();
                }
            }
        });

        lvBluetoothEncontrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BluetoothDevice device = bluetoothDispositivosEncontrados.get(i);

                if(bluetoothEncontrados.get(i).contains(device.getAddress())){
                    enviarDeviceEscolhido(device);
                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.msg_erro_bluetooth_encontrado_nao_coincidem), Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvBluetoothPareados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BluetoothDevice device = bluetoothDispositivosPareados.get(i);

                if(bluetoothPareados.get(i).contains(device.getAddress())){
                    enviarDeviceEscolhido(device);
                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.msg_erro_bluetooth_pareado_nao_coincidem), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void enviarDeviceEscolhido(BluetoothDevice device){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("btDevName", device.getName());
        returnIntent.putExtra("btDevAddress", device.getAddress());
        SharedPreferences configDevice = getSharedPreferences(PREFS_NAME_BlUETOOTH, MODE_PRIVATE);
        SharedPreferences.Editor editorDevice = configDevice.edit();
        editorDevice.putString("btDevAddress", device.getAddress());
        editorDevice.commit();


        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        buscarDispositivosPareados();
        ordenarLista(bluetoothPareados, bluetoothDispositivosPareados);
        ligarBluetooth();
    }

    private void ordenarLista(ArrayList<String> bluetoothNames, ArrayList<BluetoothDevice> devices){
        //Comparador usado para ordenar uma lista em ordem alfabetica
        Comparator<String> ALPHABETIC_ORDER_NAMES = new Comparator<String>() {
            @Override
            public int compare(String device1, String device2) {
                return device1.compareTo(device2);
            }
        };
        //Ordena a lista em ordem Alfabetica
        Collections.sort(bluetoothNames, ALPHABETIC_ORDER_NAMES);

        //Comparador usado para ordenar uma lista em ordem alfabetica
        Comparator<BluetoothDevice> ALPHABETIC_ORDER_DEVICES = new Comparator<BluetoothDevice>() {
            @Override
            public int compare(BluetoothDevice device1, BluetoothDevice device2) {
                return device1.getName().compareTo(device2.getName());
            }
        };
        //Ordena a lista em ordem Alfabetica
        Collections.sort(devices, ALPHABETIC_ORDER_DEVICES);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mn_list_bluetooth, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.menu_ligar:
                ligarBluetooth();
                break;

            case R.id.menu_desligar:
                desligarBluetooth();
                break;*/
        }

        return super.onOptionsItemSelected(item);
    }

    private void iniciarBusca() {
        Toast.makeText(getBaseContext(),getResources().getString(R.string.msg_searching_devices),Toast.LENGTH_SHORT).show();
        bluetoothDispositivosEncontrados.clear();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receberInfo, intentFilter);
        bluetoothAdapter.startDiscovery();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void ligarBluetooth() {
        if(!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    private BroadcastReceiver receberInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Message msg = Message.obtain();
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //Log.d("TAG","entrou no action found");
                if(bluetoothDispositivosPareados.size() < 1) {
                    //Log.d("TAG","entrou no if size < 1");
                    bluetoothEncontrados.add(device.getName()+"\n"+device.getAddress());
                    bluetoothDispositivosEncontrados.add(device);
                    adapterEncontrados.notifyDataSetChanged();
                } else {
                    //Log.d("TAG","entrou no else flag");
                    boolean flag = true;
                    for(int i = 0; i< bluetoothDispositivosEncontrados.size(); i++) {
                        if(device.getAddress().equals(bluetoothDispositivosEncontrados.get(i).getAddress())) {
                            flag = false;
                        }
                    }
                    if(flag == true) {
                        //Log.d("TAG","entrou no if flag == true");
                        bluetoothEncontrados.add(device.getName()+"\n"+device.getAddress());
                        bluetoothDispositivosEncontrados.add(device);
                        adapterEncontrados.notifyDataSetChanged();
                    }
                }
                ordenarLista(bluetoothEncontrados,bluetoothDispositivosEncontrados);
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private void buscarDispositivosPareados() {
        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
        if(pairedDevice.size() > 0) {
            for(BluetoothDevice device : pairedDevice) {
                bluetoothPareados.add(device.getName()+"\n"+device.getAddress());
                bluetoothDispositivosPareados.add(device);
            }
        }
        adapterPareados.notifyDataSetChanged();
    }
}
