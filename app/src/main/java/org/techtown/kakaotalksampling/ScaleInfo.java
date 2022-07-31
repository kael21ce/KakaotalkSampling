package org.techtown.kakaotalksampling;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScaleInfo extends ContentProvider {

    SimpleDateFormat simpleDateFormat;
    String[] callSet;
    String[] smsSet;
    long now = System.currentTimeMillis();
    long weekago = now - 604800000; //데이터 수집 주기: 일주일

    Uri smsUri = Uri.parse("content://mms-sms/conversations");


    //연락 저울을 위한 메소드를 포함하고 있는 클래스
    @Override
    public boolean onCreate() {
        return true;
    }

    //입력된 연락처의 정보 가져오기
    public String getCallHistory(Context context, String mobile) {
        callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);
        if ( cursor == null)
        {
            return "통화기록 없음";
        }

        int recordCount = cursor.getCount();
        StringBuffer callBuff = new StringBuffer();
        callBuff.append("\n날짜 : 요일 : 시간 : 구분 : 전화번호 : 통화시간\n\n");
        cursor.moveToFirst();

        for (int i =0; i< recordCount; i++) {

            String lMobile = cursor.getString(2);

            if (lMobile.equals(mobile)) {
                if (cursor.getLong(0)>weekago) {
                    long callDate = cursor.getLong(0);
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd : E요일 : HH:mm:ss");
                    String date_str = simpleDateFormat.format(new Date(callDate));

                    callBuff.append(date_str + " : ");

                    if (cursor.getInt(1) == CallLog.Calls.INCOMING_TYPE)
                    {
                        callBuff.append("수신 : ");
                    }
                    else if (cursor.getInt(1) == CallLog.Calls.OUTGOING_TYPE)
                    {
                        callBuff.append("발신 : ");
                    }
                    else if (cursor.getInt(1) == CallLog.Calls.MISSED_TYPE)
                    {
                        callBuff.append("부재중 : ");
                    }
                    else if (cursor.getInt(1) == CallLog.Calls.REJECTED_TYPE)
                    {
                        callBuff.append("종료 : ");
                    }
                    callBuff.append(cursor.getString(2)+ " : ");
                    callBuff.append(cursor.getString(3));
                    callBuff.append("\n");

                    cursor.moveToNext();
                } else {
                    cursor.moveToNext();
                }
            } else {
                cursor.moveToNext();
            }

        }
        cursor.close();
        return callBuff.toString();
    }

    //입력된 연락처로부터 수신된 횟수 가져오기
    public int getIncomingNum(Context context, String mobile) {
        callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);
        Integer numIncoming = 0;

        if ( cursor == null)
        {
            return 0;
        }

        int recordCount = cursor.getCount();
        cursor.moveToFirst();

        for (int i =0; i< recordCount; i++) {

            String lMobile = cursor.getString(2);

            if (lMobile.equals(mobile)) {
                if (cursor.getLong(0)>=weekago) {
                    if (Integer.parseInt(cursor.getString(3))>=20) {
                        //통화 시간이 20초 이상인 연락만 인정
                        if (cursor.getInt(1) == CallLog.Calls.INCOMING_TYPE)
                        {
                            numIncoming = numIncoming + 1;
                        }
                        else
                        {
                            numIncoming = numIncoming;
                        }
                        cursor.moveToNext();
                    } else {
                        cursor.moveToNext();
                    }
                } else {
                    cursor.moveToNext();
                }
            } else {
                cursor.moveToNext();
            }

        }
        cursor.close();
        return numIncoming;
    }


    //입력된 연락처에게 발신한 횟수 가져오기
    public int getOutgoingNum(Context context, String mobile) {
        callSet = new String[] { CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION };
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);
        Integer numOutgoing = 0;

        if ( cursor == null)
        {
            return 0;
        }

        int recordCount = cursor.getCount();
        cursor.moveToFirst();

        for (int i =0; i< recordCount; i++) {

            String lMobile = cursor.getString(2);

            if (lMobile.equals(mobile)) {
                if (cursor.getLong(0)>=weekago) {
                    if (Integer.parseInt(cursor.getString(3))>=20) {
                        //통화 시간이 20초 이상인 연락만 인정
                        if (cursor.getInt(1) == CallLog.Calls.OUTGOING_TYPE)
                        {
                            numOutgoing = numOutgoing + 1;
                        }
                        else
                        {
                            numOutgoing = numOutgoing;
                        }
                        cursor.moveToNext();
                    } else {
                        cursor.moveToNext();
                    }
                } else {
                    cursor.moveToNext();
                }
            } else {
                cursor.moveToNext();
            }

        }
        cursor.close();
        return numOutgoing;
    }

    //입력된 연락처와의 연락 수 차이 가져오기
    public int betContact(Context context, String mobile) {
        int numI = getIncomingNum(context, mobile);
        int numO = getOutgoingNum(context, mobile);

        return numI-numO;
    }

    //입력된 연락처로부터 sms thread_id 가져오기
    public int getSMSThread(Context context, String mobile) {
        int threadN=-1;
        smsSet = new String[] { "address", "thread_id" };
        Cursor cursor = context.getContentResolver().query(smsUri,
                smsSet, null, null, "date ASC");
        if ( cursor == null)
        {
            return threadN;
        }

        int recordCount = cursor.getCount();
        cursor.moveToFirst();

        for (int i =0; i< recordCount; i++) {
            String lMobile = cursor.getString(0);
            //전화번호 필터
            if (lMobile == null) {
                cursor.moveToNext();
            } else if (lMobile.equals(mobile)) {
                threadN = cursor.getInt(1);

                break;
            } else {
                cursor.moveToNext();
            }
        }
        cursor.close();
        return threadN;
    }

    //입력된 연락처로부터 sms 내역 가져오기
    public String getSMSHistory(Context context, String mobile) {
        int threadN;
        smsSet = new String[] { "date", "type", "address", "thread_id" };
        Cursor cursor = context.getContentResolver().query(smsUri,
                smsSet, null, null, "date ASC");
        if ( cursor == null)
        {
            return "sms 내역 없음.";
        }

        int recordCount = cursor.getCount();
        StringBuffer smsBuff = new StringBuffer();
        smsBuff.append("\n날짜 : 요일 : 시간 : 구분 : 전화번호 : 스레드 id\n\n");
        cursor.moveToFirst();

        for (int i =0; i< recordCount; i++) {
            String lMobile = cursor.getString(2);
            //전화번호 필터
            if (lMobile == null) {
                cursor.moveToNext();
            } else if (lMobile.equals(mobile)) {

                long smsDate = cursor.getLong(0);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd : E요일 : HH:mm:ss");
                String date_str = simpleDateFormat.format(new Date(smsDate));

                smsBuff.append(date_str+" : ");
                smsBuff.append(cursor.getInt(1) + " : ");
                smsBuff.append(cursor.getString(2) + " : ");
                smsBuff.append(cursor.getInt(3) + "\n");

                cursor.moveToNext();
            } else {
                cursor.moveToNext();
            }
        }
        cursor.close();
        return smsBuff.toString();
    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
