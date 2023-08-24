package com.example.gezilecekyerlerjavaandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class HaritadaGoster extends AppCompatActivity {

    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haritada_goster);

        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("OSMMap", MODE_PRIVATE));

        mapView = findViewById(R.id.mapView2);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);

        String lat = getIntent().getStringExtra("latİ");
        String lon = getIntent().getStringExtra("lonG");

        double defaultLat = Double.parseDouble(lat);
        double defaultLon = Double.parseDouble(lon);
        mapView.getController().setCenter(new GeoPoint(defaultLat, defaultLon));
        mapView.getController().setZoom(15);
    }
}