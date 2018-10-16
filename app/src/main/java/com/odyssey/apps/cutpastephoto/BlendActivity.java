package com.odyssey.apps.cutpastephoto;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mukesh.image_processing.ImageProcessor;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import net.alhazmy13.imagefilter.ImageFilter;


public class BlendActivity extends AppCompatActivity {

    public ImageView testimage;
    public ImageView imageView00;
    public ImageView imageView2;
    public ImageView imageView3;
    public ImageView imageView4;
    public ImageView imageView5;
    public ImageView imageView6;
    public ImageView imageView7;
    public ImageView imageView8;
    public ImageView imageView9;
    public ImageView imageView10;
    public ImageView imageView11;
    public ImageView imageView12;
    public ImageView imageView13;
    public ImageView imageView14;
    public ImageView imageView15;
    public ImageView imageView16;
    public ImageView imageView17;
    public ImageView imageView18;
    public ImageView imageView19;
    public ImageView imageView20;
    public ImageView imageView21;
    public ImageView imageView22;
    public Button doneButton;
    public Button cancelButton;



    public SeekBar seekBar;
    int seekValue = 128;
    int effectNumber;

    private InterstitialAd mInterstitialAd;
    //----nowrin---

    public boolean isBackActive = true;
    public boolean isAddActive = true;



    static
    {
        System.loadLibrary("NativeImageProcessor");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_blend);

        testimage = findViewById(R.id.imageView);
        imageView00 = findViewById(R.id.imageView00);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);
        imageView13 = findViewById(R.id.imageView13);
        imageView14 = findViewById(R.id.imageView14);
        imageView15 = findViewById(R.id.imageView15);
        imageView16 = findViewById(R.id.imageView16);
        imageView17 = findViewById(R.id.imageView17);
        imageView18 = findViewById(R.id.imageView18);
        imageView19 = findViewById(R.id.imageView19);
        imageView20 = findViewById(R.id.imageView20);
        imageView21 = findViewById(R.id.imageView21);
        imageView22 = findViewById(R.id.imageView22);
        doneButton = findViewById(R.id.button2);
        cancelButton = findViewById(R.id.button);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(255);
        seekBar.setProgress(seekValue);

        testimage.setImageBitmap(DataPassingSingelton.getInstance().getImage());

        /*Bitmap resized1 = resize(BitmapFactory.decodeResource(getResources(), R.drawable.pic3),100,100);
        System.out.println("resized image height"+(resized1.getHeight()));
        System.out.println("resized image width"+(resized1.getWidth()));

        Bitmap resized2 = resize(BitmapFactory.decodeResource(getResources(), R.drawable.s6),100,100);
        System.out.println("resized image height"+(resized2.getHeight()));
        System.out.println("resized image width"+(resized2.getWidth()));*/
        Bitmap resized1 = resize(DataPassingSingelton.getInstance().getImage(),150,150);
        Bitmap resized2 = resize(DataPassingSingelton.getInstance().getImage2(),150,150);
