package com.d.p;

import android.content.Context;

import com.d.p.bean.AlertBean;
import com.d.p.bean.RequestPermissionResult;
import com.d.p.utils.L;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author dcm
 * @since 2019/5/9
 */
public class P implements Serializable {
    private FragmentManager manager;
    private PFragment pFragment;
    private Builder builder;
    private AlertDialog dialog;

    private P(Builder builder) {
        this.builder = builder;
        executeRequestPermission(builder);
    }

    private void executeRequestPermission(Builder builder) {
        Object object = builder.weakReference.get();
        if (object == null) {
            L.logE("");
            return;
        }
        if (manager == null) {
            manager = getFragmentManager(object);
        }
        if (manager == null) {
            L.logE("");
            return;
        }
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        pFragment = PFragment.newInstance(this, builder.requestPermissions);
        fragmentTransaction.add(pFragment, "PFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private FragmentManager getFragmentManager(Object object) {
        FragmentManager manager = null;
        if (object instanceof FragmentActivity) {
            manager = ((FragmentActivity) object).getSupportFragmentManager();
        } else if (object instanceof Fragment) {
            manager = ((Fragment) object).getFragmentManager();
        }
        return manager;
    }

    /**
     * 构造权限申请提示框
     *
     * @param context
     * @param bean
     */
    private void showTipsDialog(Context context, AlertBean bean) {
        String msg = bean.getAlertMessage();
        dialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(bean.getAlertTitle())
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(bean.getNegativeButtonText(), bean.getNegativeOnClickListener())
                .setPositiveButton(bean.getPositiveButtonText(), bean.getPositiveOnClickListener())
                .show();
    }

    /**
     * 移除fragment
     */
    void removeFragment() {
        if (manager != null && pFragment != null) {
            L.logE(manager.getFragments().toString());
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(pFragment)
                    .commitAllowingStateLoss();
            L.logE(manager.getFragments().toString());
        }
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 权限申请结果
     */
    void onRequestPermissionResult(RequestPermissionResult result) {
        if (builder.onRequestPermissionCallback != null) {
            builder.onRequestPermissionCallback.onRequestPermissionResult(result);
        }
    }

    /**
     * 替换权限，执行申请权限
     *
     * @param permissions
     */
    public void replacePermissionsAndShow(String[] permissions) {
        builder.requestPermissions = permissions;
        show();
    }

    /**
     * 执行申请权限
     */
    public void show() {
        executeRequestPermission(builder);
    }

    public static class Builder {
        WeakReference<Object> weakReference;
        String[] requestPermissions;
        OnRequestPermissionCallback onRequestPermissionCallback;

        /**
         * @param object 需要{@link FragmentActivity} 或者 {@link Fragment}
         */
        public Builder(Object object) {
            this.weakReference = new WeakReference<>(object);
        }

        /**
         * 需要检测的权限组，当至少有一个权限没有通过时都会想系统申请权限
         *
         * @param requestPermissions
         * @return
         */
        public Builder requestPermissions(String[] requestPermissions) {
            this.requestPermissions = requestPermissions;
            return this;
        }

        /**
         * 权限请求结果回调
         *
         * @param onRequestPermissionCallback
         * @return
         */
        public Builder onRequestPermissionCallback(OnRequestPermissionCallback onRequestPermissionCallback) {
            this.onRequestPermissionCallback = onRequestPermissionCallback;
            return this;
        }

        public P show() {
            return new P(this);
        }
    }

    public interface OnRequestPermissionCallback {
        /**
         * 申请权限结果
         *
         * @param result
         */
        void onRequestPermissionResult(RequestPermissionResult result);
    }

}
