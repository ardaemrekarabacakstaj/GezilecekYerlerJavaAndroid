package com.example.gezilecekyerlerjavaandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Ekle extends AppCompatActivity {

    Button btnHarita,btnResim,btnKaydet;
    EditText txtAd,txtUlke;
    CalendarView takvim;
    double latitude, longitude;
    String secilenTarih,getGidilecekYer,getGidilecekUlke;
    private Uri selectedImageUri;

    private static final int REQUEST_IMAGE_PICK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        takvim = findViewById(R.id.calendarView);
        txtAd = findViewById(R.id.editTextTextPersonName);
        txtUlke = findViewById(R.id.editTextTextPersonName2);
        btnHarita = findViewById(R.id.button3);
        btnResim = findViewById(R.id.button4);
        btnKaydet = findViewById(R.id.button5);

        takvim.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            secilenTarih = dayOfMonth + "-" + (month + 1) + "-" + year;
            Toast.makeText(this, "Seçilen Tarih: " + secilenTarih, Toast.LENGTH_SHORT).show();
        });

        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);

        Log.d("lon", String.valueOf(longitude));
        Log.d("lat", String.valueOf(latitude));

        btnHarita.setOnClickListener(v -> {
            konumAl();
        });
        btnKaydet.setOnClickListener(v -> {
           yerKaydet();
        });
        btnResim.setOnClickListener(v -> {
            pickImageFromGallery();
        });

    }
    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
            }
        }
    }

    private void konumAl(){
        Intent intent = new Intent(Ekle.this,MapAdd.class);
        startActivity(intent);
    }
    private void yerKaydet(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        getGidilecekYer = txtAd.getText().toString().trim();
        getGidilecekUlke = txtUlke.getText().toString().trim();

        Map<String, Object> gezilecekyer = new HashMap<>();
        gezilecekyer.put("ad", getGidilecekYer);
        gezilecekyer.put("ulke", getGidilecekUlke);
        gezilecekyer.put("lon", String.valueOf(longitude));
        gezilecekyer.put("lat", String.valueOf(latitude));
        gezilecekyer.put("tarih", secilenTarih);



        db.collection("gezilecekyer")
                .add(gezilecekyer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("başarılı", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(Ekle.this, "Başarıyla Kaydedildi", Toast.LENGTH_SHORT).show();


                        if(selectedImageUri != null) {
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();

                            String filename = documentReference.getId() + ".jpg";
                            StorageReference imageRef = storageRef.child("images/" + filename);

                            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
                            uploadTask.addOnSuccessListener(taskSnapshot -> {
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    gezilecekyer.put("imageUrl", imageUrl);
                                    Toast.makeText(Ekle.this, "Resim Başarıyla Yüklendi", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Ekle.this,MainActivity.class);
                                    startActivity(intent);
                                }).addOnFailureListener(e -> {
                                    Log.w("ImageURL", "Error getting download URL", e);
                                });
                            }).addOnFailureListener(e -> {
                                Log.w("ImageUpload", "Error uploading image", e);
                            });
                        } else {
                            Toast.makeText(Ekle.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("başarısız", "Error adding document", e);
                    }
                });
    }
}