package com.d.pdemo;


import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.d.p.P;
import com.d.p.bean.RequestPermissionResult;
import com.d.p.utils.L;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;

    public BlankFragment() {
    }

    public static BlankFragment newInstance() {
        Bundle args = new Bundle();
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.    fragment_ptest,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initViews(view);
    }

    private void initViews(View view) {
        button1 = view.findViewById(R.id.request_1_btn);
        button2 = view.findViewById(R.id.request_2_btn);
        button3 = view.findViewById(R.id.request_3_btn);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.request_1_btn:
                P p1 = new P.Builder(this)
                        .requestPermissions(new String[]{
                                Manifest.permission.CAMERA})
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
            case R.id.request_2_btn:
                P p2 = new P.Builder(this)
                        .requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO})
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
}
