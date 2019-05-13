package com.d.p.bean;

import android.content.DialogInterface;

/**
 * @author dcm
 * @since 2019/5/9
 */

public class AlertBean {
    /**
     * 提示用户，权限作用提示框的标题
     */
    private String alertTitle;
    /**
     * 提示用户，权限作用提示框的内容
     */
    private String alertMessage;
    /**
     * 提示用户，权限作用提示框的取消按钮的字样
     */
    private String negativeButtonText;
    /**
     * 提示用户，权限作用提示框的取消按钮的回调（默认点击操作为，关闭对话框）
     */
    private DialogInterface.OnClickListener negativeOnClickListener;
    /**
     * 提示用户，权限作用提示框的确定按钮的字样
     */
    private String positiveButtonText;
    /**
     * 提示用户，权限作用提示框的确定按钮的回调（默认点击操作为，申请权限组权限）
     */
    private DialogInterface.OnClickListener positiveOnClickListener;

    public AlertBean(String alertTitle, String alertMessage, String negativeButtonText, DialogInterface.OnClickListener negativeOnClickListener, String positiveButtonText, DialogInterface.OnClickListener positiveOnClickListener) {
        this.alertTitle = alertTitle;
        this.alertMessage = alertMessage;
        this.negativeButtonText = negativeButtonText;
        this.negativeOnClickListener = negativeOnClickListener;
        this.positiveButtonText = positiveButtonText;
        this.positiveOnClickListener = positiveOnClickListener;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    public DialogInterface.OnClickListener getNegativeOnClickListener() {
        return negativeOnClickListener;
    }

    public void setNegativeOnClickListener(DialogInterface.OnClickListener negativeOnClickListener) {
        this.negativeOnClickListener = negativeOnClickListener;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public DialogInterface.OnClickListener getPositiveOnClickListener() {
        return positiveOnClickListener;
    }

    public void setPositiveOnClickListener(DialogInterface.OnClickListener positiveOnClickListener) {
        this.positiveOnClickListener = positiveOnClickListener;
    }

    @Override
    public String toString() {
        return "AlertBean{" +
                "alertTitle='" + alertTitle + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                ", negativeButtonText='" + negativeButtonText + '\'' +
                ", negativeOnClickListener=" + negativeOnClickListener +
                ", positiveButtonText='" + positiveButtonText + '\'' +
                ", positiveOnClickListener=" + positiveOnClickListener +
                '}';
    }
}
