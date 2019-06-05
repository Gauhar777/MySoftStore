package com.example.mysoftstore.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mysoftstore.Interface.ItemClickListener;
import com.example.mysoftstore.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView cartPN,cartPP,cartPQ;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartPN=itemView.findViewById(R.id.cart_p_n);
        cartPP=itemView.findViewById(R.id.cart_p_p);
        cartPQ=itemView.findViewById(R.id.cart_p_q);

    }

    @Override
    public void onClick(View v) {
        //itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
