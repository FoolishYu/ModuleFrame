package com.module.lib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;

import java.util.List;

/**
 * 应用工具类
 *
 * @author zhouchunjie
 * @date 2017/10/30
 */
public final class AppUtil {

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return Utils.getApplication().getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 判断App是否安装
     *
     * @param action   action
     * @param category category
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = Utils.getApplication().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断App是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String packageName) {
        return !StringUtil.isSpace(packageName) && getLaunchAppIntent(packageName) != null;
    }

    /**
     * 打开App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (StringUtil.isSpace(packageName)) return;
        Utils.getApplication().startActivity(getLaunchAppIntent(packageName));
    }

    /**
     * 打开App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
        if (StringUtil.isSpace(packageName)) return;
        activity.startActivityForResult(getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 打开APP设置
     */
    public static void openAppDetailsSettings(final String packageName) {
        if (StringUtil.isSpace(packageName)) return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApplication().startActivity(intent);
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getVersionName(final String packageName) {
        if (StringUtil.isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取VersionCode
     *
     * @return VersionCode
     */
    public static int getVersionCode(final String packageName) {
        if (StringUtil.isSpace(packageName)) return -1;
        try {
            PackageManager pm = Utils.getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断App是否是系统应用
     *
     * @param packageName 包名
     * @return 是/否
     */
    public static boolean isSystemApp(final String packageName) {
        if (StringUtil.isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApplication().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断App是否处于前台
     *
     * @return 是/否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager) Utils.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApplication().getPackageName());
            }
        }
        return false;
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        ActivityUtil.getInstance().finishAll();
        System.exit(0);
    }
}