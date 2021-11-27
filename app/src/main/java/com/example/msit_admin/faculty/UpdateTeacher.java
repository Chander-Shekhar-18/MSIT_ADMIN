package com.example.msit_admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.msit_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateTeacher extends AppCompatActivity {

    private CircleImageView updateTeacherImage;
    private EditText updateTeacherName, updateTeacherEmail, updateTeacherPost;
    private Button updateTeacherBtn, deleteTeacherBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String name, email, image, post;

    private StorageReference storageReference;
    private DatabaseReference reference;

    private String downloadUrl, uniqueKey, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        initViews();

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference();


        uniqueKey = getIntent().getStringExtra("key");

        category = getIntent().getStringExtra("category");

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        post = getIntent().getStringExtra("post");

        try {
            Picasso.get()
                    .load(image)
                    .into(updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTeacherName.setText(name);
        updateTeacherEmail.setText(email);
        updateTeacherPost.setText(post
        );


        updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = updateTeacherName.getText().toString();
                email = updateTeacherEmail.getText().toString();
                post = updateTeacherPost.getText().toString();

                checkValidation();
            }
        });

        deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateTeacher.this, "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTeacher.this, UpdateFaculty.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacher.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        if (name.isEmpty()) {
            updateTeacherName.setError("Name Required");
            updateTeacherName.requestFocus();
        } else if (email.isEmpty()) {
            updateTeacherEmail.setError("Email Required");
            updateTeacherEmail.requestFocus();
        } else if (post.isEmpty()) {
            updateTeacherPost.setError("Post Required");
            updateTeacherPost.requestFocus();
        } else if (bitmap == null) {
            updateData(image);
        } else {
            uploadImage();
        }
    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child("Teacher").child(finalImage + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(UpdateTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(UpdateTeacher.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateData(String s) {
        HashMap hp = new HashMap();
        hp.put("name", name);
        hp.put("email", email);
        hp.put("post", post);
        hp.put("image", s);

        reference.child(category)
                .child(uniqueKey)
                .updateChildren(hp)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(UpdateTeacher.this, "Teacher Updated Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UpdateTeacher.this, UpdateFaculty.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacher.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {
        updateTeacherImage = findViewById(R.id.updateTeacherImage);
        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherBtn = findViewById(R.id.updateTeacherBtn);
        deleteTeacherBtn = findViewById(R.id.deleteTeacherBtn);
    }


    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateTeacherImage.setImageBitmap(bitmap);
        }

    }
}