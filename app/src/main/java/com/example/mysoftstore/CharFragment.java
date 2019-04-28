package com.example.mysoftstore;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CharFragment extends ListFragment {

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    public long productId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setupDBHelper();
//        setupCharList();
        return super.onCreateView(inflater,container,savedInstanceState);
    }


    @Override
    public void onStart(){
        super.onStart();
        setupDBHelper();
        setupCharList();
    }


    public void setupCharList(){

        int productCh= (int) productId;
        Cursor cursor=mDb.rawQuery("SELECT*FROM char INNER JOIN char_title ON char.char_title_id = char_title._id AND char.product_id"+"="+productCh,null);
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String text = cursor.getString(1);
            String title = cursor.getString(5);
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("title",title);
            hm.put("txt",text);
            aList.add(hm);
            cursor.moveToNext();
        }
        cursor.close();
//        Log.d(tag,"********************"+aList+"********************");

        String[] from = { "title","txt"};
        int[] to = { R.id.char_title,R.id.char_text};

        SimpleAdapter adapter = new SimpleAdapter(getContext(), aList, R.layout.fragment_char, from, to);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.fragment_char, newList);
        setListAdapter(adapter);
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


    public void setProductid(long id){
        this.productId=id;
    }


}
