package com.example.gezilecekyerlerjavaandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;

public class Goster extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<String> imageUrlList;
    private ArrayList<Integer> heightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goster);

        recyclerView = findViewById(R.id.recyclerView);
        imageUrlList = new ArrayList<>();
        heightList = new ArrayList<>();

        adapter = new MyAdapter(this, imageUrlList,heightList);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fetchDataFromFirebaseStorage();
    }

    private void fetchDataFromFirebaseStorage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images");


        storageRef.listAll().addOnSuccessListener(listResult -> {
            imageUrlList.clear();
            heightList.clear();
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    imageUrlList.add(imageUrl);
                    heightList.add(getRandomHeight());
                    adapter.notifyDataSetChanged();
                }).addOnFailureListener(Throwable::printStackTrace);
            }
        }).addOnFailureListener(Throwable::printStackTrace);
    }
    private int getRandomHeight() {
        return new Random().nextInt(401) + 450;
    }
}
