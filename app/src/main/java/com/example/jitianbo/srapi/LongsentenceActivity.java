package com.example.jitianbo.srapi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import speechrecoginition.api.tianboandzifan.LongSentenceService;

public class LongsentenceActivity extends AppCompatActivity implements View.OnClickListener {
    Button speakBtn=null;
    TextView largeText,mText=null;
    private Handler handler=null;
    private Runnable runnable=null;
    int i,minTime = 0;
    private ProgressBar progressBar;
    EditText editText=null;
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
        mText.setText("");
        largeText.setText("Welcome!");
        editText=(EditText)findViewById(R.id.editText);
        intent = new Intent(LongsentenceActivity.this, LongSentenceService.class);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==speakBtn.getId()&&!editText.getText().toString().equals("")){
            minTime=Integer.valueOf(editText.getText().toString());
            if(minTime>=20&&minTime<=60) {
                //start service and init progressBar
                progressBar.setMax(minTime);
                mText.setText("Time Left: " + minTime + "s");
                intent.putExtra("minTime", minTime);
                startService(intent);
                progressBar.setVisibility(View.VISIBLE);

                handler.postDelayed(runnable, 1000);
                //button cannot be clicked until timer end
                speakBtn.setOnClickListener(null);
            }
            else{
                Toast.makeText(LongsentenceActivity.this,"retry",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(LongsentenceActivity.this,"retry",Toast.LENGTH_SHORT).show();
        }
    }
    private class Runnable1 implements Runnable{

        @Override
        public void run() {
            if(i<minTime) {
                i++;
//                Log.v("myActivity", "" + i);
                progressBar.incrementProgressBy(1);
                mText.setText("Time Left: " + (minTime - i)+"s");
                handler.postDelayed(this, 1000);
            }
            else{
                handler.removeCallbacks(runnable);
                Log.v("myActivity", "cancel");
                i=0;
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.incrementProgressBy(-(progressBar.getMax()));
                mText.setText("");
                s="";
                speakBtn.setOnClickListener(LongsentenceActivity.this);
                stopService(intent);
            }
        }
    }
}
