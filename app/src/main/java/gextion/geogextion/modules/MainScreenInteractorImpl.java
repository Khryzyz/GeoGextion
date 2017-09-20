package gextion.geogextion.modules;

import android.content.Context;

public class MainScreenInteractorImpl implements MainScreenInteractor {

    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */

    /**
     * #############################################################################################
     * Instanciamientos de las clases
     * #############################################################################################
     */
    private MainScreenRepository mainScreenRepository;

    /**
     * #############################################################################################
     * Constructor de la clase
     * #############################################################################################
     */
    public MainScreenInteractorImpl() {

        mainScreenRepository = new MainScreenRepositoryImpl();

    }

    /**
     * #############################################################################################
     * Metodos sobrecargados de la interface
     * #############################################################################################
     */

    /**
     * @param context
     */
    @Override
    public void validateInitialConfig(Context context) {
        mainScreenRepository.validateInitialConfig(context);
    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void validarDocumento(Context context, String identificacion) {
        mainScreenRepository.validarDocumento(context, identificacion);
    }

    /**
     * @param context
     * @param identificacion
     */
    @Override
    public void registrarPosicion(Context context, String identificacion) {
        mainScreenRepository.registrarPosicion(context, identificacion);
    }
}
