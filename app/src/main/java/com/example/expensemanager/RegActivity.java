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

public class RegActivity extends AppCompatActivity {
  private EditText mEmail;
  private EditText mPass;
  private Button btnReg;
  private TextView mSignin;
  private FirebaseAuth mAuth;
  private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        reg();
    }
    private void reg(){
        mEmail=findViewById(R.id.email_reg);
        mPass=findViewById(R.id.password_reg);
        btnReg=findViewById(R.id.btn_reg);
        mSignin=findViewById(R.id.signin_here);

        btnReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            String email=mEmail.getText().toString().trim();
            String pass=mPass.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email Required");
                return;
            }
            if(TextUtils.isEmpty(pass)){
                mPass.setError("Password Required...");
            }
            mDialog.setMessage("processing...");
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registration complete",Toast.LENGTH_SHORT);
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                else{
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT);
                }
            });
        }

        } );
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}