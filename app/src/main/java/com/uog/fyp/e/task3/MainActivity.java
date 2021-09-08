package com.uog.fyp.e.task3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_REQUEST_CODE =101 ;
    public static final String Extra_SMS_SENDER="extra_sms_number";
    public static final String Extra_SMS_Message="extra_sms_message";

    SmsReceiver receiver=new SmsReceiver();

    private TextView Smssender,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS},
                    SMS_REQUEST_CODE);
        }
        Smssender=findViewById(R.id.sms_from);
        text=findViewById(R.id.sms_content);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(!intent.hasExtra(Extra_SMS_SENDER) && !intent.hasExtra(Extra_SMS_Message)){
            return;
        }
        String smsSender=intent.getExtras().getString(Extra_SMS_SENDER);
        String smsContent=intent.getExtras().getString(Extra_SMS_Message);

        Smssender.setText(smsSender);
        text.setText(smsContent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==SMS_REQUEST_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"SMS RECEIVER PERMISSION ACCEPTED",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"SMS RECEIVER PERMISSION DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}