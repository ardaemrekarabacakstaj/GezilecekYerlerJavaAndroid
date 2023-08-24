package com.example.gezilecekyerlerjavaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

public class MapAdd extends AppCompatActivity {

    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_add);

        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("OSMMap", MODE_PRIVATE));

        mapView = findViewById(R.id.mapView2);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);


        double defaultLat = 41.026092;
        double defaultLon = 28.975049;
        mapView.getController().setCenter(new GeoPoint(defaultLat, defaultLon));
        mapView.getController().setZoom(15);


        mapView.getOverlays().add(new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {

                double latitude = p.getLatitude();
                double longitude = p.getLongitude();

                AlertDialog.Builder builder = new AlertDialog.Builder(MapAdd.this);
                builder.setTitle("Kaydet");
                builder.setMessage("Bu konumu kaydetmek istiyor musunuz?");
                builder.setPositiveButton("Evet", (dialog, which) -> {
                    Toast.makeText(MapAdd.this, "Yer Başarıyla Eklendi!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapAdd.this,Ekle.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);
                });
                builder.setNegativeButton("Hayır", (dialog, which) -> dialog.dismiss());

                builder.show();

                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        }));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Configuration.getInstance().save(getApplicationContext(), getSharedPreferences("OSMMap", MODE_PRIVATE));
    }
}