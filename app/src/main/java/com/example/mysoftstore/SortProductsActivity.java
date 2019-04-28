package com.example.mysoftstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SortProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_products);
    }
    public void onSortProducts(View view){
        Intent intent = new Intent(this,ProductActivity.class);
        startActivity(intent);
    }
}
