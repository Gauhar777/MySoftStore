package com.example.mysoftstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mysoftstore.Model.Cart;

import java.util.List;

public class Recycle_ViewConf {
private Context mcontext;
private CartAdapter  mCartAdapter;
public void setConfig(RecyclerView recyclerView,Context context, List<Cart> carts,List<String> keys){
    mcontext=context;
    mCartAdapter=new CartAdapter(carts,keys);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.setAdapter(mCartAdapter);
}
class CartItemView extends RecyclerView.ViewHolder{
        private TextView cartPN;
        private TextView cartPQ;
        private TextView cartPP;

        private String key;

        public CartItemView(ViewGroup viewGroup){
            super(LayoutInflater.from(mcontext).inflate(R.layout.cart_items_layout,viewGroup,false));

            cartPN=(TextView)itemView.findViewById(R.id.cart_p_n);
            cartPP=(TextView)itemView.findViewById(R.id.cart_p_p);
            cartPQ=(TextView)itemView.findViewById(R.id.cart_p_q);
        }
        public void bind(Cart cart, String string){
            cartPN.setText(cart.getName());
            cartPP.setText(cart.getPrice());
            cartPQ.setText(cart.getQuanty());
            this.key=key;
        }
    }

    class CartAdapter   extends RecyclerView.Adapter<CartItemView> {
    private List<Cart> mCartList;
    private List<String> mKeys;

        public CartAdapter(List<Cart> mCartList, List<String> mKeys) {
            this.mCartList = mCartList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public CartItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new CartItemView (viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemView holder, int position) {
            holder.bind(mCartList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mCartList.size();
        }
    }

}
