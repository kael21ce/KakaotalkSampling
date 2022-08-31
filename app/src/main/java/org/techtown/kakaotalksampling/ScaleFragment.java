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
    //전화번호 설정
    String mobile = "01065515413";
    //

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scale, container, false);

        scaleInfo = new ScaleInfo();

        activate = v.findViewById(R.id.activateScale);
        incoming = v.findViewById(R.id.incomingNum);
        outgoing = v.findViewById(R.id.outgoingNum);
        absContact = v.findViewById(R.id.showAbsContact);
        scaleHead = v.findViewById(R.id.scaleHead);
        //infoList = v.findViewById(R.id.infoList);

        //서비스로부터 전달된 인텐트 처리
        Intent passedIntent = getActivity().getIntent();
        if (passedIntent!=null) {
            processIntentWithScale(v.getContext(), passedIntent);
        }
        //

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer numI = scaleInfo.getIncomingNum(v.getContext(), "01065515413");
                Integer numO = scaleInfo.getOutgoingNum(v.getContext(), "01065515413");
                Integer snumI = scaleInfo.getInboxNum(v.getContext(), "01065515413");
                Integer numS = scaleInfo.getSentNum(v.getContext(), "01065515413");
                incoming.setText("수신: 통화-" + numI.toString()+" / SMS-"+snumI.toString());
                outgoing.setText("발신: 통화-" + numO.toString()+" / SMS-"+numS.toString());
                absContact.setText("연락 횟수 차: "+scaleInfo.betContact(v.getContext(), "01065515413"));
                rotateScale(v.getContext(), "01065515413");

            }
        });

        return v;
    }

    //저울 회전
    public void rotateScale(Context context, String mobile) {
        float bet = scaleInfo.betContact(context, mobile);
        if (bet<10 || bet>-10) {
            float angle = 4*bet;
            scaleHead.setRotation(angle);
        } else {
            scaleHead.setRotation(45);
        }
    }

    //수신된 인텐트 처리
    private void processIntentWithScale(Context context, Intent intent) {
        if (intent != null) {
            String newNumber = intent.getStringExtra("newNumber");
            if (newNumber!=null) {
                if (newNumber.equals(mobile)) {
                    Integer numI = scaleInfo.getIncomingNum(context, newNumber);
                    Integer numO = scaleInfo.getOutgoingNum(context, newNumber);
                    Integer snumI = scaleInfo.getInboxNum(context, newNumber);
                    Integer numS = scaleInfo.getSentNum(context, newNumber);
                    incoming.setText("수신: 통화-" + numI.toString()+" / SMS-"+snumI.toString());
                    outgoing.setText("발신: 통화-" + numO.toString()+" / SMS-"+numS.toString());
                    absContact.setText("연락 횟수 차: "+scaleInfo.betContact(context, newNumber));
                    rotateScale(context, newNumber);
                }
            }
        }
    }

}
