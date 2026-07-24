package com.example.hospital.vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
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

public class WifiActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    private TextView tvSsid;
    private TextView tvBssid;
    private TextView tvIp;
    private TextView tvFrecuencia;
    private TextView tvVelocidad;
    private TextView tvIntensidad;
    private TextView tvEstado;
    private Button btnActualizar;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        tvSsid = findViewById(R.id.tvSsid);
        tvBssid = findViewById(R.id.tvBssid);
        tvIp = findViewById(R.id.tvIp);
        tvFrecuencia = findViewById(R.id.tvFrecuencia);
        tvVelocidad = findViewById(R.id.tvVelocidad);
        tvIntensidad = findViewById(R.id.tvIntensidad);
        tvEstado = findViewById(R.id.tvEstado);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);

        verificarPermisosWifi();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarPermisosWifi();
                Toast.makeText(WifiActivity.this, "Información actualizada", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void verificarPermisosWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            } else {
                obtenerInformacionWifi();
            }
        } else {
            obtenerInformacionWifi();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerInformacionWifi();
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de ubicación necesario para obtener SSID", Toast.LENGTH_SHORT).show();
                tvSsid.setText("SSID: Permiso denegado");
                tvEstado.setText("Estado: Permiso requerido");
            }
        }
    }

    private void obtenerInformacionWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager == null) {
            tvEstado.setText("Estado: WiFi no disponible");
            return;
        }

        if (!wifiManager.isWifiEnabled()) {
            tvEstado.setText("Estado: WiFi Desactivado");
            tvSsid.setText("SSID: --");
            tvBssid.setText("BSSID: --");
            tvIp.setText("IP: --");
            tvFrecuencia.setText("Frecuencia: --");
            tvVelocidad.setText("Velocidad: --");
            tvIntensidad.setText("Intensidad: --");
            return;
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null || wifiInfo.getSSID() == null) {
            tvEstado.setText("Estado: No conectado");
            tvSsid.setText("SSID: --");
            tvBssid.setText("BSSID: --");
            tvIp.setText("IP: --");
            tvFrecuencia.setText("Frecuencia: --");
            tvVelocidad.setText("Velocidad: --");
            tvIntensidad.setText("Intensidad: --");
            return;
        }

        String ssid = wifiInfo.getSSID();
        if (ssid != null && !ssid.isEmpty() && !ssid.equals("<unknown ssid>")) {
            // Remove quotes if present (Android sometimes adds "")
            if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
            tvSsid.setText("SSID: " + ssid);
        } else {
            // Para Android 10+, verificar si tenemos permisos
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    tvSsid.setText("SSID: " + (ssid != null ? ssid : "Desconocido"));
                } else {
                    tvSsid.setText("SSID: Permiso de ubicación requerido");
                }
            } else {
                // En versiones anteriores, si es <unknown ssid>, mostrar mensaje
                if (ssid != null && ssid.equals("<unknown ssid>")) {
                    tvSsid.setText("SSID: No disponible (intenta activar ubicación)");
                } else {
                    tvSsid.setText("SSID: " + (ssid != null ? ssid : "No disponible"));
                }
            }
        }

        String bssid = wifiInfo.getBSSID();
        if (bssid != null && !bssid.isEmpty()) {
            tvBssid.setText("BSSID: " + bssid);
        } else {
            tvBssid.setText("BSSID: --");
        }

        int ipInt = wifiInfo.getIpAddress();
        if (ipInt != 0) {
            String ip = intToIp(ipInt);
            tvIp.setText("IP: " + ip);
        } else {
            tvIp.setText("IP: --");
        }

        int frequency = wifiInfo.getFrequency();
        if (frequency > 0) {
            tvFrecuencia.setText("Frecuencia: " + frequency + " MHz");
        } else {
            tvFrecuencia.setText("Frecuencia: --");
        }

        int linkSpeed = wifiInfo.getLinkSpeed();
        if (linkSpeed > 0) {
            tvVelocidad.setText("Velocidad: " + linkSpeed + " Mbps");
        } else {
            tvVelocidad.setText("Velocidad: --");
        }

        int rssi = wifiInfo.getRssi();
        if (rssi != Integer.MAX_VALUE) {
            tvIntensidad.setText("Intensidad: " + rssi + " dBm");
        } else {
            tvIntensidad.setText("Intensidad: --");
        }

        tvEstado.setText("Estado: Conectado");
    }

    private String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }
}