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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

import java.io.IOException;
import java.util.ArrayList;

public class
DetailActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "idC";
    private RecyclerView recyclerView;
    private ArrayList <CarouselItem> imageModelArrayList;
    private CarouselAdapter adapter;
    private Toolbar toolbar;

    private int[] myImageList = new int[]{R.drawable.computer, R.drawable.c1, R.drawable.c2,R.drawable.c3, R.drawable.c2,R.drawable.c1,R.drawable.c3};
    private String[] myImageNameList = new String[]{"Алма","Бүлдірген","Мандарин" ,"Кұлпынай","Алмүрт","Ананас","Қарақат"};

    private int[] myCameraImageList = new int[]{R.drawable.canon, R.drawable.camera,R.drawable.c021, R.drawable.c011,R.drawable.c031,R.drawable.c021};
    private String[] myCameraImageNameList = new String[]{"Apple","Mango" ,"Strawberry","Pineapple","Orange","Blueberry"};

    private int[] myTvImageList = new int[]{R.drawable.tv1, R.drawable.tv,R.drawable.tv3, R.drawable.tv1,R.drawable.tv,R.drawable.tv3};
    private String[] myTvImageNameList = new String[]{"Apple","Mango" ,"Strawberry","Pineapple","Orange","Blueberry"};

    private int[] myPhoneImageList = new int[]{R.drawable.phone, R.drawable.ph2,R.drawable.ph3, R.drawable.ph4,R.drawable.phone,R.drawable.ph3};
    private String[] myPhoneImageNameList = new String[]{"Apple","Mango" ,"Strawberry","Pineapple","Orange","Blueberry"};

    CarouselView carouselView;
    NestedScrollView bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    TextView textView;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    public static final String EXTRA_PRODUCT_ID = "id";
    public static final String tag="myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //**************************************fragment of detail by product's id***********************************************
        CharFragment frag=(CharFragment)getSupportFragmentManager().findFragmentById(R.id.char_list);
        int product_id=(int)getIntent().getExtras().get(EXTRA_PRODUCT_ID);
        frag.setProductid(product_id);

        int idC=(int)getIntent().getExtras().get(EXTRA_CATEGORY_ID)-1;
        GaleryDetailFragment galFrag=(GaleryDetailFragment)getSupportFragmentManager().findFragmentById(R.id.galery_detail);
        galFrag.setProductId(idC);

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
//        Toolbar toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
//        toolbar.setTitle(productName);
        TextView toolbarTitle=(TextView)findViewById(R.id.pr_det);
        toolbarTitle.setText(productName);
//        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        imageModelArrayList = items();
        adapter = new CarouselAdapter(this, imageModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }


    private ArrayList<CarouselItem> items(){
        int product_id=(int)getIntent().getExtras().get(EXTRA_PRODUCT_ID);
        ArrayList<CarouselItem> list=new ArrayList<>();
        for (int i=0;i<6;i++ ){
            CarouselItem item=new CarouselItem();
                item.setItemImage(myImageList [i]);
                item.setItemName(myImageNameList[i]);

            list.add(item);
        }
        return list;
    }


    //**************************************Buy product***********************************************
    public void onBuyProduct(View view){
        int product_id=(int)getIntent().getExtras().get(EXTRA_PRODUCT_ID);
        Intent intent=new Intent(this,BuyActivity.class);
        intent.putExtra(BuyActivity.PRODUCT_ID_TOBUY,product_id);
        startActivity(intent);

//        setupDBHelper();
//        Cursor cursorOnProductDetail=mDb.rawQuery("SELECT * FROM product WHERE _id="+product_id,null);
//        cursorOnProductDetail.moveToFirst();
//        String price=cursorOnProductDetail.getString(3);
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
