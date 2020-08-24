package com.d.pdemo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.d.p.P;
import com.d.p.bean.RequestPermissionResult;
import com.d.p.utils.L;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, P.OnRequestPermissionCallback {

    private Button button1;
    private Button button2;
    private Button button3;
    private P p1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        button1 = findViewById(R.id.request_1_btn);
        button2 = findViewById(R.id.request_2_btn);
        button3 = findViewById(R.id.request_3_btn);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.request_1_btn:
                p1 = new P.Builder(this)
                        .requestPermissions(new String[]{Manifest.permission.CAMERA})
                        .setTips("1：拨打电话 :使用本机拨打模式时需要；\n" +
                                "2：显示在其他应用上面：使用网络电话拨打模式通话中返回主页时需要；\n" +
                                "3：修改或删除您的SD卡中的内容：上传用户头像需要；\n" +
                                "4：新建/修改/删除通话记录：使用本机拨打模式时，需要将通话记录保存到App数据库时需要；\n" +
                                "5：录制音频：网络电话通话时需要；\n" +
                                "6：接听来电：使用回拨模式时，需要自动接听并返回主页\n" +
                                "7：读取通话记录:使用本机拨打模式时，需要将通话记录保存到App数据库时需要；\n" +
                                "8：拍摄照片和录制视频：上传用户头像需要；\n" +
                                "9：读取您的SD卡中的内容：上传用户头像需要；\n" +
                                "10：获取设备识别码和状态：注册设备时需要；n1：拨打电话 :使用本机拨打模式时需要；\n" +
                                "2：显示在其他应用上面：使用网络电话拨打模式通话中返回主页时需要；\n" +
                                "3：修改或删除您的SD卡中的内容：上传用户头像需要；\n" +
                                "4：新建/修改/删除通话记录：使用本机拨打模式时，需要将通话记录保存到App数据库时需要；\n" +
                                "5：录制音频：网络电话通话时需要；\n" +
                                "6：接听来电：使用回拨模式时，需要自动接听并返回主页\n" +
                                "7：读取通话记录:使用本机拨打模式时，需要将通话记录保存到App数据库时需要；\n" +
                                "8：拍摄照片和录制视频：上传用户头像需要；\n" +
                                "9：读取您的SD卡中的内容：上传用户头像需要；\n" +
                                "10：获取设备识别码和状态：注册设备时需要；")
                        .onRequestPermissionCallback(this)
                        .show();
                break;
            case R.id.request_2_btn:
                if (p1 != null)
                    p1.replacePermissionsAndShow(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO});
                break;
            case R.id.request_3_btn:
                P p3 = new P.Builder(this)
                        .requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE})
                        .onRequestPermissionCallback(new P.OnRequestPermissionCallback() {
                            @Override
                            public void onRequestPermissionResult(RequestPermissionResult result) {
                                switch (result.getState()) {
                                    case RequestPermissionResult.ALL_PERMISSION_PASS:
                                        L.logE("ALL_PERMISSION_PASS "
                                                + "\npass = " + Arrays.toString(result.getPassPermissions()));
                                        break;
                                    case RequestPermissionResult.SOME_PERMISSION_PASS:
                                        L.logE("SOME_PERMISSION_PASS "
                                                + "\ndenied = " + Arrays.toString(result.getDeniedPermissions())
                                                + "\npass = " + Arrays.toString(result.getPassPermissions()));
                                        break;
                                    case RequestPermissionResult.NO_PERMISSION_PASS:
                                        L.logE("NO_PERMISSION_PASS "
                                                + "\n denied = " + Arrays.toString(result.getDeniedPermissions()));
                                        break;
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onRequestPermissionResult(RequestPermissionResult result) {
        switch (result.getState()) {
            case RequestPermissionResult.ALL_PERMISSION_PASS:
                L.logE("ALL_PERMISSION_PASS "
                        + "\npass = " + Arrays.toString(result.getPassPermissions()));
                break;
            case RequestPermissionResult.SOME_PERMISSION_PASS:
                L.logE("SOME_PERMISSION_PASS "
                        + "\ndenied = " + Arrays.toString(result.getDeniedPermissions())
                        + "\npass = " + Arrays.toString(result.getPassPermissions()));
                break;
            case RequestPermissionResult.NO_PERMISSION_PASS:
                L.logE("NO_PERMISSION_PASS "
                        + "\n denied = " + Arrays.toString(result.getDeniedPermissions()));
                break;
        }
    }
}
