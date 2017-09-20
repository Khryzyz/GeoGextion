package gextion.geogextion.modules.ui;

/**
 * Interface usada en la actividad de la Pantalla principal
 */
public interface MainScreenView {

    /**
     * Metodo para manejar el acceso inicial
     */
    void onVerifySuccess();

    /**
     * Metodo para manejar el erroe en acceso inicial
     */
    void onVerifyError();

    /**
     * Metodo para manejar el error en la conexion de internet
     */
    void onVerifyInternetConnectionError();

    /**
     * Metodo para manejar el error en la conexion de GPS
     */
    void onVerifyGPSConnectionError();

    /**
     * Metodo para manejar el documento valido al iniciar
     */
    void onIdentificacionValida();

    /**
     * Metodo para manejar el documento no valido al iniciar
     */
    void onIdentificacionNoValida();

    /**
     * Metodo para manejar el error al verificar el documento
     */
    void onIdentificacionError();

    /**
     *
     */
    void onPosicionRegistrada();

    /**
     *
     */
    void onPosicionNoRegistrada();

    /**
     *
     */
    void onPosicionError();

}
