package org.techtown.kakaotalksampling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    public static final String TAG = "SMSReceiver";
    public static final String STAG = "SMSService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive() 메서드 호출됨.");
        //인텐트 가져오기
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessages(bundle);

        if (messages != null && messages.length > 0) {
            String sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "SMS sender: " + sender);

            //서비스로 인텐트 전송
            Intent serviceIntent = new Intent(context, SMSService.class);
            serviceIntent.putExtra("mobile", sender);
            context.startService(serviceIntent);
            Log.i(STAG, "서비스 시작");
        }
    }

    private SmsMessage[] parseSmsMessages(Bundle bundle) {
        Object[] objects = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objects.length];

        int smsCount = objects.length;
        for (int i = 0; i < smsCount; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }
        }

        return messages;
    }
}