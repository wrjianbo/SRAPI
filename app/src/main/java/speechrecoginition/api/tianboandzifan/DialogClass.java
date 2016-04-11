package speechrecoginition.api.tianboandzifan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jitianbo on 16/3/8.
 */
public class DialogClass {
    private Context context;
    private ArrayList<String> results;
    private float[] confidence=null;
    private  CommandService commandService = null;
    private AnimationDrawable animationDrawable=null;
    private int i=0;
    private TextView txtV=null;
    private ImageView imageView;
    private SpeechForDialog sfd=null;
    private Handler handler=null;
    private Runnable runnable=null;

    public DialogClass(Context c,ArrayList<String> s,float[] f,TextView t,AnimationDrawable ad, ImageView imageView){
        this.context=c;
        this.results=s;
        this.confidence=f;
        this.txtV = t;
        this.animationDrawable=ad;
        this.imageView = imageView;

    }
    public void dialog1() {
//        api.StopAPI();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //        builder.setMessage("");
        //        builder.setIcon(R.mipmap.ic_launcher);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (i < 3 && i < results.size()) {
                    dialog.dismiss();

                    imageView.setVisibility(View.INVISIBLE);
                    dialog1();
                } else {
                    if (txtV != null)
                        txtV.setText("Please Start To Talk");
                    dialog.dismiss();
                    dialog2();

                }

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i = 0;
                if (txtV != null)
                    txtV.setText(results.get(i));
                dialog.dismiss();
                commandService = new CommandService(context, results.get(i));
                commandService.checkCommand();
                imageView.setVisibility(View.INVISIBLE);


            }
        });
        builder.setTitle(i == 0 ? (("I am ") + changeToPercentage(confidence[0]) + " sure you mean:") : ("Or do you mean:"));
        builder.setMessage(i < results.size() ? results.get(i) : "No more result");
        AlertDialog thedialog = builder.create();
        thedialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        thedialog.setCanceledOnTouchOutside(false);
        thedialog.show();

        sfd=new SpeechForDialog(context,results.get(i).trim(),thedialog,i,txtV,this);
        i++;
//        new YesOrNoCommand(context,thedialog,i,results.get(i),txtV).checkYesOrNo();
    }
    public void dialog2(){
        i=0;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("The End");
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
                imageView.setVisibility(View.VISIBLE);
                APIClass ipa = new APIClass(context, txtV, animationDrawable, imageView);
                ipa.InitAPI();
                ipa.StartAPI();


            }
        });
        builder.setMessage("Do you want to speak again?");
        AlertDialog thedialog = builder.create();
        thedialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        thedialog.setCanceledOnTouchOutside(false);
        thedialog.show();

    }
    private String changeToPercentage(float f){
        float d = new Float(Math.round(f * 10000)/100);
        return ""+d+"%";
    }


}