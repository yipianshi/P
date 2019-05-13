package com.d.p;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.p.bean.RequestPermissionResult;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author dcm
 * @since 20190509
 */
public class PFragment extends Fragment {

    private final static String PARAMS1 = "params1";
    private final static String PARAMS2 = "params2";
    private final static int PERMISSION_REQUEST_CODE = 0xFF;


    private String[] permissions;
    private P p;

    public static PFragment newInstance(P p, String[] permissions) {
        Bundle args = new Bundle();
        args.putSerializable(PARAMS1, p);
        args.putStringArray(PARAMS2, permissions);
        PFragment fragment = new PFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            p = (P) args.getSerializable(PARAMS1);
            permissions = args.getStringArray(PARAMS2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            RequestPermissionResult requestPermissionResult = getRequestPermissionResult(permissions, grantResults);
            p.removeFragment();
            p.onRequestPermissionResult(requestPermissionResult);
        }
    }

    /**
     * 获取权限申请结果
     *
     * @param permissions
     * @param grantResults
     */
    private RequestPermissionResult getRequestPermissionResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<String> deniedPermissionList = new ArrayList<>();
        ArrayList<String> passPermissionList = new ArrayList<>();
        int state = 0;
        for (int i = 0; i < grantResults.length; i++) {
            int grantResult = grantResults[i];
            if (PackageManager.PERMISSION_DENIED == grantResult) {
                //没有通过的权限
                deniedPermissionList.add(permissions[i]);
            } else {
                //通过的权限
                passPermissionList.add(permissions[i]);
            }
        }
        String[] deniedPermissions = new String[deniedPermissionList.size()];
        String[] passPermissions = new String[permissions.length - deniedPermissionList.size()];
        deniedPermissionList.toArray(deniedPermissions);
        passPermissionList.toArray(passPermissions);

        if (deniedPermissions.length == 0) {
            //所有权限通过
            state = RequestPermissionResult.ALL_PERMISSION_PASS;
        } else {
            //还有权限没有被通过
            if (passPermissions.length != 0) {
                //只有部分权限没有通过
                state = RequestPermissionResult.SOME_PERMISSION_PASS;
            } else {
                //所有权限都没有通过
                state = RequestPermissionResult.NO_PERMISSION_PASS;
            }
        }
        return new RequestPermissionResult(state, deniedPermissions, passPermissions);
    }


}