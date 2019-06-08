package com.example.mysoftstore;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment  extends ListFragment {
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    public long categoryId;
    private EditText filterText;
    public SimpleAdapter adapter;
    private static final String tag="My log";

    public static interface Listener{
        void itemProductClicked(long id);
    }
    private ProductListFragment.Listener listener;

    public void setCategoryProduct(long id){
        this.categoryId=id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setupDBHelper();
        Long cid=categoryId+1;
        Log.d("myTag","*********  "+categoryId+"  **************");
        Cursor cursorOnProduct=mDb.rawQuery("SELECT * FROM product WHERE category_id"+"="+cid,null);
        cursorOnProduct.moveToFirst();
        while (!cursorOnProduct.isAfterLast()) {
            String name=cursorOnProduct.getString(1);
            int image=R.drawable.canon;
            HashMap<String,String> phm=new HashMap<String, String>();
            phm.put("pName",name);
            phm.put("pImg", Integer.toString(image));
            productList.add(phm);
//            newList.add(cursorOnProduct.getString(1));
            cursorOnProduct.moveToNext();
        }
        cursorOnProduct.close();
        String[] from={"pName","pImg"};
        int[] to={R.id.product_name,R.id.product_image};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, newList);
        adapter=new SimpleAdapter(getContext(),productList,R.layout.fragment_blank,from,to);
        setListAdapter(adapter);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    List<HashMap<String,String>> productList=new ArrayList<HashMap<String,String>>();

    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setupDBHelper();
                Cursor cursorOnProduct=mDb.rawQuery("SELECT * FROM product",null);
                cursorOnProduct.moveToFirst();
                if (listener!=null){
                    while (!cursorOnProduct.isAfterLast()) {
                        String name=cursorOnProduct.getString(1);
                        String item=productList.get(position).get("pName");
                        if (item.equals(name)){
                            String productId=cursorOnProduct.getString(0);
                            int prodId=Integer.valueOf(productId)-1;
                            listener.itemProductClicked(prodId);
                        }
                        cursorOnProduct.moveToNext();
                    }
                    cursorOnProduct.close();
                }
            }
        });
    }


    public SimpleAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener=(Listener)context;
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView txtV=(TextView)v.findViewById(R.id.product_name);
        String nameP=txtV.getText().toString();
        Cursor cursor=mDb.rawQuery("SELECT*FROM product WHERE productName='"+nameP+"'",null);
        cursor.moveToFirst();
        String b=cursor.getString(0);
        Toast.makeText(getContext(),nameP,Toast.LENGTH_SHORT).show();
        int idP=Integer.parseInt(b);
        //int ident=(int)id+1;
        if (listener!=null){
            listener.itemProductClicked(1);
        }
    }
}
