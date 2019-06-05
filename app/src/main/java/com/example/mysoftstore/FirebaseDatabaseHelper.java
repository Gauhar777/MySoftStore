package com.example.mysoftstore;

import android.support.annotation.NonNull;

import com.example.mysoftstore.Model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDB;
    private DatabaseReference dbRef;
    private List<Cart> carts=new ArrayList<>();

    public interface DataStatus{
        void DataisLoaded(List<Cart> carts,List<String> keys);
    }
    public FirebaseDatabaseHelper(){
        mDB=FirebaseDatabase.getInstance();

        dbRef=mDB.getReference("Cart");
    }

    public void readCart(final DataStatus dataStatus){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carts.clear();
                List<String> keys=new ArrayList<>();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();

                for (DataSnapshot keyNode:dataSnapshot.child(uid).child("products").getChildren()){
                    keys.add(keyNode.getKey());
                    Cart cart=keyNode.getValue(Cart.class);
                    carts.add(cart);
                }
                dataStatus.DataisLoaded(carts,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
