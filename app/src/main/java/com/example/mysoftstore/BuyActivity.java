package com.example.mysoftstore;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BuyActivity extends AppCompatActivity {

    public static final String PRODUCT_ID_TOBUY = "id";
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Button btnBuy;
        btnBuy=(Button)findViewById(R.id.btnBuy_btn);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        String saveCurrentTime,saveCurrentDate;
        Calendar cfdata=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate=currentDate.format(cfdata.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(cfdata.getTime());

        //final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("My product list");
        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pId","1");
        cartMap.put("name","Canon");
        cartMap.put("price","150000");
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quanty",1);

        /*cartListRef.child("User View").child("Products").child("1").updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartListRef.child("Admin View").child("Products").child("1").updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         Toast.makeText(BuyActivity.this,"Buyed",Toast.LENGTH_LONG).show();
                         Intent intent=new Intent(BuyActivity.this,CategoryActivity.class);
                         startActivity(intent);
                        }
                    });
                }
            }
        });*/

    }


    public void BuyProduct(int p, int q,int purch) {
        DataBaseAccess dba = DataBaseAccess.getInstance(getApplicationContext());
        dba.open();
        boolean res = dba.buyProduct(p,q,purch);
        if(res){
            Log.d("Tag1","88888888");
        }else {
            Log.d("Tag1","00000000");
        }
    }
    private void setupDBHelper() {
        Context context=this;
        mDBHelper=new DBHelper(context);
        try {
            mDBHelper.updateDataBase();
        }catch (IOException mIOException){
            throw new Error("UnableToUpdate");
        }

        try {
            mDb=mDBHelper.getWritableDatabase();
        }catch (SQLException mSQLException){
            throw mSQLException;
        }
    }
}
