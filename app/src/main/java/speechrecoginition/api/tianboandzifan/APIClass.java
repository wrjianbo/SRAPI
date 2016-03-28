package speechrecoginition.api.tianboandzifan;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import speechrecoginition.api.tianboandzifan.MyReceiver;
import speechrecoginition.api.tianboandzifan.VoiceService;

/**
 * Created by jitianbo on 16/3/8.
 */
public class APIClass {
    private VoiceService.MyBinder mBinder;
    private Intent servI=null;
    private MyReceiver mc=null;
    private Context context;
    private TextView txtV;
    private ImageView imageView;
    private Boolean isBound=false;

    private ServiceConnection connection =null;
    private ArrayList<String> results=null;
    private float [] confidence=null;
    private AnimationDrawable animationDrawable=null;
    public APIClass(Context c,TextView t,AnimationDrawable ad,ImageView imageView){
        this.context=c;
        this.txtV=t;
        this.animationDrawable=ad;
        this.imageView = imageView;
    }
    public void InitAPI(){

            connection = new ServiceConnection() {
                @Override
                public void onServiceDisconnected(ComponentName name) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    // TODO Auto-generated method stub
                    mBinder = (VoiceService.MyBinder) service;
                    mBinder.startService();
                }
            };


//            Toast.makeText(context,"init",Toast.LENGTH_SHORT).show();
            servI = new Intent(context, VoiceService.class);
            mc = new MyReceiver(context, txtV, this,animationDrawable,this.imageView);
            if(animationDrawable!=null)
                animationDrawable.start();
            IntentFilter fl = new IntentFilter();
            fl.addAction(VoiceService.MyAct);
            context.getApplicationContext().registerReceiver(mc, fl);


//        private void xSSSSS(){
//            StartAPI();
//        }
    }
    public void StartAPI(){
        if(connection!=null) {
            isBound = context.bindService(servI, connection, context.BIND_AUTO_CREATE);
        }
    }
    public void StopAPI(){
        if(isBound) {
            context.unbindService(connection);
            Toast.makeText(context,"unbind",Toast.LENGTH_SHORT).show();
        }
        isBound=false;
        context.getApplicationContext().unregisterReceiver(mc);

    }
    class ClassTest1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public void PutResultsAndConfidence(ArrayList<String> r,float[] c){
        this.results=r;
        this.confidence=c;
    }
    public ArrayList<String> GetResults(){
        return results;
    }
    public float[] GetConfidence(){
        return confidence;
    }
}
