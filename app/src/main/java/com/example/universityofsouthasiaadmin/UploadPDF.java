package com.example.universityofsouthasiaadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class UploadPDF extends AppCompatActivity {
    private CardView cardView;
    private final int REQ=1;
    private Uri pdfData;
    private EditText pdfTitle;
    private Button uploadPdfBtn;
    //uploadFirebaseImage
    private DatabaseReference reference;
    private StorageReference storageReference;
    String downloadUrl="";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        dialog=new ProgressDialog(UploadPDF.this);
        cardView=findViewById(R.id.selectImageforPdf);
        pdfTitle=findViewById(R.id.PdfTitle);
        uploadPdfBtn=findViewById(R.id.uploadPdfBtn);
        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }
    private void openGallery(){
       Intent intent=new Intent();
       intent.setType("*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode==RESULT_OK){
            pdfData=data.getData();
            Toast.makeText(this, "PDF File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}