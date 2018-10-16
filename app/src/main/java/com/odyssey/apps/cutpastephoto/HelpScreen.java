package com.odyssey.apps.cutpastephoto;

/**
 * Created by OdysseyApps on 2/15/18.
 */

public final class HelpScreen {
    public static int imageno=1;
    public static void changeScreen(){
        imageno++;
        if(imageno==7||imageno==8)changeScreen();
    }
}
