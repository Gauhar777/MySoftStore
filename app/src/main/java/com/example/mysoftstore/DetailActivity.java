package com.example.mysoftstore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    CarouselView carouselView;
    NestedScrollView bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    TextView textView;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
//    int[] images={R.drawable.c011,R.drawable.c021,R.drawable.c031};
    public static final String EXTRA_PRODUCT_ID = "id";
    public static final String tag="myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //**************************************fragment of detail by product's id***********************************************
        CharFragment frag=(CharFragment)getSupportFragmentManager().findFragmentById(R.id.char_list);
        int product_id=(int)getIntent().getExtras().get(EXTRA_PRODUCT_ID);
        frag.setProductid(product_id);

        bottomSheet=(NestedScrollView)findViewById(R.id.bottom_sheet);
        bottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Button btn=(Button)findViewById(R.id.arrowBtn);
                if (i==BottomSheetBehavior.STATE_COLLAPSED){
                    btn.setRotation(0);
                }else if (i==BottomSheetBehavior.STATE_EXPANDED){
                    btn.setRotation(180);
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }


        });
        bottomSheetBehavior.setPeekHeight(230);
        //***************************************Bottom sheet**************************************************************************
        setupDBHelper();
        Cursor cursorOnProductDetail=mDb.rawQuery("SELECT * FROM product WHERE _id="+product_id,null);
        cursorOnProductDetail.moveToFirst();
        String price=cursorOnProductDetail.getString(3);

        textView=(TextView)findViewById(R.id.product_price);
        textView.setText(price+" KZT");

        String productName=cursorOnProductDetail.getString(1);
        Toolbar toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
        toolbar.setTitle(productName);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

//    ImageListener carouselListener=new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(images[position]);
//            imageView.setRotation(-90f);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//    };

    //**************************************Buy product***********************************************
    public void onBuyProduct(View view){
        Intent intent=new Intent(this,BuyActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}