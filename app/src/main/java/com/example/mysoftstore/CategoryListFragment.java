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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends ListFragment {

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    public static interface Listener {
        void itemCategoryClicked(long id);
    }

    private Listener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupDBHelper();
        setupListofCategory();
        return super.onCreateView(inflater,container,savedInstanceState);
    }


    private void setupListofCategory(){
        Cursor cursor=mDb.rawQuery("SELECT*FROM category",null);
        cursor.moveToFirst();
        List<String> categoryList =new ArrayList<>();
        while (!cursor.isAfterLast()){
            categoryList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        ArrayAdapter<String> simpleAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,categoryList);
        setListAdapter(simpleAdapter);
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

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener=(Listener)context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        super.onListItemClick(listView,itemView,position,id);
        if (listener!=null){
            ListView lv = listView;
            String selectedFromList = (lv.getItemAtPosition(position)).toString();
            //Log.i("IU","-----------------------"+selectedFromList);
            Toast.makeText(getContext(),selectedFromList,Toast.LENGTH_SHORT).show();

            setupDBHelper();

            Cursor cursor=mDb.rawQuery("SELECT*FROM category WHERE categoryName='"+selectedFromList+"'",null);
            cursor.moveToFirst();
            String b=cursor.getString(0);
            int mid=Integer.parseInt(b);
            Log.d("Tag",b+"**---****************---****");
            listener.itemCategoryClicked(mid);
        }
    }

}
