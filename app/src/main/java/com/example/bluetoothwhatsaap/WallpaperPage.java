package com.example.bluetoothwhatsaap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class WallpaperPage extends AppCompatActivity {
ImageView w0,w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12;
RelativeLayout myLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_page);
        w0 = (ImageView) findViewById(R.id.imageView0);
        w1 = (ImageView) findViewById(R.id.imageView1);
        w2 = (ImageView) findViewById(R.id.imageView2);
        w3 = (ImageView) findViewById(R.id.imageView3);
        w4 = (ImageView) findViewById(R.id.imageView4);
        w5 = (ImageView) findViewById(R.id.imageView5);
        w6 = (ImageView) findViewById(R.id.imageView6);
        w7 = (ImageView) findViewById(R.id.imageView7);
        w8 = (ImageView) findViewById(R.id.imageView8);
        w9 = (ImageView) findViewById(R.id.imageView9);
        w10 = (ImageView) findViewById(R.id.imageView10);
        w11 = (ImageView) findViewById(R.id.imageView11);
        w12 = (ImageView) findViewById(R.id.imageView12);


        w0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(WallpaperPage.this,ChatDetail.class);
//                i.putExtra("wallpaperNumber",1);
//
//                startActivity(i);
                myLay = (RelativeLayout) findViewById(R.id.chat_background_id);
                myLay.setBackgroundResource(R.drawable.wallpaper4);
                finish();
            }
        });


    }
}