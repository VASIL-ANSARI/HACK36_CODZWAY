package com.tlabs.something.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tlabs.something.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tlabs.something.Utils.Methods.isActivitySafe;
import static com.tlabs.something.Utils.UserDetails.getUid;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    final Context appContext;
    List<String> companies;
    List<Map<String,Object>> data;
    public CartAdapter(Context context,List<String> companies,List<Map<String,Object>> data){
        this.layoutInflater=LayoutInflater.from(context);
        appContext=context;
        this.companies=companies;
        this.data=data;

    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_resource,parent,false);

        return new CartAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        String company=companies.get(position);
        Map<String,Object> map=new HashMap<>(data.get(position));
        String image= (String) map.get("image");
        String price= (String) map.get("price");
        String stock= (String) map.get("stock");

        holder.title.setText(company);
        holder.units.setText("Available Units : "+stock);
        holder.price.setText(price);
        if (isActivitySafe((Activity) appContext))
            Glide.with(appContext)
                    .load(Uri.parse(image))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.imageView);

        holder.buy.setOnClickListener(v -> {
            CollectionReference collectionReference= FirebaseFirestore.getInstance().collection("Bought").document(getUid()).collection(company);
            collectionReference.add(map).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                    Toast.makeText(appContext,"Item Bought Successfully",Toast.LENGTH_SHORT).show();
                else Toast.makeText(appContext,"Error occurred. Try again later",Toast.LENGTH_SHORT).show();
            });
        });

    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView title,price,units;
        final Button buy;
        final ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.shopPrice);
            units=itemView.findViewById(R.id.collectionCount);
            progressBar=itemView.findViewById(R.id.progreBar);
            buy=itemView.findViewById(R.id.buy);
        }
    }

}

