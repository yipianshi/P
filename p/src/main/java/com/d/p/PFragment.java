package com.d.p;


import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.p.bean.RequestPermissionResult;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * @author dcm
 * @since 20190509
 */
public class PFragment extends Fragment {

    private final static String PARAMS1 = "params1";
    private final static String PARAMS2 = "params2";
    private final static String PARAMS3 = "params3";
    private final static int PERMISSION_REQUEST_CODE = 0xFF;

    private ArrayList<String> preUnPassList = new ArrayList<>();
    private ArrayList<String> prePassList = new ArrayList<>();
    private String[] checkPermissions;

    private String[] permissions;
    private P p;
    private String tips;

    public static PFragment newInstance(P p, String[] permissions) {
        return newInstance(p, permissions, null);
    }

    public static PFragment newInstance(P p, String[] permissions, String tips) {
        Bundle args = new Bundle();
//        args.putSerializable(PARAMS1, p);
        args.putParcelable(PARAMS1, p);
        args.putStringArray(PARAMS2, permissions);
        args.putString(PARAMS3, tips);
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
//            p = (P) args.getSerializable(PARAMS1);
            p = (P) args.getParcelable(PARAMS1);
            permissions = args.getStringArray(PARAMS2);
            tips = args.getString(PARAMS3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(tips)) {
            //展示对话框
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setMessage(tips)
                    .setTitle("权限申请说明")
                    .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setOnDismissListener(dismissListener)
                    .show();
        } else {
            doRequestPermission();
        }
        return null;
    }

    private void doRequestPermission() {
        divisivePermissions(permissions);
        if (preUnPassList.size() == 0) {
            //全部通过
            RequestPermissionResult requestPermissionResult = new RequestPermissionResult(RequestPermissionResult.ALL_PERMISSION_PASS, new String[]{}, permissions);
            p.onRequestPermissionResult(requestPermissionResult);
        } else {
            //请求未通过的权限
            checkPermissions = new String[preUnPassList.size()];
            preUnPassList.toArray(checkPermissions);
            requestPermissions(checkPermissions, PERMISSION_REQUEST_CODE);
        }
    }

    private DialogInterface.OnDismissListener dismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            doRequestPermission();
        }
    };

    /**
     * 请求权限前进行筛选，区分通过或没有通过的权限
     *
     * @param permissions 请求的所有权限
     */
    private void divisivePermissions(String[] permissions) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, permission)) {
                //权限不通过
                preUnPassList.add(permission);
            } else {
                prePassList.add(permission);
            }
        }
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

        passPermissionList.addAll(prePassList);

        String[] deniedPermissions = new String[deniedPermissionList.size()];
        String[] passPermissions = new String[passPermissionList.size()];
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
