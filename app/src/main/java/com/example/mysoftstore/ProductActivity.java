package com.example.mysoftstore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ProductActivity extends AppCompatActivity implements ProductListFragment.Listener{
    public static final String EXTRA_CATEGORY_ID = "categoryId";
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView txt=(TextView)findViewById(R.id.category_products);

        setContentView(R.layout.activity_product);
        setupProductListFragment();
        int idC=(int)getIntent().getExtras().get(EXTRA_CATEGORY_ID);
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
        EditText filterText=(EditText)findViewById(R.id.filterText);
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ProductListFragment pfrag=(ProductListFragment)getSupportFragmentManager().findFragmentById(R.id.def);
                pfrag.getAdapter().getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void  setupProductListFragment(){
        int idC=(int)getIntent().getExtras().get(EXTRA_CATEGORY_ID)-1;
        ProductListFragment pFrag=(ProductListFragment)getSupportFragmentManager().findFragmentById(R.id.def);
        assert pFrag != null;
        pFrag.setCategoryProduct(idC);
    }

    public void itemProductClicked(long id) {
        int ident=(int)id+1;
        int idC=(int)getIntent().getExtras().get(EXTRA_CATEGORY_ID)-1;
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PRODUCT_ID,ident);
        intent.putExtra(DetailActivity.EXTRA_CATEGORY_ID,idC);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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
//        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
