package com.odyssey.apps.cutpastephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mukesh.image_processing.ImageProcessor;
import com.odyssey.apps.cutpastephoto.AddText.EditTextActivity;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;

import net.alhazmy13.imagefilter.ImageFilter;

public class FilterActivity extends AppCompatActivity {

    //
    private InterstitialAd mInterstitialAd;

    public ImageView imageView;
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

    public Button doneButton;
    public Button cancelButton;
   //private InterstitialAd mInterstitialAd;
    //----nowrin---

    public boolean isBackActive = true;
    public boolean isAddActive = true;

    Bitmap bitmap;
//    Bitmap resized;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_filter);

        imageView = findViewById(R.id.imageView);
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
        doneButton = findViewById(R.id.buttonDone);
        cancelButton = findViewById(R.id.buttonCancel);

//        Bundle extras = getIntent().getExtras();
//        byte[] byteArray = extras.getByteArray("FilterImage");
//
//
//        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smily1);

        bitmap = DataPassingSingelton.getInstance().getImage();
        imageView.setImageBitmap(bitmap);
//        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        Bitmap resized = resize(bitmap,100,100);
        System.out.println("resized image height"+(resized.getHeight()));
        System.out.println("resized image width"+(resized.getWidth()));



        Bitmap alhazmy_INVERT = ImageFilter.applyFilter(resized, ImageFilter.Filter.INVERT);

        ImageProcessor imageProcessor = new ImageProcessor();
        Bitmap applyBlackFilter = imageProcessor.applyBlackFilter(resized);

        Bitmap alhazmy_GRAY = ImageFilter.applyFilter(resized, ImageFilter.Filter.GRAY);

        Bitmap applySnowEffect = imageProcessor.applySnowEffect(resized);

        Bitmap alhazmy_OLD = ImageFilter.applyFilter(resized, ImageFilter.Filter.OLD);

        Bitmap applyReflection = imageProcessor.applyReflection(resized);

        Bitmap alhazmy_BLOCK = ImageFilter.applyFilter(resized, ImageFilter.Filter.BLOCK);

        Bitmap alhazmy_NEON = ImageFilter.applyFilter(resized, ImageFilter.Filter.NEON,255,0,0);

        Bitmap alhazmy_SKETCH = ImageFilter.applyFilter(resized, ImageFilter.Filter.SKETCH);

        Bitmap alhazmy_GOTHAM = ImageFilter.applyFilter(resized, ImageFilter.Filter.GOTHAM);

        Bitmap alhazmy_HDR = ImageFilter.applyFilter(resized, ImageFilter.Filter.HDR);

        Bitmap alhazmy_LOMO = ImageFilter.applyFilter(resized, ImageFilter.Filter.LOMO);

        Bitmap alhazmy_TV = ImageFilter.applyFilter(resized, ImageFilter.Filter.TV);

        Bitmap alhazmy_RELIEF = ImageFilter.applyFilter(resized, ImageFilter.Filter.RELIEF);

        Bitmap alhazmy_LIGHT = ImageFilter.applyFilter(resized, ImageFilter.Filter.LIGHT);

        Bitmap alhazmy_SOFT_GLOW = ImageFilter.applyFilter(resized, ImageFilter.Filter.SOFT_GLOW);

        imageView2.setImageBitmap(alhazmy_INVERT);
        imageView3.setImageBitmap(applyBlackFilter);
        imageView4.setImageBitmap(alhazmy_GRAY);
        imageView5.setImageBitmap(applySnowEffect);
        imageView6.setImageBitmap(alhazmy_OLD);
        imageView7.setImageBitmap(applyReflection);
        imageView8.setImageBitmap(alhazmy_BLOCK);
        imageView9.setImageBitmap(alhazmy_NEON);
        imageView10.setImageBitmap(alhazmy_SKETCH);
        imageView11.setImageBitmap(alhazmy_GOTHAM);
        imageView12.setImageBitmap(alhazmy_HDR);
        imageView13.setImageBitmap(alhazmy_LOMO);
        imageView14.setImageBitmap(alhazmy_TV);
        imageView15.setImageBitmap(alhazmy_RELIEF);
        imageView16.setImageBitmap(alhazmy_LIGHT);
        imageView17.setImageBitmap(alhazmy_SOFT_GLOW);


        //nowrin

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9079664095868787/6728823714");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });



        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap imageviewImageBitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();*/
                DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
                Intent erase = new Intent(FilterActivity.this,MainActivity.class);
                //erase.putExtra("FilterImage", byteArray);
                //bool = true;
                setResult(Activity.RESULT_OK,
                        erase);
                finish();


