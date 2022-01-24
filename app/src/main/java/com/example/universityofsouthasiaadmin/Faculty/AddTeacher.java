package com.example.universityofsouthasiaadmin.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.universityofsouthasiaadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeacher extends AppCompatActivity {
    private CircleImageView addTeacherImage;
    private EditText teacherName,teacherEmail,teacherPost;
    private Spinner addTeacherCategory;
    private Button teacherAddBtn;
    private final int REQ=1;
    private Bitmap bitmap=null;
    private String category;
    private String name,email,post,downloadUrl;
    private ProgressDialog dialog;
    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        allFindingView();


        String [] items=new String[]{"Select category","CST","DNT","TCT"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String >(AddTeacher.this, android.R.layout.simple_expandable_list_item_1,items));
        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        teacherAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        addTeacherImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }



    private void checkValidation() {
        name=teacherName.getText().toString();
        email=teacherEmail.getText().toString();
        post=teacherPost.getText().toString();
        if (name.isEmpty()){
            teacherName.setError("Empty");
            teacherName.requestFocus();
        }else if (email.isEmpty()){
            teacherEmail.setError("Empty");
            teacherEmail.requestFocus();
        }else if (post.isEmpty()){
            teacherPost.setError("Empty");
            teacherPost.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(this, "Provide Teacher Category", Toast.LENGTH_SHORT).show();
        } else if (bitmap==null){
            dialog.setMessage("Uploading....");
            dialog.show();
            insertData();
        }else {
            dialog.setMessage("Uploading....");
            dialog.show();
            uploadImage();
        }
    }

    private void uploadImage() {
        ByteArrayOutputStream boas=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,boas);
        byte[] finalImage=boas.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("teacher").child(finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();

                                }
                            });

                        }
                    });
                }else {
                    dialog.dismiss();
                    Toast.makeText(AddTeacher.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void insertData() {
        dbRef=reference.child(category);
        final String uniqkey=dbRef.push().getKey();


        TeacherData teacherData=new TeacherData(name,email,post,downloadUrl,uniqkey);

        dbRef.child(uniqkey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialog.dismiss();
                Toast.makeText(AddTeacher.this, "Teacher Added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(AddTeacher.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode==RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addTeacherImage.setImageBitmap(bitmap);

        }
    }

    private void allFindingView() {
        addTeacherImage=findViewById(R.id.addTeacherImage);
        teacherName=findViewById(R.id.teacherName);
        teacherEmail=findViewById(R.id.teacherEmail);
        teacherPost=findViewById(R.id.teacherPost);
        addTeacherCategory=findViewById(R.id.addTacherSpinner);
        teacherAddBtn=findViewById(R.id.teacherAddBtn);
        dialog=new ProgressDialog(AddTeacher.this);
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();
    }
}