//        Bitmap resized2 = DataPassingSingelton.getInstance().getImage2();


        updateImage(16,128);

        imageView2.setImageBitmap(updateImage2(0,resized1,resized2));
        imageView3.setImageBitmap(updateImage2(1,resized1,resized2));
        imageView4.setImageBitmap(updateImage2(2,resized1,resized2));
        imageView5.setImageBitmap(updateImage2(3,resized1,resized2));
        imageView6.setImageBitmap(updateImage2(4,resized1,resized2));
        imageView7.setImageBitmap(updateImage2(5,resized1,resized2));
        imageView8.setImageBitmap(updateImage2(6,resized1,resized2));
        imageView9.setImageBitmap(updateImage2(7,resized1,resized2));
        imageView10.setImageBitmap(updateImage2(8,resized1,resized2));
        imageView11.setImageBitmap(updateImage2(9,resized1,resized2));
        imageView12.setImageBitmap(updateImage2(10,resized1,resized2));
        imageView13.setImageBitmap(updateImage2(11,resized1,resized2));
        imageView14.setImageBitmap(updateImage2(12,resized1,resized2));
        imageView15.setImageBitmap(updateImage2(13,resized1,resized2));
        imageView16.setImageBitmap(updateImage2(14,resized1,resized2));
        imageView17.setImageBitmap(updateImage2(15,resized1,resized2));
        imageView18.setImageBitmap(updateImage2(16,resized1,resized2));
        imageView19.setImageBitmap(updateImage2(17,resized1,resized2));
        imageView20.setImageBitmap(updateImage2(18,resized1,resized2));
        imageView21.setImageBitmap(updateImage2(19,resized1,resized2));
        imageView22.setImageBitmap(updateImage2(20,resized1,resized2));


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bitmap imageviewImageBitmap = ((BitmapDrawable)testimage.getDrawable()).getBitmap();
                Drawable d;
                Resources r = getResources();
                Drawable[] layers = new Drawable[2];
                layers[0] = new BitmapDrawable(getResources(), DataPassingSingelton.getInstance().getImage());

                d = new BitmapDrawable(getResources(), ((BitmapDrawable)imageView00.getDrawable()).getBitmap());
                layers[1] = d;
                layers[1].setAlpha(seekValue);

                android.graphics.drawable.LayerDrawable layerDrawable = new android.graphics.drawable.LayerDrawable(layers);
        int width = layerDrawable.getIntrinsicWidth();
        int height = layerDrawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        layerDrawable.draw(canvas);


                DataPassingSingelton.getInstance().setImage(bitmap);
                Intent erase = new Intent(BlendActivity.this,MainActivity.class);
                setResult(Activity.RESULT_OK,
                        erase);
                finish();

                if (mInterstitialAd.isLoaded()) {
                    if(isBackActive){
                        mInterstitialAd.show();
                    }
                }

                //if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",BlendActivity.this)){
                   // mInterstitialAd.show();
               // }


            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",BlendActivity.this)){
                    mInterstitialAd.show();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                 seekValue = progress;

