package com.example.eedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class ContactUsActivity extends AppCompatActivity {
    EditText sender_subject,sender_message;
    Button submit_btn;
    String subject,message,receiver_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sender_subject=findViewById(R.id.contact_subject);
        sender_message=findViewById(R.id.contact_message);
        submit_btn=findViewById(R.id.send_mail);

        receiver_mail="project.msc003@gmail.com";

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject=sender_subject.getText().toString().trim();
                message=sender_message.getText().toString().trim();
                if(subject.equals("") && message.equals("")){
                    return;
                }else{
                    SendingMail(subject,message);
                }
            }
        });
    }

    private void SendingMail(String subject, String message) {
        Intent Email=new Intent(Intent.ACTION_SEND);

        Email.setData(Uri.parse("mailto:"));
        Email.setType("text/plain");

        Email.putExtra(Intent.EXTRA_EMAIL,new String[]{receiver_mail});
        Email.putExtra(Intent.EXTRA_SUBJECT,subject);
        Email.putExtra(Intent.EXTRA_TEXT,message);

        try {
            startActivity(Intent.createChooser(Email,"Choose Email Client"));
            Toast.makeText(this,"Mail send successfully",Toast.LENGTH_LONG).show();
            sender_subject.setText("");
            sender_message.setText("");
        }catch(Exception e){
            Log.i("Sending Mail", "SendingMail: "+e.getCause().toString());
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
}