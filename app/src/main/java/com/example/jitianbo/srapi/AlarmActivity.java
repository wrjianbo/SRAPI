package com.example.jitianbo.srapi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import speechrecoginition.api.tianboandzifan.AlarmReceiver;
import speechrecoginition.api.tianboandzifan.SpeechForDialog;

public class AlarmActivity extends AppCompatActivity implements OnClickListener{
    Button yesBT,noBt;
    TimePicker tp=null;
    SpeechForDialog sfd=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        yesBT=(Button)findViewById(R.id.yes_button);
        noBt=(Button)findViewById(R.id.no_button);
        tp=(TimePicker)findViewById(R.id.timePicker);
        Calendar cal=Calendar.getInstance();
        tp.setCurrentHour(cal.get(Calendar.HOUR));
        tp.setCurrentMinute(cal.get(Calendar.MINUTE));
        tp.setIs24HourView(true);
        yesBT.setOnClickListener(this);
        noBt.setOnClickListener(this);

    }








    @Override
    public void onClick(View v) {
        if (v.getId() == yesBT.getId()) {
            int hour = tp.getCurrentHour();
            int min = tp.getCurrentMinute();
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            calSet.set(Calendar.HOUR_OF_DAY, hour);
            calSet.set(Calendar.MINUTE, min);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);

            Toast.makeText(this,"Set alarm",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        if(v.getId() == noBt.getId()){
//            Intent i = new Intent(this,MainActivity.class);
//            startActivity(i);
//            sfd=new SpeechForDialog(this,"THis is the test");


        }
    }
        private void setAlarm(Calendar targetCal){
            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        }

}
