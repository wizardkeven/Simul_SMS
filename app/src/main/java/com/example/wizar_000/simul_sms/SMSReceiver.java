package com.example.wizar_000.simul_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by wizar_000 on 29/05/2015.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //--get the SMS message passed in--
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null){
            //--retrieve the SMS message received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i<msgs.length;i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "SMS from" + msgs[i].getOriginatingAddress();//getOriginatingAddress() enable us to get back the sender's number
                str +=" :";
                str+= msgs[i].getMessageBody().toString();
                str += "\n";
            }
            //--displat the new SMS message ---
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();

            //send a broadcast intent to update the SMS received in a textview
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms",str);
            context.sendBroadcast(broadcastIntent);
        }
    }
}
