package org.techtown.kakaotalksampling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScaleFragment extends Fragment {

    Button activate;
    TextView incoming;
    TextView outgoing;
    TextView absContact;
    ImageView scaleHead;
    ScaleInfo scaleInfo;
    TextView infoList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scale, container, false);

        scaleInfo = new ScaleInfo();

        activate = v.findViewById(R.id.activateScale);
        incoming = v.findViewById(R.id.incomingNum);
        outgoing = v.findViewById(R.id.outgoingNum);
        absContact = v.findViewById(R.id.showAbsContact);
        scaleHead = v.findViewById(R.id.scaleHead);
        infoList = v.findViewById(R.id.infoList);

        //서비스로부터 전달된 인텐트 처리
        Intent passedIntent = getActivity().getIntent();
        processIntentWithScale(v.getContext(), passedIntent);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer numI = scaleInfo.getIncomingNum(v.getContext(), "01065515413");
                Integer numO = scaleInfo.getOutgoingNum(v.getContext(), "01065515413");
                incoming.setText("수신: " + numI.toString());
                outgoing.setText("발신: " + numO.toString());
                absContact.setText("연락 횟수 차: "+scaleInfo.betContact(v.getContext(), "01065515413"));
                rotateScale(v.getContext(), "01065515413");
                //infoList.setText(scaleInfo.getCallHistory(v.getContext(), "01065515413"));
                //01065515413 01071816705 01099398250
                infoList.setText(scaleInfo.getSMSHistory(v.getContext(), "01099398250"));
            }
        });

        return v;
    }

    //저울 회전
    public void rotateScale(Context context, String mobile) {
        int bet = scaleInfo.betContact(context, mobile);
        if (bet<10 || bet>-10) {
            int angle = 4*bet;
            scaleHead.setRotation(angle);
        } else {
            scaleHead.setRotation(45);
        }
    }

    //수신된 인텐트 처리
    private void processIntentWithScale(Context context, Intent intent) {
        if (intent != null) {
            String newNumber = intent.getStringExtra("newNumber");
            Integer numI = scaleInfo.getIncomingNum(context, newNumber);
            Integer numO = scaleInfo.getOutgoingNum(context, newNumber);
            incoming.setText("수신: " + numI.toString());
            outgoing.setText("발신: " + numO.toString());
            absContact.setText("연락 횟수 차: "+scaleInfo.betContact(context, newNumber));
            rotateScale(context, newNumber);
        }
    }

}
