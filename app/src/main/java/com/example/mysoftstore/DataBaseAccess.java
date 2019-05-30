package com.example.mysoftstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private  static DataBaseAccess instance;
    Cursor c=null;


    private DataBaseAccess(Context context){
        this.openHelper=new DataBaseOpenHelper(context);
    }


    public static DataBaseAccess getInstance(Context context){
        if (instance==null){
            instance=new DataBaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();

    }

    public void close(){
        if(db!=null) {
            this.db.close();
        }
    }

    public boolean buyProduct (int product,int quanty,int purchase){

        ContentValues values = new ContentValues();
        values.put("product_id", product);
        values.put("product_quanty", quanty);
        values.put("purchase_id", purchase);
        long res=db.insert("buy_product",null,values);

        if (res == -1){
            return false;
        }else {
            return true;
        }
    }
}
