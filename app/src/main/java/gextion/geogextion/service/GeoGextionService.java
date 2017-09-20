package gextion.geogextion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Un {@link Service} que notifica la cantidad de memoria disponible en el sistema
 */
public class GeoGextionService extends Service {

    private static final String TAG = GeoGextionService.class.getSimpleName();

    TimerTask timerTask;

    /**
     * Constructor de la clase
     */
    public GeoGextionService() {

    }

    /**
     * Sobrecarga del metodo IBinder
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }

    /**
     * Sobrecarga del metodo onCreate
     */
    @Override
    public void onCreate() {

        Log.d(TAG, "Servicio creado...");

    }

    /**
     * Sobrecarga dl metodo onStartCommand
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Servicio iniciado...");


        Timer timer = new Timer();

        timerTask = new TimerTask() {

            /**
             * Ejecucion de la tarea
             */
            @Override
            public void run() {

                Intent localIntent = new Intent(Constants.ACTION_RUN_SERVICE);

                // Emitir el intent a la actividad
                LocalBroadcastManager.getInstance(GeoGextionService.this).sendBroadcast(localIntent);

            }

        };

        timer.scheduleAtFixedRate(timerTask, 0, 60000);

        return START_NOT_STICKY;
    }

    /**
     * Sobrecarga del metodo onDestroy
     */
    @Override
    public void onDestroy() {

        timerTask.cancel();

        Intent localIntent = new Intent(Constants.ACTION_RUN_FINISH);

        // Emitir el intent a la actividad
        LocalBroadcastManager.getInstance(GeoGextionService.this).sendBroadcast(localIntent);
        Log.d(TAG, "Servicio destruido...");
    }


}
