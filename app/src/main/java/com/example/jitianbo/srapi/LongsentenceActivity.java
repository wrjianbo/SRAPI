package com.example.jitianbo.srapi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import speechrecoginition.api.tianboandzifan.LongSentenceService;

public class LongsentenceActivity extends AppCompatActivity implements View.OnClickListener {
    Button speakBtn=null;
    TextView largeText,mText=null;
    private Handler handler=null;
    private Runnable runnable=null;
    int i = 0;
    private ProgressBar progressBar;
    String s="";
    Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longsentence);
        progressBar=(ProgressBar)findViewById(R.id.progressbar1);
        speakBtn=(Button)findViewById(R.id.speakBtn);
        largeText=(TextView)findViewById(R.id.largeText1);
        mText=(TextView)findViewById(R.id.mediumText1);
        speakBtn.setOnClickListener(this);
        handler = new Handler();
        runnable = new Runnable1();
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setMax(30);
        mText.setText("" + 30);
        intent = new Intent(LongsentenceActivity.this, LongSentenceService.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==speakBtn.getId()){

            startService(intent);

            progressBar.setVisibility(View.VISIBLE);
            mText.setText("" + 30);
            handler.postDelayed(runnable, 1000);
            speakBtn.setOnClickListener(null);
        }
    }
    private class Runnable1 implements Runnable{

        @Override
        public void run() {
            if(i<30) {
                i++;
//                Log.v("myActivity", "" + i);
                progressBar.incrementProgressBy(1);
                mText.setText("" + (30 - i));
                handler.postDelayed(this, 1000);
                s = s + "this is a test"+i+"\n";
                largeText.setText(s);
            }
            else{
                handler.removeCallbacks(runnable);
                Log.v("myActivity", "cancel");
                i=0;
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.incrementProgressBy(-(progressBar.getMax()));
                mText.setText("10");
                largeText.setText("");
                s="";
                speakBtn.setOnClickListener(LongsentenceActivity.this);
//                Intent i = new Intent(LongsentenceActivity.this, LongSentenceService.class);
                stopService(intent);
            }
        }
    }
}
