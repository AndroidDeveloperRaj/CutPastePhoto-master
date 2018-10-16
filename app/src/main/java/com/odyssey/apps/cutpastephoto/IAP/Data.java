package com.odyssey.apps.cutpastephoto.IAP;


import com.odyssey.apps.cutpastephoto.R;

/**
 * Created by admin on 2018-02-17.
 */




public class Data {
    // Singleton Structure
    private static final Data instance = new Data();
    //private constructor to avoid client applications to use constructor
    private Data(){}
    public static Data getSharedInstance(){
        return instance;
    }


    // Icons
    private int[] icons = new int[]{


            R.drawable.settingunlockbackgrounds,
            R.drawable.settingunlockstickers,
            R.drawable.settingunlockfonts,
            R.drawable.settingunlockfontcolors,
            R.drawable.settingofflineuseageremoveads,
            R.drawable.ratio

    };

    public int getNumberOfIcons(){
        return icons.length ;
    }

    public int[] getIcons(){
        return icons;
    }

    //Titles
    private int[] titles = new int[]{


            R.string.Backgrounds,
            R.string.Stickers,
            R.string.TextFontsTitle,
            R.string.TextColorsTitle,
            R.string.AdvertisementsTitle,
            R.string.CropFrameTitle

    };

    public int getTitlesSize(){
        return titles.length ;
    }

    public int[] getTitles(){
        return titles;
    }

    //Sub Titles
    private int[] subTitles = new int[]{


            R.string.UnlockBackground,
            R.string.UnlockStickers,
            R.string.UnlockFonts,
            R.string.UnlockColors,
            R.string.RemoveAdvertisements,
            R.string.UnlockCropFrame
    };

    public int getSubTitlesSize(){
        return subTitles.length ;
    }

    public int[] getSubTitles(){
        return subTitles;
    }

    /*

            "cutpaste.inapp.1",
            "cutpaste.inapp.2",
            "cutpaste.inapp.3",
            "cutpaste.inapp.4",
            "cutpaste.inapp.5"


     */

    //General Product IDs
    public String PRO = "cutpaste.inapp.1";
    public String ADMOB = "cutpaste.inapp.2";
    public String BACKGROUND= "cutpaste.inapp.3";
    public String STICKER = "cutpaste.inapp.4";
    public String FONT = "cutpaste.inapp.5";
    public String FONTCOLOR = "cutpaste.inapp.6";
    public String CROPLAYER = "cutpaste.inapp.7";

    private String[] generalProductIDs = new String[]{

            BACKGROUND,
            STICKER,
            FONT,
            FONTCOLOR,
            ADMOB,
            CROPLAYER
    };

    public int getGeneralProductIDsSize(){
        return generalProductIDs.length ;
    }
    public String[] getGeneralProductIDs(){
        return generalProductIDs;
    }
    public String getGeneralProductIDAt(int index ){
        return generalProductIDs[index];
    }
    public String getProProductID(){
        return PRO;
    }

    // Demo Price List
    private String[] demoPriceList = new String[]{
            "$1.99",
            "$1.99",
            "$0.99",
            "$0.99",
            "$2.99",
            "$1.99"
    };
    public int getDemoPriceListSize(){
        return demoPriceList.length ;
    }

    public String[] getDemoPriceList(){
        return demoPriceList;
    }





}
