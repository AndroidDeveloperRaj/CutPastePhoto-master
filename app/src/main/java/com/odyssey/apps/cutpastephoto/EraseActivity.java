package com.odyssey.apps.cutpastephoto;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.Admobs.Advertisement;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;
import com.odyssey.apps.cutpastephoto.util.FileUtil;
import com.baoyz.actionsheet.ActionSheet;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

//import android.support.v7.app.AppCompatDelegate;

public class EraseActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener{

    private static final int REQUEST_ERASE_STICKER = 0;
    private int initialDrawingCountLimit = 20;
    private int offset = 250;
    private int undoLimit = 10;
    private float brushSize = 70.0f;

    public static boolean bool = false;

    private boolean isMultipleTouchErasing;
    private boolean isTouchOnBitmap;
    private int initialDrawingCount;
    private int updatedBrushSize;
    private int imageViewWidth;

    private int imageViewHeight;
    private float currentx;
    private float currenty;

    private Bitmap bitmapMaster;
    private Bitmap lastEditedBitmap;
    private Bitmap originalBitmap;
    private Bitmap resizedBitmap;
    private Bitmap highResolutionOutput;

    private Canvas canvasMaster;
    private Point mainViewSize;
    private Path drawingPath;

    private Vector<Integer> brushSizes;
    private Vector<Integer> redoBrushSizes;

    private ArrayList<Path> paths;
    private ArrayList<Path> redoPaths;

    private RelativeLayout rlImageViewContainer;
    private LinearLayout llTopBar;
    private ImageView ivRedo;
    private ImageView ivUndo;
    private TextView ivDone;
    private TextView ivCancel;
    private SeekBar sbOffset;
    private SeekBar sbWidth;
    private TouchImageView touchImageView;
    private BrushImageView brushImageView;

    private boolean isImageResized;
    private MediaScannerConnection msConn;
    private int MODE;
    private DatabaseHelper mydb ;
    private InterstitialAd mInterstitialAd;



