package com.odyssey.apps.cutpastephoto.Admobs;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.R;


/**
 * Created by admin on 2018-02-18.
 */

public class Advertisement {

    // Singleton Structure
    private static final Advertisement instance = new Advertisement();
    //private constructor to avoid client applications to use constructor
    private Advertisement(){}
    public static Advertisement getSharedInstance(){
        return instance;
    }


    //Ad Info
    private final String ADMOB_AD_UNIT_ID = "ca-app-pub-9079664095868787/9399098010";/*"ca-app-pub-3940256099942544/2247696110";*/
    private final String ADMOB_APP_ID = "ca-app-pub-9079664095868787~7009217320";/*"ca-app-pub-3940256099942544~3347511713";*/


    public String getNativeAdvanceAdUnitID(){
        return  ADMOB_AD_UNIT_ID;
    }

    public String getNativeAdvanceAdAppID(){
        return  ADMOB_APP_ID;
    }

    // App Install Ad
    public void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView unifiedNativeAdViewadView) {


        unifiedNativeAdViewadView.setHeadlineView(unifiedNativeAdViewadView.findViewById(R.id.appinstall_headline));
        unifiedNativeAdViewadView.setBodyView(unifiedNativeAdViewadView.findViewById(R.id.appinstall_body));
        unifiedNativeAdViewadView.setCallToActionView(unifiedNativeAdViewadView.findViewById(R.id.appinstall_call_to_action));
        unifiedNativeAdViewadView.setIconView(unifiedNativeAdViewadView.findViewById(R.id.appinstall_app_icon));
        unifiedNativeAdViewadView.setPriceView(unifiedNativeAdViewadView.findViewById(R.id.appinstall_price));
        unifiedNativeAdViewadView.setStarRatingView(unifiedNativeAdViewadView.findViewById(R.id.appinstall_stars));



        try{

            // Some assets are guaranteed to be in every NativeAppInstallAd.
        ((TextView) unifiedNativeAdViewadView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) unifiedNativeAdViewadView.getBodyView()).setText(nativeAd.getBody());
        ((Button) unifiedNativeAdViewadView.getCallToActionView()).setText(nativeAd.getCallToAction());
        ((ImageView) unifiedNativeAdViewadView.getIconView()).setImageDrawable(
                nativeAd.getIcon().getDrawable());

        } catch (Exception e){

        }



        // These assets aren't guaranteed to be in every NativeAppInstallAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getPrice() == null) {
            unifiedNativeAdViewadView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdViewadView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdViewadView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStarRating() == null) {
            unifiedNativeAdViewadView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) unifiedNativeAdViewadView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            unifiedNativeAdViewadView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        unifiedNativeAdViewadView.setNativeAd(nativeAd);
    }


    public void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {

        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            //adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
    }


}
