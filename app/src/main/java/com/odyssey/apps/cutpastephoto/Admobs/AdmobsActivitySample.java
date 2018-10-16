package com.odyssey.apps.cutpastephoto.Admobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.R;


public class AdmobsActivitySample extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admobs);


        // Admob
        MobileAds.initialize(this, Advertisement.getSharedInstance().getNativeAdvanceAdAppID());
        AdLoader adLoader = new AdLoader.Builder(this, Advertisement.getSharedInstance().getNativeAdvanceAdUnitID())
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the app install ad.
                        //Toast.makeText(AdmobsActivitySample.this, "Ad App Install Loaded", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.AAFrameAd);
                       UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                                .inflate(R.layout.ad_app_install, null);
                       Advertisement.getSharedInstance().populateUnifiedNativeAdView(unifiedNativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                })
                .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                    @Override
                    public void onContentAdLoaded(NativeContentAd contentAd) {
                        // Show the content ad.
                        //Toast.makeText(AdmobsActivitySample.this, "Ad Content Loaded", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.AAFrameAd2);
                        NativeContentAdView adView = (NativeContentAdView) getLayoutInflater()
                                .inflate(R.layout.ad_content, null);
                        Advertisement.getSharedInstance().populateContentAdView(contentAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.

                        //Toast.makeText(AdmobsActivitySample.this, "Failed Ad Loaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

                adLoader.loadAd(new AdRequest.Builder().build());

    }
}
