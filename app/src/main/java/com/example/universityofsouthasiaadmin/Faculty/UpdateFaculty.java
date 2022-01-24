package com.example.universityofsouthasiaadmin.Faculty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityofsouthasiaadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView cst,dtnt,tct;
    private LinearLayout CST_NoData,DNT_NoData,TCT_NoData;
    private ArrayList<TeacherData> list1,list2,list3;
    private DatabaseReference reference,dbRef;
    private TeacherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        cst=findViewById(R.id.udapte_faculty_recyclerView_cst);
        dtnt=findViewById(R.id.update_faculty_recyclerView_dnt);
        tct=findViewById(R.id.update_faculty_recyclerView_tct);

        CST_NoData=findViewById(R.id.cstNo_data);
        DNT_NoData=findViewById(R.id.dntNo_data);
        TCT_NoData=findViewById(R.id.tctNo_data);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");

        cstDepertment();
        dntDepartment();
        tctDepartment();

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateFaculty.this,AddTeacher.class);
                startActivity(intent);
            }
        });
    }
    private void cstDepertment() {
        dbRef= FirebaseDatabase.getInstance().getReference("teacher").child("CST");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if (!snapshot.exists()){
                    CST_NoData.setVisibility(View.VISIBLE);
                    cst.setVisibility(View.GONE);
                    Log.d("CSTDataCheck1", "onDataChange: CSTDataCheck: "+list1.toString());
                }else {
                    CST_NoData.setVisibility(View.GONE);
                    cst.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list1.add(data);
                        Log.d("CSTDataCheck0", "onDataChange: CSTDataCheck: "+list1.toString()+"\nsanp: "+data.toString());
                    }
                    cst.setHasFixedSize(true);
                    cst.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list1,UpdateFaculty.this);
                    cst.setAdapter(adapter);
                    Log.d("CSTDataCheck", "onDataChange: CSTDataCheck: "+list1.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void dntDepartment() {
        dbRef= FirebaseDatabase.getInstance().getReference("teacher").child("DTNT");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();

                DNT_NoData.setVisibility(View.GONE);
                dtnt.setVisibility(View.VISIBLE);
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    TeacherData data=snapshot.getValue(TeacherData.class);
                    list2.add(data);
                    Log.d("DTNTDataCheck", "onDataChange: DTNTDataCheck: "+list2.toString());
                /*if (!snapshot.exists()){
                    DNT_NoData.setVisibility(View.VISIBLE);
                    dtnt.setVisibility(View.GONE);
                    Log.d("DTNTDataCheck1", "onDataChange: DTNTDataCheck: "+list2.toString());

                }else {
                    DNT_NoData.setVisibility(View.GONE);
                    dtnt.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list2.add(data);
                        Log.d("DTNTDataCheck", "onDataChange: DTNTDataCheck: "+list2.toString());
                    }*/
                    dtnt.setHasFixedSize(true);
                    dtnt.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list2,UpdateFaculty.this);
                    dtnt.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("DTNTDataCheck", "onDataChange: DTNTDataCheck: "+list2.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void tctDepartment() {
        dbRef= FirebaseDatabase.getInstance().getReference("teacher").child("TCT");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if (!snapshot.exists()){
                    TCT_NoData.setVisibility(View.VISIBLE);
                    tct.setVisibility(View.GONE);

                }else {
                    TCT_NoData.setVisibility(View.GONE);
                    tct.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list3.add(data);

                    }
                    tct.setHasFixedSize(true);
                    tct.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list3,UpdateFaculty.this);
                    tct.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}