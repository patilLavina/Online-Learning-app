package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn2 extends AppCompatActivity {
    TextView create_account,forgot_password;
    Button btn_login;
    TextInputEditText username,pass;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_login=findViewById(R.id.btnlogin);
        username=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        create_account=findViewById(R.id.create_account);
        forgot_password=findViewById(R.id.forgot_password);
        pDialog=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_acc_intent=new Intent(LogIn2.this,RegisterActivity.class);
                startActivity(create_acc_intent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_password_intent=new Intent(LogIn2.this,ForgotPasswordActivity.class);
                startActivity(forgot_password_intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin(){

        String email = username.getText().toString();
        final String password = pass.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter your mail address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        pDialog.setTitle("Sign In");
        pDialog.setMessage("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LogIn2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            pDialog.dismiss();
                            checkEmailVerification();
                        }
                        else
                        {
                            pDialog.dismiss();
                            String messege=task.getException().toString();
                            Toast.makeText(LogIn2.this, "Error: "+messege, Toast.LENGTH_SHORT).show();
                            pass.setText("");
                        }

                    }
                });

    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if (emailflag)
        {
            Toast.makeText(LogIn2.this, "Logged in successfully....", Toast.LENGTH_SHORT).show();
            finish();
            username.setText("");
            pass.setText("");
            SendToMainActivity();
        }
        else
        {
            Toast.makeText(LogIn2.this, "Your email is not verified...", Toast.LENGTH_SHORT).show();
            pass.setText("");
            mAuth.signOut();
        }

    }

    public void SendToMainActivity(){
        Intent loginIntent=new Intent(LogIn2.this,MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

}