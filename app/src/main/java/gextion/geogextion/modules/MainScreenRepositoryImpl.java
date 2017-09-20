package gextion.geogextion.modules;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.HashMap;

import gextion.geogextion.global.InfoGlobalTransaccionREST;
import gextion.geogextion.lib.EventBus;
import gextion.geogextion.lib.GreenRobotEventBus;
import gextion.geogextion.lib.VolleyTransaction;
import gextion.geogextion.modules.events.MainScreenEvent;

public class MainScreenRepositoryImpl implements MainScreenRepository {

    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */


    /**
     * #############################################################################################
     * Constructor de la clase
     * #############################################################################################
     */
    public MainScreenRepositoryImpl() {
    }

    /**
     * #############################################################################################
     * Metodos sobrecargados de la interface
     * #############################################################################################
     */

    /**
     * Metodo que verifica:
     * - La existencia de la configuración inicial
     * - En caso de no existir mostrará la vista de configuración
     * - En caso de existir validara el acceso
     *
     * @param context
     */
    @Override
    public void validateInitialConfig(Context context) {

        boolean verifyInternetConnection = verifyInternetConnection(context);
        boolean verifyGPSConnection = verifyGPSConnection(context);


        if (verifyInternetConnection && verifyGPSConnection) {
            postEvent(MainScreenEvent.onVerifySuccess);
        } else {
            if (!verifyInternetConnection) {
                postEvent(MainScreenEvent.onVerifyInternetConnectionError);
            }
            if (!verifyGPSConnection) {
                postEvent(MainScreenEvent.onVerifyGPSConnectionError);
            }

            postEvent(MainScreenEvent.onVerifyError);
        }

    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void validarDocumento(Context context, String identificacion) {

        final HashMap<String, String> parameters = new HashMap<>();

        parameters.put(InfoGlobalTransaccionREST.VALIDAR_IDENTIFICACION_PARAM_IDENTIFICACION, identificacion);

        VolleyTransaction volleyTransaction = new VolleyTransaction();

        volleyTransaction.getData(context,
                parameters,
                InfoGlobalTransaccionREST.METHOD_VALIDAR_IDENTIFICACION,
                new VolleyTransaction.VolleyCallback() {
                    @Override
                    public void onSuccess(JsonObject data) {

                        if (data.get(InfoGlobalTransaccionREST.STATUS_KEY).getAsString().equals(InfoGlobalTransaccionREST.STATUS_KEY_TRUE)) {
                            postEvent(MainScreenEvent.onIdentificacionValida);
                        } else {
                            postEvent(MainScreenEvent.onIdentificacionNoValida);
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        postEvent(MainScreenEvent.onIdentificacionError);
                    }
                });
    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void registrarPosicion(Context context, String identificacion) {

        String latitud;

        String longitud;

        if (verifyGPSConnection(context)) {

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                latitud = String.valueOf(location.getLatitude());
                longitud = String.valueOf(location.getLongitude());

                final HashMap<String, String> parameters = new HashMap<>();

                parameters.put(InfoGlobalTransaccionREST.GEOLOCALIZACION_PARAM_IDENTIFICACION, identificacion);
                parameters.put(InfoGlobalTransaccionREST.GEOLOCALIZACION_PARAM_LONGITUD, longitud);
                parameters.put(InfoGlobalTransaccionREST.GEOLOCALIZACION_PARAM_LATITUD, latitud);

                Log.i("identificacion", identificacion);
                Log.i("latitud", latitud);
                Log.i("longitud", longitud);

                VolleyTransaction volleyTransaction = new VolleyTransaction();

                volleyTransaction.getData(context,
                        parameters,
                        InfoGlobalTransaccionREST.METHOD_REGISTRAR_UBICACION,
                        new VolleyTransaction.VolleyCallback() {
                            @Override
                            public void onSuccess(JsonObject data) {

                                if (data.get(InfoGlobalTransaccionREST.STATUS_KEY).getAsString().equals(InfoGlobalTransaccionREST.STATUS_KEY_TRUE)) {
                                    postEvent(MainScreenEvent.onPosicionRegistrada);
                                    Log.i("Evento", "Posicion registrada");
                                } else {
                                    postEvent(MainScreenEvent.onPosicionNoRegistrada);
                                    Log.i("Evento", "Posicion registrada");
                                }
                            }

                            @Override
                            public void onError(String errorMessage) {
                                postEvent(MainScreenEvent.onPosicionError);
                            }
                        });
            } else {
                postEvent(MainScreenEvent.onPosicionError);
            }

        }


    }

    /**
     * #############################################################################################
     * Metodo propios de la clase
     * #############################################################################################
     */

    /**
     * Metodo que verifica la existencia de conexion a internet en el dispositivo
     *
     * @param context
     * @return
     */
    private boolean verifyInternetConnection(Context context) {

        boolean resultVerifyInternetConnection = false;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            resultVerifyInternetConnection = true;
        }

        return resultVerifyInternetConnection;

    }

    /**
     * @param context
     * @return
     */
    private boolean verifyGPSConnection(Context context) {

        boolean resultVerifyGPSConnection = false;


        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            resultVerifyGPSConnection = true;

        }

        return resultVerifyGPSConnection;
    }

    /**
     * Metodo que registra los eventos
     *
     * @param type
     * @param errorMessage
     */
    private void postEvent(int type, String errorMessage) {

        MainScreenEvent mainScreenEvent = new MainScreenEvent();

        mainScreenEvent.setEventType(type);

        if (errorMessage != null) {
            mainScreenEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();

        eventBus.post(mainScreenEvent);
    }

    /**
     * Sobrecarga del metodo postevent
     *
     * @param type
     */
    private void postEvent(int type) {

        postEvent(type, null);

    }

}
