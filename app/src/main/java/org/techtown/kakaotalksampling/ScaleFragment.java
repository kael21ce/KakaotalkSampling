package org.techtown.kakaotalksampling;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    TextView incoming;
    TextView outgoing;
    TextView absContact;
    ImageView scaleHead;
    ScaleInfo scaleInfo;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scale, container, false);

        scaleInfo = new ScaleInfo();

        calling = v.findViewById(R.id.callCall);
        incoming = v.findViewById(R.id.incomingNum);
        outgoing = v.findViewById(R.id.outgoingNum);
        absContact = v.findViewById(R.id.showAbsContact);
        scaleHead = v.findViewById(R.id.scaleHead);


        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer numI = scaleInfo.getIncomingNum("01056099441");
                Integer numO = scaleInfo.getOutgoingNum("01056099441");
                incoming.setText("수신: " + numI.toString());
                outgoing.setText("발신: " + numO.toString());
                absContact.setText("연락 횟수 차: "+scaleInfo.betContact("01056099441"));
                rotateScale("01056099441");

            }
        });

        return v;
    }

    //저울 회전
    public void rotateScale(String mobile) {
        int bet = scaleInfo.betContact(mobile);
        if (bet<10 || bet>-10) {
            int angle = 4*bet;
            scaleHead.setRotation(angle);
        } else {
            scaleHead.setRotation(45);
        }
    }

}
