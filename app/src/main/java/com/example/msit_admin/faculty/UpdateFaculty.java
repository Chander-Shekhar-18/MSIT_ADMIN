package com.example.msit_admin.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.msit_admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {

    FloatingActionButton fab;

    private RecyclerView csDepartment, itDepartment, eceDepartment, eeeDepartment, appliedScienceDepartment;
    private LinearLayout csNoData, itNoData, eceNoData, eeeNoData, appliedScienceNoData;
    private List<TeacherData> csList, itList, eceList, eeeList, appliedScienceList;
    private TeacherAdapter adapter;

    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        fab = findViewById(R.id.fab);

        csDepartment = findViewById(R.id.csDepartment);
        itDepartment = findViewById(R.id.itDepartment);
        eceDepartment = findViewById(R.id.eceDepartment);
        eeeDepartment = findViewById(R.id.eeeDepartment);
        appliedScienceDepartment = findViewById(R.id.appliedScienceDepartment);
        csNoData = findViewById(R.id.csNodata);
        itNoData = findViewById(R.id.itNodata);
        eceNoData = findViewById(R.id.eceNodata);
        eeeNoData = findViewById(R.id.eeeNodata);
        appliedScienceNoData = findViewById(R.id.appliedScienceNodata);

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");

        csDepartment();

        itDepartment();

        eceDepartment();

        eeeDepartment();

        appliedScienceDepartment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this, AddTeacher.class));
            }
        });
    }

    private void appliedScienceDepartment() {
        dbRef = reference.child("Applied Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appliedScienceList = new ArrayList<>();
                if (!snapshot.exists()) {
                    appliedScienceNoData.setVisibility(View.VISIBLE);
                    appliedScienceDepartment.setVisibility(View.GONE);
                } else {
                    appliedScienceNoData.setVisibility(View.GONE);
                    appliedScienceDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        appliedScienceList.add(data);
                    }
                    appliedScienceDepartment.setHasFixedSize(true);
                    appliedScienceDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(appliedScienceList, UpdateFaculty.this, "Applied Science");
                    appliedScienceDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eeeDepartment() {
        dbRef = reference.child("EEE");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eeeList = new ArrayList<>();
                if (!snapshot.exists()) {
                    eeeNoData.setVisibility(View.VISIBLE);
                    eeeDepartment.setVisibility(View.GONE);
                } else {
                    eeeNoData.setVisibility(View.GONE);
                    eeeDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        eeeList.add(data);
                    }
                    eeeDepartment.setHasFixedSize(true);
                    eeeDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(eeeList, UpdateFaculty.this, "EEE");
                    eeeDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eceDepartment() {
        dbRef = reference.child("ECE");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eceList = new ArrayList<>();
                if (!snapshot.exists()) {
                    eceNoData.setVisibility(View.VISIBLE);
                    eceDepartment.setVisibility(View.GONE);
                } else {
                    eceNoData.setVisibility(View.GONE);
                    eceDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        eceList.add(data);
                    }
                    eceDepartment.setHasFixedSize(true);
                    eceDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(eceList, UpdateFaculty.this, "ECE");
                    eceDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void itDepartment() {
        dbRef = reference.child("IT");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itList = new ArrayList<>();
                if (!snapshot.exists()) {
                    itNoData.setVisibility(View.VISIBLE);
                    itDepartment.setVisibility(View.GONE);
                } else {
                    itNoData.setVisibility(View.GONE);
                    itDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        itList.add(data);
                    }
                    itDepartment.setHasFixedSize(true);
                    itDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(itList, UpdateFaculty.this, "IT");
                    itDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void csDepartment() {
        dbRef = reference.child("CSE");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                csList = new ArrayList<>();
                if (!snapshot.exists()) {
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                } else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        csList.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(csList, UpdateFaculty.this, "CSE");
                    csDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}