    public EraseActivity() {
        paths = new ArrayList();
        redoPaths = new ArrayList();
        brushSizes = new Vector();
        redoBrushSizes = new Vector();
        MODE=0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_erase);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdmobClass.INTERSTITIAL_AD_UNIT_ID);
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                /*if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }*/

            }
        });

        Intent i = getIntent();

        mydb = new DatabaseHelper(this);


        drawingPath = new Path();
        Display display = getWindowManager().getDefaultDisplay();
        mainViewSize = new Point();
        display.getSize(mainViewSize);

        initViews();

        //Bundle extras = getIntent().getExtras();
        //byte[] byteArray = extras.getByteArray("EraseImage");


        //originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        originalBitmap = DataPassingSingelton.getInstance().getImage();




        setBitMap();
        updateBrush((float) (mainViewSize.x / 2), (float) (mainViewSize.y / 2));


        // Admob

        MobileAds.initialize(this, Advertisement.getSharedInstance().getNativeAdvanceAdAppID());
        final AdLoader adLoader = new AdLoader.Builder(this, Advertisement.getSharedInstance().getNativeAdvanceAdUnitID())
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the app install ad.
                        //Toast.makeText(AdmobsActivitySample.this, "Ad App Install Loaded", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.ERAdmob);
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
                        //Toast.makeText(EraseActivity.this, "Ad Content loading", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.ERAdmob);
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
                        //Toast.makeText(EraseActivity.this, "Failed Ad loading", Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        //Admob Visibility
        //findViewById(R.id.ERAdmob).setVisibility(View.GONE);
        if (!CheckIf.isPurchased("admob",this)){
            adLoader.loadAd(new AdRequest.Builder().build());
        }


        if (!CheckIf.isPurchased("admob",this)) {
            showInterstitial();
        }

    }
    public void showInterstitial(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                //}
            }
        }, 2000);

    }
    public void goSettingsPage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(getString(R.string.GoSettingsMsg));
        builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                dialog.cancel();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MainActivity.PERM_RQST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    // do your work here
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                    //Toast.makeText(MainActivity.this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT).show();
                    // User selected the Never Ask Again Option
                    goSettingsPage();
                } else {

                    //Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void initViews() {
        touchImageView = (TouchImageView) findViewById(R.id.drawingImageView);
        brushImageView = (BrushImageView) findViewById(R.id.brushContainingView);
        llTopBar = (LinearLayout) findViewById(R.id.ll_top_bar);
        rlImageViewContainer = (RelativeLayout) findViewById(R.id.rl_image_view_container);
        ivUndo = (ImageView) findViewById(R.id.iv_undo);
        ivRedo = (ImageView) findViewById(R.id.iv_redo);
        ivDone = (TextView) findViewById(R.id.iv_done);
        ivCancel = (TextView) findViewById(R.id.iv_cancel);
        sbOffset = (SeekBar) findViewById(R.id.sb_offset);
        sbWidth = (SeekBar) findViewById(R.id.sb_width);


        rlImageViewContainer.getLayoutParams().height = mainViewSize.y
                - (llTopBar.getLayoutParams().height);
        imageViewWidth = mainViewSize.x;
        imageViewHeight = rlImageViewContainer.getLayoutParams().height;

        ivUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });

        ivRedo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redo();
            }
        });

        ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                saveImage();
                ActionSheet.createBuilder(EraseActivity.this, getSupportFragmentManager())
                        .setCancelButtonTitle(getString(R.string.Cancel))
                        .setOtherButtonTitles(getString(R.string.Apply), getString(R.string.CropAndApply),getString(R.string.SaveToForegroundGallery),getString(R.string.SaveToPhotoAlbum))
                        .setCancelableOnTouchOutside(true)
                        .setListener(EraseActivity.this).show();

                //startActivity(erase);


            }
        });


        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                originalBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                //DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
                Intent erase = new Intent(EraseActivity.this,MainActivity.class);
                erase.putExtra("BitmapImage2", byteArray);
                //bool = true;
                setResult(Activity.RESULT_OK,
                        erase);
                finish();

            }
        });

        touchImageView.setOnTouchListener(new OnTouchListner());
        sbWidth.setMax(150);
        sbWidth.setProgress((int) (brushSize - 20.0f));
        sbWidth.setOnSeekBarChangeListener(new OnWidthSeekbarChangeListner());
        sbOffset.setMax(350);
        sbOffset.setProgress(offset);
        sbOffset.setOnSeekBarChangeListener(new OnOffsetSeekbarChangeListner());

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Bitmap imageviewImageBitmap = ((BitmapDrawable)touchImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
        Intent erase = new Intent(EraseActivity.this,MainActivity.class);
        erase.putExtra("BitmapImage2", byteArray);
        //bool = true;
        setResult(Activity.RESULT_OK,
                erase);
        finish();
    }


    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        //Toast.makeText(getApplicationContext(), "click item index = " + index, Toast.LENGTH_SHORT).show();
        if(index==0){
            Bitmap imageviewImageBitmap = ((BitmapDrawable)touchImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            //DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
            Intent erase = new Intent(EraseActivity.this,MainActivity.class);
            erase.putExtra("BitmapImage2", byteArray);
            //bool = true;
            setResult(Activity.RESULT_OK,
                    erase);
            finish();

        }
        else if(index==1){

            Bitmap imageviewImageBitmap = ((BitmapDrawable)touchImageView.getDrawable()).getBitmap();
            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();*/
            DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
            Intent erase = new Intent(EraseActivity.this,CropActivity2.class);
            //erase.putExtra("BitmapImage2", byteArray);
            erase.putExtra("CalledActivity", "Erase");
            //bool = true;
            startActivity(erase);

        }
        else if(index==2){

            Bitmap imageviewImageBitmap = ((BitmapDrawable)touchImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            System.out.println("asdf"+mydb.getCount());
            int id = mydb.getCount();
            mydb.addImage(id,byteArray);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage(getString(R.string.SuccessfullySaveForegroundGallery));
            builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            //Toast.makeText(this, getText(R.string.SuccessfullySaveForegroundGallery), Toast.LENGTH_SHORT).show();
        }
        else{
            if (ActivityCompat.checkSelfPermission(EraseActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(EraseActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EraseActivity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MainActivity.PERM_RQST_CODE);
            } else {
                saveImageInGallery();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setMessage(getString(R.string.SuccessfullySavePhotoAlbum));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            //Toast.makeText(this, "Saved to Photo Album", Toast.LENGTH_SHORT).show();
        }

        /*if(mInterstitialAd.isLoaded() && !CheckIf.isPurchased("admob",this)){
            mInterstitialAd.show();
        }*/
    }
    private void saveImageInGallery(){
        File file = FileUtil.getNewFile(EraseActivity.this,"Sticker");
        if (file != null) {
            try {
                StickerUtils.saveImageToGallery(file, createBitmap());
                StickerUtils.notifySystemGallery(EraseActivity.this, file);
            } catch (IllegalArgumentException | IllegalStateException ignored) {
                //
            }
            //stickerView.save(file);
            //Toast.makeText(EraseActivity.this, "saved in " + file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(EraseActivity.this, "the file is null", Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap createBitmap(){
        Bitmap imageviewImageBitmap = ((BitmapDrawable)touchImageView.getDrawable()).getBitmap();
        return imageviewImageBitmap;
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancle) {
        //Toast.makeText(getApplicationContext(), "dismissed isCancle = " + isCancle, Toast.LENGTH_SHORT).show();
    }

    public void resetPathArrays() {
        ivUndo.setEnabled(false);
        ivRedo.setEnabled(false);
        paths.clear();
        brushSizes.clear();
        redoPaths.clear();
        redoBrushSizes.clear();
    }

    public void resetRedoPathArrays() {
        ivRedo.setEnabled(false);
        redoPaths.clear();
        redoBrushSizes.clear();
    }

    public void undo() {
        int size = this.paths.size();
        if (size != 0) {
            if (size == 1) {
                this.ivUndo.setEnabled(false);
            }
            size--;
            redoPaths.add(paths.remove(size));
            redoBrushSizes.add(brushSizes.remove(size));
            if (!ivRedo.isEnabled()) {
                ivRedo.setEnabled(true);
            }
            UpdateCanvas();
        }
    }

    public void redo() {
        int size = redoPaths.size();
        if (size != 0) {
            if (size == 1) {
                ivRedo.setEnabled(false);
            }
            size--;
            paths.add(redoPaths.remove(size));
            brushSizes.add(redoBrushSizes.remove(size));
            if (!ivUndo.isEnabled()) {
                ivUndo.setEnabled(true);
            }
            UpdateCanvas();
        }
    }

    public void setBitMap() {
        this.isImageResized = false;
        if (resizedBitmap != null) {
            resizedBitmap.recycle();
            resizedBitmap = null;
        }
        if (bitmapMaster != null) {
            bitmapMaster.recycle();
            bitmapMaster = null;
        }
        canvasMaster = null;
        resizedBitmap = resizeBitmapByCanvas();

        lastEditedBitmap = resizedBitmap.copy(Config.ARGB_8888, true);
        bitmapMaster = Bitmap.createBitmap(lastEditedBitmap.getWidth(), lastEditedBitmap.getHeight(), Config.ARGB_8888);
        canvasMaster = new Canvas(bitmapMaster);
        canvasMaster.drawBitmap(lastEditedBitmap, 0.0f, 0.0f, null);
        touchImageView.setImageBitmap(bitmapMaster);
        resetPathArrays();
        touchImageView.setPan(false);
        brushImageView.invalidate();
    }


    public Bitmap resizeBitmapByCanvas() {
        float width;
        float heigth;
        float orginalWidth = (float) originalBitmap.getWidth();
        float orginalHeight = (float) originalBitmap.getHeight();
        if (orginalWidth > orginalHeight) {
            width = (float) imageViewWidth;
            heigth = (((float) imageViewWidth) * orginalHeight) / orginalWidth;
        } else {
            heigth = (float) imageViewHeight;
            width = (((float) imageViewHeight) * orginalWidth) / orginalHeight;
        }
        if (width > orginalWidth || heigth > orginalHeight) {
            return originalBitmap;
        }
        Bitmap background = Bitmap.createBitmap((int) width, (int) heigth, Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        float scale = width / orginalWidth;
        float yTranslation = (heigth - (orginalHeight * scale)) / 2.0f;
        Matrix transformation = new Matrix();
        transformation.postTranslate(0.0f, yTranslation);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(originalBitmap, transformation, paint);
        this.isImageResized = true;
        return background;
    }

    private void moveTopoint(float startx, float starty) {
        float zoomScale = getImageViewZoom();
        starty -= (float) offset;
        if (redoPaths.size() > 0) {
            resetRedoPathArrays();
        }
        PointF transLation = getImageViewTranslation();
        int projectedX = (int) ((float) (((double) (startx - transLation.x)) / ((double) zoomScale)));
        int projectedY = (int) ((float) (((double) (starty - transLation.y)) / ((double) zoomScale)));
        drawingPath.moveTo((float) projectedX, (float) projectedY);

        updatedBrushSize = (int) (brushSize / zoomScale);
    }

    private void lineTopoint(Bitmap bm, float startx, float starty) {
        if (initialDrawingCount < initialDrawingCountLimit) {
            initialDrawingCount += 1;
            if (initialDrawingCount == initialDrawingCountLimit) {
                isMultipleTouchErasing = true;
            }
        }
        float zoomScale = getImageViewZoom();
        starty -= (float) offset;
        PointF transLation = getImageViewTranslation();
        int projectedX = (int) ((float) (((double) (startx - transLation.x)) / ((double) zoomScale)));
        int projectedY = (int) ((float) (((double) (starty - transLation.y)) / ((double) zoomScale)));
        if (!isTouchOnBitmap && projectedX > 0 && projectedX < bm.getWidth() && projectedY > 0 && projectedY < bm.getHeight()) {
            isTouchOnBitmap = true;
        }
        drawingPath.lineTo((float) projectedX, (float) projectedY);
    }

    private void addDrawingPathToArrayList() {
        if (paths.size() >= undoLimit) {
            UpdateLastEiditedBitmapForUndoLimit();
            paths.remove(0);
            brushSizes.remove(0);
        }
        if (paths.size() == 0) {
            ivUndo.setEnabled(true);
            ivRedo.setEnabled(false);
        }
        brushSizes.add(updatedBrushSize);
        paths.add(drawingPath);
        drawingPath = new Path();
    }

    private void drawOnTouchMove() {
        Paint paint = new Paint();
        paint.setStrokeWidth((float) updatedBrushSize);
        paint.setColor(0);
        paint.setStyle(Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Join.ROUND);
        paint.setStrokeCap(Cap.ROUND);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
        canvasMaster.drawPath(drawingPath, paint);
        touchImageView.invalidate();
    }

    public void UpdateLastEiditedBitmapForUndoLimit() {
        Canvas canvas = new Canvas(lastEditedBitmap);
        for (int i = 0; i < 1; i += 1) {
            int brushSize = brushSizes.get(i);
            Paint paint = new Paint();
            paint.setColor(0);
            paint.setStyle(Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeJoin(Join.ROUND);
            paint.setStrokeCap(Cap.ROUND);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
            paint.setStrokeWidth((float) brushSize);
            canvas.drawPath(paths.get(i), paint);
        }
    }

    public void UpdateCanvas() {
        canvasMaster.drawColor(0, Mode.CLEAR);
        canvasMaster.drawBitmap(lastEditedBitmap, 0.0f, 0.0f, null);
        int i = 0;
        while (true) {
            if (i >= paths.size()) {
                break;
            }
            int brushSize = brushSizes.get(i);
            Paint paint = new Paint();
            paint.setColor(0);
            paint.setStyle(Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeJoin(Join.ROUND);
            paint.setStrokeCap(Cap.ROUND);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
            paint.setStrokeWidth((float) brushSize);
            canvasMaster.drawPath(paths.get(i), paint);
            i += 1;
        }
        touchImageView.invalidate();
    }

    public void updateBrushWidth() {
        brushImageView.width = brushSize / 2.0f;
        brushImageView.invalidate();
    }

    public void updateBrushOffset() {
        float doffest = ((float) offset) - brushImageView.offset;
        BrushImageView brushImageViewView = brushImageView;
        brushImageViewView.centery += doffest;
        brushImageView.offset = (float) offset;
        brushImageView.invalidate();
    }

    public void updateBrush(float x, float y) {
        brushImageView.offset = (float) offset;
        brushImageView.centerx = x;
        brushImageView.centery = y;
        brushImageView.width = brushSize / 2.0f;
        brushImageView.invalidate();
    }

    public float getImageViewZoom() {
        return touchImageView.getCurrentZoom();
    }

    public PointF getImageViewTranslation() {
        return touchImageView.getTransForm();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onDestroy() {
        super.onDestroy();
        UpdateCanvas();
        if (lastEditedBitmap != null) {
            lastEditedBitmap.recycle();
            lastEditedBitmap = null;
        }
        if (originalBitmap != null) {
            originalBitmap.recycle();
            originalBitmap = null;
        }
        if (resizedBitmap != null) {
            resizedBitmap.recycle();
            resizedBitmap = null;
        }
        if (bitmapMaster != null) {
            bitmapMaster.recycle();
            bitmapMaster = null;
        }
        if (this.highResolutionOutput != null) {
            this.highResolutionOutput.recycle();
            this.highResolutionOutput = null;
        }
    }

    private class OnTouchListner implements OnTouchListener {
        OnTouchListner() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if (!(event.getPointerCount() == 1 || isMultipleTouchErasing)) {
                if (initialDrawingCount > 0) {
                    UpdateCanvas();
                    drawingPath.reset();
                    initialDrawingCount = 0;
                }
                touchImageView.onTouchEvent(event);
                MODE = 2;
            } else if (action == MotionEvent.ACTION_DOWN) {
                isTouchOnBitmap = false;
                touchImageView.onTouchEvent(event);
                MODE = 1;
                initialDrawingCount = 0;
                isMultipleTouchErasing = false;
                moveTopoint(event.getX(), event.getY());

                updateBrush(event.getX(), event.getY());
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (MODE == 1) {
                    currentx = event.getX();
                    currenty = event.getY();

                    updateBrush(currentx, currenty);
                    lineTopoint(bitmapMaster, currentx, currenty);

                    drawOnTouchMove();
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
                if (MODE == 1) {
                    if (isTouchOnBitmap) {
                        addDrawingPathToArrayList();
                    }
                }
                isMultipleTouchErasing = false;
                initialDrawingCount = 0;
                MODE = 0;
            }
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
                MODE = 0;
            }
            return true;
        }
    }

    private class OnWidthSeekbarChangeListner implements OnSeekBarChangeListener {
        OnWidthSeekbarChangeListner() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            brushSize = ((float) progress) + 20.0f;
            updateBrushWidth();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    private class OnOffsetSeekbarChangeListner implements OnSeekBarChangeListener {
        OnOffsetSeekbarChangeListner() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            offset = progress;
            updateBrushOffset();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    private void saveImage() {
        makeHighResolutionOutput();
        new imageSaveByAsync().execute(new String[0]);
    }

    private void makeHighResolutionOutput() {
        if (this.isImageResized) {
            Bitmap solidColor = Bitmap.createBitmap(this.originalBitmap.getWidth(), this.originalBitmap.getHeight(), this.originalBitmap.getConfig());
            Canvas canvas = new Canvas(solidColor);
            Paint paint = new Paint();
            paint.setColor(Color.argb(255, 255, 255, 255));
            Rect src = new Rect(0, 0, this.bitmapMaster.getWidth(), this.bitmapMaster.getHeight());
            Rect dest = new Rect(0, 0, this.originalBitmap.getWidth(), this.originalBitmap.getHeight());
            canvas.drawRect(dest, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            canvas.drawBitmap(this.bitmapMaster, src, dest, paint);
            this.highResolutionOutput = null;
            this.highResolutionOutput = Bitmap.createBitmap(this.originalBitmap.getWidth(), this.originalBitmap.getHeight(), this.originalBitmap.getConfig());
            Canvas canvas1 = new Canvas(this.highResolutionOutput);
            canvas1.drawBitmap(this.originalBitmap, 0.0f, 0.0f, null);
            Paint paint1 = new Paint();
            paint1.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            canvas1.drawBitmap(solidColor, 0.0f, 0.0f, paint1);
            if (solidColor != null && !solidColor.isRecycled()) {
                solidColor.recycle();
                solidColor = null;
            }
            return;
        }
        this.highResolutionOutput = null;
        this.highResolutionOutput = this.bitmapMaster.copy(this.bitmapMaster.getConfig(), true);
    }

    private class imageSaveByAsync extends AsyncTask<String, Void, Boolean> {
        private imageSaveByAsync() {
        }

        protected void onPreExecute() {
            getWindow().setFlags(16, 16);
        }

        protected Boolean doInBackground(String... args) {
            try {
                savePhoto(highResolutionOutput);
                return Boolean.valueOf(true);
            } catch (Exception e) {
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean success) {
            Toast toast = Toast.makeText(getBaseContext(), "PNG Saved", Toast.LENGTH_LONG);
            toast.setGravity(17, 0, 0);
            toast.show();
            getWindow().clearFlags(16);

        }
    }

    public void savePhoto(Bitmap bmp) {
        File imageFileName;
        FileOutputStream out;
        File imageFileFolder = new File(Environment.getExternalStorageDirectory(), "ImageEraser");
        imageFileFolder.mkdir();
//        File sdCard = Environment.getExternalStorageDirectory();
//        File imageFileFolder = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
//        imageFileFolder.mkdirs();
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.MONTH))
                + String.valueOf(c.get(Calendar.DAY_OF_MONTH))
                + String.valueOf(c.get(Calendar.YEAR))
                + String.valueOf(c.get(Calendar.HOUR_OF_DAY))
                + String.valueOf(c.get(Calendar.MINUTE))
                + String.valueOf(c.get(Calendar.SECOND));
        FileOutputStream out2;


        imageFileName = new File(imageFileFolder, date.toString() + ".png");
        try {
            out2 = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out2);
            out = out2;
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }
        scanPhoto(imageFileName.toString());
    }

    public void scanPhoto(String imageFileName) {
        this.msConn = new MediaScannerConnection(this, new ScanPhotoConnection(imageFileName));
        this.msConn.connect();
    }

    class ScanPhotoConnection implements MediaScannerConnection.MediaScannerConnectionClient {
        final String val$imageFileName;

        ScanPhotoConnection(String str) {
            this.val$imageFileName = str;
        }

        public void onMediaScannerConnected() {
            msConn.scanFile(this.val$imageFileName, null);
        }

        public void onScanCompleted(String path, Uri uri) {
            msConn.disconnect();
        }
    }





}
