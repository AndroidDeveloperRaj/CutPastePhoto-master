package com.odyssey.apps.cutpastephoto.StaticClasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2018-02-17.
 */

public class UserDefault {

    public static void set(boolean state , String against , Context onContext ){
        SharedPreferences preferences = onContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(against,state);
        editor.commit();
    }

    public static  boolean check(String against , Context onContext){
        SharedPreferences preferences = onContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getBoolean(against,false );
    }
}
