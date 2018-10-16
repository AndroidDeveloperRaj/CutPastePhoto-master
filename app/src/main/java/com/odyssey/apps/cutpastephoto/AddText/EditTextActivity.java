package com.odyssey.apps.cutpastephoto.AddText;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.AdmobClass;
import com.odyssey.apps.cutpastephoto.Admobs.Advertisement;
import com.odyssey.apps.cutpastephoto.IAP.IAPActivity;
import com.odyssey.apps.cutpastephoto.MainActivity;

import com.odyssey.apps.cutpastephoto.R;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;
import com.odyssey.apps.cutpastephoto.StaticClasses.NotificationCenter;
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

public class EditTextActivity extends AppCompatActivity {

    GridView collectionView ;

    // Data part . . .
    private int colors [];
    private String fonts[];

    //----nowrin---

    public boolean isBackActive = true;
    public boolean isAddActive = true;


    private String font = "ostrich-regular.ttf";
    private int color = Color.rgb(255,255,255);
    private InterstitialAd mInterstitialAd;
    private void prepareData(){

        colors = Data.getSharedInstance().getColors();
        fonts = Data.getSharedInstance().getFonts();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialising Required Values
        prepareData();

        //------------Removed by Ashraf-----------
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setId(R.id.EditTextConstraintLayout);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //-------------------
        setContentView(R.layout.activity_edit_text);

        /*
        Button b = (Button) findViewById(R.id.button);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) b.getLayoutParams();
        layoutParams.topMargin = 100 ;
        b.requestLayout();
        */

        //Text View Setup
        final EditText textBox = (EditText) findViewById(R.id.ETATextBox);
        textBox.setDrawingCacheEnabled(true);


        // Done Button Setup . .
        Button doneButton = (Button) findViewById(R.id.ETADoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println(textBox.getText());
                //Bitmap imageFromTextBox  = Bitmap.createBitmap(textBox.getDrawingCache());
                //DataPassingSingelton.getInstance().setImage(imageFromTextBox);
                Intent crop = new Intent(EditTextActivity.this,MainActivity.class);
                crop.putExtra("TextFont", font);
                crop.putExtra("TextColor", color);
                crop.putExtra("TextString", textBox.getText().toString());
                setResult(Activity.RESULT_OK, crop);
                finish();

                if (mInterstitialAd.isLoaded()) {
                    if(isBackActive){
                        mInterstitialAd.show();
                    }
                }
                /*if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",EditTextActivity.this)){
                    mInterstitialAd.show();
                }*/

            }
        });
        Button cancelButton = (Button) findViewById(R.id.ETACancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                /*if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",EditTextActivity.this)){
                    mInterstitialAd.show();
                }*/
            }
        });

        //Collection View Setup . .
        collectionView = (GridView) findViewById(R.id.ETACollectionView);
        final CollectionViewAdapterColor colorAdapter = new CollectionViewAdapterColor(EditTextActivity.this);
        collectionView.setAdapter(colorAdapter);
        collectionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(EditTextActivity.this,"Clicked on Item" + i , Toast.LENGTH_SHORT ).show();

                if (adapterView.getAdapter() instanceof CollectionViewAdapterColor ) {
                    if(!CheckIf.isPurchased("color",EditTextActivity.this) && i>15) {
                        Intent iap = new Intent(EditTextActivity.this,IAPActivity.class);
                        startActivity(iap);
                    }
                    else {

                        textBox.setTextColor(colors[i]);
                        color = colors[i];
                    }
                }
                else {

                    if(!CheckIf.isPurchased("font",EditTextActivity.this) && i>15) {
                        Intent iap = new Intent(EditTextActivity.this,IAPActivity.class);
                        startActivity(iap);
                    }
                    else {
                        textBox.setTypeface(Typeface.createFromAsset(getAssets(), fonts[i]));
                        font = fonts[i];
                    }
                }
            }
        });



     /*   //KeyBoard Button Setup . .
        Button keyboardButton = (Button) findViewById(R.id.ETAKeyboardButton);
        keyboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //Toast.makeText(EditTextActivity.this,"Keyboard Button Works Good"  , Toast.LENGTH_SHORT ).show();

                //Get Keyboard Info
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isAcceptingText()) {
                    //KeyBoad is active
                    //Hide it
                    imm.hideSoftInputFromWindow(textBox.getWindowToken(), 0);
                }

            }
        });  */

        //Color Button Setup . .
        Button colorButton = (Button) findViewById(R.id.ETAColorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isAcceptingText()) {
                    //KeyBoad is active
                    //Hide it
                    imm.hideSoftInputFromWindow(textBox.getWindowToken(), 0);
                }

                //Toast.makeText(EditTextActivity.this,"Color Button Works Good"  , Toast.LENGTH_SHORT ).show();
                CollectionViewAdapterColor colorAdapter = new CollectionViewAdapterColor(EditTextActivity.this);
                collectionView.setAdapter(colorAdapter);
            }
        });

        //Font Button Setup . .
        Button fontButton = (Button) findViewById(R.id.ETAFontButton);
        fontButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isAcceptingText()) {
                    //KeyBoad is active
                    //Hide it
                    imm.hideSoftInputFromWindow(textBox.getWindowToken(), 0);
                }

                //Toast.makeText(EditTextActivity.this,"Font Button Works Good"  , Toast.LENGTH_SHORT ).show();
                CollectionViewAdapterFont fontAdapter = new CollectionViewAdapterFont(EditTextActivity.this);
                collectionView.setAdapter(fontAdapter);

            }
        });


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
                                findViewById(R.id.ETAdmob);
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
                        //Toast.makeText(EditTextActivity.this, "Ad Content loading", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.ETAdmob);
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
                        //Toast.makeText(EditTextActivity.this, "Failed Ad loading", Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        //Admob Visibility
        //findViewById(R.id.ETAdmob).setVisibility(View.GONE);
        if (!CheckIf.isPurchased("admob",this)){
            adLoader.loadAd(new AdRequest.Builder().build());
        }

        } catch (Exception e){

        }

        NotificationCenter.addReceiver("Purchased",mMessageReceiver,this);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdmobClass.INTERSTITIAL_AD_UNIT_ID);
        final AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                //Toast.makeText(EditTextActivity.this, "Failed Ad loading" +i, Toast.LENGTH_SHORT).show();

            }
        });
        if (!CheckIf.isPurchased("admob",this)) {
            showInterstitial();
        }
    }
    public void showInterstitial(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd.isLoaded()) {
                    if(isBackActive){
                        mInterstitialAd.show();
                    }
                }
            }
        }, 2000);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            purchasedJustNow();
            System.out.println("Notified !");
        }
    };


    @Override
    public void onDestroy() {

        /*if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",EditTextActivity.this)){
            mInterstitialAd.show();
        }*/
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();

    }

    private void doneButtonGotReady(Button doneButton)
    {

    }


    private void purchasedJustNow(){
        collectionView.invalidate();
        if(CheckIf.isPurchased("admob",this))
            findViewById(R.id.ETAdmob).setVisibility(View.GONE);
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
