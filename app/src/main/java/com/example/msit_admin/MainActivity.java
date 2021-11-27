package com.example.msit_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.msit_admin.DeleteNotice.DeleteNoticeActivity;
import com.example.msit_admin.DeleteNotice.UploadNotice;
import com.example.msit_admin.faculty.UpdateFaculty;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialCardView cardViewNotice, addGalleryImage, addEBook, faculty, deleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addGalleryImage = findViewById(R.id.addGalleryImage);
        cardViewNotice = findViewById(R.id.addNotice);
        addEBook = findViewById(R.id.addEBook);
        faculty = findViewById(R.id.addFaculty);
        deleteNotice = findViewById(R.id.deleteNotice);

        cardViewNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        addEBook.setOnClickListener(this);
        faculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNotice:
                Intent intent = new Intent(MainActivity.this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.addGalleryImage:
                Intent intent1 = new Intent(MainActivity.this, UploadImage.class);
                startActivity(intent1);
                break;
            case R.id.addEBook:
                Intent intent2 = new Intent(MainActivity.this, UploadPdf.class);
                startActivity(intent2);
                break;
            case R.id.addFaculty:
                Intent intent3 = new Intent(MainActivity.this, UpdateFaculty.class);
                startActivity(intent3);
                break;
            case R.id.deleteNotice:
                Intent intent4 = new Intent(MainActivity.this, DeleteNoticeActivity.class);
                startActivity(intent4);
                break;
        }
    }
}