                if (mInterstitialAd.isLoaded()) {
                    if(isBackActive){
                        mInterstitialAd.show();
                    }
                }
               //if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",FilterActivity.this)){
                    //mInterstitialAd.show();
                //}

            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                finish();
               // if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",FilterActivity.this)){
                  //  mInterstitialAd.show();
              //  }

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(errorCode);
                //Toast.makeText(FilterActivity.this, "Failed Ad loading" + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                super.onAdOpened();
                //Toast.makeText(FilterActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                super.onAdLeftApplication();
               // Toast.makeText(FilterActivity.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                super.onAdClosed();
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

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Toast.makeText(FilterActivity.this, "Failed Ad loading" + i, Toast.LENGTH_SHORT).show();

            }
        });

       /*  if (!CheckIf.isPurchased("admob",this)) {
            showInterstitial();
        }*/

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

    public void alhazmy_INVERT(View view){

//        ImageProcessor imageProcessor = new ImageProcessor();
//        imageView.setImageBitmap(imageProcessor.doInvert(bitmap));


//        Toast.makeText(FilterActivity.this,
//                "Processing.....", Toast.LENGTH_SHORT).show();

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.INVERT));
    }

    public void applyBlackFilter(View view){

//        Toast.makeText(getApplicationContext(),
//                "Processing.....", Toast.LENGTH_SHORT).show();

        ImageProcessor imageProcessor = new ImageProcessor();
        imageView.setImageBitmap(imageProcessor.applyBlackFilter(bitmap));
    }


    public void alhazmy_GRAY(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GRAY));
    }

    public void applySnowEffect(View view){

        ImageProcessor imageProcessor = new ImageProcessor();
        imageView.setImageBitmap(imageProcessor.applySnowEffect(bitmap));
    }

    public void alhazmy_OLD(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.OLD));

    }

    public void applyReflection(View view){

        ImageProcessor imageProcessor = new ImageProcessor();
        imageView.setImageBitmap(imageProcessor.applyReflection(bitmap));
    }

    public void alhazmy_BLOCK(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.BLOCK));
    }

    public void alhazmy_NEON(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.NEON,255,0,0));
    }

    public void alhazmy_SKETCH(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.SKETCH));
    }

    public void alhazmy_GOTHAM(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GOTHAM));
    }


    public void alhazmy_HDR(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.HDR));
    }

    public void alhazmy_LOMO(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.LOMO));
    }

    public void alhazmy_TV(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.TV));
    }

    public void alhazmy_RELIEF(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.RELIEF));
    }

    public void alhazmy_LIGHT(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.LIGHT));
    }

    public void alhazmy_SOFT_GLOW(View view){

        imageView.setImageBitmap(ImageFilter.applyFilter(bitmap, ImageFilter.Filter.SOFT_GLOW));
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




//    public Bitmap toSephia(Bitmap bmpOriginal)
//    {
//        int width, height, r,g, b, c, gry;
//        height = bmpOriginal.getHeight();
//        width = bmpOriginal.getWidth();
//        int depth = 20;
//
//        Bitmap bmpSephia = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bmpSephia);
//        Paint paint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setScale(.3f, .3f, .3f, 1.0f);
//        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
//        paint.setColorFilter(f);
//        canvas.drawBitmap(bmpOriginal, 0, 0, paint);
//        for(int x=0; x < width; x++) {
//            for(int y=0; y < height; y++) {
//                c = bmpOriginal.getPixel(x, y);
//
//                r = Color.red(c);
//                g = Color.green(c);
//                b = Color.blue(c);
//
//                gry = (r + g + b) / 3;
//                r = g = b = gry;
//
//                r = r + (depth * 2);
//                g = g + depth;
//
//                if(r > 255) {
//                    r = 255;
//                }
//                if(g > 255) {
//                    g = 255;
//                }
//                bmpSephia.setPixel(x, y, Color.rgb(r, g, b));
//            }
//        }
//        return bmpSephia;
//    }



//    public Bitmap createEffect(Bitmap src, int depth, double red, double green, double blue) {
//        // image size
//        int width = src.getWidth();
//        int height = src.getHeight();
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//        // constant grayscale
//        final double GS_RED = 0.3;
//        final double GS_GREEN = 0.59;
//        final double GS_BLUE = 0.11;
//        // color information
//        int A, R, G, B;
//        int pixel;
//
//        // scan through all pixels
//        for(int x = 0; x < width; ++x) {
//            for(int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                // get color on each channel
//                A = Color.alpha(pixel);
//                R = Color.red(pixel);
//                G = Color.green(pixel);
//                B = Color.blue(pixel);
//                // apply grayscale sample
//                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
//
//                // apply intensity level for sepid-toning on each channel
//                R += (depth * red);
//                if(R > 255) { R = 255; }
//
//                G += (depth * green);
//                if(G > 255) { G = 255; }
//
//                B += (depth * blue);
//                if(B > 255) { B = 255; }
//
//                // set new pixel color to output image
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
//
//        // return final image
//        return bmOut;
//    }

    @Override
    public void onBackPressed() {


        if (isBackActive) {
            isAddActive = false;
        } else {
            super.onBackPressed();
        }
    }


}
