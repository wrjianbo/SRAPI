package speechrecoginition.api.tianboandzifan;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jitianbo on 16/3/8.
 */
public class VoiceService extends Service {

    //	private Boolean intentFromClock=false;
    public static final String MyAct = "MyAct";
//    public static final String actForClock = "actForClock";
    private MyBinder mBinder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
//		Toast.makeText(getApplicationContext(), "onBind", Toast.LENGTH_LONG).show();
        return mBinder;
    }
//    public void onRebind(Intent intent) {
//        // TODO Auto-generated method stub
////		Toast.makeText(getApplicationContext(), "onBind", Toast.LENGTH_LONG).show();
//        super.onRebind(intent);
//    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public boolean onUnbind(final Intent intent) {
//        Log.d("TestService","unbind");
        return true;
    }

    @Override
    public void onDestroy() {
        Log.d("TestService", "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//		intentFromClock=intent.getBooleanExtra("intentFromClock", false);
        return super.onStartCommand(intent, flags, startId);
    }


    class MyBinder extends Binder {
        public void startService() {
            SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(VoiceService.this);
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            //       intent.putExtra("calling_package", "VoiceIME");
            //       intent.putExtra(name, value);
            recognizer.setRecognitionListener(new listener());
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 6);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
            intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES,true);
//            intent.putExtra(RecognizerIntent.EXTRA_RESULTS,true);
            recognizer.startListening(intent);
        }
    }


    class listener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
            // TODO Auto-generated method stub



        }

        @Override
        public void onBeginningOfSpeech() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onEndOfSpeech() {
            // TODO Auto-generated method stub

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
                    s = "SPEECH TIMEOUT";
                    break;
            }
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();

        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> recognizer_result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float [] confidence = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
            if(confidence[0] >= 0.85){
                Toast.makeText(getApplicationContext(), "Very clear", Toast.LENGTH_SHORT).show();
            }
            else if(confidence[0] >=0.5){
                Toast.makeText(getApplicationContext(), "Not that clear", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Fuzzy!", Toast.LENGTH_SHORT).show();
            }
            defaultFunction(recognizer_result,confidence);

        }

//		public void compareVoice(String s) {
////			if(s.contains("clock")){
////				invokeClock();
////			}
////			else if(s.contains("sunshine")||s.contains("snow")||s.contains("rain")){
////				functionForClock(s);
////			}
////			else{
//				defaultFunction(s);
////			}
//		}

        public void defaultFunction(ArrayList<String> s,float [] c) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("results", s);
            intent.putExtra("confidence", c);
            intent.setAction(MyAct);
            sendBroadcast(intent);
        }

//		public void invokeClock() {
//			Intent intent = new Intent(getApplicationContext(),ClockActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			getApplication().startActivity(intent);
//		}
//		public void functionForClock(String s) {
//			Intent i = new Intent();
//			i.putExtra("flag_for_clock", true);
//			i.setAction(MyAct);
//			if(s.contains("sunshine")){
//				i.putExtra("voice", "Sunshine");
//			}
//			else if(s.contains("snow")){
//				i.putExtra("voice", "Snow");
//			}
//			else{
//				i.putExtra("voice", "Rain");
//			}
//			sendBroadcast(i);
//		}

        @Override
        public void onPartialResults(Bundle partialResults) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub

        }

    }

}

