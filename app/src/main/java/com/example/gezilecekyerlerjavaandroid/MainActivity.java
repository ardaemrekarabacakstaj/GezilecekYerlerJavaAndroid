package com.example.gezilecekyerlerjavaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn, btn2;
    String ad, ulke, tarih, lon, lat;
    ArrayList<String> adS, ulkeS, tarihS, lonS, latS;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        listView = findViewById(R.id.listview);

        adS = new ArrayList<>();
        ulkeS = new ArrayList<>();
        tarihS = new ArrayList<>();
        lonS = new ArrayList<>();
        latS = new ArrayList<>();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ekle.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Goster.class);
                startActivity(intent);
            }
        });
        verileriGetir();
    }

    private void verileriGetir() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("gezilecekyer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("başarılı", document.getId() + " => " + document.getData());

                                ad = document.getString("ad");
                                tarih = document.getString("tarih");
                                ulke = document.getString("ulke");
                                lon = document.getString("lon");
                                lat = document.getString("lat");

                                adS.add(ad);
                                ulkeS.add(ulke);
                                latS.add(lat);
                                lonS.add(lon);
                                tarihS.add(tarih);
                            }

                            Adapter adapter = new Adapter(MainActivity.this,adS,ulkeS);
                            listView.setAdapter(adapter);
                            listOnClick(listView);
                        } else {
                            Log.w("başarısız", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    private void listOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickName = adS.get(position).toString();
                String clickCountry = ulkeS.get(position).toString();
                String clickCalendar = tarihS.get(position).toString();
                String clickLon = lonS.get(position).toString();
                String clickLat = latS.get(position).toString();

                Intent intent = new Intent(MainActivity.this,YerIncele.class);
                intent.putExtra("name",clickName);
                intent.putExtra("country",clickCountry);
                intent.putExtra("calendar",clickCalendar);
                intent.putExtra("lat",clickLat);
                intent.putExtra("lon",clickLon);
                startActivity(intent);
            }
        });
    }
}
