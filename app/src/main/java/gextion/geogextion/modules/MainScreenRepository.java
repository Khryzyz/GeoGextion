package gextion.geogextion.modules;

import android.content.Context;

public interface MainScreenRepository {

    /**
     * @param context
     */
    void validateInitialConfig(Context context);

    /**
     * @param context
     */
    void cerrarSesion(Context context);

    /**
     * @param context
     * @param identificacion
     */
    void validarDocumento(Context context, String identificacion);

    /**
     * @param context
     * @param identificacion
     */
    void registrarPosicion(Context context, String identificacion, String latitud, String longitud);
}
