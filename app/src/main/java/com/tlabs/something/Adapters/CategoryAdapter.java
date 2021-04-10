package com.tlabs.something.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tlabs.something.Activities.Items;
import com.tlabs.something.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<String> cardText;
    private final List<Integer> drawable;
    final Context appContext;
    public CategoryAdapter(Context context, List<String> cardText, List<Integer> drawable){
        this.layoutInflater=LayoutInflater.from(context);
        this.cardText =cardText;
        this.drawable =drawable;
        appContext=context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.category_resource,parent,false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = (parent.getMeasuredHeight() / 2);
        view.setLayoutParams(params);

        return new CategoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ViewHolder holder, final int position) {

        final String brand= cardText.get(position);
        final Integer image= drawable.get(position);
        holder.textView.setText(brand);
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(0,image,0,0);
        holder.cardView.setOnClickListener(view -> {
            Intent intent=new Intent(appContext, Items.class);
            intent.putExtra("brand",brand);
            appContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cardText.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView);
            textView=itemView.findViewById(R.id.cardTextView);
        }
    }
}