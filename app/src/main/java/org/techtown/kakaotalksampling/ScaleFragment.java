package org.techtown.kakaotalksampling;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class ScaleFragment extends Fragment {

    Button calling;
    TextView listCalling;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scale, container, false);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_CALL_LOG, Permission.READ_CONTACTS)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast.makeText(v.getContext(), "허용된 권한 개수: "+permissions.size(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast.makeText(v.getContext(), "거부된 권한 개수: "+permissions.size(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

        calling = v.findViewById(R.id.callCall);
        listCalling = v.findViewById(R.id.callLogs);

        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCalling.setText(getCallHistory());
            }
        });

        return v;
    }

    public String getCallHistory() {
        String[] callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, callSet, null, null, null);

        if ( c == null)
        {
            return "통화기록 없음";
        }
        StringBuffer callBuff = new StringBuffer();
        callBuff.append("\n날짜 : 요일 : 시간 : 구분 : 전화번호 : 통화시간\n\n");
        c.moveToFirst();
        do{
            long callDate = c.getLong(0);
            SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd : E요일 : HH:mm:ss");
            String date_str = datePattern.format(new Date(callDate));

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


        } while (c.moveToNext());
        c.close();
        return callBuff.toString();
    }
}
