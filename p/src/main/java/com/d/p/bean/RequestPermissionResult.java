package com.d.p.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class RequestPermissionResult implements Parcelable {
    /**
     * {@link RequestPermissionResult#ALL_PERMISSION_PASS}、
     * {@link RequestPermissionResult#SOME_PERMISSION_PASS}、
     * {@link RequestPermissionResult#NO_PERMISSION_PASS}
     */
    private int state;
    //被拒绝的权限
    private String[] deniedPermissions;
    //被通过的权限
    private String[] passPermissions;

    /**
     * 所有权限通过
     */
    public static final int ALL_PERMISSION_PASS = 0;
    /**
     * 只有一部分权限通过
     */
    public static final int SOME_PERMISSION_PASS = 1;
    /**
     * 所有权限都没通过
     */
    public static final int NO_PERMISSION_PASS = 2;

    public RequestPermissionResult(int state, String[] deniedPermissions, String[] passPermissions) {
        this.state = state;
        this.deniedPermissions = deniedPermissions;
        this.passPermissions = passPermissions;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String[] getDeniedPermissions() {
        return deniedPermissions;
    }

    public void setDeniedPermissions(String[] deniedPermissions) {
        this.deniedPermissions = deniedPermissions;
    }

    public String[] getPassPermissions() {
        return passPermissions;
    }

    public void setPassPermissions(String[] passPermissions) {
        this.passPermissions = passPermissions;
    }

    @Override
    public String toString() {
        return "RequestPermissionResult{" +
                "state=" + state +
                ", deniedPermissions=" + Arrays.toString(deniedPermissions) +
                ", passPermissions=" + Arrays.toString(passPermissions) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state);
        dest.writeStringArray(this.deniedPermissions);
        dest.writeStringArray(this.passPermissions);
    }

    protected RequestPermissionResult(Parcel in) {
        this.state = in.readInt();
        this.deniedPermissions = in.createStringArray();
        this.passPermissions = in.createStringArray();
    }

    public static final Parcelable.Creator<RequestPermissionResult> CREATOR = new Parcelable.Creator<RequestPermissionResult>() {
        @Override
        public RequestPermissionResult createFromParcel(Parcel source) {
            return new RequestPermissionResult(source);
        }

        @Override
        public RequestPermissionResult[] newArray(int size) {
            return new RequestPermissionResult[size];
        }
    };
}