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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout username,usermail,userpassword,userconfpassword;
    Button btnSignup;
    TextView signin;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressDialog mdialog;
    String name,email,password,conpassword, currentUserUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username=findViewById(R.id.full_name);
        usermail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        userconfpassword=findViewById(R.id.confirm_password);
        signin=findViewById(R.id.reg_signin);
        btnSignup = findViewById(R.id.btn_register);
        btnSignup.setOnClickListener(this);
        mdialog=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");

    }
    public void onClick(View view){
        if (view==btnSignup){
            UserRegister();
        }
    }

    private void UserRegister(){
        name=username.getEditText().getText().toString();
        email = usermail.getEditText().getText().toString();
        password = userpassword.getEditText().getText().toString();
        conpassword = userconfpassword.getEditText().getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(conpassword)){

            if (password.equals(conpassword)){

                if(isValidPassword(password)){
                    if(isValid(email)){
                        mdialog.setTitle("Creating New Account");
                        mdialog.setMessage("We are creating new account for you please wait...");
                        mdialog.setCanceledOnTouchOutside(false);
                        mdialog.show();
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    mdialog.dismiss();
                                    // saving data
                                    OnAuth(task.getResult().getUser());

                                    SendEmailVerification();

                                } else {
                                    String errorMessage = task.getException().toString();
                                    Toast.makeText(RegisterActivity.this, "Error :"+ errorMessage, Toast.LENGTH_LONG).show();
                                }


                            }
                        });
                    }else{
                        Toast.makeText(RegisterActivity.this, "Email is not Valid", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Password is not valid", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this, "Password and confirm password does not match", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void SendEmailVerification()
    {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "Check your Email for Verification...", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        SendUserToLoginActivity();

                    }

                }
            });
        }
    }



    private boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){

            sendToMain();

        }
    } */

    public void OnAuth(FirebaseUser user){
        createAnUser(user.getUid());
    }

    private void createAnUser(String uid) {
        UserInformation userInformation=BuildNewUser();
        databaseReference.child(uid).setValue(userInformation);
    }

    private UserInformation BuildNewUser() {
        return new UserInformation(
                getName(),
                getemail()
        );
    }

    private String getName() {
        return name;
    }

    private String getemail() {
        return email;
    }


    private void SendUserToLoginActivity()
    {
        Intent loginIntent=new Intent(RegisterActivity.this,LogIn.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }
}