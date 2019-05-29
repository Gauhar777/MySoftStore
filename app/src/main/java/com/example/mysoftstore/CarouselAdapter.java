package com.example.mysoftstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<CarouselItem> imageModelArrayList;

    public CarouselAdapter(Context ctx, ArrayList<CarouselItem> imageModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
    }

    @Override
    public CarouselAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = inflater.inflate(R.layout.fragment_recomend_product_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(CarouselAdapter.MyViewHolder holder, int position) {
        holder.iv.setImageResource(imageModelArrayList.get(position).getItemImage());
        holder.time.setText(imageModelArrayList.get(position).getItemName());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        {
        }

        TextView time;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}