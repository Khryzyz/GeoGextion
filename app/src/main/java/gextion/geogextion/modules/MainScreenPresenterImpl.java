package gextion.geogextion.modules;

import android.content.Context;

import gextion.geogextion.lib.EventBus;
import gextion.geogextion.lib.GreenRobotEventBus;
import gextion.geogextion.modules.events.MainScreenEvent;
import gextion.geogextion.modules.ui.MainScreenView;

public class MainScreenPresenterImpl implements MainScreenPresenter {

    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */
    //Declaracion del bus de eventos
    EventBus eventBus;

    /**
     * #############################################################################################
     * Instanciamientos de las clases
     * #############################################################################################
     */
    //Instanciamiento de la interface MainScreenView
    private MainScreenView mainScreenView;

    //Instanciamiento de la interface MainScreenInteractor
    private MainScreenInteractor mainScreenInteractor;

    /**
     * #############################################################################################
     * Constructor de la clase
     * #############################################################################################
     *
     * @param mainScreenView
     */
    public MainScreenPresenterImpl(MainScreenView mainScreenView) {
        this.mainScreenView = mainScreenView;
        this.mainScreenInteractor = new MainScreenInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    /**
     * #############################################################################################
     * Metodos sobrecargados de la interface
     * #############################################################################################
     */

    /**
     * Sobrecarga del metodo onCreate de la interface MainScreenPresenter "crear" el registro al bus de eventos
     */
    @Override
    public void onCreate() {

        eventBus.register(this);

    }

    /**
     * Sobrecarga del metodo onDestroy de la interface MainScreenPresenter para "eliminar"  el registro al bus de eventos
     */
    @Override
    public void onDestroy() {
        mainScreenView = null;
        eventBus.unregister(this);
    }

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
        if (mainScreenView != null) {
            mainScreenInteractor.validateInitialConfig(context);
        }
    }

    @Override
    public void cerrarSesion(Context context) {
        if (mainScreenView != null) {
            mainScreenInteractor.cerrarSesion(context);
        }
    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void validarDocumento(Context context, String identificacion) {
        if (mainScreenView != null) {
            mainScreenInteractor.validarDocumento(context, identificacion);
        }
    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void registrarPosicion(Context context, String identificacion, String latitud, String longitud) {
        if (mainScreenView != null) {
            mainScreenInteractor.registrarPosicion(context, identificacion, latitud, longitud);
        }
    }

    /**
     * Sobrecarga del metodo onEventMainThread de la interface MainScreenPresenter para el manejo de eventos
     *
     * @param mainScreenEvent
     */
    @Override
    public void onEventMainThread(MainScreenEvent mainScreenEvent) {
        switch (mainScreenEvent.getEventType()) {

            case MainScreenEvent.onVerifySuccessConfig:
                onVerifySuccessConfig();
                break;
            case MainScreenEvent.onVerifySuccessLogin:
                onVerifySuccessLogin(mainScreenEvent.getIdentificacion());
                break;
            case MainScreenEvent.onVerifyError:
                onVerifyError();
                break;
            case MainScreenEvent.onVerifyInternetConnectionError:
                onVerifyInternetConnectionError();
                break;
            case MainScreenEvent.onVerifyGPSConnectionError:
                onVerifyGPSConnectionError();
                break;
            case MainScreenEvent.onIdentificacionValida:
                onIdentificacionValida();
                break;
            case MainScreenEvent.onIdentificacionNoValida:
                onIdentificacionNoValida();
                break;
            case MainScreenEvent.onIdentificacionNoRegistrada:
                onIdentificacionNoRegistrada();
                break;
            case MainScreenEvent.onIdentificacionError:
                onIdentificacionError();
                break;
            case MainScreenEvent.onPosicionRegistrada:
                onPosicionRegistrada();
                break;
            case MainScreenEvent.onPosicionNoRegistrada:
                onPosicionNoRegistrada();
                break;
            case MainScreenEvent.onPosicionError:
                onPosicionError(mainScreenEvent.getErrorMessage());
                break;
            case MainScreenEvent.onCierreSesionSuccess:
                onCierreSesionSuccess();
                break;
            case MainScreenEvent.onCierreSesionError:
                onCierreSesionError();
                break;
        }
    }

    /**
     * #############################################################################################
     * Metodo propios de la clase
     * #############################################################################################
     */

    /**
     * Metodo para manejar el acceso inicial
     */
    private void onVerifySuccessConfig() {
        if (mainScreenView != null) {
            mainScreenView.onVerifyConfigSuccess();
        }
    }

    /**
     * Metodo para manejar el acceso inicial
     */
    private void onVerifySuccessLogin(String identificacion) {
        if (mainScreenView != null) {
            mainScreenView.onVerifySuccessLogin(identificacion);
        }
    }

    /**
     * Metodo para manejar el erroe en acceso inicial
     */
    private void onVerifyError() {
        if (mainScreenView != null) {
            mainScreenView.onVerifyError();
        }
    }

    /**
     * Metodo para manejar el error en la conexion de internet
     */
    private void onVerifyInternetConnectionError() {
        if (mainScreenView != null) {
            mainScreenView.onVerifyInternetConnectionError();
        }
    }

    /**
     * Metodo para manejar el error en la conexion de GPS
     */
    private void onVerifyGPSConnectionError() {
        if (mainScreenView != null) {
            mainScreenView.onVerifyGPSConnectionError();
        }
    }

    /**
     * Metodo para manejar el documento valido al iniciar
     */
    private void onIdentificacionValida() {
        if (mainScreenView != null) {
            mainScreenView.onIdentificacionValida();
        }
    }

    /**
     * Metodo para manejar el documento no valido al iniciar
     */
    private void onIdentificacionNoValida() {
        if (mainScreenView != null) {
            mainScreenView.onIdentificacionNoValida();
        }
    }

    /**
     * Metodo para manejar el documento no valido al iniciar
     */
    private void onIdentificacionNoRegistrada() {
        if (mainScreenView != null) {
            mainScreenView.onIdentificacionNoRegistrada();
        }
    }

    /**
     * Metodo para manejar el error al verificar el documento
     */
    private void onIdentificacionError() {
        if (mainScreenView != null) {
            mainScreenView.onIdentificacionError();
        }
    }

    /**
     *
     */
    private void onPosicionRegistrada() {
        if (mainScreenView != null) {
            mainScreenView.onPosicionRegistrada();
        }
    }

    /**
     *
     */
    private void onPosicionNoRegistrada() {
        if (mainScreenView != null) {
            mainScreenView.onPosicionNoRegistrada();
        }
    }

    /**
     *
     */
    private void onPosicionError(String errorMessage) {
        if (mainScreenView != null) {
            mainScreenView.onPosicionError(errorMessage);
        }
    }

    /**
     *
     */
    private void onCierreSesionSuccess() {
        if (mainScreenView != null) {
            mainScreenView.onCierreSesionSuccess();
        }
    }

    /**
     *
     */
    private void onCierreSesionError() {
        if (mainScreenView != null) {
            mainScreenView.onCierreSesionError();
        }
    }

}
