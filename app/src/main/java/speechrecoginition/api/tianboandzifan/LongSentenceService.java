package speechrecoginition.api.tianboandzifan;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LongSentenceService extends Service {
    private Handler handler=null;
    private Runnable runnable=null;
    int i = 0;
    public LongSentenceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        handler = new Handler();
        runnable = new Runnable1();
        super.onCreate();
    }


    private class Runnable1 implements Runnable{

        @Override
        public void run() {
            if(i<10) {
                i++;
                Log.v("myService", "" + i);
                handler.postDelayed(this, 1000);
            }
            else{
                handler.removeCallbacks(runnable);
                Log.v("myService", "cancel");

            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this,"aaa",Toast.LENGTH_SHORT).show();
//        Log.v("myService", "onStart");
        i=0;
        handler.postDelayed(runnable, 1000);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
