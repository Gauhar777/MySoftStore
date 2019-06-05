package com.example.mysoftstore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysoftstore.Model.Cart;
import com.example.mysoftstore.Model.Product;
import com.example.mysoftstore.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView ;
    private RecyclerView.LayoutManager layoutManager;
    private Button GoPay;
    private TextView txttotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mRecyclerView =(RecyclerView) findViewById(R.id.cart_list);

        new FirebaseDatabaseHelper().readCart(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataisLoaded(List<Cart> carts, List<String> keys) {
                new Recycle_ViewConf().setConfig(mRecyclerView,CartActivity.this,carts,keys);
            }
        });
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        txttotalAmount = (TextView) findViewById(R.id.totalPrice);
//        GoPay = (Button) findViewById(R.id.go_pay_btn);


    }


    //
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
//        Log.d("MMM","*-*-*-*-*-*-*-*-*-*-"+uid);
//
//        DatabaseReference cartListReference = FirebaseDatabase.getInstance().getReference().child("Cart");
//        FirebaseRecyclerOptions<Product> opt =
//                new FirebaseRecyclerOptions.Builder<Product>()
//                        .setQuery(cartListReference.child(uid).child("products"), Product.class).build();
//                String list=opt.toString();
//        Log.d("TAG",list);
//        FirebaseRecyclerAdapter<Product, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Product, CartViewHolder>(opt) {
//            @Override
//            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Product model) {
//                holder.cartPQ.setText(model.getQuanty());
//                holder.cartPN.setText(model.getName());
//                holder.cartPP.setText(model.getPrice());
//            }
//
//            @NonNull
//            @Override
//            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout, viewGroup, false);
//                CartViewHolder holder = new CartViewHolder(view);
//                return holder;
//            }
//        };
//    }
}