//                     updateImage(effectNumber,progress);
                 imageView00.setImageAlpha(progress);

             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
        });


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdmobClass.INTERSTITIAL_AD_UNIT_ID);
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {


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



    public void updateImage(int position, int value){
//        Bitmap a;
        Bitmap b;
//        a = DataPassingSingelton.getInstance().getImage();
        b = DataPassingSingelton.getInstance().getImage2();
//        Drawable d;
//        Resources r = getResources();
//        Drawable[] layers = new Drawable[2];
//        layers[0] = new BitmapDrawable(getResources(), a);
        switch (position) {
            case 0:
//                d = new BitmapDrawable(getResources(), getBlueMessFilter(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(getBlueMessFilter(b));
                imageView00.setImageAlpha(value);
                effectNumber = 0;
                break;

            case 1:
//                d = new BitmapDrawable(getResources(), getAweStruckVibeFilter(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(applySnowEffect(b));
                imageView00.setImageAlpha(value);
                effectNumber = 1;
                break;

            case 2:
//                d = new BitmapDrawable(getResources(), getLimeStutterFilter(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(getLimeStutterFilter(b));
                imageView00.setImageAlpha(value);
                effectNumber = 2;
                break;

            case 3:
//                d = new BitmapDrawable(getResources(), getNightWhisperFilter(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_NEON(b));
                imageView00.setImageAlpha(value);
                effectNumber = 3;
                break;

            case 4:
//                d = new BitmapDrawable(getResources(), getStarLitFilter(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_TV(b));
                imageView00.setImageAlpha(value);
                effectNumber = 4;
                break;

            case 5:
//                d = new BitmapDrawable(getResources(), alhazmy_INVERT(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_INVERT(b));
                imageView00.setImageAlpha(value);
                effectNumber = 5;
                break;

            case 6:
//                d = new BitmapDrawable(getResources(), applyBlackFilter(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(applyBlackFilter(b));
                imageView00.setImageAlpha(value);
                effectNumber = 6;
                break;

            case 7:
//                d = new BitmapDrawable(getResources(), alhazmy_GRAY(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_GRAY(b));
                imageView00.setImageAlpha(value);
                effectNumber = 7;
                break;

            case 8:
//                d = new BitmapDrawable(getResources(), applySnowEffect(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);

                imageView00.setImageBitmap(getAweStruckVibeFilter(b));
                imageView00.setImageAlpha(value);
                effectNumber = 8;
                break;

            case 9:
//                d = new BitmapDrawable(getResources(), alhazmy_OLD(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_OLD(b));
                imageView00.setImageAlpha(value);
                effectNumber = 9;
                break;

            case 10:
//                d = new BitmapDrawable(getResources(), applyReflection(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(applyReflection(b));
                imageView00.setImageAlpha(value);
                effectNumber = 10;
                break;

            case 11:
//                d = new BitmapDrawable(getResources(), alhazmy_BLOCK(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_BLOCK(b));
                imageView00.setImageAlpha(value);
                effectNumber = 11;
                break;

            case 12:
//                d = new BitmapDrawable(getResources(), alhazmy_NEON(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);

                imageView00.setImageBitmap(getNightWhisperFilter(b));
                imageView00.setImageAlpha(value);
                effectNumber = 12;
                break;

            case 13:
//                d = new BitmapDrawable(getResources(), alhazmy_SKETCH(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_SKETCH(b));
                imageView00.setImageAlpha(value);
                effectNumber = 13;
                break;

            case 14:
//                d = new BitmapDrawable(getResources(), alhazmy_GOTHAM(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_GOTHAM(b));
                imageView00.setImageAlpha(value);
                effectNumber = 14;
                break;

            case 15:
//                d = new BitmapDrawable(getResources(), alhazmy_HDR(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_HDR(b));
                imageView00.setImageAlpha(value);
                effectNumber = 15;
                break;

            case 16:
//                layers[1] = new BitmapDrawable(getResources(), b);
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(b);
                imageView00.setImageAlpha(value);
                effectNumber = 16;
                break;

            case 17:
//                d = new BitmapDrawable(getResources(), alhazmy_LOMO(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_LOMO(b));
                imageView00.setImageAlpha(value);
                effectNumber = 17;
                break;

            case 18:
//                d = new BitmapDrawable(getResources(), alhazmy_TV(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);

                imageView00.setImageBitmap(getStarLitFilter(b));
                imageView00.setImageAlpha(value);
                effectNumber = 18;
                break;

            case 19:
//                d = new BitmapDrawable(getResources(), alhazmy_RELIEF(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_RELIEF(b));
                imageView00.setImageAlpha(value);
                effectNumber = 19;
                break;

            case 20:
//                d = new BitmapDrawable(getResources(), alhazmy_LIGHT(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_LIGHT(b));
                imageView00.setImageAlpha(value);
                effectNumber = 20;
                break;

            case 21:
//                d = new BitmapDrawable(getResources(), alhazmy_SOFT_GLOW(b));
//                layers[1] = d;
//                layers[1].setAlpha(value);
                imageView00.setImageBitmap(alhazmy_SOFT_GLOW(b));
                imageView00.setImageAlpha(value);
                effectNumber = 21;
                break;
        }



//        android.graphics.drawable.LayerDrawable layerDrawable = new android.graphics.drawable.LayerDrawable(layers);
////        testimage.setImageDrawable(layerDrawable);
//
//        int width = layerDrawable.getIntrinsicWidth();
//        int height = layerDrawable.getIntrinsicHeight();
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        layerDrawable.draw(canvas);
//
//        testimage.setImageBitmap(bitmap);
//
//        testimage.destroyDrawingCache();


    }



    public Bitmap alhazmy_INVERT(Bitmap bitmap){
        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.INVERT);
    }

    public Bitmap applyBlackFilter(Bitmap bitmap){

//        Toast.makeText(getApplicationContext(),
//                "Processing.....", Toast.LENGTH_SHORT).show();

        ImageProcessor imageProcessor = new ImageProcessor();
        return imageProcessor.applyBlackFilter(bitmap);
    }


    public Bitmap alhazmy_GRAY(Bitmap bitmap){

       return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GRAY);
    }

    public Bitmap applySnowEffect(Bitmap bitmap){

        ImageProcessor imageProcessor = new ImageProcessor();
        return imageProcessor.applySnowEffect(bitmap);
    }

    public Bitmap alhazmy_OLD(Bitmap bitmap){

        return  ImageFilter.applyFilter(bitmap, ImageFilter.Filter.OLD);

    }

    public Bitmap applyReflection(Bitmap bitmap){

//        ImageProcessor imageProcessor = new ImageProcessor();
//        return imageProcessor.applyReflection(bitmap);
        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.NEON,0,255,0);
    }

    public Bitmap alhazmy_BLOCK(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.BLOCK);
    }

    public Bitmap alhazmy_NEON(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.NEON,255,0,0);
    }

    public Bitmap alhazmy_SKETCH(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.SKETCH);
    }

    public Bitmap alhazmy_GOTHAM(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GOTHAM);
    }


    public Bitmap alhazmy_HDR(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.HDR);
    }

    public Bitmap alhazmy_LOMO(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.LOMO);
    }

    public Bitmap alhazmy_TV(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.TV);
    }

    public Bitmap alhazmy_RELIEF(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.RELIEF);
    }

    public Bitmap alhazmy_LIGHT(Bitmap bitmap){

        return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.LIGHT);
    }

    public Bitmap getBlueMessFilter(Bitmap bitmap){

        Filter fooFilter = SampleFilters.getBlueMessFilter();
        return  fooFilter.processFilter(Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),false));
