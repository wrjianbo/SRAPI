package speechrecoginition.api.tianboandzifan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jitianbo on 16/3/8.
 */
public class MyReceiver extends BroadcastReceiver {
    private ArrayList<String> results;
    private float[] confidence = null;
    private DialogClass dc=null;
    private Context thisContext=null;
    private ImageView imageView;
    private TextView txtV;
    private APIClass api=null;
    private AnimationDrawable animationDrawable=null;
    private  CommandService commandService = null;
    public MyReceiver(Context c,TextView t,APIClass a,AnimationDrawable ad,ImageView imageView){
        this.thisContext=c;
        this.txtV=t;
        this.api=a;
        this.animationDrawable=ad;
        this.imageView = imageView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        results = intent.getStringArrayListExtra("results");
        confidence=intent.getFloatArrayExtra("confidence");

                api.PutResultsAndConfidence(results, confidence);
                dc = new DialogClass(thisContext, results, confidence, txtV, animationDrawable, imageView);
        api.StopAPI();
        dc.dialog1();

                if (animationDrawable != null)
                    animationDrawable.stop();


        //invoke DialogClass
    }

}
