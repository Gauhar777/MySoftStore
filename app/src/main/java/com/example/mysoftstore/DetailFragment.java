package com.example.mysoftstore;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    private long productId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        setupDBHelper();
        View view=getView();
        Cursor cursorOnProductDetail=mDb.rawQuery("SELECT * FROM product WHERE _id"+"="+productId,null);
        cursorOnProductDetail.moveToFirst();
        System.out.print(cursorOnProductDetail.getString(0));
        if (view!=null){
            TextView title=(TextView)view.findViewById(R.id.textTitle);
            TextView description=(TextView)view.findViewById(R.id.textDescription);
            String name=cursorOnProductDetail.getString(1);
            String desc=cursorOnProductDetail.getString(1);
            title.setText(name);
            description.setText(desc);

        }
    }

    private void setupDBHelper(){
        Context context=getContext();
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


    public void setProduct(long id){
        this.productId=id;
    }

}
