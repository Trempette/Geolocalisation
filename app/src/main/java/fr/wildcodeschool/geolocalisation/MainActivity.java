package fr.wildcodeschool.geolocalisation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager mLocationManager = null;

    private void checkPermission(){
        // vérification de l'autorisation d'accéder à la position GPS

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // l'autorisation n'est pas acceptée

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // l'autorisation a été refusée précédemment, on peut prévenir l'utilisateur ici

            } else {

                // l'autorisation n'a jamais été réclamée, on la demande à l'utilisateur

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        100);
            }
        } else {

             initLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {

                // cas de notre demande d'autorisation

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initLocation();

                } else {

                    Toast toast = Toast.makeText(MainActivity.this, "Batard, accepte que je te suive !", Toast.LENGTH_LONG);
                    toast.show();

                }
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocation() {
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast toast = Toast.makeText(MainActivity.this, location.toString(), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast toast = Toast.makeText(MainActivity.this, "GPS activé", Toast.LENGTH_LONG);
                toast.show();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast toast = Toast.makeText(MainActivity.this, "GPS désactivé", Toast.LENGTH_LONG);
                toast.show();
            }
        };

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

    }
}
