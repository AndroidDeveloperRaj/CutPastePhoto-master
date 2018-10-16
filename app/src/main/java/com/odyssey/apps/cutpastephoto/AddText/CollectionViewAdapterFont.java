package com.odyssey.apps.cutpastephoto.AddText;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.odyssey.apps.cutpastephoto.R;
import com.odyssey.apps.cutpastephoto.StaticClasses.CheckIf;


/**
 * Created by admin on 2018-01-29.
 */

public class CollectionViewAdapterFont extends BaseAdapter {

    private String fonts[];
    private Context context ;
    private LayoutInflater inflater ;
    //private int colors[] ;

    public CollectionViewAdapterFont(Context context  ) {
        this.context = context ;
        //this.icons = icons;
        //colors = Data.getSharedInstance().getColors();
        fonts = Data.getSharedInstance().getFonts();
    }

    @Override
    public int getCount() {
        return Data.getSharedInstance().getNumberOfFonts();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View cell = view ;
        if (cell == null){
            inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cell = inflater.inflate(R.layout.collection_view_cell_font,null);
        }
        TextView icon = (TextView) cell.findViewById(R.id.CVCTextView); // CVC = Collection View Cell
        icon.setTypeface(Typeface.createFromAsset(this.context.getAssets(), fonts[i]));
        //cell.setBackgroundColor(colors[i]);

        int valueInPixels = (int) context.getResources().getDimension(R.dimen.collection_view_cell_font);
        ImageView img = (ImageView) cell.findViewById(R.id.CVCImageView);
        Bitmap original = resize(R.drawable.lock, valueInPixels, valueInPixels);

        if(!CheckIf.isPurchased("font",context) && i>15) {
            img.setImageBitmap(original);
        }
        else{
            img.setImageResource(android.R.color.transparent);
        }

        return cell;
    }
    private Bitmap resize(Integer image, int width, int height) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), image);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return bitmapResized;
    }
}
