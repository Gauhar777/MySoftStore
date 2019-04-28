package com.example.mysoftstore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

public class ProductActivity extends AppCompatActivity implements ProductListFragment.Listener{
    public static final String EXTRA_CATEGORY_ID = "categoryId";
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setupProductListFragment();
        int idC=(int)getIntent().getExtras().get(EXTRA_CATEGORY_ID)+1;
        setupDBHelper();
        Cursor cursor=mDb.rawQuery("SELECT*FROM category WHERE _id="+idC,null);
        cursor.moveToFirst();
        String categoryName = cursor.getString(1);
        Toolbar toolbar=(Toolbar)findViewById(R.id.product_toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void  setupProductListFragment(){
        int idC=(int)getIntent().getExtras().get(EXTRA_CATEGORY_ID);
        ProductListFragment pFrag=(ProductListFragment)getSupportFragmentManager().findFragmentById(R.id.def);
        assert pFrag != null;
        pFrag.setCategoryProduct(idC);
    }

    public void itemProductClicked(long id) {
        int ident=(int)id+1;
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PRODUCT_ID,ident);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        Log.d("tag","//////////////////////////////"+"HELLO YOU CLICKED"+"///////////////////////////////");
    }
    private void setupDBHelper(){
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
    public void onSort(View view){
        Intent intent=new Intent(this,SortProductsActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
