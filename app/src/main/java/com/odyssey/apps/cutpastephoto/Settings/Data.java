package com.odyssey.apps.cutpastephoto.Settings;


import com.odyssey.apps.cutpastephoto.R;

/**
 * Created by admin on 2018-02-06.
 */

public class Data {




    // Singleton Structure
    private static final Data instance = new Data();
    //private constructor to avoid client applications to use constructor
    private Data(){}
    public static Data getSharedInstance(){
        return instance;
    }

    //Settings View
    // Icons
    private int[] icons = new int[]{

            0,
            R.drawable.cart,
            R.drawable.settinghelp,
            R.drawable.settingfriends,
            R.drawable.settingsupport,
            R.drawable.settingshare,
            R.drawable.settingstar,
            R.drawable.more_apps,
            0,
            R.drawable.settingfacebook,
            R.drawable.settingtwitter,
            R.drawable.settinginstagram,
            R.drawable.settinglinkedin,
            0

    };
    public int getNumberOfIcons(){
        return icons.length ;
    }

    public int[] getIcons(){
        return icons;
    }


    //Titles
    private int[] titles = new int[]{

//            Resources.getSystem().getString(R.string.HelpSettings),
//            Resources.getSystem().getString(R.string.Store),
//            Resources.getSystem().getString(R.string.Help),
//            Resources.getSystem().getString(R.string.TellAFriend),
//            Resources.getSystem().getString(R.string.ContactUs),
//            Resources.getSystem().getString(R.string.ShareApp),
//            Resources.getSystem().getString(R.string.RateUs),
//
//
//            Resources.getSystem().getString(R.string.FollowUs),
//            Resources.getSystem().getString(R.string.Facebook),
//            Resources.getSystem().getString(R.string.Twitter),
//            Resources.getSystem().getString(R.string.Instagram),
//            Resources.getSystem().getString(R.string.LinkedIn),


            R.string.HelpSettings, //Header
            R.string.Store,
            R.string.Help,
            R.string.TellAFriend,
            R.string.ContactUs,
            R.string.ShareApp,
            R.string.RateUs,
            R.string.MoreApps,

            R.string.FollowUs, //Header
            R.string.Facebook,
            R.string.Twitter,
            R.string.Instagram,
            R.string.LinkedIn,

            R.string.CompanyName // Footer

    };


    public int getNumberOfRow(){
        return titles.length ;
    }

    public int[] getTitles(){
        return titles;
    }


}
