package gextion.geogextion.modules.events;

/**
 * Clase que maneja los eventos de la Pantalla de Inicio
 */
public class MainScreenEvent {

    /**
     * Eventos asociados a la Pantalla de Inicio
     */
    // Eventos de verificacion de disponibilidad del sistema
    public final static int onVerifySuccessConfig = 0;
    public final static int onVerifySuccessLogin = 1;
    public final static int onVerifyError = 2;
    public final static int onVerifyInternetConnectionError = 3;
    public final static int onVerifyGPSConnectionError = 4;
    public final static int onIdentificacionValida = 5;
    public final static int onIdentificacionNoValida = 6;
    public final static int onIdentificacionNoRegistrada = 7;
    public final static int onIdentificacionError = 8;
    public final static int onPosicionRegistrada = 9;
    public final static int onPosicionNoRegistrada = 10;
    public final static int onPosicionError = 11;
    public final static int onCierreSesionSuccess = 12;
    public final static int onCierreSesionError = 13;

    public final static String errorMessageConnectionGPSDevice = "Conexion con GPS no disponible";
    public final static String errorMessageLocationNotFound = "Error al obtener Localización";
    public final static String errorMessageProviderNotFound = "Error al obtener Proveedor";
    public final static String errorMessagePositionNotRegistered = "Error al registrar la posicion";
    public final static String errorMessagePermission = "Permisos no otorgados";


    // Variable que maneja los tipos de eventos
    private int eventType;

    // Variable que maneja los mensajes de error de los eventos
    private String errorMessage;

    private String identificacion;

    //Getters y Setters de la clase

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
