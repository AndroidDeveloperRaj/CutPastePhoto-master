package com.odyssey.apps.cutpastephoto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;

/**
 * Created by OdysseyApps on 2/1/18.
 */

public class StickerAdapter extends BaseAdapter {
    private final Context mContext;
    private Integer[] images;
    private Boolean isBackground;


    // 1
    public StickerAdapter(Context context, Integer[] img, Boolean bool) {
        this.mContext = context;
        this.images = img;
        this.isBackground=bool;

    }

    // 2
    @Override
    public int getCount() {
        return images.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        int valueInPixels = (int) mContext.getResources().getDimension(R.dimen.collection_view_cell_sticker);


        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(valueInPixels, valueInPixels));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(5, 5, 5, 5);

        }
        else
        {
            imageView = (ImageView) convertView;

        }
        imageView.setBackgroundColor(Color.rgb(239, 239, 239)); //change
//        imageView.setBackgroundColor(Color.parseColor("#66c9c7c7"));
        //Drawable d = mContext.getResources().getDrawable(images[position]);
        Bitmap original = decodeSampledBitmapFromResource(mContext.getResources(), images[position], 100, 100);
        if(position==0&&isBackground)
            original = BitmapFactory.decodeResource(mContext.getResources(),images[position]);


            if (!isBackground) {
                if(!CheckIf.isPurchased("sticker",mContext)) {
                    if (position > 14)
                        original = overlay(original, resize(R.drawable.lock, original.getWidth(), original.getHeight()));
                }
            } else {
                if(!CheckIf.isPurchased("background",mContext)) {
                    if (position % 2 != 0)
                        original = overlay(original, resize(R.drawable.lock, original.getWidth(), original.getHeight()));
                }
            }

        imageView.setImageBitmap(original);

        return imageView;
    }
    private Bitmap resize(Integer image, int width, int height) {
        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), image);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return bitmapResized;
    }
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, 0,0, null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /*public Integer[] mThumbIds = {
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90,
            R.drawable.haizewang_90, R.drawable.haizewang_90
    };*/
}
