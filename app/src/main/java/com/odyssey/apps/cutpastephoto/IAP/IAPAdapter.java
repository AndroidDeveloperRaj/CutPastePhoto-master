package com.odyssey.apps.cutpastephoto.IAP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.odyssey.apps.cutpastephoto.R;


/**
 * Created by admin on 2018-02-15.
 */

public class IAPAdapter extends BaseAdapter {

    private int icons[];
    private int titles[];
    private int subTitles[];
    private Context context ;
    private LayoutInflater inflater ;
    BillingProcessor billingProcessor;
    private String demoPriceList[];

    public IAPAdapter(Context context , BillingProcessor billingProcessor) {
        this.context = context ;
        icons = Data.getSharedInstance().getIcons();
        titles= Data.getSharedInstance().getTitles();
        subTitles = Data.getSharedInstance().getSubTitles();
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.billingProcessor = billingProcessor ;
        demoPriceList = Data.getSharedInstance().getDemoPriceList();
    }

    @Override
    public int getCount() {
        return Data.getSharedInstance().getTitlesSize();
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

        View cell = inflater.inflate(R.layout.iap_cell,null);

        ImageView icon = (ImageView) cell.findViewById(R.id.IAPCIcon);
        icon.setImageResource(icons[i]);

        TextView title =  (TextView) cell.findViewById(R.id.IAPCTitle);
//        title.setText(titles[i]);
        title.setText(context.getString(titles[i]));

        TextView subTitle = (TextView) cell.findViewById(R.id.IAPCSubTitle);
        subTitle.setText(context.getString(subTitles[i]));

        TextView price = (TextView) cell.findViewById(R.id.IAPCPrice);
        if(billingProcessor.isInitialized()) {
            String PRODUCTID = Data.getSharedInstance().getGeneralProductIDAt(i);
            String PRICE = billingProcessor.getPurchaseListingDetails(PRODUCTID).priceText;
            price.setText(PRICE);
        }
        else
            price.setText(demoPriceList[i]);

        return cell;
    }
}
