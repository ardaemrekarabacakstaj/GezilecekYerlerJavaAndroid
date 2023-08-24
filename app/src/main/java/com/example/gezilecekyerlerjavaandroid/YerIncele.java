package com.example.gezilecekyerlerjavaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class YerIncele extends AppCompatActivity {

    EditText txtYer,txtAd,txtTarih;
    Button btnKonumGoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yer_incele);

        String ad = getIntent().getStringExtra("name");
        String country = getIntent().getStringExtra("country");
        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");
        String calendar = getIntent().getStringExtra("calendar");

        txtYer = findViewById(R.id.editTextGosterYer);
        txtAd = findViewById(R.id.editTextGosterUlke);
        txtTarih = findViewById(R.id.editTextTarihGoster);
        btnKonumGoster = findViewById(R.id.btnYerGoster);

        txtTarih.setText(calendar);
        txtYer.setText(ad);
        txtAd.setText(country);

        btnKonumGoster.setOnClickListener(v -> {
            Intent intent = new Intent(YerIncele.this,HaritadaGoster.class);
            intent.putExtra("latİ",lat);
            intent.putExtra("lonG",lon);
            startActivity(intent);
        });
    }
}