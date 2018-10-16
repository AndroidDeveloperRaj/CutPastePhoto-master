package com.odyssey.apps.cutpastephoto.StaticClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by admin on 2018-02-17.
 */

public class NotificationCenter {
    public static void broadcast(String forEvent , Context onContext){
        Intent intent = new Intent(forEvent);
        LocalBroadcastManager.getInstance(onContext).sendBroadcast(intent);
    }

    public static void addReceiver(String forEvent , BroadcastReceiver receiver , Context onContext){
        LocalBroadcastManager.getInstance(onContext).registerReceiver(receiver,
                new IntentFilter(forEvent));
    }


}