//        return  fooFilter.processFilter(bitmap);
    }

    public Bitmap getLimeStutterFilter(Bitmap bitmap){

        Filter fooFilter = SampleFilters.getLimeStutterFilter();
        return  fooFilter.processFilter(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
//        return  fooFilter.processFilter(bitmap);
    }

    public Bitmap getAweStruckVibeFilter(Bitmap bitmap){

        Filter fooFilter = SampleFilters.getAweStruckVibeFilter();
        return  fooFilter.processFilter(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
//        return  fooFilter.processFilter(bitmap);
    }

    public Bitmap getStarLitFilter(Bitmap bitmap){

        Filter fooFilter = SampleFilters.getStarLitFilter();
        return  fooFilter.processFilter(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
//        return  fooFilter.processFilter(bitmap);
    }

    public Bitmap getNightWhisperFilter(Bitmap bitmap){

        Filter fooFilter = SampleFilters.getNightWhisperFilter();
        return  fooFilter.processFilter(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
//        return  fooFilter.processFilter(bitmap);
    }

    public Bitmap alhazmy_SOFT_GLOW(Bitmap bitmap){

        return  ImageFilter.applyFilter(bitmap, ImageFilter.Filter.SOFT_GLOW);
    }


    public void button1(View view){

        seekBar.setProgress(128);

        updateImage(0,128);

    }

    public void button2(View view){

        seekBar.setProgress(128);

        updateImage(1,128);

    }

    public void button3(View view){

        seekBar.setProgress(128);

        updateImage(2,128);

    }

    public void button4(View view){

        seekBar.setProgress(128);

        updateImage(3,128);

    }

    public void button5(View view){

        seekBar.setProgress(128);

        updateImage(4,128);

    }

    public void button6(View view){

        seekBar.setProgress(128);

        updateImage(5,128);

    }

    public void button7(View view){

        seekBar.setProgress(128);

        updateImage(6,128);

    }

    public void button8(View view){

        seekBar.setProgress(128);

        updateImage(7,128);

    }

    public void button9(View view){

        seekBar.setProgress(128);

        updateImage(8,128);

    }

    public void button10(View view){

        seekBar.setProgress(128);

        updateImage(9,128);

    }

    public void button11(View view){

        seekBar.setProgress(128);

        updateImage(10,128);

    }

    public void button12(View view){

        seekBar.setProgress(128);

        updateImage(11,128);

    }

    public void button13(View view){

        seekBar.setProgress(128);

        updateImage(12,128);

    }

    public void button14(View view){

        seekBar.setProgress(128);

        updateImage(13,128);

    }

    public void button15(View view){

        seekBar.setProgress(128);

        updateImage(14,128);

    }

    public void button16(View view){

        seekBar.setProgress(128);

        updateImage(15,128);

    }

    public void button17(View view){

        seekBar.setProgress(128);

        updateImage(17,128);

    }
    public void button18(View view){

        seekBar.setProgress(128);

        updateImage(18,128);

    }
    public void button19(View view){

        seekBar.setProgress(128);

        updateImage(19,128);

    }
    public void button20(View view){

        seekBar.setProgress(128);

        updateImage(20,128);

    }
    public void button21(View view){

        seekBar.setProgress(128);

        updateImage(21,128);

    }




    public Bitmap updateImage2(int position, Bitmap a, Bitmap b){

        Drawable d;
        Resources r = getResources();
        Drawable[] layers = new Drawable[2];
        //layers[0] = r.getDrawable(R.drawable.pic3);
        layers[0] = new BitmapDrawable(getResources(), a);
        switch (position) {
            case 0:
               //a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
                //b = alhazmy_INVERT(a);
                d = new BitmapDrawable(getResources(), getBlueMessFilter(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 1:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = applyBlackFilter(a);
                d = new BitmapDrawable(getResources(), applySnowEffect(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 2:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_GRAY(a);
                d = new BitmapDrawable(getResources(), getLimeStutterFilter(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 3:
//                layers[1] = r.getDrawable(R.drawable.s5);
//                layers[1].setAlpha(200);
//                break;

//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = applySnowEffect(a);
                d = new BitmapDrawable(getResources(), alhazmy_NEON(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 4:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_OLD(a);
                d = new BitmapDrawable(getResources(), alhazmy_TV(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 5:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = applyReflection(a);
                d = new BitmapDrawable(getResources(), alhazmy_INVERT(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 6:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_BLOCK(a);
                d = new BitmapDrawable(getResources(), applyBlackFilter(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 7:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_NEON(a);
                d = new BitmapDrawable(getResources(), alhazmy_GRAY(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 8:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_SKETCH(a);
                d = new BitmapDrawable(getResources(), getAweStruckVibeFilter(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 9:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_GOTHAM(a);
                d = new BitmapDrawable(getResources(), alhazmy_OLD(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 10:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_HDR(a);
                d = new BitmapDrawable(getResources(), applyReflection(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 11:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_LOMO(a);
                d = new BitmapDrawable(getResources(), alhazmy_BLOCK(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 12:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_TV(a);
                d = new BitmapDrawable(getResources(), getNightWhisperFilter(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 13:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_RELIEF(a);
                d = new BitmapDrawable(getResources(), alhazmy_SKETCH(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 14:
//                a = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
//                b = alhazmy_LIGHT(a);
                d = new BitmapDrawable(getResources(), alhazmy_GOTHAM(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 15:
                d = new BitmapDrawable(getResources(), alhazmy_HDR(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 16:
                d = new BitmapDrawable(getResources(), alhazmy_LOMO(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 17:
                d = new BitmapDrawable(getResources(), getStarLitFilter(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 18:
                d = new BitmapDrawable(getResources(), alhazmy_RELIEF(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 19:
                d = new BitmapDrawable(getResources(), alhazmy_LIGHT(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

            case 20:
                d = new BitmapDrawable(getResources(), alhazmy_SOFT_GLOW(b));
                layers[1] = d;
                layers[1].setAlpha(128);
                break;

        }
        android.graphics.drawable.LayerDrawable layerDrawable = new android.graphics.drawable.LayerDrawable(layers);
//        testimage.setImageDrawable(layerDrawable);

        int width = layerDrawable.getIntrinsicWidth();
        int height = layerDrawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        layerDrawable.draw(canvas);

        return bitmap;
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
    @Override
    public void onBackPressed() {


        if (isBackActive) {
            isAddActive = false;
        } else {
            super.onBackPressed();
        }
    }

}
