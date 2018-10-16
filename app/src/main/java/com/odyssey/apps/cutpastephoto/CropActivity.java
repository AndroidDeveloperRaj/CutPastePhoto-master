package com.odyssey.apps.cutpastephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.Admobs.Advertisement;

import com.odyssey.apps.cutpastephoto.IAP.IAPActivity;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;
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
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {
    private CropImageView cropImageView;
    private Bitmap originalBitmap;
    private String CalledBy = "";
    private InterstitialAd mInterstitialAd;
    ImageView imageView;

    ImageView imageViewLock7;
    ImageView imageViewLock8;
    ImageView imageViewLock9;
    ImageView imageViewLock10;
    ImageView imageViewLock11;
    ImageView imageViewLock12;
    ImageView imageViewLock13;
    ImageView imageViewLock14;
    ImageView imageViewLock15;
    ImageView imageViewLock16;
    ImageView imageViewLock17;
    ImageView imageViewLock18;
    ImageView imageViewLock19;
    ImageView imageViewLock20;
    ImageView imageViewLock21;
    ImageView imageViewLock22;
    ImageView imageViewLock23;
    ImageView imageViewLock24;
    //----nowrin---

    public boolean isBackActive = true;
    public boolean isAddActive = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crop);
        cropImageView = findViewById(R.id.cropImageView);
        imageView = findViewById(R.id.imageView);

        imageViewLock7 = findViewById(R.id.imageViewLock7);
        imageViewLock8 = findViewById(R.id.imageViewLock8);
        imageViewLock9 = findViewById(R.id.imageViewLock9);
        imageViewLock10 = findViewById(R.id.imageViewLock10);
        imageViewLock11 = findViewById(R.id.imageViewLock11);
        imageViewLock12 = findViewById(R.id.imageViewLock12);
        imageViewLock13 = findViewById(R.id.imageViewLock13);
        imageViewLock14 = findViewById(R.id.imageViewLock14);
        imageViewLock15 = findViewById(R.id.imageViewLock15);
        imageViewLock16 = findViewById(R.id.imageViewLock16);
        imageViewLock17 = findViewById(R.id.imageViewLock17);
        imageViewLock18 = findViewById(R.id.imageViewLock18);
        imageViewLock19 = findViewById(R.id.imageViewLock19);
        imageViewLock20 = findViewById(R.id.imageViewLock20);
        imageViewLock21 = findViewById(R.id.imageViewLock21);
        imageViewLock22 = findViewById(R.id.imageViewLock22);
        imageViewLock23 = findViewById(R.id.imageViewLock23);
        imageViewLock24 = findViewById(R.id.imageViewLock24);

        if(CheckIf.isPurchased("crop",CropActivity.this)==true){
            imageViewLock7.setVisibility(View.INVISIBLE);
            imageViewLock8.setVisibility(View.INVISIBLE);
            imageViewLock9.setVisibility(View.INVISIBLE);
            imageViewLock10.setVisibility(View.INVISIBLE);
            imageViewLock11.setVisibility(View.INVISIBLE);
            imageViewLock12.setVisibility(View.INVISIBLE);
            imageViewLock13.setVisibility(View.INVISIBLE);
            imageViewLock14.setVisibility(View.INVISIBLE);
            imageViewLock15.setVisibility(View.INVISIBLE);
            imageViewLock16.setVisibility(View.INVISIBLE);
            imageViewLock17.setVisibility(View.INVISIBLE);
            imageViewLock18.setVisibility(View.INVISIBLE);
            imageViewLock19.setVisibility(View.INVISIBLE);
            imageViewLock20.setVisibility(View.INVISIBLE);
            imageViewLock21.setVisibility(View.INVISIBLE);
            imageViewLock22.setVisibility(View.INVISIBLE);
            imageViewLock23.setVisibility(View.INVISIBLE);
            imageViewLock24.setVisibility(View.INVISIBLE);
        }

        Bundle extras = getIntent().getExtras();
        //byte[] byteArray;
        CalledBy = extras.getString("CalledActivity");


        originalBitmap = DataPassingSingelton.getInstance().getImage();


        //originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //cropImageView.setImageUriAsync(uri);
        imageView.setImageBitmap(resize(originalBitmap,200,200));
        cropImageView.setImageBitmap(originalBitmap);
        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*2/3)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*2/3+(originalBitmap.getHeight()-originalBitmap.getWidth()*2/3)/2));
