package org.techtown.kakaotalksampling;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    boolean flag;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container, false);

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
                        registerReceiver(receiver, filter);
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

}
