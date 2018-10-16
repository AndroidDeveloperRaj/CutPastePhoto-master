package com.odyssey.apps.cutpastephoto.Settings;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.odyssey.apps.cutpastephoto.R;


/**
 * Created by admin on 2018-02-05.
 */

public class SettingsAdapter extends BaseAdapter {

    private int icons[];
    private int titles[];
    private Context context ;
    private LayoutInflater inflater ;

    public SettingsAdapter(Context context  ) {
        this.context = context ;
        icons = Data.getSharedInstance().getIcons();
        titles= Data.getSharedInstance().getTitles();
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static final int ITEM = 0 ;
    private static final int HEADER = 1 ;
    private static final int FOOTER = 2 ;

    @Override
    public int getCount() {

        return Data.getSharedInstance().getNumberOfRow();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position==8 )
           return  HEADER ;
        else if (position==13 )
            return  FOOTER ;
        return  ITEM ;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View cell = view ;
        if (cell == null){
            switch (getItemViewType(i))
            {
                case HEADER :
                    cell = inflater.inflate(R.layout.settings_view_cell_header,null);
                    cell.setBackgroundColor(Color.parseColor("#f4f5f7"));
                    break;
                case FOOTER :
                    cell = inflater.inflate(R.layout.settings_view_cell_header,null);
                    cell.setBackgroundColor(Color.parseColor("#f4f5f7"));
                    break;
                case ITEM:
                    cell = inflater.inflate(R.layout.settings_view_cell,null);
                    cell.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
        switch (getItemViewType(i))
        {
            case HEADER :
                TextView textView = (TextView) cell.findViewById(R.id.SVCHTextView);
                textView.setText(context.getString(titles[i]));
                break;
            case FOOTER:
                textView = (TextView) cell.findViewById(R.id.SVCHTextView);
                textView.setText(context.getString(titles[i]));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                break;
            case ITEM:
                ImageView icon = (ImageView) cell.findViewById(R.id.SVCImageView);
                icon.setImageResource(icons[i]);
                textView = (TextView) cell.findViewById(R.id.SVCTextView);
                textView.setText(context.getString(titles[i]));
                textView.setTextColor(Color.parseColor("#4c4d4e"));

                break;
        }




        //TextView icon = (TextView) cell.findViewById(R.id.CVCtextView); // CVC = Collection View Cell
        //cell.setBackgroundColor(colors[i]);

        return cell;
    }
}