//        cropImageView.setAspectRatio(3,2);


        // Admob
        try {


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
        if (!CheckIf.isPurchased("admob",this)){
            adLoader.loadAd(new AdRequest.Builder().build());
        }


        } catch (Exception e){

        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdmobClass.INTERSTITIAL_AD_UNIT_ID);
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);

    }


    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public void cropDone(View view){
        //DataPassingSingelton.getInstance().freeMemory();
        Bitmap imageviewImageBitmap = cropImageView.getCroppedImage();
        DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();*/
        Intent crop = new Intent(CropActivity.this,MainActivity.class);
        //crop.putExtra("CropedImage", byteArray);
        setResult(Activity.RESULT_OK, crop);
        if(CalledBy.equals("Erase")){
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
    public void cropCancel(View view){
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


    public void Original(View v){

        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*2/3)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*2/3+(originalBitmap.getHeight()-originalBitmap.getWidth()*2/3)/2));
//        cropImageView.setAspectRatio(null,2);
        cropImageView.clearAspectRatio();


    }

    public void r_1_1(View v){

//        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*1/1)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*1/1+(originalBitmap.getHeight()-originalBitmap.getWidth()*1/1)/2));
        cropImageView.setAspectRatio(1,1);

    }


    public void r_2_3(View v){

//        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*3/2)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*3/2+(originalBitmap.getHeight()-originalBitmap.getWidth()*3/2)/2));
        cropImageView.setAspectRatio(2,3);

    }


    public void r_3_2(View v){
//        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*3/2)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*3/2+(originalBitmap.getHeight()-originalBitmap.getWidth()*3/2)/2));
        cropImageView.setAspectRatio(3,2);
    }


    public void r_3_4(View v){
//        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*3/4)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*3/4+(originalBitmap.getHeight()-originalBitmap.getWidth()*3/4)/2));
        cropImageView.setAspectRatio(3,4);
    }


    public void r_4_3(View v){
//        cropImageView.setCropRect(new Rect(0,0,originalBitmap.getWidth(),originalBitmap.getHeight()));
//        cropImageView.setCropRect(new Rect(0,(originalBitmap.getHeight()-originalBitmap.getWidth()*4/3)/2,originalBitmap.getWidth(),originalBitmap.getWidth()*4/3+(originalBitmap.getHeight()-originalBitmap.getWidth()*4/3)/2));
        cropImageView.setAspectRatio(4,3);
    }


    public void f_r_4_3(View v){

        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {

            cropImageView.setAspectRatio(4, 3);
//            imageViewLock7.setVisibility(View.INVISIBLE);
        }
    }


    public void f_r_2_3(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(2, 3);
//            imageViewLock8.setVisibility(View.INVISIBLE);
        }
    }


    public void f_r_2p6_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(26, 10);
//            imageViewLock9.setVisibility(View.INVISIBLE);
        }
    }


    public void f_r_2_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(2, 1);
//            imageViewLock10.setVisibility(View.INVISIBLE);
        }
    }


    public void f_r_2p7_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(27, 10);
//            imageViewLock11.setVisibility(View.INVISIBLE);
        }
    }

    public void f_r_1p9_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(19, 10);
//            imageViewLock12.setVisibility(View.INVISIBLE);
        }
    }

    public void i_r_4_5(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(4, 5);
//            imageViewLock13.setVisibility(View.INVISIBLE);
        }
    }

    public void i_r_1p91_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(19, 10);
//            imageViewLock14.setVisibility(View.INVISIBLE);
        }
    }

    public void t_r_16_9(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(16, 9);
//            imageViewLock15.setVisibility(View.INVISIBLE);
        }
    }

    public void t_r_3_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(3, 1);
//            imageViewLock16.setVisibility(View.INVISIBLE);
        }
    }

    public void p_r_2_3(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(2, 3);
//            imageViewLock17.setVisibility(View.INVISIBLE);
        }
    }

    public void l_r_1p4_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(14, 10);
//            imageViewLock18.setVisibility(View.INVISIBLE);
        }
    }

    public void l_r_2_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(2, 1);
//            imageViewLock19.setVisibility(View.INVISIBLE);
        }
    }

    public void y_r_16_9(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(16, 9);
//            imageViewLock20.setVisibility(View.INVISIBLE);
        }
    }

    public void y_r_1p8_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(18, 10);
//            imageViewLock21.setVisibility(View.INVISIBLE);
        }
    }

    public void w_r_16_25(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(16, 25);
//            imageViewLock22.setVisibility(View.INVISIBLE);
        }
    }

    public void et_r_4_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(4, 1);
//            imageViewLock23.setVisibility(View.INVISIBLE);
        }
    }

    public void ev_r_2_1(View v){
        if(CheckIf.isPurchased("crop",CropActivity.this)==false){
            Intent iap = new Intent(CropActivity.this,IAPActivity.class);
            startActivity(iap);
        }
        else {
            cropImageView.setAspectRatio(2, 1);
//            imageViewLock24.setVisibility(View.INVISIBLE);
        }
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
