package gextion.geogextion.modules.events;

/**
 * Clase que maneja los eventos de la Pantalla de Inicio
 */
public class MainScreenEvent {

    /**
     * Eventos asociados a la Pantalla de Inicio
     */
    // Eventos de verificacion de disponibilidad del sistema
    public final static int onVerifySuccess = 0;
    public final static int onVerifyError = 1;
    public final static int onVerifyInternetConnectionError = 2;
    public final static int onVerifyGPSConnectionError = 3;
    public final static int onIdentificacionValida = 4;
    public final static int onIdentificacionNoValida = 5;
    public final static int onIdentificacionError = 6;
    public final static int onPosicionRegistrada = 7;
    public final static int onPosicionNoRegistrada = 8;
    public final static int onPosicionError = 9;


    // Variable que maneja los tipos de eventos
    private int eventType;

    // Variable que maneja los mensajes de error de los eventos
    private String errorMessage;

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
}
