package com.example.mysoftstore;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminPanelActivity extends AppCompatActivity {
    Button createProduct;
    EditText nameEdBox,priceEdBox;
    private  DatabaseReference RooterRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Toast.makeText(getApplicationContext(),"welcome admin",Toast.LENGTH_LONG).show();

        nameEdBox=(EditText)findViewById(R.id.new_product);
        priceEdBox=(EditText)findViewById(R.id.new_product_price);

        createProduct=(Button)findViewById(R.id.add_new_product);
        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RooterRef= FirebaseDatabase.getInstance().getReference().child("Products");
            RooterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SaveNewProduct();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            }
        });
    }

    private void SaveNewProduct() {
        String sCurrentDate,sCurrentTime;
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        sCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        sCurrentTime = currentTime.format(calendar.getTime());
        String random="01"+sCurrentDate+sCurrentTime;
        String price=priceEdBox.getText().toString();
        String name=nameEdBox.getText().toString();

        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",random);
        productMap.put("name",name);
        productMap.put("price",price);
        RooterRef.child(random).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Жаңа тауар қосылды!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Қате бар!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
