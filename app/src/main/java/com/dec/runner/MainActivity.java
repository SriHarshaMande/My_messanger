package com.dec.runner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button login_btn,reg_btn;
private EditText pswd_inp,email_inp;
public Toast t;
private Context context;
private FirebaseAuth auth;
private FirebaseAuth.AuthStateListener loginlistner;

    @Override
    protected void onStart() {
        super.onStart ();
        auth.addAuthStateListener (loginlistner);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_btn=findViewById(R.id.login_btn);
        pswd_inp=findViewById(R.id.pswd_inp);
        email_inp=findViewById(R.id.email_inp);
        login_btn.setOnClickListener(this);
        reg_btn=findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(this);
        getWindow ().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        context=getApplicationContext();
        t=Toast.makeText(context,"",500);


        //Firebase
        FirebaseApp.initializeApp(context);
        auth=FirebaseAuth.getInstance();
        loginlistner = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=auth.getCurrentUser();
                if(firebaseUser!=null)
                {   t.setText ("Logged In");
                    Intent intent = new Intent(MainActivity.this, Home1.class);
                    startActivity(intent);
                }
                else
                {
                    t.setText("Login first");

                }
                t.show();
            }
        };

    }

    @Override
    public void onClick(View view) {

        String email=email_inp.getText().toString();
        String password = pswd_inp.getText().toString();
        switch (view.getId()) {
            case R.id.login_btn:
                if (email.length() != 0 && password.length() != 0) {
                    auth.signInWithEmailAndPassword (email, password).addOnCompleteListener (MainActivity.this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful ()) {
                                Intent intent = new Intent (MainActivity.this, Home1.class);
                                startActivity (intent);
                                t.setText (getResources ().getString (R.string.sucess));
                            } else {
                                t.setText (getResources ().getString (R.string.invalid));
                            }
                            t.show ();
                        }
                    });
                }
                break;
            case R.id.reg_btn:
                if (email.length() != 0 && password.length() != 0) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                t.setText("Failed--Already Registered");
                            } else {
                                t.setText("Congo");
                            }
                            t.show();

                        }
                    });
                }

                break;

        }
    }
}
