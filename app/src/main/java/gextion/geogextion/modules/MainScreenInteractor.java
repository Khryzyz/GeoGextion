package gextion.geogextion.modules;

import android.content.Context;

public interface MainScreenInteractor {

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
}
