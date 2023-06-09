package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   private EditText mEmail;
   private EditText mPass;
   private Button btnLogin;
   private TextView mForgetpassword;
   private TextView mSignuphere;
   private ProgressDialog mDialog;
   private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
        mDialog=new ProgressDialog(this);
        LoginDetails();
    }
    private void LoginDetails(){
        mEmail=findViewById(R.id.email_login);
        mPass=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        mForgetpassword=findViewById(R.id.forget_password);
        mSignuphere=findViewById(R.id.sign_up);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString().trim();
                String pass=mPass.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email required..");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password required...");
                    return;
                }
                mDialog.setMessage("processing...");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        mDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        //Toast.makeText(getApplicationContext(),"Login Successfull..",Toast.LENGTH_SHORT);
                    }
                    else{
                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Login failed..",Toast.LENGTH_SHORT);

                    }
                });
            }
        });
        mSignuphere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegActivity.class));
            }
        });
        mForgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetActivity.class));
            }
        });

    }
}