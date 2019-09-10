package com.dec.runner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity implements View.OnClickListener {
private Button update_btn;
    public Toast t;
private EditText name_inp,pswd_inp1,phone_inp,oldPass_inp;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);
        getWindow ().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        update_btn=findViewById (R.id.update_btn);
        name_inp=findViewById (R.id.name_inp);
        pswd_inp1=findViewById (R.id.pswd_inp1);
        phone_inp=findViewById (R.id.phone_inp);
        oldPass_inp=findViewById (R.id.oldPass_inp);
        t=Toast.makeText (getApplicationContext (),"",Toast.LENGTH_SHORT);

        update_btn.setOnClickListener (this);
        auth=FirebaseAuth.getInstance ();

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("/users");
//
//        myRef.setValue("hai");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.update_btn:
                String name=name_inp.getText ().toString ();
                final String phone=phone_inp.getText ().toString ();
                final String password=pswd_inp1.getText ().toString ();
                final FirebaseUser firebaseUser=auth.getCurrentUser ();
                String email=firebaseUser.getEmail ().toString ();
                String oldPass=oldPass_inp.getText().toString();
                AuthCredential authCredential= EmailAuthProvider.getCredential (email,oldPass);
                firebaseUser.reauthenticate (authCredential).addOnCompleteListener (new OnCompleteListener<Void> (){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful ())
                        {
                            firebaseUser.updatePassword (password);
                           // firebaseUser.updatePhoneNumber (phone);
                            t.setText(getResources ().getString (R.string.sucess));

                        }
                        else
                            t.setText(getResources ().getString (R.string.invalid));

                        t.show ();
                    }



                });
                Intent intent=new Intent (this,Home1.class);
                startActivity (intent);

                break;
        }
    }
}
