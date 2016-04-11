package speechrecoginition.api.tianboandzifan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jitianbo on 16/3/15.
 */
public class YesOrNoCommand {
    private Context context;
    private AlertDialog targetDialog=null;
    private int targetI=0;
    private int maxRetryTime=0;
    private String commandString;
    CommandService comserv=null;
    DialogClass dc =null;
    TextView txtV=null;

    public YesOrNoCommand(Context c,AlertDialog d,int i,String s,TextView tv, DialogClass dccc){
        this.context=c;
        this.targetDialog=d;
        this.targetI=i;
        this.commandString=s;
        this.txtV=tv;
        this.dc = dccc;

    }

    public void checkYesOrNo(){
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(context.getApplicationContext());
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizer.setRecognitionListener(new listener());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 6);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES,true);
//            intent.putExtra(RecognizerIntent.EXTRA_RESULTS,true);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(context,"please start to talk",Toast.LENGTH_SHORT).show();
        recognizer.startListening(intent);

    }
    public int returnI(){
        return targetI+1;
    }

    private class listener implements RecognitionListener {

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
//            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();

        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> recognizer_result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if(recognizer_result.get(0).trim().equals("yes")){
                targetDialog.dismiss();
                comserv=new CommandService(context,commandString);
                if(comserv.checkIfItIsACommand()){
                    dialogForMakeSure();

                }
                else{
                    if(txtV!=null)
                        txtV.setText(commandString);
                }

                Toast.makeText(context,"yesDismiss",Toast.LENGTH_SHORT).show();
            }
            else if(recognizer_result.get(0).trim().equals("no")){
                targetDialog.dismiss();
                if(targetI<3) {
                    System.out.print(targetI);
                    dc.dialog1();
                }
                else
                    dc.dialog2();
            }
            else if(maxRetryTime<3){
                maxRetryTime++;
//                Toast.makeText(context,"Pleas say yes or no",Toast.LENGTH_SHORT).show();
                checkYesOrNo();

            }
            else {
                targetDialog.dismiss();
            }
        }
        private void dialogForMakeSure(){

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("This speech contains " + commandString.trim() + " command");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    comserv.checkCommand();

                }
            });
            builder.setMessage("Do you want to start it?");
            AlertDialog thedialog = builder.create();
            thedialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            thedialog.setCanceledOnTouchOutside(false);
            thedialog.show();
//            checkYesOrNo();

        }
        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

    }

}