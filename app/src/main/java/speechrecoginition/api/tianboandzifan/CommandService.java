package speechrecoginition.api.tianboandzifan;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.jitianbo.srapi.AlarmActivity;

import java.util.ArrayList;

/**
 * Created by jitianbo on 16/3/14.
 */
public class CommandService{
    Context context=null;
    String string=null;
    private AudioManager audio=null;



    public CommandService(Context c, String s){
        this.context=c;
        this.string=s;
    }
    public void checkCommand(){
        if(string.contains("set")&&string.contains("alarm")){
            Intent i=new Intent(context, AlarmActivity.class);
            context.startActivity(i);
        }
        else if(string.contains("silent")&&context!=null){
            audio = (AudioManager)context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            Toast.makeText(context,"slient mode",Toast.LENGTH_SHORT).show();
        }
        else if(string.contains("send")&&string.contains("message")){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("vnd.android-dir/mms-sms");
//              intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码
            context.startActivity(intent);
        }
        else if(string.contains("call")){
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_CALL_BUTTON);
            context.startActivity(intent);
        }

    }
    public boolean checkIfItIsACommand(){
        if(string.contains("set")&&string.contains("alarm")){
            return true;
        }
        else if(string.contains("silent")&&context!=null){
            return true;
        }
        else if(string.contains("send")&&string.contains("message")&&context!=null){
            return true;
        }
        else if(string.contains("call")){
            return true;
        }
        else {
            return false;
        }
    }
}
