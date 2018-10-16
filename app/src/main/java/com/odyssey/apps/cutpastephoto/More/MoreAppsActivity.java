package com.odyssey.apps.cutpastephoto.More;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.odyssey.apps.cutpastephoto.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;


public class MoreAppsActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener {

    private DiscreteScrollView scrollView;
    //private InfiniteScrollAdapter infiniteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps);


        //Determine screen size
        if ((getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            Log.d("Screen Size: ", "LARGE");

            scrollView = findViewById(R.id.AMApicker);
            scrollView.setOrientation(Orientation.HORIZONTAL);
            scrollView.addOnItemChangedListener(this);
            //infiniteAdapter = InfiniteScrollAdapter.wrap(new PromoAdapter());
            scrollView.setAdapter(new PromoAdapter(this));
            scrollView.setItemTransitionTimeMillis(500);
            scrollView.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());

        }
//        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
//            Log.d("Screen Size: ", "NORMAL");
//
//        }
//        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
//            Log.d("Screen Size: ", "SMALL");
//        }
        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            Log.d("Screen Size: ", "XLARGE");

            scrollView = findViewById(R.id.AMApicker);
            scrollView.setOrientation(Orientation.HORIZONTAL);
            scrollView.addOnItemChangedListener(this);
            //infiniteAdapter = InfiniteScrollAdapter.wrap(new PromoAdapter());
            scrollView.setAdapter(new PromoAdapter(this));
            scrollView.setItemTransitionTimeMillis(500);
            scrollView.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
        }
        else {
            Log.d("Screen Size: ","UNKNOWN_CATEGORY_SCREEN_SIZE");

            scrollView = findViewById(R.id.AMApicker);
            scrollView.setOrientation(Orientation.HORIZONTAL);
            scrollView.addOnItemChangedListener(this);
            //infiniteAdapter = InfiniteScrollAdapter.wrap(new PromoAdapter());
            scrollView.setAdapter(new PromoAdapter(this));
            scrollView.setItemTransitionTimeMillis(500);
            scrollView.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
        }


//        scrollView = findViewById(R.id.AMApicker);
//        scrollView.setOrientation(Orientation.HORIZONTAL);
//        scrollView.addOnItemChangedListener(this);
//        //infiniteAdapter = InfiniteScrollAdapter.wrap(new PromoAdapter());
//        scrollView.setAdapter(new PromoAdapter(this));
//        scrollView.setItemTransitionTimeMillis(500);
//        scrollView.setItemTransformer(new ScaleTransformer.Builder()
//                .setMinScale(0.8f)
//                .build());


    }

    @Override
    public void onClick(View view) {
        System.out.println("asdfasdfasdfasdf");
        switch (view.getId()) {
            case 0:
                Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void backBtnAct(View view){
        finish();
    }
}
