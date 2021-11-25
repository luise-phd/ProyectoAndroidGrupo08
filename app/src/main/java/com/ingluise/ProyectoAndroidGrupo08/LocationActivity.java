package com.ingluise.ProyectoAndroidGrupo08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Códigos de petición
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_CHECK_SETTINGS = 2;

    public static final long UPDATE_INTERVAL = 1000;
    public static final long UPDATE_FASTEST_INTERVAL = UPDATE_INTERVAL / 2;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private TextView mLatitude, mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLatitude = findViewById(R.id.tv_latitude);
        mLongitude = findViewById(R.id.tv_longitude);

        //Establecer punto de entrada para la API de ubicación
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();

        //Crear configuración de peticiones
        mLocationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Crear opciones de peticiones
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);

        // Verificar ajustes de ubicación actuales
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                        .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Toast.makeText(getApplicationContext(),
                                "Los ajustes de ubicación satisfacen la configuración.", Toast.LENGTH_SHORT).show();
                        processLastLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Toast.makeText(getApplicationContext(),
                                    "Los ajustes de ubicación no satisfacen la configuración. Se mostrará un diálogo de ayuda.",
                                    Toast.LENGTH_SHORT).show();
                            status.startResolutionForResult(LocationActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(getApplicationContext(), "El Intent del diálogo no funcionó.", Toast.LENGTH_SHORT).show();
                            //Sin operaciones
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(getApplicationContext(), "Los ajustes de ubicación no son apropiados.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if(ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // Aquí muestras confirmación explicativa al usuario
//                // por si rechazó los permisos anteriormente
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//            }
//        } else {
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            if (mLastLocation != null) {
//                mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
//                mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
//            } else {
//                Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
//            }
//        }

        // Obtenemos la última ubicación al ser la primera vez
        processLastLocation();
        // Iniciamos las actualizaciones de ubicación
//        startLocationUpdates();
    }

//    private void startLocationUpdates() {
//        if (isLocationPermissionGranted()) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
//                            mLocationRequest, this);
//        } else {
//            manageDeniedPermission();
//        }
//    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Código adicional para el manejo de la ubicación

    //Manejar el resultado de la asignación de permisos
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_LOCATION) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                if (mLastLocation != null) {
//                    mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
//                    mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
//                } else {
//                    Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(this, "Permisos no otorgados", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }

    private void updateLocationUI() {
        mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
    }

    private void getLastLocation() {
        if (isLocationPermissionGranted()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            manageDeniedPermission();
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Aquí muestras confirmación explicativa al usuario
            // por si rechazó los permisos anteriormente
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "El usuario permitió el cambio de ajustes de ubicación.",
                                Toast.LENGTH_SHORT).show();
                        processLastLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "El usuario no permitió el cambio de ajustes de ubicación",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(getApplicationContext(), String.format("Nueva ubicación: (%s, %s)",
                location.getLatitude(), location.getLongitude()), Toast.LENGTH_SHORT).show();
        mLastLocation = location;
        updateLocationUI();
    }
}