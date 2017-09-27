package gextion.geogextion.modules;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.HashMap;

import gextion.geogextion.database.AppDatabase;
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
            if (AppDatabase.getInstance(context).conteoRegistroLogin() > 0) {

                postEvent(MainScreenEvent.onVerifySuccessLogin, null, AppDatabase.getInstance(context).getIdentificacionLogin());

            } else {

                postEvent(MainScreenEvent.onVerifySuccessConfig);

            }
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

    @Override
    public void cerrarSesion(Context context) {
        if(AppDatabase.getInstance(context).closeLogin()){
            postEvent(MainScreenEvent.onCierreSesionSuccess);
        }else{
            postEvent(MainScreenEvent.onCierreSesionError);
        }
    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void validarDocumento(final Context context, final String identificacion) {

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

                            if (AppDatabase.getInstance(context).insertRegistroLogin(identificacion)) {

                                postEvent(MainScreenEvent.onIdentificacionValida);

                            } else {

                                postEvent(MainScreenEvent.onIdentificacionNoRegistrada);

                            }
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
    public void registrarPosicion(Context context, String identificacion, String latitud, String longitud) {


        final HashMap<String, String> parameters = new HashMap<>();

        parameters.put(InfoGlobalTransaccionREST.GEOLOCALIZACION_PARAM_IDENTIFICACION, identificacion);
        parameters.put(InfoGlobalTransaccionREST.GEOLOCALIZACION_PARAM_LONGITUD, longitud);
        parameters.put(InfoGlobalTransaccionREST.GEOLOCALIZACION_PARAM_LATITUD, latitud);


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
                            Log.i("Evento", "Posicion no registrada");
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        postEvent(MainScreenEvent.onPosicionError, MainScreenEvent.errorMessagePositionNotRegistered);
                    }
                });
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

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
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
    private void postEvent(int type, String errorMessage, String identificacion) {

        MainScreenEvent mainScreenEvent = new MainScreenEvent();

        mainScreenEvent.setEventType(type);

        if (errorMessage != null) {
            mainScreenEvent.setErrorMessage(errorMessage);
        }

        if (identificacion != null) {
            mainScreenEvent.setIdentificacion(identificacion);
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

        postEvent(type, null, null);

    }

    /**
     * Sobrecarga del metodo postevent
     *
     * @param type
     */
    private void postEvent(int type, String errorMessage) {

        postEvent(type, errorMessage, null);

    }


}
