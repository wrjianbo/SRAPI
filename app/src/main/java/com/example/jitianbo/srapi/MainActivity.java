package com.example.jitianbo.srapi;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import speechrecoginition.api.tianboandzifan.APIClass;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener,View.OnLongClickListener {
    ImageButton pressToTalkBtn=null;
    TextView tv,tv2=null;
    APIClass api=null;
    Button lsBtn = null;
    private ImageView imageView=null;
    private AnimationDrawable animationDrawable=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pressToTalkBtn=(ImageButton)findViewById(R.id.imageButton);
        tv=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        imageView=(ImageView)findViewById(R.id.animationIV);
        imageView.setImageResource(R.drawable.animation);
        imageView.setVisibility(View.INVISIBLE);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        pressToTalkBtn.setOnTouchListener(this);
        pressToTalkBtn.setOnClickListener(this);
        pressToTalkBtn.setOnLongClickListener(this);
        lsBtn=(Button)findViewById(R.id.lsBtn);
        lsBtn.setOnClickListener(this);
        //invoke API
        api=new APIClass(this,tv2,animationDrawable,imageView);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==pressToTalkBtn.getId()) {
            tv.setText("Too fast, please retry");
        }
        else if(v.getId()==lsBtn.getId()){
            Intent i = new Intent(this,LongsentenceActivity.class);
            startActivity(i);

        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId()==pressToTalkBtn.getId()){
//            pressToTalkBtn.setText("Ready to talk");

//            bindService(servI, connection, BIND_AUTO_CREATE);
//            api.StopAPI();
            api.InitAPI();
            api.StartAPI();
            imageView.setVisibility(View.VISIBLE);



        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            tv.setText("Button Pressed");
        }
        else if (event.getAction() == KeyEvent.ACTION_UP) {
//            api.StopAPI();
//            unbindService(connection);
//            tv.setText("Button Unpressed");

        }
        return false;
    }
}
