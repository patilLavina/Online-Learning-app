package com.example.eedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CertificateActivity extends AppCompatActivity {
    Button download_pdf;
    EditText name;
    Bitmap bmp,scaledbmp,ribbon,scaleRibbon,stamp,scaleStamp;
    int pageWidth=1500;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        download_pdf=findViewById(R.id.download);
        name=findViewById(R.id.certificate_name);

        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.certificate_border);
        scaledbmp=Bitmap.createScaledBitmap(bmp,1500,1125,false);

        ribbon= BitmapFactory.decodeResource(getResources(),R.drawable.gold_ribbon);
        scaleRibbon=Bitmap.createScaledBitmap(ribbon,800,200,false);

        stamp= BitmapFactory.decodeResource(getResources(),R.drawable.badge);
        scaleStamp=Bitmap.createScaledBitmap(stamp,250,250,false);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        download_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadPDF();
            }
        });

    }

    private void DownloadPDF() {

        date=new Date();

        PdfDocument pdfDocument=new PdfDocument();
        Paint paint= new Paint();
        Paint title=new Paint();

        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(1500,1130,1).create();
        PdfDocument.Page page=pdfDocument.startPage(pageInfo);
        Canvas canvas=page.getCanvas();
        canvas.drawBitmap(scaledbmp,10,10,paint);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(90);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setLetterSpacing((float) 0.1);
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("CERTIFICATE",pageWidth/2,250,title);

        title.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));
        title.setTextSize(40);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("OF COMPLETION",pageWidth/2,310,title);

        canvas.drawBitmap(scaleRibbon,350,300,paint);

        title.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("THIS CERTIFICATE IS PRESENTED TO",pageWidth/2,500,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(50);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(name.getText().toString(),pageWidth/2,570,title);

        title.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));
        title.setTextSize(20);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("...................................................................",pageWidth/2,580,title);

        title.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("SUCCESSFULLY COMPLETED THE",pageWidth/2,630,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(40);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("COURSE NAME",pageWidth/2,680,title);

        canvas.drawBitmap(scaleStamp,630,750,paint);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(this,R.color.golden));
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("eEdu",755,890,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        DateFormat dateFormat=new SimpleDateFormat("MMMM dd,yyyy");
        canvas.drawText(dateFormat.format(date),400,890,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(20);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("_______________",300,900,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("DATE",350,940,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(20);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("_______________",1200,900,title);

        title.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(this,R.color.black));
        title.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("SIGNATURE",1200,940,title);







        pdfDocument.finishPage(page);

        File file=new File(Environment.getExternalStorageDirectory(),"/eEdu Certificate.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        pdfDocument.close();

        Toast.makeText(CertificateActivity.this,"File download",Toast.LENGTH_SHORT).show();
    }











}