package com.odyssey.apps.cutpastephoto.More;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.odyssey.apps.cutpastephoto.R;
import com.odyssey.apps.cutpastephoto.Settings.SettingModel;


/**
 * Created by admin on 2018-02-01.
 */

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {


    Context mcontext;
    public PromoAdapter(Context ctx) {
        mcontext=ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.page_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==0) {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.collaging)
                    .into(holder.image);
        }
        else if(position==1){
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.photovault)
                    .into(holder.image);
        }
        else if(position==2){
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.docscanner)
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.PIimage);
            itemView.findViewById(R.id.PIimage).setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if(getAdapterPosition()==0){
                SettingModel.getSharedInstance().pushURL(mcontext,"https://play.google.com/store/apps/details?id=com.odyssey.apps.collagingpic");
            }
            else if(getAdapterPosition()==1){
                SettingModel.getSharedInstance().pushURL(mcontext,"https://play.google.com/store/apps/details?id=odysseyapps.photolocker.photovault");
            }
            else if(getAdapterPosition()==2){
                SettingModel.getSharedInstance().pushURL(mcontext,"https://play.google.com/store/apps/details?id=odyssey.apps.documentscanner");
            }
            //System.out.println("asdfasdfasdf "+getAdapterPosition());
            //parentRecycler.smoothScrollToPosition(getAdapterPosition());
        }
    }
}
