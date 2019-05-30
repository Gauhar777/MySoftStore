package com.example.mysoftstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysoftstore.Model.Admin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    EditText email,password;
    private TextView AdLink,UsLink;
    private String parentDbName="Users";
    Button loginButton;
    TextView registerButton;
    FirebaseAuth firebaseAuth;
    Button button;
    private final static int RC_SIGN_IN = 123;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListner);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, CategoryActivity.class));
            finish();
        }
        setContentView(R.layout.activity_log_in);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.old_user);
        registerButton = (TextView) findViewById(R.id.new_user);
        button = (Button) findViewById(R.id.google_btn);
//**************************************************************************
        AdLink=(TextView)findViewById(R.id.im_admin);
        UsLink=(TextView)findViewById(R.id.im_user);
        UsLink.setVisibility(View.INVISIBLE);

//**************************************************************************

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (parentDbName.equals("Admins")) {
                    Log.d("Tag21","*******************************************************im admin");
                    logInAdmin();
                }
                else {
                    final String emailText = email.getText().toString();
                    final String passwordText = password.getText().toString();
                    if (TextUtils.isEmpty(emailText)) {
                        Toast.makeText(getApplicationContext(), "Email теріңіз!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(passwordText)) {
                        Toast.makeText(getApplicationContext(), "Құпия сөзді енгізіңіз!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    firebaseAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(LogInActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Қош келдіңіз!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LogInActivity.this, CategoryActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.d("", "singInWithEmail:Fail");
                                    }
                                }
                            });
                    }
//***********************************************************************************************************************************


            }
        });

        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Log.d("Tag21","******************************************************m admin");
                    startActivity(new Intent(LogInActivity.this,CategoryActivity.class));
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
      //*********************************************************************************************************************

        UsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Кіру");
                AdLink.setVisibility(View.VISIBLE);
                UsLink.setVisibility(View.INVISIBLE);
               parentDbName="Users";
            }
        });


        AdLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Админ панель");
                parentDbName="Admins";
                AdLink.setVisibility(View.INVISIBLE);
                UsLink.setVisibility(View.VISIBLE);
            }
        });

        //*********************************************************************************************************************
    }

    private void logInAdmin() {
        String phone,adPassword;
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone=email.getText().toString();
        adPassword=password.getText().toString();
        if (email.getText()!=null && password.getText()!=null){
            Log.d("Tag21","log like admin");
            signInAccount(phone,adPassword);
        }else {
            Toast.makeText(getApplicationContext(),"Қате бар!",Toast.LENGTH_LONG).show();
        }


    }

    private void signInAccount(final String phone, final String myPassword) {
        final String state="Admins";
        final DatabaseReference RooterRef;
        RooterRef= FirebaseDatabase.getInstance().getReference();
        RooterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Log.d("tag", "---------------------------------------you exist");
                    if (dataSnapshot.child("Admins").child(phone).exists()) {
                        Log.d("tag", "****************************************************************you exist");
                        Admin adData = dataSnapshot.child(state).child(phone).getValue(Admin.class);
                        if (adData.getPhone().equals(phone)) {
                            Log.d("tag", "email fine****************************************************************");
                            if (adData.getPassword().equals(myPassword)) {
                                Intent intent = new Intent(LogInActivity.this, AdminPanelActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Қате пороль тердіңіз!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Қате email тердіңіз", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Қате бар!", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Қате бар!", Toast.LENGTH_LONG).show();
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        final AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Қош келдіңіз!",Toast.LENGTH_LONG).show();
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                }else {
                    Toast.makeText(getApplicationContext(),"Қате бар!",Toast.LENGTH_LONG).show();
                    Log.w("", "*********************************************"+"signInWithCredential:failure"+task.getException()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++", task.getException());
                }
            }

        });

    }
}
