package com.odyssey.apps.cutpastephoto.Settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import com.odyssey.apps.cutpastephoto.R;


/**
 * Created by admin on 2018-02-07.
 */

public class SettingModel {

    // Singleton Structure
    private static final SettingModel instance = new SettingModel();
    //private constructor to avoid client applications to use constructor
    private SettingModel(){

    }
    public static SettingModel getSharedInstance(){
        return instance;
    }


    public void pushURL(Context context , String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    void shareURL(Context context, String url){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.CutPastePhoto));
            String sAux = "\n"+context.getString(R.string.ShareURLMessage)+"\n\n";
            sAux = sAux + url + "\n";
            intent. putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(intent, "Share"));
        }
        catch(Exception e) {
            //e.toString();
        }
    }

    public void contactUS(Context context) {

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        String str = null;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.d("OpenFeedback", e.getMessage());
        }

        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.ContactUsTitle));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.ContactUsBody)+"\n\n\n----------------------------------\n "+ context.getString(R.string.DeviceOS) +"\n "+ context.getString(R.string.version)+
               " "+ Build.VERSION.RELEASE + "\n "+ context.getString(R.string.AppVersion) +" "+ str + "\n "+ context.getString(R.string.DeviceBrand)+" "+ Build.BRAND +
                "\n "+ context.getString(R.string.DeviceModel)+" "+ Build.MODEL + "\n "+ context.getString(R.string.DeviceManufacturer) +" "+ Build.MANUFACTURER);

        intent.setData(Uri.parse("mailto:support@odysseyapps.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);

    }



    public void tellAFriend(Context context, String url ) {

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        String str = null;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.d("OpenFeedback", e.getMessage());
        }

        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.CutPastePhoto));
        String body = new String("<p>"+context.getString(R.string.TellAFriendMessage)+" <p/> <a> "+url+" </a>");
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
        intent.putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml(body));

        intent.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);

    }
}

