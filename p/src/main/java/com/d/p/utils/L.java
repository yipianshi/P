package com.d.p.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 打印工具类
 */
public class L {
    private static boolean isDebug = true;
    private static boolean isPrintLog = true;
    private static String TAG = "yipianshi";

    private static final int LOG_TYPE_INFO = 0;
    private static final int LOG_TYPE_WORNING = 1;
    private static final int LOG_TYPE_ERROR = 2;
    private static final boolean isPrintMethodInfo = false;

    public static void setIsDebug(boolean b) {
        isDebug = b;
    }

    public static void setIsPrintLog(boolean b) {
        isPrintLog = b;
    }

    public static void logPingManagerMsg(String msg) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String describe = "";
        if (isPrintMethodInfo) {
            describe = "\n类名: " + ste.getFileName() +
                    "\n行号: " + ste.getLineNumber() +
                    "\n方法名: " + ste.getMethodName() +
                    "\n时间: " + sdf.format(date) +
                    "\n毫秒值: " + date.getTime();
        }
        log("PingManager", msg + describe, LOG_TYPE_INFO);
    }

    public static void logReceiverMsg(String msg) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String describe = "";
        if (isPrintMethodInfo) {
            describe = "\n类名: " + ste.getFileName() +
                    "\n行号: " + ste.getLineNumber() +
                    "\n方法名: " + ste.getMethodName() +
                    "\n时间: " + sdf.format(date) +
                    "\n毫秒值: " + date.getTime();
        }
        log("receiverMessageTask", msg + describe, LOG_TYPE_INFO);
    }

    public static void logSeatsStateMsg(String msg) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String describe = "";
        if (isPrintMethodInfo) {
            describe = "\n类名: " + ste.getFileName() +
                    "\n行号: " + ste.getLineNumber() +
                    "\n方法名: " + ste.getMethodName() +
                    "\n时间: " + sdf.format(date) +
                    "\n毫秒值: " + date.getTime();
        }
        log("SeatsState", msg + describe, LOG_TYPE_INFO);
    }

    /**
     * 获取当前类路径和调用的行号, 用于快速定位代码
     *
     * @param content 内容
     */
    public static void log(String content) {
        logI(content);
    }

    public static void logI(String msg) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String describe = "";
        if (isPrintMethodInfo) {
            describe = "\n类名: " + ste.getFileName() +
                    "\n行号: " + ste.getLineNumber() +
                    "\n方法名: " + ste.getMethodName() +
                    "\n时间: " + sdf.format(date) +
                    "\n毫秒值: " + date.getTime();
        }
        log(TAG, msg + describe, LOG_TYPE_INFO);
    }

    public
    static void logW(String msg) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String describe = "";
        if (isPrintMethodInfo) {
            describe = "\n类名: " + ste.getFileName() +
                    "\n行号: " + ste.getLineNumber() +
                    "\n方法名: " + ste.getMethodName() +
                    "\n时间: " + sdf.format(date) +
                    "\n毫秒值: " + date.getTime();
        }

        log(TAG, msg + describe, LOG_TYPE_WORNING);
    }

    public static void logE(String msg) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String describe = "";
        if (isPrintMethodInfo) {
            describe = "\n类名: " + ste.getFileName() +
                    "\n行号: " + ste.getLineNumber() +
                    "\n方法名: " + ste.getMethodName() +
                    "\n时间: " + sdf.format(date) +
                    "\n毫秒值: " + date.getTime();
        }
        log(TAG, msg + describe, LOG_TYPE_ERROR);
    }

    private static void log(String tag, String msg, int type) {
        StringBuilder builder = getContent(msg);
        switch (type) {
            case LOG_TYPE_INFO:
                i(tag, builder.toString());
                break;
            case LOG_TYPE_WORNING:
                w(tag, builder.toString());
                break;
            case LOG_TYPE_ERROR:
                e(tag, builder.toString());
                break;
        }
    }

    private static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    private static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    private static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    private static StringBuilder getContent(String content) {
        StringBuilder builder = new StringBuilder();
        String[] splits = content.split("\\n");
        for (int i = 0; i < splits.length; i++) {
            splits[i] = " ║  " + splits[i] + "     ";
            builder.append(splits[i]).append("\n");
        }
        int count = getMaxLen(splits);
        String firstLetters = "╔";
        String lastLetters = "╚";

        String letters = "";
        for (int i = 0; i < (count - 2); i++) {
            letters += "═";
        }
        firstLetters = firstLetters + letters + "╗";
        lastLetters = lastLetters + letters + "╝";
        builder.insert(0, firstLetters + "\n");
        builder.append(" ").append(lastLetters);
        return builder;
    }

    /**
     * 获取最长的长度
     *
     * @param splits
     * @return
     */
    private static int getMaxLen(String[] splits) {
        int maxLen = 0;
        int tempLen;
        for (String split : splits) {
            if (TextUtils.isEmpty(split)) {
                continue;
            }
            tempLen = getLen(split);
            if (tempLen > maxLen) {
                maxLen = tempLen;
            }
        }
        return maxLen;
    }

    /**
     * 获取符串的长度，中文占1，其他占0.5
     * 由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
     * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。
     * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。
     * 如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。
     * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。
     *
     * @return
     */
    private static int getLen(String text) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        double length = 0;
        for (int i = 0; i < text.length(); i++) {
            int ascii = Character.codePointAt(text, i);
            if (ascii >= 0 && ascii <= 255)
                length += 0.5;
            else
                length += 1;
        }
        return (int) Math.ceil(length);
    }

    /**
     * 获取当前时间的时分秒
     *
     * @return 当前时间的时分秒格式的字符串
     */
    public static String current() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
//        return year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒";
        return String.format("%1$s时%2$s分%3$s秒",  hour, minute, second);
    }
}
