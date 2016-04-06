package speechrecoginition.api.tianboandzifan;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class LongSentenceService extends Service {
    private Handler handler=null;
    private Runnable runnable=null;
    int i,minTime = 0;
    SpeechRecognizer recognizer=null;
    Intent intent1=null;
    String str,flag = "";
    listener listen=null;
    public LongSentenceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {

        //init timer
        handler = new Handler();
        runnable = new Runnable1();

        super.onCreate();
    }
    private void init(){
        //init listener and recognizer
        listen=new listener();
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        intent1= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent1.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 6);
        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        intent1.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);
       /* EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS and
          EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS and
          EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS didn't work
          Still not fix
      */
        intent1.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(8000));
        intent1.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, Long.valueOf(8000));
        intent1.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(8000));

        recognizer.setRecognitionListener(listen);
    }

    private class Runnable1 implements Runnable{

        @Override
        public void run() {
            if(i<minTime) {
                i++;
                Log.v("myService", "" + i);
                handler.postDelayed(this, 1000);
            }
            else{
                handler.removeCallbacks(runnable);
                Log.v("myService", "cancel");
                recognizer.stopListening();
                Intent intentI=new Intent();
                intentI.setAction(flag);
                intentI.putExtra("sentence", str);
                sendBroadcast(intentI);
                Toast.makeText(LongSentenceService.this, "send", Toast.LENGTH_SHORT).show();
                //send broadcast
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //init i ,str
        i=0;
        str="";
        flag=intent.getStringExtra("flag");

        minTime=intent.getIntExtra("minTime",30);
        Log.v("onBind", "" + minTime+flag);
        this.init();
        //start listening user
        recognizer.startListening(intent1);
        //start timer
        handler.postDelayed(runnable, 1000);
        return super.onStartCommand(intent, flags, startId);

    }

    private class listener implements RecognitionListener{

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            String s="";
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    s = "AUDIO ERROR";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    s = "CLIENT ERROR";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    s = "INSUFFICIENT PERMISSIONS ERROR";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    s = "NETWORK ERROR";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    s = "NETWORK TIMEOUT";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    s = "NO MATCH";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    s = "RECOGNIZER BUSY";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    s = "SERVER ERROR";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    s = "";
                    if(i<minTime) {
                        //restart if timeout
                        Log.v("SPEECH TIMEOUT", "restart listening");
                        recognizer.destroy();
                        init();
                        recognizer.startListening(intent1);
                    }
                    break;
            }
            if(!s.equals(""))
                Toast.makeText(LongSentenceService.this,s,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> recognizer_result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            str = str + recognizer_result.get(0).trim() + " ";
            Log.v("myService", str);
            if(i<minTime) {
                //restart listening
                recognizer.startListening(intent1);
                Log.v("onResults", "restart listening");
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

    @Override
    public void onDestroy(){
        recognizer.destroy();
        super.onDestroy();
    }
}
