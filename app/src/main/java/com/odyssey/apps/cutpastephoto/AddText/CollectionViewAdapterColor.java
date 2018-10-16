package com.odyssey.apps.cutpastephoto.AddText;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.odyssey.apps.cutpastephoto.R;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;

/**
 * Created by admin on 2018-01-29.
 */

public class CollectionViewAdapterColor extends BaseAdapter {

    private int icons[];
    private String letters[];
    private Context context ;
    private LayoutInflater inflater ;
    private int colors[] ;

    public CollectionViewAdapterColor(Context context  ) {
        this.context = context ;
        //this.icons = icons;
        colors = Data.getSharedInstance().getColors();
    }

    @Override
    public int getCount() {
        return Data.getSharedInstance().getNumberOfColors();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ImageView imageView;

        int valueInPixels = (int) context.getResources().getDimension(R.dimen.collection_view_cell_color);


        if (convertView == null) {

            imageView = new ImageView(context);

            imageView.setLayoutParams(new GridView.LayoutParams(valueInPixels, valueInPixels));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //imageView.setPadding(8, 8, 8, 8);

        }
        else
        {
            imageView = (ImageView) convertView;
            imageView.setImageResource(android.R.color.transparent);
        }
        imageView.setBackgroundColor(colors[i]);
        //Drawable d = mContext.getResources().getDrawable(images[position]);
        Bitmap original = resize(R.drawable.lock, valueInPixels, valueInPixels);

        if(!CheckIf.isPurchased("color",context) && i>15) {
            imageView.setImageBitmap(original);
        }
        return imageView;




//        View cell = view;
//        if (cell == null){
//            inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  //         cell = inflater.inflate(R.layout.collection_view_cell_color,null);
//        }
//        Bitmap original;
//        if(!isPurchased) {
//            if (i > 14)
//            {
//                //original = resize(R.drawable.lock, cell.getWidth(), cell.getHeight());
//                //cell.setBackgroundResource(R.drawable.lock);
//            }
//        }
//        cell.setBackgroundResource(R.drawable.lock);
//        cell.setBackgroundColor(colors[i]);
//
//        return cell;
    }
    private Bitmap resize(Integer image, int width, int height) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), image);
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

}
