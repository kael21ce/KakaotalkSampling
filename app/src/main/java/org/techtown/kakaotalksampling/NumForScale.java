package org.techtown.kakaotalksampling;

import android.database.Cursor;
import android.media.session.PlaybackState;
import android.provider.CallLog;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NumForScale extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat;

    public String getCallHistory(String mobile) {
        String[] callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);

        if ( c == null)
        {
            return "통화기록 없음";
        }

        int recordCount = c.getCount();
        StringBuffer callBuff = new StringBuffer();
        callBuff.append("\n날짜 : 요일 : 시간 : 구분 : 전화번호 : 통화시간\n\n");
        c.moveToFirst();

        for (int i =0; i< recordCount; i++) {

            String lMobile = c.getString(2);

            if (lMobile.equals(mobile)) {

                long callDate = c.getLong(0);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd : E요일 : HH:mm:ss");
                String date_str = simpleDateFormat.format(new Date(callDate));

                callBuff.append(date_str + " : ");

                if (c.getInt(1) == CallLog.Calls.INCOMING_TYPE)
                {
                    callBuff.append("수신 : ");
                }
                else if (c.getInt(1) == CallLog.Calls.OUTGOING_TYPE)
                {
                    callBuff.append("발신 : ");
                }
                else if (c.getInt(1) == CallLog.Calls.MISSED_TYPE)
                {
                    callBuff.append("부재중 : ");
                }
                else if (c.getInt(1) == CallLog.Calls.REJECTED_TYPE)
                {
                    callBuff.append("종료 : ");
                }
                callBuff.append(c.getString(2)+ " : ");
                callBuff.append(c.getString(3));
                callBuff.append("\n");

                c.moveToNext();
            } else {
                c.moveToNext();
            }

        }
        c.close();
        return callBuff.toString();
    }
}
