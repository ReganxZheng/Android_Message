package nz.ac.aut.dms.lab7_messageapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText txt_phone;
    public EditText txt_msg;
    public Button send;
    final int SENT_SMS_PERMISSION_REQUEST_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_msg = (EditText) findViewById(R.id.msgBody);
        txt_phone = (EditText) findViewById(R.id.pNumber);
        send = (Button) findViewById(R.id.sendMsg);


        send.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            send.setEnabled(true);
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, SENT_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    public void btn_send(View v) {
        String phoneNumber = txt_phone.getText().toString();
        String sentMsg = txt_msg.getText().toString();

        if(phoneNumber==null || phoneNumber.length()==0 ||
            sentMsg == null || sentMsg.length()==0) {
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phoneNumber,null, sentMsg, null, null);
            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
        } else {Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();}
    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }




}
