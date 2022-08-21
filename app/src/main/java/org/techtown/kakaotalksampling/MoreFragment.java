package org.techtown.kakaotalksampling;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MoreFragment extends Fragment {
    private final static int REQUEST_ENABLED_BT = 101;
    TextView btStatus;
    Button btLED;
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedDevices;
    ArrayList<String> devicePairedArrayList;
    ArrayList<String> devicePairedNameL;
    ArrayList<String> deviceLocalArrayList;
    ArrayList<String> deviceLocalNameL;
    BluetoothSocket btSocket;
    BluetoothDevice device;
    ConnectedThread connectedThread;
    Button getButton;
    EditText gotText;
    TextView text4Test;
    boolean flag;

    //database 생성
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //
    String TAG = "MoreFragment";

    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container, false);

        //
        Map<String, Object> user1 = new HashMap<>();
        user1.put("name", "장형준");
        user1.put("birth", "20020510");
        user1.put("mobile", "01046076705");

        Map<String, Object> user2 = new HashMap<>();
        user2.put("name", "홍길동");
        user2.put("birth", "20000909");
        user2.put("mobile", "01000000000");


        db.collection("users")
                .add(user1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: "+documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        db.collection("users")
                .add(user2)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: "+documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        //
        gotText = v.findViewById(R.id.gotText);
        getButton = v.findViewById(R.id.getButton);
        text4Test = v.findViewById(R.id.text4Test);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = gotText.getText().toString();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> data = document.getData();
                                        String userName = data.get("name").toString();
                                        String userMobile = data.get("mobile").toString();
                                        assert userName != null;
                                        if (userName.equals(string)) {
                                            text4Test.setText("전화번호: "+userMobile);
                                            break;
                                        } else {
                                            text4Test.setText("해당하는 이름이 존재하지 않습니다.");
                                        }
                                    }
                                } else {
                                    gotText.setText("데이터 불러오는 중 에러 발생: "+task.getException());
                                }
                            }
                        });
            }
        });
        //

        btStatus = v.findViewById(R.id.btStatus);
        btLED = v.findViewById(R.id.btLED);
        btLED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBTPermission(v.getContext());

                //페어링된 디바이스 목록
                devicePairedArrayList = new ArrayList<>();
                devicePairedNameL = new ArrayList<>();
                if (devicePairedArrayList != null && devicePairedArrayList.isEmpty()) {
                    devicePairedArrayList.clear();
                }
                pairedDevices = btAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress();
                        devicePairedArrayList.add(deviceHardwareAddress);
                        devicePairedNameL.add(deviceName);
                    }
                }

                //주변 디바이스 목록
                deviceLocalArrayList = new ArrayList<>();
                deviceLocalNameL = new ArrayList<>();
                if (btAdapter.isDiscovering()) {
                    btAdapter.cancelDiscovery();
                } else {
                    if (btAdapter.isEnabled()) {
                        btAdapter.startDiscovery();
                        if (deviceLocalArrayList != null && !deviceLocalArrayList.isEmpty()) {
                            deviceLocalArrayList.clear();
                        }
                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        requireActivity().registerReceiver(receiver, filter);

                    } else {
                        Toast.makeText(v.getContext(), "블루투스가 켜지지 않았습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                //블루투스 통신
                //기기 주소 가져오기-기기명이 " faceArduino"인 기기의 주소를 가져온다.
                String faceDeviceAddress = "";
                int pairedLength = devicePairedArrayList.size();
                for (int i = 0; i < pairedLength; i++) {
                    if (devicePairedNameL.get(i).equals(" faceArduino")) {
                        faceDeviceAddress = devicePairedArrayList.get(i);
                        break;
                    }
                }
                int localLength = deviceLocalArrayList.size();
                for (int i = 0; i < localLength; i++) {
                    if (deviceLocalNameL.get(i).equals(" faceArduino")) {
                        faceDeviceAddress = deviceLocalArrayList.get(i);
                        break;
                    }
                }
                try {
                    device = btAdapter.getRemoteDevice(faceDeviceAddress);
                    flag = true;
                } catch (Exception e) {
                    flag = false;
                    btStatus.setText("상태: 기기 없음.");
                }

                //소캣 생성 및 연결
                try {
                    btSocket = createBluetoothSocket(device);
                    if (btSocket != null) {
                        btSocket.connect();
                    }
                } catch (IOException e) {
                    flag = false;
                    btStatus.setText("상태: 연결 실패");
                    e.printStackTrace();
                }

                if (flag) {
                    btStatus.setText("상태: 연결되었음.");
                    connectedThread = new ConnectedThread(btSocket);
                    connectedThread.start();
                }

                if (connectedThread != null) {
                    connectedThread.write("1");
                }

            }
        });
        return v;
    }

    //블루투스 권한 확인 및 어댑터 생성
    public void checkBTPermission(Context context) {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                btStatus.setText("상태: 블루투스 권한 없음.");
                return;
            }
            startActivityForResult(enableIntent, REQUEST_ENABLED_BT);
        }
    }

    //ACTION_FOUND를 위한 브로드캐스트 리시버
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                deviceLocalArrayList.add(deviceHardwareAddress);
                deviceLocalNameL.add(deviceName);
            }
        }
    };

    //receiver 등록 해제
    @Override
    public void onDestroy() {
        super.onDestroy();

        requireActivity().unregisterReceiver(receiver);
    }

    //createBluetoothSocket 메서드
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, uuid);
            } catch (Exception e) {

            }
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        try {
            return device.createRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) {
            return null;
        }
    }
}
