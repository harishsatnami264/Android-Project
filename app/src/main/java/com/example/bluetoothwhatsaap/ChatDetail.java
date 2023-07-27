package com.example.bluetoothwhatsaap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ChatDetail extends AppCompatActivity {

    TextView nameTv,statusTv,tvsText,tvsTime,dateTv,tvrText,tvrTime;
    EditText inpMsgEt;
    ImageView sendImg,menuImgs;
    RecyclerView chatRecyclerView;



       ConstraintLayout sendLayout;
///    Layout layout;

    RelativeLayout myLay;

    final int senderId = 1;
    int receiverId = 2;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice btDevice;
//    ArrayList<String> al = new ArrayList<String>();
//
//    int countLogic = 1;
//    int countLogic1 = 1;



    int flagg = 0;

    static final int STATE_LISTENING =1;
    static final int STATE_CONNECTING =2;
    static final int STATE_CONNECTED =3;
    static final int STATE_CONNECTION_FAILED =4;
    static final int STATE_MESSAGE_RECEIVED =5;

   SendReceive sendReceive;
   String userName="Fetching Name";


    final ArrayList<MessageModel> messageModels = new ArrayList<>();
    final ChatAdapter chatAdapter = new ChatAdapter(messageModels,this);


//    int REQUEST_ENABLE_BLUETOOTH =1;

    private static final String APP_NAME = "Bluetooth Whatsapp";
    private static final UUID MY_UUID = UUID.fromString("3a523b1a-173f-4f06-b223-fdf72dc4e707");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        getSupportActionBar().hide();



///        recieveLayout = (ConstraintLayout) findViewById(R.id.chat_recieve_layout_id);
///       sendLayout = (ConstraintLayout) findViewById(R.id.chat_send_layout_id);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        statusTv = (TextView) findViewById(R.id.status_tv_chat_id);
        nameTv = (TextView) findViewById(R.id.chat_UserName);
        nameTv.setText(userName);


//

        try{
            myLay = (RelativeLayout) findViewById(R.id.chat_background_id);
            int a=getIntent().getExtras().getInt("wallpaperNumber");
            if(a==1){
//                myLay.setBackground(Drawable.createFromPath("@drawable/wallpaper11"));
                myLay.setBackgroundResource(R.drawable.wallpaper4);
            }
        }catch (Exception e){}






        menuImgs = (ImageView) findViewById(R.id.menuImg);

        inpMsgEt = (EditText) findViewById(R.id.typedMessage);
        sendImg = (ImageView) findViewById(R.id.sendBtn);

        tvsText = (TextView) findViewById(R.id.senderText);
        tvsTime = (TextView) findViewById(R.id.senderTime);



        tvrText = (TextView) findViewById(R.id.receiverText);
        tvrTime = (TextView) findViewById(R.id.receiverTime);

        dateTv = (TextView) findViewById(R.id.date_tv_id);
        String datefordisplay = String.valueOf(DateFormat.getDateTimeInstance().format(new Date()));
//        dateTv.setText(datefordisplay);
        datefordisplay = datefordisplay.replaceAll("-"," ");
        String arrDate[] = datefordisplay.split(" ");
        if(arrDate.length>3) {
            dateTv.setText(arrDate[0] + " " + arrDate[1] + " " + arrDate[2]);
        }else{
            dateTv.setText(String.valueOf(DateFormat.getDateTimeInstance().format(new Date())));
        }



        final ImageView backImgV = (ImageView) findViewById(R.id.back_arrow);
        backImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatDetail.this,MainActivity.class);
                startActivity(i);
            }
        });
        menuImgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(),view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch(menuItem.getItemId()){

                            case R.id.subItem0:
                                myLay.setBackgroundResource(R.drawable.wpbackgroung);
                                return true;
                            case R.id.subItem1:
                                myLay.setBackgroundResource(R.drawable.wallpaper1);
                                return true;
                            case R.id.subItem2:
                                myLay.setBackgroundResource(R.drawable.wallpaper2);
                                return true;
                            case R.id.subItem3:
                                myLay.setBackgroundResource(R.drawable.wallpaper3);
                                return true;
                            case R.id.subItem4:
                                myLay.setBackgroundResource(R.drawable.wallpaper4);
                                return true;
                            case R.id.subItem5:
                                myLay.setBackgroundResource(R.drawable.wallpaper5);
                                return true;
                            case R.id.subItem6:
                                myLay.setBackgroundResource(R.drawable.wallpaper6);
                                return true;
                            case R.id.subItem7:
                                myLay.setBackgroundResource(R.drawable.wallpaper7);
                                return true;
                                case R.id.subItem8:
                            myLay.setBackgroundResource(R.drawable.wallpaper8);
                            return true;
                            case R.id.subItem9:
                                myLay.setBackgroundResource(R.drawable.wallpaper9);
                                return true;
                            case R.id.subItem10:
                                myLay.setBackgroundResource(R.drawable.wallpaper10);
                                return true;
                            case R.id.subItem11:
                                myLay.setBackgroundResource(R.drawable.wallpaper11);
                                return true;
                            case R.id.subItem12:
                                myLay.setBackgroundResource(R.drawable.wallpaper12);
                                return true;

                            case R.id.theme1:
                                try{
                                myLay.setBackgroundResource(R.drawable.wallpaper8);



                                //changeAnimation();

//                                setContentView(R.layout.sample_sender);
//                                sendLayout = (ConstraintLayout) findViewById(R.id.chat_send_layout_id);
//                                sendLayout.setBackgroundResource(R.drawable.outgoing_message);
//                                setContentView(R.layout.activity_chat_detail);

//
//                                Intent intenthgh = new Intent(ChatDetail.this,SampleReciever.class);
//                                startActivity(intenthgh);

//                                sendLayout = ssss.getMyLayout();
//                                sendLayout.setBackgroundResource(R.drawable.outgoing_message);



///                                sendLayout.setBackgroundResource(R.drawable.outgoing_message);

///                                recieveLayout.setBackgroundResource(R.drawable.incoming_message);
                                }catch(Exception e){
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }
//                                tvrText.setTextColor(Color.parseColor("#000000"));
//                                tvrTime.setTextColor(Color.parseColor("#0F0FF0"));
//                                tvsText.setTextColor(Color.parseColor("#000000"));
//                                tvsTime.setTextColor(Color.parseColor("#C407E4"));
                                return true;

                            case R.id.theme2:
                                myLay.setBackgroundResource(R.drawable.wallpaper8);
///                                recieveLayout.setBackgroundResource(R.drawable.incoming_message1);
///                                sendLayout.setBackgroundResource(R.drawable.outgoing_message1);
///                                tvrText.setTextColor(Color.parseColor("#000000"));
///                                tvrTime.setTextColor(Color.parseColor("#C407E4"));
///                                tvsText.setTextColor(Color.parseColor("#FFFFFF"));
///                                tvsTime.setTextColor(Color.parseColor("#FFFF00"));
                                return true;

                            case R.id.theme3:
                                myLay.setBackgroundResource(R.drawable.wallpaper8);
//                                recieveLayout.setBackgroundResource(R.drawable.incoming_message2);
//                                sendLayout.setBackgroundResource(R.drawable.outgoing_message2);
//                                tvrText.setTextColor(Color.parseColor("#FFFFFF"));
//                                tvrTime.setTextColor(Color.parseColor("#FF0000"));
//                                tvsText.setTextColor(Color.parseColor("#FFFFFF"));
//                                tvsTime.setTextColor(Color.parseColor("#FFF000"));
                                return true;

                            case R.id.theme4:
                                myLay.setBackgroundResource(R.drawable.wpbackgroung);
//                                recieveLayout.setBackgroundResource(R.drawable.incoming_message_d);
//                                sendLayout.setBackgroundResource(R.drawable.outgoing_message_d);
//                                tvrText.setTextColor(Color.parseColor("#000000"));
//                                tvrTime.setTextColor(Color.parseColor("#C407E4"));
//                                tvsText.setTextColor(Color.parseColor("#000000"));
//                                tvsTime.setTextColor(Color.parseColor("#C407E4"));

                                return true;

                            case R.id.item4:

                            default:
                                return false;
                        }


                    }
                });

                popup.inflate(R.menu.menu_chat);
                popup.show();
            }
        });


        chatRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setAdapter(chatAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setStackFromEnd(true);

        chatRecyclerView.setLayoutManager(layoutManager);




        sendImg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                try {
                    EditText etMsg = (EditText) findViewById(R.id.typedMessage);
                    String msg = etMsg.getText().toString();
                    MessageModel model = new MessageModel(new Date().getTime(), msg, senderId);

                    model.setTime(DateFormat.getDateTimeInstance().format(new Date()));
                    etMsg.setText("");
                    chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    messageModels.add(model);
//                    LinearLayoutManager lll = LinearLayoutManager.class.cast(chatRecyclerView.getLayoutManager());

                   chatAdapter.notifyDataSetChanged();



//                    changeAnimation(lll.findViewByPosition(chatAdapter.getItemCount()-1));

                    String checking = msg.toUpperCase();
                    String tempMessageToDisplay = "";

                    if (checking.contains("HELLO SAHAD CHANGE BG COLOR TO")) {
                        try {
                            String arr[] = checking.split(" ");
//                            Toast.makeText(getApplicationContext(), "Please write code correctly", Toast.LENGTH_SHORT).show();
//                        2684354556

                            myLay.setBackgroundColor(Color.parseColor("#"+arr[arr.length-1]));

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Please write code correctly", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        switch (checking) {
                            case "HELLO SAHAD":
                                tempMessageToDisplay = "Hello " + bluetoothAdapter.getName().toString() + "\nKey Features:-\nHello sahad change wallpaper1   :- for wallpaper1 ";
                                MessageModel model1 = new MessageModel(new Date().getTime(), tempMessageToDisplay, senderId);
                                model1.setTime(DateFormat.getDateTimeInstance().format(new Date()));
                                chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                                messageModels.add(model1);
                                chatAdapter.notifyDataSetChanged();
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER1":
                                myLay.setBackgroundResource(R.drawable.wallpaper1);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER2":
                                myLay.setBackgroundResource(R.drawable.wallpaper2);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER3":
                                myLay.setBackgroundResource(R.drawable.wallpaper3);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER4":
                                myLay.setBackgroundResource(R.drawable.wallpaper4);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER5":
                                myLay.setBackgroundResource(R.drawable.wallpaper5);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER6":
                                myLay.setBackgroundResource(R.drawable.wallpaper6);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER7":
                                myLay.setBackgroundResource(R.drawable.wallpaper7);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER8":
                                myLay.setBackgroundResource(R.drawable.wallpaper8);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER9":
                                myLay.setBackgroundResource(R.drawable.wallpaper9);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER10":
                                myLay.setBackgroundResource(R.drawable.wallpaper10);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER11":
                                myLay.setBackgroundResource(R.drawable.wallpaper11);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER12":
                                myLay.setBackgroundResource(R.drawable.wallpaper12);
                                break;
                            case "HELLO SAHAD CHANGE WALLPAPER0":
                                myLay.setBackgroundResource(R.drawable.wpbackgroung);
                                break;
                            case "HELLO SAHAD CHANGE BG W COLOR":
//                        myLay.setBackgroundResource(R.color);
                                myLay.setBackgroundColor(0xFFFFFFFF);
                                break;
                            default:
                                sendReceive.write(msg.getBytes());
                        }


                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"The Other User is Not Online",Toast.LENGTH_LONG).show();
                }


                //changeAnimation();


            }
        });



        try {
            flagg = getIntent().getExtras().getInt("flag");

        }catch (Exception e){}


        if(flagg==1){
            ServerClass serverClass = new ServerClass();
            flagg=3;
            serverClass.start();
        }
        if(flagg==2){
            flagg=4;
            try {
                btDevice = getIntent().getExtras().getParcelable("btdevice");
                userName = btDevice.getName();
                nameTv.setText(userName);
                ClientClass clientClass = new ClientClass(btDevice);
                clientClass.start();
            }catch (Exception e){}

        }
    }


