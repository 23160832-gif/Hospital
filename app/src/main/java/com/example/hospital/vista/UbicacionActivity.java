package com.example.hospital.vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hospital.R;

public class UbicacionActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    private TextView tvLatitud;
    private TextView tvLongitud;
    private TextView tvAltitud;
    private TextView tvPrecision;
    private TextView tvVelocidad;
    private Button btnObtenerUbicacion;
    private Button btnVolver;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        tvAltitud = findViewById(R.id.tvAltitud);
        tvPrecision = findViewById(R.id.tvPrecision);
        tvVelocidad = findViewById(R.id.tvVelocidad);
        btnObtenerUbicacion = findViewById(R.id.btnObtenerUbicacion);
        btnVolver = findViewById(R.id.btnVolver);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnObtenerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarPermisosYObtenerUbicacion();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verificarPermisosYObtenerUbicacion();
    }

    private void verificarPermisosYObtenerUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE);
        } else {
            obtenerUbicacion();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void obtenerUbicacion() {
        if (locationManager == null) {
            Toast.makeText(this, "LocationManager no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled && !networkEnabled) {
            Toast.makeText(this, "Proveedores de ubicación deshabilitados", Toast.LENGTH_SHORT).show();
            tvLatitud.setText("Latitud: --");
            tvLongitud.setText("Longitud: --");
            tvAltitud.setText("Altitud: --");
            tvPrecision.setText("Precisión: --");
            tvVelocidad.setText("Velocidad: --");
            return;
        }

        Location lastLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (gpsEnabled) {
                lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (lastLocation == null && networkEnabled) {
                lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if (lastLocation != null) {
            actualizarUI(lastLocation);
            Toast.makeText(this, "Ubicación actualizada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // Si no hay última ubicación, solicitar una actualización temporal
            Toast.makeText(this, "Obteniendo ubicación...", Toast.LENGTH_SHORT).show();
            solicitarActualizacionTemporal();
        }
    }

    private void solicitarActualizacionTemporal() {
        if (locationListener == null) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    actualizarUI(location);
                    Toast.makeText(UbicacionActivity.this, "Ubicación actualizada correctamente",
                            Toast.LENGTH_SHORT).show();
                    // Remover el listener después de obtener la ubicación
                    if (locationManager != null) {
                        locationManager.removeUpdates(this);
                    }
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
            };
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Solicitar actualización única con timeout de 10 segundos
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                // También podemos solicitar por red para mayor cobertura
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            } catch (SecurityException e) {
                Toast.makeText(this, "Error al solicitar ubicación", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void actualizarUI(Location location) {
        if (location != null) {
            tvLatitud.setText("Latitud: " + location.getLatitude());
            tvLongitud.setText("Longitud: " + location.getLongitude());

            if (location.hasAltitude()) {
                tvAltitud.setText("Altitud: " + location.getAltitude() + " m");
            } else {
                tvAltitud.setText("Altitud: N/A");
            }

            if (location.hasAccuracy()) {
                tvPrecision.setText("Precisión: " + location.getAccuracy() + " m");
            } else {
                tvPrecision.setText("Precisión: N/A");
            }

            if (location.hasSpeed()) {
                tvVelocidad.setText("Velocidad: " + location.getSpeed() + " m/s");
            } else {
                tvVelocidad.setText("Velocidad: N/A");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            try {
                locationManager.removeUpdates(locationListener);
            } catch (SecurityException e) {
            }
        }
    }
}