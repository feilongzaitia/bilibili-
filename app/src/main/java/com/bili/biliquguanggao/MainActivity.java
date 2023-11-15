package com.bili.biliquguanggao;



import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements AdCancleBroadcastReceiver.Handle{
    private AdCancleBroadcastReceiver receiver;
    private TextView kaiping_count;
    private TextView charu_count;
    private int kaipingcount=0;
    private int charucount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kaiping_count = findViewById(R.id.kaiping_count);
        charu_count = findViewById(R.id.charu_count);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("danmaku.bili.AD");
        receiver = new AdCancleBroadcastReceiver();
        registerReceiver(receiver,filter);
        receiver.setMhandle(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void handle(int type) {
        if (type == 0){
            kaipingcount++;
            kaiping_count.setText(kaipingcount+"");
        }
        else
            if (type==1){
                charucount++;
                charu_count.setText(charucount+"");
            }
    }

}