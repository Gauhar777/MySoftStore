package com.example.mysoftstore;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;


public class CategoryActivity extends AppCompatActivity implements CategoryListFragment.Listener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private Toolbar toolbar;
    private TextView textView;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        textView=(TextView)findViewById(R.id.ttt);
       // textView.setText(mAuth.getCurrentUser().getEmail());
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
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.support)
                {
                    Intent intent2=new Intent(CategoryActivity.this,QuestionsActivity.class);
                    startActivity(intent2);
                    Toast.makeText(CategoryActivity.this, "Settings",Toast.LENGTH_SHORT).show();

                } else if (id == R.id.mycart)
                {
                    Intent intent=new Intent(CategoryActivity.this,CartActivity.class);
                    startActivity(intent);
                    Toast.makeText(CategoryActivity.this, "My Cart",Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.aboutUs)
                {
                    Intent intent3=new Intent(CategoryActivity.this,MainActivity.class);
                    startActivity(intent3);
                    Toast.makeText(CategoryActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.out)
                {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(CategoryActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                }

                return true;

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

        EditText filterEditText = (EditText) findViewById(R.id.filterText);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CategoryImgFragment cfrag=(CategoryImgFragment)getSupportFragmentManager().findFragmentById(R.id.categoryFragment);
                cfrag.getCategoryAdapter().getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button signOut=(Button)findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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

}
