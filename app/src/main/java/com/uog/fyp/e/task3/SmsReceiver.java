package com.uog.fyp.e.task3;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UFormat;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();


    public SmsReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        try{
            if(bundle!=null){
                Object[] pdusObj=(Object[]) bundle.get("pdus");
                 if (pdusObj!=null){
                     for (Object apdusObj:pdusObj){
                         SmsMessage currentMessage=getIncomingMessage(apdusObj,bundle);
                         String senderNum=currentMessage.getDisplayOriginatingAddress();
                         String message=currentMessage.getDisplayMessageBody();

                         Log.d(TAG,"senderNum:"+senderNum+";message:"+message);
                         Intent directIntent=new Intent(context,MainActivity.class);
                         directIntent.putExtra(MainActivity.Extra_SMS_SENDER,senderNum);
                         directIntent.putExtra(MainActivity.Extra_SMS_Message,message);

                         PendingIntent pendingIntent=PendingIntent.getActivity(context,0,
                                 directIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                         pendingIntent.send();

                     }
                 }else{
                     Log.d(TAG,"onReceive:SMS is null");
                 }
            }

        }
        catch (Exception e){
            Log.d(TAG, "Exception smsReceiver: "+e);
        }
    }

    private SmsMessage getIncomingMessage(Object object,Bundle bundle){
        SmsMessage currentSMS;
        if(Build.VERSION.SDK_INT>=23){
            String format=bundle.getString("format");
            currentSMS=SmsMessage.createFromPdu((byte[]) object,format);
        } else{
            currentSMS=SmsMessage.createFromPdu((byte[]) object);
        }
        return currentSMS;
    }
}
