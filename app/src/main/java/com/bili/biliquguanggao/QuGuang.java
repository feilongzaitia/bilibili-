package com.bili.biliquguanggao;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class QuGuang implements IXposedHookLoadPackage {

    public Context Main_context;
    public static String TAG = "xingtong";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("tv.danmaku.bili")){
            Class SplashClazz = XposedHelpers.findClassIfExists("tv.danmaku.bili.ui.splash.ad.model.Splash",loadPackageParam.classLoader);
            Class SourceContentClazz = XposedHelpers.findClassIfExists("com.bilibili.adcommon.basic.model.SourceContent", loadPackageParam.classLoader);
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Main_context = (Context) param.args[0];
                }
            });

            XposedHelpers.findAndHookMethod("tv.danmaku.bili.ui.splash.ad.page.x", loadPackageParam.classLoader, "a", SplashClazz, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0] = null;
                    super.beforeHookedMethod(param);
                    Log.d(TAG,"hook1成功");
                    Intent intent = new Intent("danmaku.bili.AD");
                    intent.putExtra("type","kaiping");
                    Main_context.sendBroadcast(intent);

                }
            });
            XposedHelpers.findAndHookMethod("org.json.JSONObject", loadPackageParam.classLoader, "optInt",
                    String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if (param.args[0].equals("code") && StackUtils.isCallingFromupdate()){
                                Log.d(TAG,"hook2成功");
                                param.setResult(304);
                                Intent intent = new Intent("danmaku.bili.AD");
                                intent.putExtra("type","gengxing");
                                Main_context.sendBroadcast(intent);
                            }
                            super.afterHookedMethod(param);
                        }
                    });
            XposedHelpers.findAndHookMethod("com.bilibili.ad.adview.videodetail.b", loadPackageParam.classLoader, "a", SourceContentClazz, new XC_MethodHook() {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG, "layout:" + (int)methodHookParam.getResult());
                    if ((int)methodHookParam.getResult() != 105){
                        methodHookParam.setResult(105);
                        Intent intent = new Intent("danmaku.bili.AD");
                        intent.putExtra("type","charu");
                        Main_context.sendBroadcast(intent);
                    }
                    super.afterHookedMethod(methodHookParam);
                }
            });



        }
    }
}
