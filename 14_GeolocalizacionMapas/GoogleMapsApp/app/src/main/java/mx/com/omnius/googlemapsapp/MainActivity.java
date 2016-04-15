package mx.com.omnius.googlemapsapp;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int codigoGooglePlay = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (codigoGooglePlay != ConnectionResult.SUCCESS) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(codigoGooglePlay, this, 6);
            if (dialog != null) {
                dialog.show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Error al verificar Google Play Servivces",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }

        FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
        googleMap = mySupportMapFragment.getMap();

        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.setMyLocationEnabled(true);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc != null) {
            showLocation(loc.getLatitude(), loc.getLongitude());
        }

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                googleMap.moveCamera(center);
                showLocation(location.getLatitude(), location.getLongitude());
            }

            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(true);
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 1000, 70, locationListener);
        }
    }

    private void showLocation(double lat, double lng) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("Mi ubicación..."));
    }
}
