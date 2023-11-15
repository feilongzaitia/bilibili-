package com.bili.biliquguanggao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AdCancleBroadcastReceiver extends BroadcastReceiver {
    private Handle mhandle;
    public interface Handle{
        public void handle(int type);
    }

    public void setMhandle(Handle handle){
        this.mhandle = handle;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null){
            String type = intent.getStringExtra("type");
            if (type.equals("kaiping")){
                mhandle.handle(0);
            }
            else
                if (type.equals("charu")){
                    mhandle.handle(1);
                }
                else
                    Toast.makeText(context,"成功绕过一次更新检测",Toast.LENGTH_SHORT).show();
        }
    }
}
