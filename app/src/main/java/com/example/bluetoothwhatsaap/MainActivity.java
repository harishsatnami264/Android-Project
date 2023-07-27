package com.example.bluetoothwhatsaap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {

    Button listen_btn,list_contact_btn,discModeBtn,addContactBtn;
//    TextView status_tv;
    ListView contact_list_lv,add_contact_list;
    ImageView onOffImg;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btarray;

    BluetoothDevice bdDeviceToPair;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<BluetoothDevice> newBluetoothDeviceSet = new ArrayList<BluetoothDevice>();
    ArrayList<String> stringArrayList = new ArrayList<String>();
//    ArrayList<String> stringArrayList1111 = new ArrayList<String>();
//    ArrayList<String> al = new ArrayList<String>();

//    SendReceive sendReceive;

//    static final int STATE_LISTENING =1;
//    static final int STATE_CONNECTING =2;
//    static final int STATE_CONNECTED =3;
//    static final int STATE_CONNECTION_FAILED =4;
//    static final int STATE_MESSAGE_RECEIVED =5;


    int REQUEST_ENABLE_BLUETOOTH =1;

//    private static final String APP_NAME = "Bluetooth Whatsapp";
//    private static final UUID MY_UUID = UUID.fromString("3a523b1a-173f-4f06-b223-fdf72dc4e707");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_contact_btn = (Button) findViewById(R.id.list_contact_btn_id);
        listen_btn = (Button) findViewById(R.id.listen_btn_id);
        contact_list_lv = (ListView) findViewById(R.id.list_contact_lv_id);
        add_contact_list = (ListView) findViewById(R.id.add_new_contact_lv_id);
//        status_tv = (TextView) findViewById(R.id.status_tv_id);
        onOffImg = (ImageView) findViewById(R.id.on_off_img_id);
        discModeBtn = (Button) findViewById(R.id.discovarable_btn_id);
        addContactBtn = (Button) findViewById(R.id.add_contact_btn_id);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        discModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                onOffImg.setImageResource(R.drawable.ic_bluetooth_searching_24);
                onOffImg.setBackgroundResource(R.drawable.button_design4);
                startActivity(discoverableIntent);
            }
        });



        BroadcastReceiver myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    stringArrayList.add(device.getName());
                    newBluetoothDeviceSet.add(device);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myReceiver, intentFilter);


        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                stringArrayList.clear();
                newBluetoothDeviceSet.clear();
                bluetoothAdapter.cancelDiscovery();
                arrayAdapter.notifyDataSetChanged();
                if(bluetoothAdapter.startDiscovery()){
                    Toast.makeText(getApplicationContext(),"Devices Discovered",Toast.LENGTH_SHORT).show();
                }




            }
        });

        add_contact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                bdDeviceToPair = newBluetoothDeviceSet.get(i);
                Boolean isBonded = false;
                try {
                    isBonded = createBond(bdDeviceToPair);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList);
        add_contact_list.setAdapter(arrayAdapter);

        onOffImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                    onOffImg.setImageResource(R.drawable.ic_bluetooth_disabled_24);

                    onOffImg.setBackgroundResource(R.drawable.button_design2);

                }else{
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent,REQUEST_ENABLE_BLUETOOTH);
                    onOffImg.setImageResource(R.drawable.ic_bluetooth_24);

                    onOffImg.setBackgroundResource(R.drawable.button_design3);
                }
            }
        });

        if(!bluetoothAdapter.isEnabled()){
            onOffImg.setImageResource(R.drawable.ic_bluetooth_24);
            onOffImg.setBackgroundResource(R.drawable.button_design3);
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BLUETOOTH);
        }




        listen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ServerClass serverClass = new ServerClass();
//                serverClass.start();
                try{
                Intent intent = new Intent(MainActivity.this,ChatDetail.class);
                intent.putExtra("flag",1);
                intent.putExtra("indexComeFrom",2);
                intent.putExtra("myDevName",bluetoothAdapter.getName());
                startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please Turn On your Bluetooth",Toast.LENGTH_LONG).show();
                }
            }
        });

        list_contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
                String []strings = new String[bt.size()];
                btarray = new BluetoothDevice[bt.size()];
                int index = 0;
                if(bt.size()>0){
                    for(BluetoothDevice device:bt){
                        btarray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,strings);
                    contact_list_lv.setAdapter(arrayAdapter);
                }
            }
        });

        contact_list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ClientClass clientClass = new ClientClass(btarray[i]);
//                clientClass.start();
                Intent intent = new Intent(MainActivity.this,ChatDetail.class);
                intent.putExtra("btdevice",btarray[i]);
                intent.putExtra("flag",2);
                intent.putExtra("myDevName",bluetoothAdapter.getName());
                intent.putExtra("indexComeFrom",1);


//                status_tv.setText("Connecting..");
                startActivity(intent);
            }
        });




//        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList);
//        add_contact_list.setAdapter(arrayAdapter);


    }



//    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if(BluetoothDevice.ACTION_FOUND.equals(action)){
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                stringArrayList.add(device.getName());
//                arrayAdapter.notifyDataSetChanged();
//            }
//        }
//    };







/*
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch(msg.what){
                case STATE_LISTENING:
                    status_tv.setText("LISTENING..");
                    break;
                case STATE_CONNECTING:
                    status_tv.setText("CONNECTING...");
                    break;
                case STATE_CONNECTED:
                    status_tv.setText("CONNECTED");
                    break;
                case STATE_CONNECTION_FAILED:
                    status_tv.setText("CONN. FAILED.");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readbuff = (byte[]) msg.obj;
                    String tempMsg = new String(readbuff,0,msg.arg1);

//                    TextView tv = new TextView(getApplicationContext());
//
//                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    tv.setText(tempMsg);
//                    tv.setBackgroundColor(0xff66ff66);
//                    tv.setPadding(10,10,10,10);
//                    msg_box_ll.addView(tv);
//
//



//                    al.add("HE::>>>"+tempMsg);
//                    ArrayAdapter<String> atmsg = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,al);
//
//                    atmsg.notifyDataSetChanged();
//                    msg_box.setAdapter(atmsg);

                    status_tv.setText("MSG Received");
                    break;

            }
            return true;
        }
    });
*/






/*
    private class ClientClass extends Thread{
        private BluetoothSocket socket;
        private BluetoothDevice device;

        public ClientClass(BluetoothDevice device1){
            device = device1;
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void run(){

            try{
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);


                sendReceive = new SendReceive(socket);
                sendReceive.start();
            }catch (IOException e){
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }



    }

    private class SendReceive extends Thread{
        private final BluetoothSocket bluetoothSocket;


        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket){
            bluetoothSocket = socket;
            InputStream tempInp = null;
            OutputStream tempOut  =null;

            try {
                tempInp = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream =tempInp;
            outputStream =tempOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];
            int bytes;
            while(true){
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes){
            try{
                outputStream.write(bytes);
            }catch(IOException e){
                e.printStackTrace();
            }
        }


    }

    private class ServerClass extends Thread{
        private BluetoothServerSocket serverSocket;
        public ServerClass(){
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            BluetoothSocket socket = null;
            while(socket==null){
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);

                    socket = serverSocket.accept();
                }catch (IOException e){

                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }

                if(socket!=null){
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;

                    handler.sendMessage(message);

                    //Write some code for send receive
                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                    break;
                }
            }
        }
    }

*/public boolean createBond(BluetoothDevice btDevice)
        throws Exception
{
    Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
    Method createBondMethod = class1.getMethod("createBond");
    Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
    return returnValue.booleanValue();
}

}