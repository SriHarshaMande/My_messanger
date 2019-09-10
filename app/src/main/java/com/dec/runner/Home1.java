package com.dec.runner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home1 extends AppCompatActivity implements View.OnClickListener {
private Button logout_btn,update_btn;
    public Toast t;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener loginlistner;

    @Override
    protected void onStart() {
        super.onStart ();
        auth.addAuthStateListener (loginlistner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        getWindow ().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        logout_btn=findViewById(R.id.logout_btn);
        update_btn=findViewById (R.id.update_btn);
        update_btn.setOnClickListener (this);
        logout_btn.setOnClickListener(this);
        t=Toast.makeText (getApplicationContext (),"",500);

        //firebase
        auth=FirebaseAuth.getInstance();
        loginlistner = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = auth.getCurrentUser ();
                //
                if(firebaseUser==null) {
                    t.setText ("Login first");
                    t.show ();
                    Intent intent=new Intent (Home1.this,MainActivity.class);
                    startActivity (intent);
                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.logout_btn:
                auth.signOut ();

                break;
            case R.id.update_btn:
                Intent intent=new Intent (this,Profile.class);
                startActivity (intent);
                break;

        }
    }
}
