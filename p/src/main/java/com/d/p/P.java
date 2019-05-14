package com.d.p;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.d.p.bean.AlertBean;
import com.d.p.bean.RequestPermissionResult;
import com.d.p.utils.L;

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
public class P implements Parcelable {
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
            L.logE("builder.weakReference.get() == null");
            return;
        }
        if (manager == null) {
            manager = getFragmentManager(object);
        }
        if (manager == null) {
            L.logE("FragmentManager == null");
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
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(pFragment)
                    .commitAllowingStateLoss();
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

    public static class Builder implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        protected Builder(Parcel in) {
        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel source) {
                return new Builder(source);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
    }

    public interface OnRequestPermissionCallback {
        /**
         * 申请权限结果
         *
         * @param result
         */
        void onRequestPermissionResult(RequestPermissionResult result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.builder, flags);
    }

    protected P(Parcel in) {
        this.builder = in.readParcelable(Builder.class.getClassLoader());
    }

    public static final Parcelable.Creator<P> CREATOR = new Parcelable.Creator<P>() {
        @Override
        public P createFromParcel(Parcel source) {
            return new P(source);
        }

        @Override
        public P[] newArray(int size) {
            return new P[size];
        }
    };
}
