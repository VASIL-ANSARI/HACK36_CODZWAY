package com.tlabs.something.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tlabs.something.R;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    final Context appContext;
    List<Integer> imageList;
    public BannerAdapter(Context context,List<Integer> imageList){
        this.layoutInflater=LayoutInflater.from(context);
        appContext=context;
       this.imageList=imageList;

    }

    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.banner_resource,parent,false);

        return new BannerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final BannerAdapter.ViewHolder holder, final int position) {



    holder.imageView.setBackground(ContextCompat.getDrawable(appContext,imageList.get(position)));



    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);

        }
    }

}

