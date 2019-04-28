package com.example.mysoftstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CategoryActivity extends AppCompatActivity implements CategoryListFragment.Listener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private Toolbar toolbar;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(CategoryActivity.this, "My Account",Toast.LENGTH_SHORT).show();
                    case R.id.settings:
                        Toast.makeText(CategoryActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                    case R.id.mycart:
                        Toast.makeText(CategoryActivity.this, "My Cart",Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }
            }
        });

        Button btnOut=(Button)findViewById(R.id.sign_out);
        mAuth = FirebaseAuth.getInstance();
        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(CategoryActivity.this,LogInActivity.class));
                }
            }
        };
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void itemCategoryClicked(long id) {
        Intent intent=new Intent(this,ProductActivity.class);
        intent.putExtra(ProductActivity.EXTRA_CATEGORY_ID, (int) id);
        startActivity(intent);
//        Intent intent=new Intent(this,TestActivity.class);
//        intent.putExtra(TestActivity.EXTRA_CATEGORY_ID, (int) id);
//        startActivity(intent);
    }
}
