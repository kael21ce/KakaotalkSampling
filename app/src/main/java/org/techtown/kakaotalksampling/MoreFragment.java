package org.techtown.kakaotalksampling;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
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
import java.util.Set;
import java.util.UUID;

public class MoreFragment extends Fragment {

    TextView btStatus;
    Button btLED;
    public BluetoothAdapter bluetoothAdapter;
    public Set<BluetoothDevice> mDevices;
    private BluetoothSocket bSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private BluetoothDevice mRemoteDevice;
    public boolean onBT = false;
    public byte[] sendByte = new byte[4];
    private static final int REQUEST_ENABLE_BT = 1;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container, false);

        btStatus = v.findViewById(R.id.btStatus);
        btLED = v.findViewById(R.id.btLED);
        btLED.setOnClickListener(v1 -> {
            checkBluetoothOn();
            connectDevice("HC-06");
        });

        return v;
    }

    @SuppressLint("SetTextI18n")
    public void checkBluetoothOn() {
        if (!onBT) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                btStatus.setText("상태: Bluetooth 지원을 하지 않는 기기입니다.");
            } else {
                if (!bluetoothAdapter.isEnabled()) {
                    //활성 상태로 바꾸기 위한 요청
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                } else {
                    if (ActivityCompat.checkSelfPermission(getView().getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        btStatus.setText("상태: Bluetooth 권한이 없습니다.");
                        return;
                    }
                    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                    if (pairedDevices.size() > 0) {
                        btStatus.setText("상태: 페어링되었습니다.");
                    } else {
                        btStatus.setText("상태: 페어링을 진행해주세요.");
                    }
                }
            }
        } else {
            //블루투스 연결 종료
            try {
                //데이터 통신 스레드 종료
                BTSend.interrupt();
                mInputStream.close();
                mOutputStream.close();
                bSocket.close();
                onBT = false;
                btLED.setText("LED 전원");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void connectDevice(final String deviceName) {
        mRemoteDevice = getDeviceFromBondedList(deviceName);
        if (mRemoteDevice==null) {
            btStatus.setText("상태: 연결 가능한 기기가 없습니다.");
            return;
        }

        @SuppressLint("SetTextI18n") Thread BTConnect = new Thread(() -> {
            try {
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                //소켓 생성
                if (ActivityCompat.checkSelfPermission(getView().getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
                        PackageManager.PERMISSION_GRANTED) {
                    btStatus.setText("상태: Bluetooth 권한이 없습니다.");
                    return;
                }
                bSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
                bSocket.connect();
                //데이터 송수신을 위한 스트림 열기
                mOutputStream = bSocket.getOutputStream();
                mInputStream = bSocket.getInputStream();

                btStatus.setText("상태: " + deviceName + " 연결 완료");
                btLED.setText("연결 해제하기");

                onBT = true;
            } catch (Exception e) {
                //블루투스 연결 중 오류 발생
                btStatus.setText("연결 오류->Bluetooth 상태 확인 바랍니다.");
            }
        });
        BTConnect.start();
    }

    public BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
        try {
            for (BluetoothDevice device : mDevices) {
                if (ActivityCompat.checkSelfPermission(getView().getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
                        PackageManager.PERMISSION_GRANTED) {
                    btStatus.setText("상태: Bluetooth 권한이 없습니다.");
                    break;
                }
                if (name.equals(device.getName())) {
                    selectedDevice = device;
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return selectedDevice;
    }

    Thread BTSend = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //프로토콜 전송
                mOutputStream.write(sendByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


}
