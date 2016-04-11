package speechrecoginition.api.tianboandzifan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by jitianbo on 16/3/15.
 */
public class SpeechForDialog {
    TextToSpeech tts;
    protected Context context;
    protected String words="";
    protected AlertDialog ad=null;
    int targetI=0;
    TextView tv=null;
    DialogClass dc;
    public SpeechForDialog(Context c,String s,AlertDialog d,int i,TextView t,DialogClass dcccc){
        this.context=c;
        this.words=s;
        this.ad=d;
        this.targetI=i;
        this.dc=dcccc;
        this.tv=t;

        initSpeech();
    }

    public void initSpeech(){
        tts=new TextToSpeech(context.getApplicationContext(), new ttsInitListener());
    }

    private class ttsInitListener implements TextToSpeech.OnInitListener {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                if (tts!=null&&!tts.isSpeaking()) {
                    CharSequence cs="Do you mean "+words;
                    tts.setLanguage(Locale.US);
                    tts.setOnUtteranceProgressListener(new ttsUtteranceListener());
                    tts.speak(cs, TextToSpeech.QUEUE_FLUSH, null, "thisID");
                }
            }
            else if(status == TextToSpeech.ERROR){
                tts = null;
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ttsUtteranceListener extends UtteranceProgressListener{
        @Override
        public void onStart(String utteranceId) {

        }
        @Override
        public void onDone(String utteranceId) {
            final Activity a=(Activity)context;
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(a, "kkkk", Toast.LENGTH_SHORT).show();
                        YesOrNoCommand yon = new YesOrNoCommand(context,ad,targetI,words,tv,dc);
                        yon.checkYesOrNo();
                    }
                });
        }
        @Override
        public void onError(String utteranceId) {

        }
    }


}




