package com.example.mysoftstore;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryImgFragment extends ListFragment {

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    public static interface Listener {
        void itemCategoryClicked(long id);
    }

    private CategoryListFragment.Listener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupDBHelper();
        List<HashMap<String,String>> categoryList = new ArrayList<HashMap<String,String>>();
        Cursor cursor=mDb.rawQuery("SELECT*FROM category",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name=cursor.getString(1);
            int image=R.drawable.horz;
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("cname",name);
            hm.put("cimg", Integer.toString(image));
            categoryList.add(hm);
            cursor.moveToNext();
        }
        cursor.close();
        String[] from={"cname","cimg"};
        int[] to={R.id.category_name,R.id.category_image};
        SimpleAdapter categoryAdapter = new SimpleAdapter(getContext(), categoryList, R.layout.fragment_category_img,from,to);
        setListAdapter(categoryAdapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    private void setupListofCategory(){


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
        this.listener=(CategoryListFragment.Listener)context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        super.onListItemClick(listView,itemView,position,id);
        if (listener!=null){
            listener.itemCategoryClicked(id);
        }
    }

}
