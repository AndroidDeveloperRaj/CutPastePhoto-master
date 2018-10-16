package com.odyssey.apps.cutpastephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.Admobs.Advertisement;
import com.odyssey.apps.cutpastephoto.CropActivity;
import com.odyssey.apps.cutpastephoto.DataPassingSingelton;
import com.odyssey.apps.cutpastephoto.MainActivity;
import com.odyssey.apps.cutpastephoto.R;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity2 extends AppCompatActivity {
    private CropImageView cropImageView;
    private Bitmap originalBitmap;
    private String CalledBy = "";
    private InterstitialAd mInterstitialAd;

    //----nowrin---

    public boolean isBackActive = true;
    public boolean isAddActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crop2);
        cropImageView = findViewById(R.id.cropImageView);
        Bundle extras = getIntent().getExtras();
        //byte[] byteArray;
        CalledBy = extras.getString("CalledActivity");


        originalBitmap = DataPassingSingelton.getInstance().getImage();


        //originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //cropImageView.setImageUriAsync(uri);
        cropImageView.setImageBitmap(originalBitmap);
        cropImageView.setCropRect(new Rect(0, 0, originalBitmap.getWidth(), originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*2/3)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*2/3+(originalBitmap.getHeight()-originalBitmap.getWidth()*2/3)/2));
//        cropImageView.setAspectRatio(3,2);


        // Admob
        try{

        MobileAds.initialize(this, Advertisement.getSharedInstance().getNativeAdvanceAdAppID());
        final AdLoader adLoader = new AdLoader.Builder(this, Advertisement.getSharedInstance().getNativeAdvanceAdUnitID())
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the app install ad.
                        //Toast.makeText(AdmobsActivitySample.this, "Ad App Install Loaded", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.ACAdmob);
                        frameLayout.setVisibility(View.VISIBLE);
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
                        //Toast.makeText(CropActivity.this, "Ad Content loading", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.ACAdmob);
                        frameLayout.setVisibility(View.VISIBLE);
                        NativeContentAdView adView = (NativeContentAdView) getLayoutInflater()
                                .inflate(R.layout.ad_content, null);
                        Advertisement.getSharedInstance().populateContentAdView(contentAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();


                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                        //Toast.makeText(CropActivity.this, "Failed Ad loading", Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        //Admob Visibility
        //findViewById(R.id.ACAdmob).setVisibility(View.GONE);
        if (!CheckIf.isPurchased("admob", this)) {
            adLoader.loadAd(new AdRequest.Builder().build());
        }

        } catch (Exception e){

        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdmobClass.INTERSTITIAL_AD_UNIT_ID);
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);

    }

    public void cropDone(View view) {
        //DataPassingSingelton.getInstance().freeMemory();
        Bitmap imageviewImageBitmap = cropImageView.getCroppedImage();
        DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();*/
        Intent crop = new Intent(CropActivity2.this, MainActivity.class);
        //crop.putExtra("CropedImage", byteArray);
        setResult(Activity.RESULT_OK, crop);
        if (CalledBy.equals("Erase")) {
            crop.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(crop);
        }
        finish();

        if (mInterstitialAd.isLoaded()) {
            if(isBackActive){
                mInterstitialAd.show();
            }
        }
        /*if(!CalledBy.equals("Erase") && mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",this)){
            mInterstitialAd.show();
        }*/


    }

    public void cropCancel(View view) {
        /*Bitmap imageviewImageBitmap = originalBitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Intent erase = new Intent(CropActivity.this,MainActivity.class);
        erase.putExtra("CropedImage", byteArray);
        setResult(Activity.RESULT_OK,
                erase);*/
        //DataPassingSingelton.getInstance().freeMemory();
        finish();
        /*if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",this)){
            mInterstitialAd.show();
        }*/

    }

    @Override
    public void onBackPressed() {


        if (isBackActive) {
            isAddActive = false;
        } else {
            super.onBackPressed();
        }
    }
}