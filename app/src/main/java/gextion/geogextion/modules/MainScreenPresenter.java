package gextion.geogextion.modules;

import android.content.Context;

import gextion.geogextion.modules.events.MainScreenEvent;

public interface MainScreenPresenter {

    /**
     * Metodo para la creacion del presentador
     */
    void onCreate();

    /**
     * Metodo para la destruccion del presentador
     */
    void onDestroy();

    /**
     * @param context
     */
    void validateInitialConfig(Context context);

    /**
     * @param context
     * @param identificacion
     */
    void validarDocumento(Context context, String identificacion);

    /**
     * @param context
     * @param identificacion
     */
    void registrarPosicion(Context context, String identificacion);

    /**
     * Metodo para recibir los eventos generados
     *
     * @param mainScreenEvent
     */
    void onEventMainThread(MainScreenEvent mainScreenEvent);

}
