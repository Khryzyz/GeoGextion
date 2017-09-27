package gextion.geogextion.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Un {@link Service} que notifica la cantidad de memoria disponible en el sistema
 */
public class GeoGextionService extends Service implements LocationListener {

    private static final String TAG = GeoGextionService.class.getSimpleName();

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler handler = new Handler();
    private Timer timer = null;
    long intervaloNotificacion = 600000;

    public GeoGextionService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        timer.schedule(new TimerTaskToGetLocation(), 5, intervaloNotificacion);

    }

    /**
     * Sobrecarga del metodo onDestroy
     */
    @Override
    public void onDestroy() {

        timer.cancel();

        Intent localIntent = new Intent(Constants.ACTION_RUN_FINISH);

        // Emitir el intent a la actividad
        LocalBroadcastManager.getInstance(GeoGextionService.this).sendBroadcast(localIntent);
        Log.d(TAG, "Servicio destruido...");
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnable || isNetworkEnable) {

            boolean locationCaptured = false;

            if (isNetworkEnable && !locationCaptured) {
                location = null;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                        locationCaptured = true;
                    }
                }

            }
            if (isGPSEnable && !locationCaptured) {
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                        locationCaptured = true;
                    }
                }
            }
        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

    private void fn_update(Location location) {

        Intent localIntent = new Intent(Constants.ACTION_RUN_SERVICE);
        localIntent.putExtra(Constants.PARAM_LATITUD, String.valueOf(location.getLatitude()));
        localIntent.putExtra(Constants.PARAM_LONGITUD, String.valueOf(location.getLongitude()));
        // Emitir el intent a la actividad
        LocalBroadcastManager.getInstance(GeoGextionService.this).sendBroadcast(localIntent);

    }
}
