package com.odyssey.apps.cutpastephoto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



import java.util.ArrayList;

/**
 * Created by OdysseyApps on 2/15/18.
 */

public class ForegroundAdapter extends BaseAdapter {
    private final Context mContext;
    private ArrayList<Bitmap> images = new ArrayList<Bitmap>();


    // 1
    public ForegroundAdapter(Context context, ArrayList<Bitmap> img) {
        this.mContext = context;
        this.images = img;

    }

    // 2
    @Override
    public int getCount() {
        return images.size();
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
        imageView.setBackgroundColor(Color.rgb(99, 101, 112));
        //Drawable d = mContext.getResources().getDrawable(images[position]);
        imageView.setImageBitmap(images.get(position));
        //imageView.setBackground(resize(d));

        return imageView;
    }
    /*private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        //Bitmap bitmapResized = BitmapFactory.decodeStream(b, null, options);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
        return new BitmapDrawable(mContext.getResources(), bitmapResized);
    }*/
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
