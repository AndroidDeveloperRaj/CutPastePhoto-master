package com.odyssey.apps.cutpastephoto;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.odyssey.apps.cutpastephoto.AddText.EditTextActivity;
import com.odyssey.apps.cutpastephoto.Admobs.Advertisement;
import com.odyssey.apps.cutpastephoto.CustomClass.Custom;
import com.odyssey.apps.cutpastephoto.IAP.IAPActivity;
import com.odyssey.apps.cutpastephoto.Settings.SettingsActivity;
import com.odyssey.apps.cutpastephoto.StaticClasses.NotificationCenter;
import com.odyssey.apps.cutpastephoto.util.FileUtil;
import com.baoyz.actionsheet.ActionSheet;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.odyssey.apps.cutpastephoto.More.MoreAppsActivity;
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
import com.tjeannin.apprate.AppRate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener{

    private static final int REQUEST_ERASE_STICKER = 0;
    private static final int REQUEST_GET_TEXT_STICKER = 1;
    private static final int REQUEST_GET_CROP_STICKER = 2;
    private static final int PHOTO_PICKER_ID = 3;
    private static final int CAMERA_REQUEST = 4;
    private static final int REQUEST_GET_FILTER_IMAGE = 5;
    private static final int REQUEST_GET_BLEND_IMAGE = 6;
    private static final int REQUEST_GET_HELP = 7;
    private static final int REQUEST_BACK_FROM_SHARE_INTEND = 8;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int PERM_RQST_CODE = 110;
    private StickerView stickerView;
    private TextSticker sticker;
    private GridView gridView;
    private RelativeLayout gridViewLayout;
    private StickerAdapter stickerAdapter;
    private ForegroundAdapter foregroundAdapter;
    private Boolean isImagePicker;
    private Boolean isChangeBG;
    private TextView gridViewTitle;
    private DatabaseHelper mydb ;
    private InterstitialAd mInterstitialAd;
    private Boolean isBackground;
    public enum StickerSet{
        Foreground,Background,Smileys,None
    }
    public enum RequestSet{
        Foreground,Background,SaveShare,Smileys,None
    }
    SharedPreferences preferences1;
    Boolean isGoSettingsPage;
    private RequestSet requestSet = RequestSet.None;
    private StickerSet stickerSet = StickerSet.None;
    private ImageView bgImage;
    private ImageView helpImageView;
    private ImageView helpImageView2;
    private Button crossButton;
    Uri imageUri ;
    private ArrayList<Bitmap> mThumbIdsFg = new ArrayList<Bitmap>();
    public Integer[] mhelpIds = {
            R.drawable.instr1, R.drawable.instr2, R.drawable.instr3,R.drawable.instr4,
            R.drawable.instr5, R.drawable.instr6, R.drawable.instr7,R.drawable.instr8,
            R.drawable.instr9, R.drawable.instr10
    };

    public Integer[] mThumbIdsbg = {
            R.drawable.camera, R.drawable.bg1, R.drawable.bg12, R.drawable.bg34,R.drawable.bg45, R.drawable.bg56,R.drawable.bg67, R.drawable.bg78,R.drawable.bg89, R.drawable.bg100,R.drawable.bg111, R.drawable.bg122,R.drawable.bg133, R.drawable.bg144,R.drawable.bg155, R.drawable.bg166,R.drawable.bg177, R.drawable.bg188,R.drawable.bg199,
            R.drawable.bg2, R.drawable.bg13,R.drawable.bg24, R.drawable.bg35,R.drawable.bg46, R.drawable.bg57,R.drawable.bg68, R.drawable.bg79,R.drawable.bg90, R.drawable.bg101,R.drawable.bg112, R.drawable.bg123,R.drawable.bg134, R.drawable.bg145,R.drawable.bg156, R.drawable.bg167,R.drawable.bg178, R.drawable.bg189,R.drawable.bg200,
            R.drawable.bg3, R.drawable.bg14,R.drawable.bg25, R.drawable.bg36,R.drawable.bg47, R.drawable.bg58,R.drawable.bg69, R.drawable.bg80,R.drawable.bg91, R.drawable.bg102,R.drawable.bg113, R.drawable.bg124,R.drawable.bg135, R.drawable.bg146,R.drawable.bg157, R.drawable.bg168,R.drawable.bg179, R.drawable.bg190,R.drawable.bg201,
            R.drawable.bg4, R.drawable.bg15,R.drawable.bg26, R.drawable.bg37,R.drawable.bg48, R.drawable.bg59,R.drawable.bg70, R.drawable.bg81,R.drawable.bg92, R.drawable.bg103,R.drawable.bg114, R.drawable.bg125,R.drawable.bg136, R.drawable.bg147,R.drawable.bg158, R.drawable.bg169,R.drawable.bg180, R.drawable.bg191,R.drawable.bg202,
            R.drawable.bg5, R.drawable.bg16,R.drawable.bg27, R.drawable.bg38,R.drawable.bg49, R.drawable.bg60,R.drawable.bg71, R.drawable.bg82,R.drawable.bg93, R.drawable.bg104,R.drawable.bg115, R.drawable.bg126,R.drawable.bg137, R.drawable.bg148,R.drawable.bg159, R.drawable.bg170,R.drawable.bg181, R.drawable.bg192,R.drawable.bg203,
            R.drawable.bg6, R.drawable.bg17,R.drawable.bg28, R.drawable.bg39,R.drawable.bg50, R.drawable.bg61,R.drawable.bg72, R.drawable.bg83,R.drawable.bg94, R.drawable.bg105,R.drawable.bg116, R.drawable.bg127,R.drawable.bg138, R.drawable.bg149,R.drawable.bg160, R.drawable.bg171,R.drawable.bg182, R.drawable.bg193,R.drawable.bg204,
            R.drawable.bg7, R.drawable.bg18,R.drawable.bg29, R.drawable.bg40,R.drawable.bg51, R.drawable.bg62,R.drawable.bg73, R.drawable.bg84,R.drawable.bg95, R.drawable.bg106,R.drawable.bg117, R.drawable.bg128,R.drawable.bg139, R.drawable.bg150,R.drawable.bg161, R.drawable.bg172,R.drawable.bg183, R.drawable.bg194,
            R.drawable.bg8, R.drawable.bg19,R.drawable.bg30, R.drawable.bg41,R.drawable.bg52, R.drawable.bg63,R.drawable.bg74, R.drawable.bg85,R.drawable.bg96, R.drawable.bg107,R.drawable.bg118, R.drawable.bg129,R.drawable.bg140, R.drawable.bg151,R.drawable.bg162, R.drawable.bg173,R.drawable.bg184, R.drawable.bg195,
            R.drawable.bg9, R.drawable.bg20,R.drawable.bg31, R.drawable.bg42,R.drawable.bg53, R.drawable.bg64,R.drawable.bg75, R.drawable.bg86,R.drawable.bg97, R.drawable.bg108,R.drawable.bg119, R.drawable.bg130,R.drawable.bg141, R.drawable.bg152,R.drawable.bg163, R.drawable.bg174,R.drawable.bg185, R.drawable.bg196,
            R.drawable.bg10, R.drawable.bg21,R.drawable.bg32, R.drawable.bg43,R.drawable.bg54, R.drawable.bg65,R.drawable.bg76, R.drawable.bg87,R.drawable.bg98, R.drawable.bg109,R.drawable.bg120, R.drawable.bg131,R.drawable.bg142, R.drawable.bg153,R.drawable.bg164, R.drawable.bg175,R.drawable.bg186, R.drawable.bg197,
            R.drawable.bg11, R.drawable.bg22,R.drawable.bg33, R.drawable.bg44,R.drawable.bg55, R.drawable.bg66,R.drawable.bg77, R.drawable.bg88,R.drawable.bg99, R.drawable.bg110,R.drawable.bg121, R.drawable.bg132,R.drawable.bg143, R.drawable.bg154,R.drawable.bg165, R.drawable.bg176,R.drawable.bg187, R.drawable.bg198
    };
    public Integer[] mThumbIdsstk = {
            R.drawable.smily1, R.drawable.smily2,R.drawable.smily3, R.drawable.smily4,R.drawable.smily5, R.drawable.smily6,R.drawable.smily7, R.drawable.smily8,
            R.drawable.smily9, R.drawable.smily10,R.drawable.smily11, R.drawable.smily12,R.drawable.smily13, R.drawable.smily14,R.drawable.smily15, R.drawable.smily16,
            R.drawable.smily17, R.drawable.smily18,R.drawable.smily19, R.drawable.smily20,R.drawable.smily21, R.drawable.smily22,R.drawable.smily23, R.drawable.smily24,
            R.drawable.smily25, R.drawable.smily26,R.drawable.smily27, R.drawable.smily28,R.drawable.smily29, R.drawable.smily30,R.drawable.smily31, R.drawable.smily32,
            R.drawable.smily33, R.drawable.smily34,R.drawable.smily35, R.drawable.smily36,R.drawable.smily37, R.drawable.smily38,R.drawable.smily39, R.drawable.smily40,
            R.drawable.smily41, R.drawable.smily42,R.drawable.smily43, R.drawable.smily44,R.drawable.smily45, R.drawable.smily46,R.drawable.smily47, R.drawable.smily48,
            R.drawable.smily49, R.drawable.smily50,R.drawable.smily51, R.drawable.smily52,R.drawable.smily53, R.drawable.smily54,R.drawable.smily55, R.drawable.smily56,
            R.drawable.smily57, R.drawable.smily58,R.drawable.smily59, R.drawable.smily60,R.drawable.smily61, R.drawable.smily62,R.drawable.smily63, R.drawable.smily64,
            R.drawable.smily65, R.drawable.smily66,R.drawable.smily67, R.drawable.smily68,R.drawable.smily69, R.drawable.smily70,R.drawable.smily71, R.drawable.smily72,
            R.drawable.smily73, R.drawable.smily74,R.drawable.smily75, R.drawable.smily76,R.drawable.smily77, R.drawable.smily78,R.drawable.smily79, R.drawable.smily80,
            R.drawable.smily81, R.drawable.smily82,R.drawable.smily83, R.drawable.smily84,R.drawable.smily85, R.drawable.smily86,R.drawable.smily87, R.drawable.smily88,
            R.drawable.smily89, R.drawable.smily90,R.drawable.smily91, R.drawable.smily92,R.drawable.smily93, R.drawable.smily94,R.drawable.smily95,R.drawable.smily96,
            R.drawable.smily97, R.drawable.smily98,R.drawable.smily99, R.drawable.smily100,R.drawable.smily101, R.drawable.smily102,R.drawable.smily103,R.drawable.smily104,
            R.drawable.smily105, R.drawable.smily106,R.drawable.smily107, R.drawable.smily108,R.drawable.smily109, R.drawable.smily110,R.drawable.smily111,R.drawable.smily112,
            R.drawable.smily113, R.drawable.smily114,R.drawable.smily115, R.drawable.smily116,R.drawable.smily117, R.drawable.smily118,
            R.drawable.smily119, R.drawable.smily120,R.drawable.smily121, R.drawable.smily122,R.drawable.smily123, R.drawable.smily124,
            R.drawable.smily125, R.drawable.smily126,R.drawable.smily127, R.drawable.smily128,R.drawable.smily129, R.drawable.smily130,
            R.drawable.smily131, R.drawable.smily132,R.drawable.smily133, R.drawable.smily134,R.drawable.smily135

    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        System.out.println("OnCreate");


        mydb = new DatabaseHelper(this);


        stickerView = (StickerView) findViewById(R.id.sticker_view);
        crossButton = (Button) findViewById(R.id.button1);
        bgImage = (ImageView) findViewById(R.id.BgImageView);
        helpImageView = (ImageView) findViewById(R.id.helpImageView);
        helpImageView2 = (ImageView) findViewById(R.id.helpImageView2);
        gridViewLayout = (RelativeLayout) findViewById(R.id.gridViewLayout);
        gridViewTitle = (TextView) findViewById(R.id.gridViewTitle);

        isBackground=false;

        //check first time
        SharedPreferences preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        Boolean isFirstTime = preferences.getBoolean("firstTime",true);
        if(isFirstTime) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
            showHelpScreen();
        }
        //preferences1 = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //isGoSettingsPage = preferences1.getBoolean("goSettings",false);



        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.bg4);
        bgImage.setBackground(new BitmapDrawable(getResources(), resizeBitmap(bmp)));
        DataPassingSingelton.getInstance().setBGImage(bmp);

        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.close),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.scale),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.flip),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.edit),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new HelloIconEvent());

        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, heartIcon));

        //default icon layout
        //stickerView.configDefaultIcons();

        stickerView.setBackgroundColor(Color.WHITE); // previous WHITE CBKH
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        /*sticker = new TextSticker(this);

        sticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.sticker_transparent_background));
        sticker.setText("Hello, CutPaste!");
        sticker.setTextColor(Color.BLACK);

        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();*/

        gridView = (GridView)findViewById(R.id.gridview);



        helpImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id

                if(HelpScreen.imageno==10){
                    helpImageView.setVisibility(View.INVISIBLE);
                    helpImageView2.setVisibility(View.INVISIBLE);
                    HelpScreen.imageno=1;
                }
                Bitmap icon = BitmapFactory.decodeResource(getResources(),mhelpIds[HelpScreen.imageno]);
                helpImageView.setImageBitmap(resizeBitmap(icon));
                HelpScreen.changeScreen();
            }
        });
        //StickerAdapter stickerAdapter = new StickerAdapter(this,mThumbIds);
        //gridView.setAdapter(stickerAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(stickerSet == StickerSet.Background){

                    if(i==0){
                        //requestSet = RequestSet.Background;
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED
                                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_RQST_CODE);
                        } else {
                            ActionSheet.createBuilder(MainActivity.this, getSupportFragmentManager())
                                    .setCancelButtonTitle(getString(R.string.Cancel))
                                    .setOtherButtonTitles(getString(R.string.PHOTO_LIBRARY), getString(R.string.CAMERA))
                                    .setCancelableOnTouchOutside(true)
                                    .setListener(MainActivity.this).show();
                            isImagePicker = true;
                            isChangeBG=false;
                        }

                    }
                    else{
                        if(!CheckIf.isPurchased("background", MainActivity.this) && i%2!=0){
                            Intent iap = new Intent(MainActivity.this,IAPActivity.class);
                            startActivity(iap);

                        }
                        else {
                            Bitmap imageviewImageBitmap = ((BitmapDrawable)getResources().getDrawable(mThumbIdsbg[i])).getBitmap();
                            if(imageviewImageBitmap.getWidth()>1500||imageviewImageBitmap.getHeight()>1500)
                                imageviewImageBitmap = resizeWithRatio(imageviewImageBitmap,1500,1500);

                            DataPassingSingelton.getInstance().setBGImage(imageviewImageBitmap);
                            goCropView(imageviewImageBitmap);
                        }
                    }

                }
                else if(stickerSet == StickerSet.Foreground){
                    if(i==0){
                        //requestSet = RequestSet.Background;
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED
                                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_RQST_CODE);
                        } else {
                            ActionSheet.createBuilder(MainActivity.this, getSupportFragmentManager())
                                    .setCancelButtonTitle(getString(R.string.Cancel))
                                    .setOtherButtonTitles(getString(R.string.PHOTO_LIBRARY), getString(R.string.CAMERA))
                                    .setCancelableOnTouchOutside(true)
                                    .setListener(MainActivity.this).show();
                            isImagePicker = true;
                            isChangeBG=false;
                        }
                    }
                    else{
                        final int indx = i;
                        new AlertView.Builder().setContext(MainActivity.this)
                                .setStyle(AlertView.Style.Alert)
                                .setTitle(null)
                                .setMessage(null)
                                .setCancelText(getString(R.string.Cancel))
                                .setDestructive(null)
                                .setOthers(new String[]{getString(R.string.Apply), getString(R.string.DeleteFromGallery),getString(R.string.Share)})
                                .setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {
                                        if(position==0){
                                            stickerView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), mThumbIdsFg.get(indx))));
                                            gridViewLayout.setVisibility(View.INVISIBLE);

                                        }
                                        else if(position==1){



                                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setCancelable(true);
                                            builder.setMessage(getString(R.string.AreYouSureMsg));
                                            builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //if user pressed "yes", then he is allowed to exit from application
                                                    mydb.deleteImage(indx);
                                                    restoreForegroundData();
                                                    changeForegroundAdapter(mThumbIdsFg);
                                                }
                                            });
                                            builder.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //if user select "No", just cancel this dialog and continue with app
                                                    dialog.cancel();
                                                }
                                            });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                        else if(position==2){
                                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED
                                                    || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MainActivity.this,
                                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_RQST_CODE);
                                            } else {

                                                Bitmap icon = mThumbIdsFg.get(indx);
                                                Intent share = new Intent(Intent.ACTION_SEND);
                                                share.setType("image/jpeg");
                                                String filename = Custom.makeFileNameFrom(".jpg");
                                                saveTempFile(icon, filename);
                                                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/" + filename));
                                                startActivity(Intent.createChooser(share, "Share Image"));
                                            }
                                        }
                                    }


                                })
                                .build()
                                .show();



                    }


                }
                else if(stickerSet == StickerSet.Smileys){
                    if(!CheckIf.isPurchased("sticker", MainActivity.this) && i>14){
                        Intent iap = new Intent(MainActivity.this,IAPActivity.class);
                        startActivity(iap);
                    }
                    else {
                        //ImageView imgView = (ImageView) view;
                        stickerView.addSticker(new DrawableSticker(getResources().getDrawable(mThumbIdsstk[i])));
                        gridViewLayout.setVisibility(View.INVISIBLE);
                        //crossButton.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                /*if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    stickerView.replace(sticker);
                    stickerView.invalidate();
                }
                Log.d(TAG, "onStickerClicked");*/
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerTouchedDown");
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d(TAG, "onDoubleTapped: double tap will be with two click");

            }

            @Override
            public void onStickerEdit(@NonNull Sticker sticker) {
                Bitmap imageviewImageBitmap;
                imageviewImageBitmap = ((BitmapDrawable) stickerView.currentDrawable()).getBitmap();

                /*if (sticker instanceof TextSticker) {
                    imageviewImageBitmap = ((BitmapDrawable) stickerView.currentDrawable()).getBitmap();
                }
                else
                    imageviewImageBitmap = ((GradientDrawable) stickerView.currentDrawable()).getBitmap();*/
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageviewImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();*/
                //boolean bool = false;
                DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
                Intent erase = new Intent(MainActivity.this,EraseActivity.class);
                //erase.putExtra("EraseImage", byteArray);
                startActivityForResult(erase, REQUEST_ERASE_STICKER);
                //stickerView.remove(stickerView.getCurrentSticker());

            }
            @Override
            public void onStickerNotClicked() {
                stickerView.invalidate();
            }
        });





        /*button = findViewById(R.id.change_Image);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveImage();
                //changeBgImage();
                // Code here executes on main thread after user presses button
            }
        });*/





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
                                findViewById(R.id.AMAdmob);
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
                        //Toast.makeText(MainActivity.this, "Ad Content loading", Toast.LENGTH_SHORT).show();
                        FrameLayout frameLayout =
                                findViewById(R.id.AMAdmob);
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
                        //Toast.makeText(MainActivity.this, "Failed Ad loading", Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        //Admob Visibility
        //findViewById(R.id.AMAdmob).setVisibility(View.GONE);
        if (!CheckIf.isPurchased("admob",this)){
            adLoader.loadAd(new AdRequest.Builder().build());
        }

        } catch (Exception e){

        }


        NotificationCenter.addReceiver("Purchased",mMessageReceiver,this);

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

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        System.out.println("onnewintent");
        /*Bundle extras = intent.getExtras();
        byte[] byteArray = extras.getByteArray("CropedImage");
        Bitmap originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);*/
        Bitmap originalBitmap = DataPassingSingelton.getInstance().getImage();
        Drawable drawable = new BitmapDrawable(getResources(), originalBitmap);
        stickerView.replace(new DrawableSticker(drawable));
        //DataPassingSingelton.getInstance().freeMemory();

    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(getString(R.string.AppExitMessage));
        builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_BACK_FROM_SHARE_INTEND){

            new AppRate(MainActivity.this).init();

        }
        if (requestCode == REQUEST_ERASE_STICKER && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            byte[] byteArray = extras.getByteArray("BitmapImage2");
            Bitmap originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            //Bitmap originalBitmap = DataPassingSingelton.getInstance().getImage();
            Drawable drawable = new BitmapDrawable(getResources(), originalBitmap);
            //Drawable drawable = originalBitmap;
            System.out.println("asdf");

            stickerView.replace(new DrawableSticker(drawable));
            //stickerView.addSticker(new DrawableSticker(drawable));
        }
        if (requestCode == REQUEST_GET_TEXT_STICKER && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            /*Drawable bubble = ContextCompat.getDrawable(this, R.drawable.bubble);
            stickerView.addSticker(
                    new TextSticker(getApplicationContext())
                            .setDrawable(bubble)
                            .setText(extras.getString("TextString"))
                            .setMaxTextSize(14)
                            .resizeText()
                    , Sticker.Position.TOP);*/


            final TextSticker sticker = new TextSticker(this);
            sticker.setText(extras.getString("TextString"));
            sticker.setTextColor(extras.getInt("TextColor"));
            sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
            sticker.setTypeface(Typeface.createFromAsset(getAssets(), extras.getString("TextFont")));
            sticker.resizeText();

            stickerView.addSticker(sticker);
        }
        if (requestCode == REQUEST_GET_CROP_STICKER && resultCode == Activity.RESULT_OK) {
            //stickerView.addSticker();
            //Bundle extras = data.getExtras();
            //byte[] byteArray = extras.getByteArray("CropedImage");
            //Bitmap originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap originalBitmap = DataPassingSingelton.getInstance().getImage();

            if(stickerSet == StickerSet.Background){
                Bitmap resizedBitmap = resizeBitmap(originalBitmap);
                Drawable drawable = new BitmapDrawable(getResources(), resizedBitmap);
                changeBgImage(drawable);
            }
            else if(stickerSet == StickerSet.Foreground){
                Drawable drawable = new BitmapDrawable(getResources(), originalBitmap);
                stickerView.addSticker(new DrawableSticker(drawable));
            }
            //DataPassingSingelton.getInstance().freeMemory();
        }
        if (requestCode == PHOTO_PICKER_ID && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            try {
                Bitmap originalBitmap = getBitmapFromUri(selectedImageUri);
                if(stickerSet == StickerSet.Foreground) {
                    if (originalBitmap.getWidth() > 500 || originalBitmap.getHeight() > 500)
                        originalBitmap = resizeWithRatio(originalBitmap, 500, 500);
                }
                else if(stickerSet == StickerSet.Background){
                    if (originalBitmap.getWidth() > 1500 || originalBitmap.getHeight() > 1500)
                        originalBitmap = resizeWithRatio(originalBitmap, 1500, 1500);
                }
                //------------------change orientation-----------------
                Bitmap bm=originalBitmap;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.moveToFirst();
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                int rotateImage = getCameraPhotoOrientation(this, selectedImageUri, filePath);
                System.out.println("asdf"+rotateImage);
                Matrix matrix = new Matrix();
                matrix.postRotate(rotateImage);
                originalBitmap = Bitmap.createBitmap(bm , 0, 0,
                        bm.getWidth(), bm.getHeight(),
                        matrix, true);
                //------------------change orientation---------------------

                if(stickerSet==StickerSet.Background)
                    DataPassingSingelton.getInstance().setBGImage(originalBitmap);
                if(isBackground==true){
                    goCropView(originalBitmap);
                }
                else {
                    goCropView2(originalBitmap);
                }
                /*Bitmap resizedBitmap = resizeBitmap(originalBitmap);
                Drawable drawable = new BitmapDrawable(getResources(), resizedBitmap);
                changeBgImage(drawable);*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap originalBitmap = DataPassingSingelton.getInstance().getBGImage();//(Bitmap) data.getExtras().get("data");
            try{
                originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }

            String filename = Environment.getExternalStorageDirectory() + File.separator + "cutpastecamera";
            originalBitmap = changeOrientation(imageUri,originalBitmap,filename);

            if(stickerSet == StickerSet.Foreground) {
                if (originalBitmap.getWidth() > 500 || originalBitmap.getHeight() > 500)
                    originalBitmap = resizeWithRatio(originalBitmap, 500, 500);
            }

            if(stickerSet == StickerSet.Background) {
                if (originalBitmap.getWidth() > 1000 || originalBitmap.getHeight() > 1000)
                    originalBitmap = resizeWithRatio(originalBitmap, 1000, 1000);
            }

            if(stickerSet==StickerSet.Background)
            {

                DataPassingSingelton.getInstance().setBGImage(originalBitmap);
            }

//            goCropView2(originalBitmap);
            if(isBackground==true){
                goCropView(originalBitmap);
            }
            else {
                goCropView2(originalBitmap);
            }

        }
        if ((requestCode == REQUEST_GET_BLEND_IMAGE || requestCode == REQUEST_GET_FILTER_IMAGE) && resultCode == Activity.RESULT_OK) {
            stickerView.removeAllStickers();
            Bitmap originalBitmap = DataPassingSingelton.getInstance().getImage();
            Bitmap resizedBitmap = resizeBitmap(originalBitmap);
            Drawable drawable = new BitmapDrawable(getResources(), resizedBitmap);
            changeBgImage(drawable);
            //DataPassingSingelton.getInstance().freeMemory2();
        }
        if (requestCode == REQUEST_GET_HELP && resultCode == Activity.RESULT_OK) {
            HelpScreen.imageno=1;
            showHelpScreen();
        }
    }
    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
    private void goCropView(Bitmap imageviewImageBitmap){
        Intent crop = new Intent(MainActivity.this,CropActivity.class);
        DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
        crop.putExtra("CalledActivity", "Main");
        startActivityForResult(crop, REQUEST_GET_CROP_STICKER);
        gridViewLayout.setVisibility(View.INVISIBLE);
        //crossButton.setVisibility(View.INVISIBLE);
    }


    private void goCropView2(Bitmap imageviewImageBitmap){
        Intent crop = new Intent(MainActivity.this,CropActivity2.class);
        DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
        crop.putExtra("CalledActivity", "Main");
        startActivityForResult(crop, REQUEST_GET_CROP_STICKER);
        gridViewLayout.setVisibility(View.INVISIBLE);
        //crossButton.setVisibility(View.INVISIBLE);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    private Bitmap resizeBitmap(Bitmap originalBitmap){
        double width = dipToPixels(originalBitmap.getWidth());
        double height = dipToPixels(originalBitmap.getHeight());
        double imgRatio = width/height;
        double newWidth;
        double newHeight;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double Sheight = displayMetrics.heightPixels-dipToPixels(150);
        double Swidth = displayMetrics.widthPixels;
        System.out.println("jkl"+Sheight);
        System.out.println("jkl"+Swidth);
        double SRatio = Swidth/Sheight;
        if(imgRatio>SRatio){
            newWidth = Swidth;
            newHeight = Swidth/imgRatio;
        }
        else{
            newWidth = Sheight*imgRatio;
            newHeight = Sheight;
        }
        if (Math.ceil(newHeight) != Math.floor(newHeight)){
            newHeight = Math.floor(newHeight) + 0.5;
        }
        if (Math.ceil(newWidth) != Math.floor(newWidth)){
            newWidth = Math.floor(newWidth) + 0.5;
        }
        System.out.println(newWidth);
        System.out.println(newHeight);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, (int)newWidth, (int)newHeight, true);
        return resizedBitmap;
    }
    public double dipToPixels(int dipValue) {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }


    @Override
    protected void onStart() {
        super.onStart();
        restoreForegroundData();
    }
    private void restoreForegroundData(){
        ArrayList<Bitmap> bmp = mydb.getAllData();
        mThumbIdsFg.clear();
        mThumbIdsFg.add(BitmapFactory.decodeResource(getResources(), R.drawable.camera));
        for(int i=1;i<=bmp.size();i++){
            mThumbIdsFg.add(bmp.get(i-1));
        }
    }

    public void saveImage(){
        File file = FileUtil.getNewFile(MainActivity.this, "Sticker");
        Bitmap result = stickerView.createBitmap();
        Bitmap icon =Bitmap.createBitmap(result,bgImage.getLeft(), bgImage.getTop(), bgImage.getWidth(), bgImage.getHeight());
        try {


            StickerUtils.saveImageToGallery(file, icon);
            StickerUtils.notifySystemGallery(this, file);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage(getString(R.string.SuccessfullySavePhotoAlbum));
            builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    dialog.cancel();
                    new AppRate(MainActivity.this).init();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (IllegalArgumentException | IllegalStateException ignored) {
            //
        }
        /*if (file != null) {
            stickerView.save(file);
            //Toast.makeText(MainActivity.this, "saved in " + file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.this, "the file is null", Toast.LENGTH_SHORT).show();
        }*/
    }

    /*private void loadSticker() {
        Drawable drawable =
                ContextCompat.getDrawable(this, R.drawable.haizewang_215);
        Drawable drawable1 =
                ContextCompat.getDrawable(this, R.drawable.haizewang_23);
        stickerView.addSticker(new DrawableSticker(drawable));
        stickerView.addSticker(new DrawableSticker(drawable1), Sticker.Position.BOTTOM | Sticker.Position.RIGHT);

        Drawable bubble = ContextCompat.getDrawable(this, R.drawable.bubble);
        stickerView.addSticker(
                new TextSticker(getApplicationContext())
                        .setDrawable(bubble)
                        .setText("Sticker\n")
                        .setMaxTextSize(14)
                        .resizeText()
                , Sticker.Position.TOP);
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERM_RQST_CODE:
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

    public Bitmap changeOrientation(Uri selectedImageUri, Bitmap originalBitmap, String filePath){
        //------------------change orientation-----------------
        Bitmap bm=originalBitmap;
        /*String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();*/

        int rotateImage = getCameraPhotoOrientation(this, selectedImageUri, filePath);
        System.out.println("asdf"+rotateImage);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateImage);
        originalBitmap = Bitmap.createBitmap(bm , 0, 0,
                bm.getWidth(), bm.getHeight(),
                matrix, true);
        //------------------change orientation---------------------
        return originalBitmap;
    }

    public void testReplace(View view) {
        if (stickerView.replace(sticker)) {
            //Toast.makeText(MainActivity.this, "Replace Sticker successfully!", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.this, "Replace Sticker failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void testLock(View view) {
        stickerView.setLocked(!stickerView.isLocked());
    }

    public void testRemove(View view) {
        if (stickerView.removeCurrentSticker()) {
            //Toast.makeText(MainActivity.this, "Remove current Sticker successfully!", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.this, "Remove current Sticker failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void testRemoveAll(View view) {
        stickerView.removeAllStickers();
    }

    public void addText(View view) {
        if (gridViewLayout.getVisibility() == View.VISIBLE)
            gridViewLayout.setVisibility(View.INVISIBLE);

        if (!CheckIf.isPurchased("admob",this)) {
            if (haveNetworkConnection() == true) {
                // Code here executes on main thread after user presses button
                //Toast.makeText(getApplicationContext(), "More Button Works Good!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, EditTextActivity.class);
                //myIntent.putExtra("key", "Kumbaya . . !"); //Optional parameters
                //MainActivity.this.startActivity(myIntent);
                startActivityForResult(myIntent, REQUEST_GET_TEXT_STICKER);

            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(getString(R.string.NoInternetConnection));
                builder.setMessage(getString(R.string.NoInternetMessage));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        else{
            Intent myIntent = new Intent(MainActivity.this, EditTextActivity.class);
            startActivityForResult(myIntent, REQUEST_GET_TEXT_STICKER);
        }

    }

    /*public void reset(View view) {
        stickerView.removeAllStickers();
        loadSticker();
    }*/

    public void testAdd(View view) {
        final TextSticker sticker = new TextSticker(this);
        sticker.setText("Hello, world!");
        sticker.setTextColor(Color.BLUE);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();

        stickerView.addSticker(sticker);
    }



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private static Bitmap resizeWithRatio(Bitmap image, int maxWidth, int maxHeight) {
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


    public void addBackground(View v) {

            //loadSticker();

        isBackground=true;

        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle(getString(R.string.Cancel))
                .setOtherButtonTitles(getString(R.string.ChangeBG), getString(R.string.EditBG))
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
        isChangeBG=true;
        isImagePicker=false;


    }
    public void changeBackground(){
        if (!CheckIf.isPurchased("admob", this)) {
            if (haveNetworkConnection() == true) {

                if (gridViewLayout.getVisibility() == View.INVISIBLE) {
                    gridViewLayout.setVisibility(View.VISIBLE);
                    //crossButton.setVisibility(View.VISIBLE);

                }
                gridViewTitle.setText(getString(R.string.Backgrounds));
                stickerSet = StickerSet.Background;
                changeAdapter(mThumbIdsbg);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(getString(R.string.NoInternetConnection));
                builder.setMessage(getString(R.string.NoInternetMessage));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        } else {
            if (gridViewLayout.getVisibility() == View.INVISIBLE) {
                gridViewLayout.setVisibility(View.VISIBLE);
                //crossButton.setVisibility(View.VISIBLE);

            }
            gridViewTitle.setText(getString(R.string.Backgrounds));
            stickerSet = StickerSet.Background;
            changeAdapter(mThumbIdsbg);
        }
    }
    public void addForeground(View v) {

            //loadSticker();

        isBackground = false;

            if (!CheckIf.isPurchased("admob", this)) {
                if (haveNetworkConnection() == true) {

                    if (gridViewLayout.getVisibility() == View.INVISIBLE) {
                        gridViewLayout.setVisibility(View.VISIBLE);
                        //crossButton.setVisibility(View.VISIBLE);

                    }
                    gridViewTitle.setText(getString(R.string.Foregrounds));
                    stickerSet = StickerSet.Foreground;
                    changeForegroundAdapter(mThumbIdsFg);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(true);
                    builder.setTitle(getString(R.string.NoInternetConnection));
                    builder.setMessage(getString(R.string.NoInternetMessage));
                    builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                if (gridViewLayout.getVisibility() == View.INVISIBLE) {
                    gridViewLayout.setVisibility(View.VISIBLE);
                    //crossButton.setVisibility(View.VISIBLE);

                }
                gridViewTitle.setText(getString(R.string.Foregrounds));
                stickerSet = StickerSet.Foreground;
                changeForegroundAdapter(mThumbIdsFg);
            }


    }
    public void addSmiles(View v) {


            if (!CheckIf.isPurchased("admob", this)) {
                if (haveNetworkConnection() == true) {

                    if (gridViewLayout.getVisibility() == View.INVISIBLE) {
                        gridViewLayout.setVisibility(View.VISIBLE);
                        //crossButton.setVisibility(View.VISIBLE);
                    }
                    gridViewTitle.setText(getString(R.string.Stickers));
                    stickerSet = StickerSet.Smileys;
                    changeAdapter(mThumbIdsstk);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(true);
                    builder.setTitle(getString(R.string.NoInternetConnection));
                    builder.setMessage(getString(R.string.NoInternetMessage));
                    builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                if (gridViewLayout.getVisibility() == View.INVISIBLE) {
                    gridViewLayout.setVisibility(View.VISIBLE);
                    //crossButton.setVisibility(View.VISIBLE);
                }
                gridViewTitle.setText(getString(R.string.Stickers));
                stickerSet = StickerSet.Smileys;
                changeAdapter(mThumbIdsstk);
            }


    }
    public void addBlend(View v) {
        if (gridViewLayout.getVisibility() == View.VISIBLE)
            gridViewLayout.setVisibility(View.INVISIBLE);

        if (!CheckIf.isPurchased("admob",this)) {
            if (haveNetworkConnection() == true) {

                if (stickerView.stickers.size() != 0) {

                    bgImage.setVisibility(View.INVISIBLE);
                    stickerView.setBackgroundColor(Color.TRANSPARENT);
                    Bitmap result = stickerView.createBitmap();
                    Bitmap imageviewImageBitmap = Bitmap.createBitmap(result, bgImage.getLeft(), bgImage.getTop(), bgImage.getWidth(), bgImage.getHeight());
                    bgImage.setVisibility(View.VISIBLE);
                    stickerView.setBackgroundColor(Color.WHITE);
                    DataPassingSingelton.getInstance().setImage2(imageviewImageBitmap);

                    Bitmap result1 = ((BitmapDrawable) bgImage.getBackground()).getBitmap();
                    Bitmap backgroundImage = Bitmap.createScaledBitmap(result1, bgImage.getWidth(), bgImage.getHeight(), false);

                    DataPassingSingelton.getInstance().setImage(backgroundImage);
                    Intent erase = new Intent(MainActivity.this, BlendActivity.class);
                    startActivityForResult(erase, REQUEST_GET_BLEND_IMAGE);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(true);
                    builder.setMessage(getString(R.string.NoForegroundAdded));
                    builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(getString(R.string.NoInternetConnection));
                builder.setMessage(getString(R.string.NoInternetMessage));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        else{
            if (stickerView.stickers.size() != 0) {

                bgImage.setVisibility(View.INVISIBLE);
                stickerView.setBackgroundColor(Color.TRANSPARENT);
                Bitmap result = stickerView.createBitmap();
                Bitmap imageviewImageBitmap = Bitmap.createBitmap(result, bgImage.getLeft(), bgImage.getTop(), bgImage.getWidth(), bgImage.getHeight());
                bgImage.setVisibility(View.VISIBLE);
                stickerView.setBackgroundColor(Color.WHITE);
                DataPassingSingelton.getInstance().setImage2(imageviewImageBitmap);

                Bitmap result1 = ((BitmapDrawable) bgImage.getBackground()).getBitmap();
                Bitmap backgroundImage = Bitmap.createScaledBitmap(result1, bgImage.getWidth(), bgImage.getHeight(), false);

                DataPassingSingelton.getInstance().setImage(backgroundImage);
                Intent erase = new Intent(MainActivity.this, BlendActivity.class);
                startActivityForResult(erase, REQUEST_GET_BLEND_IMAGE);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setMessage(getString(R.string.NoForegroundAdded));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    public void addFilter(View view) {

        if (gridViewLayout.getVisibility() == View.VISIBLE)
            gridViewLayout.setVisibility(View.INVISIBLE);

            if (!CheckIf.isPurchased("admob",this)) {
            if (haveNetworkConnection() == true) {

                Bitmap result = stickerView.createBitmap();
                Bitmap imageviewImageBitmap = Bitmap.createBitmap(result, bgImage.getLeft(), bgImage.getTop(), bgImage.getWidth(), bgImage.getHeight());
                DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
                Intent erase = new Intent(MainActivity.this, FilterActivity.class);
                startActivityForResult(erase, REQUEST_GET_FILTER_IMAGE);

            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(getString(R.string.NoInternetConnection));
                builder.setMessage(getString(R.string.NoInternetMessage));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        else{
            Bitmap result = stickerView.createBitmap();
            Bitmap imageviewImageBitmap = Bitmap.createBitmap(result, bgImage.getLeft(), bgImage.getTop(), bgImage.getWidth(), bgImage.getHeight());
            DataPassingSingelton.getInstance().setImage(imageviewImageBitmap);
            Intent erase = new Intent(MainActivity.this, FilterActivity.class);
            startActivityForResult(erase, REQUEST_GET_FILTER_IMAGE);
        }

    }
    public void saveAndShare(View v) {
        requestSet = RequestSet.SaveShare;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_RQST_CODE);
        } else {
            //loadSticker();


            if (gridViewLayout.getVisibility() == View.VISIBLE)
                gridViewLayout.setVisibility(View.INVISIBLE);

            if (!CheckIf.isPurchased("admob", this)) {
                if (haveNetworkConnection() == true) {
                    ActionSheet.createBuilder(this, getSupportFragmentManager())
                            .setCancelButtonTitle(getString(R.string.Cancel))
                            .setOtherButtonTitles(getString(R.string.Share), getString(R.string.SaveToAlbum))
                            .setCancelableOnTouchOutside(true)
                            .setListener(this).show();
                    isImagePicker = false;
                    isChangeBG=false;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(true);
                    builder.setTitle(getString(R.string.NoInternetConnection));
                    builder.setMessage(getString(R.string.NoInternetMessage));
                    builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                ActionSheet.createBuilder(this, getSupportFragmentManager())
                        .setCancelButtonTitle(getString(R.string.Cancel))
                        .setOtherButtonTitles(getString(R.string.Share), getString(R.string.SaveToAlbum))
                        .setCancelableOnTouchOutside(true)
                        .setListener(this).show();
                isImagePicker = false;
                isChangeBG=false;
            }
        }

    }
    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        //Toast.makeText(getApplicationContext(), "click item index = " + index, Toast.LENGTH_SHORT).show();
        //System.out.println(actionSheet.getActivity());


        if(index==0 && !isImagePicker && !isChangeBG)
            share();
        else if(index==1 && !isImagePicker && !isChangeBG)
            saveImage();
        else if(index==0 && isImagePicker && !isChangeBG) {
            Intent intent;
            intent = new Intent(Intent.ACTION_PICK);
            //intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            //intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(Intent.createChooser(intent,
                    getString(R.string.CompleteActionUsing)), PHOTO_PICKER_ID);
        }
        else if(index==1 && isImagePicker && !isChangeBG){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            String filename = Environment.getExternalStorageDirectory() + File.separator + "cutpastecamera";
            imageUri = Uri.fromFile(new File(filename));

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        else if(index==0 && !isImagePicker && isChangeBG){
            changeBackground();
        }
        else if(index==1 && !isImagePicker && isChangeBG){
            stickerSet = StickerSet.Background;
            goCropView(DataPassingSingelton.getInstance().getBGImage());
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancle) {
        //Toast.makeText(getApplicationContext(), "dismissed isCancle = " + isCancle, Toast.LENGTH_SHORT).show();
    }
    public void share(){

        Bitmap result = stickerView.createBitmap();
        Bitmap icon =Bitmap.createBitmap(result,bgImage.getLeft(), bgImage.getTop(), bgImage.getWidth(), bgImage.getHeight());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        String filename = Custom.makeFileNameFrom(".jpg");
        System.out.println(filename);
        saveTempFile(icon, filename);
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/"+filename));
        startActivityForResult(Intent.createChooser(share, getString(R.string.ShareImage)),REQUEST_BACK_FROM_SHARE_INTEND);

//        new AppRate(MainActivity.this).init();

    }
    public void saveTempFile(Bitmap icon, String fileName){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Cart(View view) {
        //Toast.makeText(getApplicationContext(), "Cart Button Works Good!", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(MainActivity.this, IAPActivity.class);
        myIntent.putExtra("key", "Kumbaya . . !"); //Optional parameters
        MainActivity.this.startActivity(myIntent);


    }
    public void More(View view) {

        // Code here executes on main thread after user presses button
        //Toast.makeText(getApplicationContext(), "More Button Works Good!", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(MainActivity.this, MoreAppsActivity.class);
        myIntent.putExtra("key", "Kumbaya . . !"); //Optional parameters
        MainActivity.this.startActivity(myIntent);

    }
    public void Info(View view) {
        if (gridViewLayout.getVisibility() == View.VISIBLE)
            gridViewLayout.setVisibility(View.INVISIBLE);

        if (!CheckIf.isPurchased("admob",this)) {
            if (haveNetworkConnection() == true) {
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                myIntent.putExtra("key", "Kumbaya . . !"); //Optional parameters
                MainActivity.this.startActivityForResult(myIntent, REQUEST_GET_HELP);
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(getString(R.string.NoInternetConnection));
                builder.setMessage(getString(R.string.NoInternetMessage));
                builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        else{
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            myIntent.putExtra("key", "Kumbaya . . !"); //Optional parameters
            MainActivity.this.startActivityForResult(myIntent, REQUEST_GET_HELP);
        }

    }
    public void crossAction(View view){
        gridViewLayout.setVisibility(View.INVISIBLE);
        //crossButton.setVisibility(View.INVISIBLE);
    }
    private void changeAdapter(Integer[] images){
        Boolean bool=false;
        if(stickerSet==StickerSet.Background)
            bool=true;
        else if(stickerSet==StickerSet.Smileys)
            bool=false;
        stickerAdapter = new StickerAdapter(this,images,bool);
        stickerAdapter.notifyDataSetChanged();
        //gridView.invalidateViews();
        gridView.setAdapter(stickerAdapter);

    }
    private void changeForegroundAdapter(ArrayList<Bitmap> images){
        foregroundAdapter = new ForegroundAdapter(this,images);
        //foregroundAdapter.notifyDataSetChanged();
        //gridView.invalidateViews();
        gridView.setAdapter(foregroundAdapter);
    }
    private void changeBgImage(Drawable draw){
        bgImage.setBackground(draw);
    }
    private void showHelpScreen(){
        helpImageView2.setVisibility(View.VISIBLE);
        helpImageView.setVisibility(View.VISIBLE);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),mhelpIds[0]);
        helpImageView.setImageBitmap(resizeBitmap(icon));
    }
    private void purchasedJustNow(){
        if(stickerSet==StickerSet.Background)
            changeAdapter(mThumbIdsbg);
        else if(stickerSet==StickerSet.Smileys)
            changeAdapter(mThumbIdsstk);
        if(CheckIf.isPurchased("admob",this))
            findViewById(R.id.AMAdmob).setVisibility(View.GONE);
    }
}
