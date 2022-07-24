package org.techtown.kakaotalksampling;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScaleFragment extends Fragment {

    Button calling;
    TextView listCalling;
    TextView incoming;
    TextView outgoing;
    TextView absContact;

    SimpleDateFormat simpleDateFormat;
    Integer numIncoming = 0;
    Integer numOutgoing = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scale, container, false);

        calling = v.findViewById(R.id.callCall);
        listCalling = v.findViewById(R.id.callLogs);
        incoming = v.findViewById(R.id.incomingNum);
        outgoing = v.findViewById(R.id.outgoingNum);
        absContact = v.findViewById(R.id.showAbsContact);

        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCalling.setText(getCallHistory("01067673243"));
                numIncoming = getIncomingNum("01067673243");
                numOutgoing = getOutgoingNum("01067673243");
                incoming.setText("수신: " + numIncoming.toString());
                outgoing.setText("발신: " + numOutgoing.toString());
                absContact.setText("연락 횟수 차: "+betContact("01067673243"));

                numOutgoing = 0;
                numIncoming = 0;
            }
        });

        return v;
    }



    //Method For Scale
    //입력된 연락처의 정보 가져오기
    public String getCallHistory(String mobile) {
        String[] callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor c = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
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

    //입력된 연락처로부터 수신된 횟수 가져오기
    public int getIncomingNum(String mobile) {
        String[] callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor c = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);

        if ( c == null)
        {
            return 0;
        }

        int recordCount = c.getCount();
        c.moveToFirst();

        for (int i =0; i< recordCount; i++) {

            String lMobile = c.getString(2);

            if (lMobile.equals(mobile)) {
                if (c.getInt(1) == CallLog.Calls.INCOMING_TYPE)
                {
                    numIncoming = numIncoming + 1;
                }
                else
                {
                    numIncoming = numIncoming;
                }
                c.moveToNext();
            } else {
                c.moveToNext();
            }
        }
        c.close();
        return numIncoming;
    }


    //입력된 연락처에게 발신한 횟수 가져오기
    public int getOutgoingNum(String mobile) {
        String[] callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor c = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);

        if ( c == null)
        {
            return 0;
        }

        int recordCount = c.getCount();
        c.moveToFirst();

        for (int i =0; i< recordCount; i++) {

            String lMobile = c.getString(2);

            if (lMobile.equals(mobile)) {
                if (c.getInt(1) == CallLog.Calls.OUTGOING_TYPE)
                {
                    numOutgoing = numOutgoing + 1;
                }
                else
                {
                    numOutgoing = numOutgoing;
                }
                c.moveToNext();
            } else {
                c.moveToNext();
            }
        }
        c.close();
        return numOutgoing;
    }

    //입력된 연락처와의 연락 수 차이 가져오기
    public int betContact(String mobile) {
        numOutgoing = 0;
        numIncoming = 0;

        int numI = getIncomingNum(mobile);
        int numO = getOutgoingNum(mobile);

        return numI-numO;
    }

}
