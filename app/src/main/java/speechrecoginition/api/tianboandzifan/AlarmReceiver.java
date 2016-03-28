package speechrecoginition.api.tianboandzifan;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.jitianbo.srapi.MainActivity;
import com.example.jitianbo.srapi.R;

/**
 * Created by Zifan on 16/3/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private int i = 0;
    private NotificationCompat.Builder nb;
    private NotificationManagerCompat nm;
    private int id = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Log.i("1", "1");
        final MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
        mp.start();
        try {
            Thread.sleep(2000);
            mp.stop();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Intent in = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, in, PendingIntent.FLAG_CANCEL_CURRENT);
        nb = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon1)
                .setContentTitle("This is a notification")
                .setContentText("GO, Go, GO")
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent);
        nm = NotificationManagerCompat.from(context);
        nm.notify(id, nb.build());


//        notification_manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        final Notification notify = new Notification(R.drawable.icon1,"Notification Title",System.currentTimeMillis());
//        notify.defaults |= Notification.DEFAULT_SOUND;
//        notify.defaults |= Notification.DEFAULT_VIBRATE;
//        notify.defaults |= Notification.DEFAULT_LIGHTS;
////			notify.defaults |= Notification.COLOR_DEFAULT;
//        notify.flags |= Notification.FLAG_INSISTENT;
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//        Intent open_activity_intent = new Intent(context,MainActivity.class);
//        String str  = "This is body text";
//        open_activity_intent.putExtra("itemid", str);
//        PendingIntent pending_intent = PendingIntent.getBroadcast(context, 0, open_activity_intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        notify.setLatestEventInfo(context, "Notification subject", str, pending_intent);
//        notification_manager.notify(0,notify);
    }
}