//    public void changeAnimation() {
//        LinearLayoutManager lll = LinearLayoutManager.class.cast(chatRecyclerView.getLayoutManager());
//        View view = lll.findViewByPosition(chatAdapter.getItemCount()-1);
//        Animation animation = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
//        if(view!=null) {
//            view.startAnimation(animation);
//        }
//    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch(msg.what){
                case STATE_LISTENING:
                    statusTv.setText("LISTENING..");
                    break;
                case STATE_CONNECTING:
                    //nameTv.setText(btDevice.getName());
                    statusTv.setText("CONNECTING...");
                    break;
                case STATE_CONNECTED:
//                    nameTv.setText("");
                    statusTv.setText("Online");

                    if(flagg==4) {
                        flagg=0;


                        try {


                            //Toast.makeText(getApplicationContext(),"Connected1",Toast.LENGTH_LONG).show();

                        sendReceive.write(("SAHAD MY DEVICE NAME IS AS "+ bluetoothAdapter.getName()+"").getBytes());

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Exception",Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case STATE_CONNECTION_FAILED:
                    statusTv.setText("Offline");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readbuff = (byte[]) msg.obj;



                    String tempMsg = new String(readbuff,0,msg.arg1);
                    if(tempMsg.toUpperCase().contains("SAHAD MY DEVICE NAME IS AS ")){
                        String recDevName = tempMsg.substring(27,tempMsg.length());
                        nameTv.setText(recDevName);
                        break;
                    }

                    MessageModel model = new MessageModel(new Date().getTime(),tempMsg, receiverId);
                    model.setTime(DateFormat.getDateTimeInstance().format(new Date()));
                    messageModels.add(model);

                    chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount());



                    chatAdapter.notifyDataSetChanged();

                    //changeAnimation();

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

//                    statusTv.setText("MSG Received");
                    break;

            }
            return true;
        }
    });


    private class ClientClass extends Thread{
        private BluetoothSocket socket;
        private BluetoothDevice device;

        public ClientClass(BluetoothDevice device1){
            device = device1;
            try {

                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                nameTv.setText(device.getName().toString());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void run(){

            try{
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                BluetoothDevice nDevice = getIntent().getExtras().getParcelable("btdevice");
               // message.obj = nDevice.getName().toString();
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

//            if(flagg==4){
//                flagg=0;
//                try {
//                    outputStream.write(bluetoothAdapter.getName().getBytes());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
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
//                    userName = socket.getRemoteDevice().getName().toString();
//                    Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_LONG).show();
//                    BluetoothDevice dev11 = socket.getRemoteDevice();
//                    userName = dev11.getName();
//                    nameTv = (TextView) findViewById(R.id.chat_UserName);
//                    nameTv.setText(userName);
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


}