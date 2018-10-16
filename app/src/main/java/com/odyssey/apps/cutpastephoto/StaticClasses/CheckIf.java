package com.odyssey.apps.cutpastephoto.StaticClasses;

import android.content.Context;

import com.odyssey.apps.cutpastephoto.IAP.Data;

/**
 * Created by admin on 2018-02-20.
 */

public class CheckIf {

    /*
        Note : Product List  . .

        background,
        sticker,
        admob,
        font,
        color,
        all

     */



    public static boolean isPurchased(String product , Context onContext){
        switch (product){
            case "background" :
                product = Data.getSharedInstance().BACKGROUND;
                break;
            case "sticker" :
                product = Data.getSharedInstance().STICKER;
                break;
            case "admob" :
                product = Data.getSharedInstance().ADMOB;
                break;
            case "font" :
                product = Data.getSharedInstance().FONT;
                break;
            case "color" :
                product = Data.getSharedInstance().FONTCOLOR;
                break;
            case "crop" :
                product = Data.getSharedInstance().CROPLAYER;
                break;
            case "all" :
                product = Data.getSharedInstance().PRO;
                break;
        }
        return UserDefault.check( product , onContext) ;
    }
}
