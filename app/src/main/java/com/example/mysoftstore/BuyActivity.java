package com.example.mysoftstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class BuyActivity extends AppCompatActivity {

    public static final String PRODUCT_ID_TOBUY = "id";
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
    }

    public void onSetCart(View view){
        view.setBackgroundColor(Color.RED);
        int product_id=(int)getIntent().getExtras().get(PRODUCT_ID_TOBUY);
        setupDBHelper();
        ContentValues insertValues = new ContentValues();
        insertValues.put("product_id",1);
        insertValues.put("product_quanty",1);
        insertValues.put("purchase_id",2);

          long ins = mDb.insert("buy_product", null, insertValues);

          if (ins==-1){
                Log.d("GGG","errore"+"********************************************");
            }else {
                Log.d("GGG","right"+"********************************************");
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
