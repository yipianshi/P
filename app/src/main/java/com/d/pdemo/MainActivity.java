package com.d.pdemo;

import android.Manifest;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.d.p.P;
import com.d.p.bean.RequestPermissionResult;
import com.d.p.utils.L;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

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
                        .requestPermissions(new String[]{
                                Manifest.permission.CAMERA})
                        .onRequestPermissionCallback(this)
                        .show();
                break;
            case R.id.request_2_btn:
                p1.replacePermissionsAndShow(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO});
                break;
            case R.id.request_3_btn:
                P p3 = new P.Builder(this)
                        .requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE})
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
