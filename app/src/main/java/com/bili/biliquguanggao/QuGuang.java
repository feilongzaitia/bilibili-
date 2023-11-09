package com.bili.biliquguanggao;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class QuGuang implements IXposedHookLoadPackage {


    public static String TAG = "xingtong";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("tv.danmaku.bili")){
            Class SplashClazz = XposedHelpers.findClassIfExists("tv.danmaku.bili.ui.splash.ad.model.Splash",loadPackageParam.classLoader);
            XposedHelpers.findAndHookMethod("tv.danmaku.bili.ui.splash.ad.page.x", loadPackageParam.classLoader, "a", SplashClazz, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0] = null;
                    super.beforeHookedMethod(param);
                    Log.d(TAG,"hook1成功");
                }
            });
            XposedHelpers.findAndHookMethod("org.json.JSONObject", loadPackageParam.classLoader, "optInt",
                    String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if (param.args[0].equals("code") && StackUtils.isCallingFromupdate()){
                                Log.d(TAG,"hook2成功");
                                param.setResult(304);
                            }
                            super.afterHookedMethod(param);
                        }
                    });


        }
    }
}
