package com.example.hospital.vista;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;

public class WifiActivity extends AppCompatActivity {

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

        // Vincular componentes
        tvSsid = findViewById(R.id.tvSsid);
        tvBssid = findViewById(R.id.tvBssid);
        tvIp = findViewById(R.id.tvIp);
        tvFrecuencia = findViewById(R.id.tvFrecuencia);
        tvVelocidad = findViewById(R.id.tvVelocidad);
        tvIntensidad = findViewById(R.id.tvIntensidad);
        tvEstado = findViewById(R.id.tvEstado);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);

        // Cargar información inicial
        obtenerInformacionWifi();

        // Listener para actualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerInformacionWifi();
                Toast.makeText(WifiActivity.this, "Información actualizada", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        if (wifiInfo == null) {
            tvEstado.setText("Estado: No conectado");
            tvSsid.setText("SSID: --");
            tvBssid.setText("BSSID: --");
            tvIp.setText("IP: --");
            tvFrecuencia.setText("Frecuencia: --");
            tvVelocidad.setText("Velocidad: --");
            tvIntensidad.setText("Intensidad: --");
            return;
        }

        // SSID
        String ssid = wifiInfo.getSSID();
        if (ssid != null && !ssid.isEmpty()) {
            // Remove quotes if present (Android sometimes adds "")
            if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
            tvSsid.setText("SSID: " + ssid);
        } else {
            tvSsid.setText("SSID: --");
        }

        // BSSID
        String bssid = wifiInfo.getBSSID();
        if (bssid != null && !bssid.isEmpty()) {
            tvBssid.setText("BSSID: " + bssid);
        } else {
            tvBssid.setText("BSSID: --");
        }

        // Dirección IP
        int ipInt = wifiInfo.getIpAddress();
        if (ipInt != 0) {
            String ip = intToIp(ipInt);
            tvIp.setText("IP: " + ip);
        } else {
            tvIp.setText("IP: --");
        }

        // Frecuencia
        int frequency = wifiInfo.getFrequency();
        if (frequency > 0) {
            tvFrecuencia.setText("Frecuencia: " + frequency + " MHz");
        } else {
            tvFrecuencia.setText("Frecuencia: --");
        }

        // Velocidad de enlace
        int linkSpeed = wifiInfo.getLinkSpeed();
        if (linkSpeed > 0) {
            tvVelocidad.setText("Velocidad: " + linkSpeed + " Mbps");
        } else {
            tvVelocidad.setText("Velocidad: --");
        }

        // Intensidad de señal (RSSI)
        int rssi = wifiInfo.getRssi();
        if (rssi != Integer.MAX_VALUE) {
            tvIntensidad.setText("Intensidad: " + rssi + " dBm");
        } else {
            tvIntensidad.setText("Intensidad: --");
        }

        // Estado de conexión
        tvEstado.setText("Estado: Conectado");
    }

    /**
     * Convierte una dirección IP entera (representación de red) a su formato IPv4 estándar.
     */
    private String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }
}