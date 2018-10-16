package com.odyssey.apps.cutpastephoto.AddText;

import android.graphics.Color;

/**
 * Created by admin on 2018-01-29.
 */

public class Data {

    // Singleton Structure
    private static final Data instance = new Data();
    //private constructor to avoid client applications to use constructor
    private Data(){}
    public static Data getSharedInstance(){
        return instance;
    }


    // Colors . .
    private int[] colors = new int[]{



            Color.rgb(255,255,255),
            Color.rgb(223,223,223),
            Color.rgb(191,191,191),
            Color.rgb(158,158,158),
            Color.rgb(127,127,127),
            Color.rgb(107,107,107),
            Color.rgb(30,30,30),
            Color.rgb(0,0,0),
            Color.rgb(254, 0, 0),
            Color.rgb(254,127,10),
            Color.rgb(0,0,254),
            Color.rgb(0,133,66),
            Color.rgb(191,191,255),
            Color.rgb(191,223,255),
            Color.rgb(191,255,255),
            Color.rgb(191,255,223),

            Color.rgb(91,91,91),
            Color.rgb(79,79,79),
            Color.rgb(63,63,63),
            Color.rgb(48,48,48),



            Color.rgb(191,255,191),
            Color.rgb(223,255,191),
            Color.rgb(255,255,191),
            Color.rgb(255,223,191),
            Color.rgb(255,191,191),
            Color.rgb(255,191,223),
            Color.rgb(255,191,255),
            Color.rgb(223,191,255),
            Color.rgb(127,127,255),
            Color.rgb(127,191,255),
            Color.rgb(127,255,255),
            Color.rgb(191,255,191),
            Color.rgb(127,255,127),
            Color.rgb(191,255,127),
            Color.rgb(255,255,127),
            Color.rgb(255,191,127),
            Color.rgb(255,127,127),
            Color.rgb(255,127,191),
            Color.rgb(255,127,255),
            Color.rgb(191,127,255),
            Color.rgb(63,63,255),
            Color.rgb(63,159,255),
            Color.rgb(63,255,255),
            Color.rgb(63,255,159),
            Color.rgb(63,255,63),
            Color.rgb(159,255,63),
            Color.rgb(255,255,63),
            Color.rgb(255,159,63),
            Color.rgb(255,63,63),
            Color.rgb(255,63,159),
            Color.rgb(255,63,255),
            Color.rgb(159,63,255),
            Color.rgb(0,0,255),
            Color.rgb(0,127,255),
            Color.rgb(0,255,255),
            Color.rgb(0,255,127),
            Color.rgb(0,255,0),
            Color.rgb(127,255,0),
            Color.rgb(255,255,0),
            Color.rgb(255,127,0),
            Color.rgb(255,0,0),
            Color.rgb(255,0,127),
            Color.rgb(255,0,255),
            Color.rgb(127,0,255),
            Color.rgb(0,0,191),
            Color.rgb(0,95,191),
            Color.rgb(0,191,191),
            Color.rgb(0,191,95),
            Color.rgb(0,191,0),
            Color.rgb(95,191,0),
            Color.rgb(191,191,0),
            Color.rgb(191,95,0),
            Color.rgb(191,0,0),
            Color.rgb(191,0,95),
            Color.rgb(191,0,191),
            Color.rgb(95,0,191),
            Color.rgb(0,0,127),
            Color.rgb(0,63,127),
            Color.rgb(0,127,127),
            Color.rgb(0,127,63),
            Color.rgb(0,127,0),
            Color.rgb(63,127,0),
            Color.rgb(127,127,0),
            Color.rgb(127,63,0),
            Color.rgb(127,0,0),
            Color.rgb(127,0,63),
            Color.rgb(127,0,127),
            Color.rgb(63,0,127),
            Color.rgb(0,0,63),
            Color.rgb(0,31,63),
            Color.rgb(0,63,63),
            Color.rgb(0,63,31),
            Color.rgb(0,63,0),
            Color.rgb(31,63,0),
            Color.rgb(63,63,0),
            Color.rgb(63,31,0),
            Color.rgb(63,0,0),
            Color.rgb(63,0,31),
            Color.rgb(63,0,63),
            Color.rgb(31,0,63)
    };
    public int[] getColors(){
        return colors;
    }
    public int getNumberOfColors(){
        return colors.length;
    }


    //Fonts . .
    private String[] fonts = new String[] {
            "ostrich-regular.ttf",
            "OstrichSans-Black.otf",
            "OstrichSans-Bold.otf",
            "OstrichSans-Heavy.otf",
            "OstrichSans-Light.otf",
            "OstrichSans-Medium.otf",
            "OstrichSansDashed-Medium.otf",
            "OstrichSansRounded-Medium.otf",
            "Windsong.ttf",
            "11S0BLTI.TTF",
            "12tonsushi.ttf",
            "8bitlim.ttf",
            "ABBERANC.TTF",
            "ABEAKRG.TTF",
            "ADD-JAZZ.TTF",
            "AIR_____.TTF",
            "ALEXA.TTF",
            "ALIEESBI.TTF",
            "ALIEESB_.TTF",
            "ALIEESI_.TTF",
            "ALIEES__.TTF",
            "ALIEN5.TTF",
            "ANDOVER_.TTF",
            "ARCHBLC_.TTF",
            "ARMOPB__.TTF",
            "ATOMIC__.TTF",
            "ATROX.TTF",
            "AZRAEL__.TTF",
            "Aaargh.ttf",
            "Adventure.ttf",
            "Aliquam.ttf",
            "AliquamREG.ttf",
            "AliquamRit.ttf",
            "AllCaps.ttf",
            "Alpha Thin.ttf",
            "AnkeHand.ttf",
            "Artbrush.ttf",
            "K90.TTF",
           	"K91.TTF",
           	"K92.TTF",
          	"K93.TTF",
           	"K94.TTF",
            "K95.TTF",
           	"K96.TTF"




    };

    public String[] getFonts(){
        return fonts;
    }
    public int getNumberOfFonts(){
        return fonts.length;
    }


}
