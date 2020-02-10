package com.example.chat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MsgFlashService extends Service {
    private CallBack callBack;
    public MsgFlashService(CallBack callBack) {
        this.callBack = callBack;
        Log.d(TAG, "MsgFlashService: create");
    }
    public MsgFlashService(){
        Log.d(TAG, "MsgFlashService: create 1");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    private static final String TAG = "MsgFlashService";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startService(new Intent(MsgFlashService.this, MsgActivity.InnerService));
        Log.d(TAG, "onStartCommand: ");
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int three = 3*1000;
        long triggerAtTime = SystemClock.elapsedRealtime()+three;
        Intent intent1 = new Intent(this,MsgFlashService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,intent1,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        if(callBack == null){
            Log.d(TAG, "onStartCommand: 456");
           // CallBack callBack = new MsgActivity();
            this.callBack = callBack;
        }
        if(callBack != null){
            Log.d(TAG, "onStartCommand: callback");
            callBack.myCallbackMethod();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void callMethod(){
        callBack.myCallbackMethod();
    }
    private MyBinder myBinder = new MyBinder();
    class MyBinder extends  Binder{
        public void refresh(CallBack callBack){
            callBack.myCallbackMethod();
        }
    }

}
