package gextion.geogextion.modules.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import gextion.geogextion.R;
import gextion.geogextion.modules.MainScreenPresenter;
import gextion.geogextion.modules.MainScreenPresenterImpl;
import gextion.geogextion.service.Constants;
import gextion.geogextion.service.GeoGextionService;

@EActivity(R.layout.activity_main)
public class MainScreenActivity extends AppCompatActivity implements MainScreenView {
    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */

    String identificacion = "";

    //Controles Vista principal
    @ViewById
    RelativeLayout main_container;
    @ViewById
    RelativeLayout mainContent;
    @ViewById
    RelativeLayout logContent;
    @ViewById
    FrameLayout frlPgbGeoGextion;

    //Controles content_main
    @ViewById
    TextView txvGeoGextionLogInicio;
    @ViewById
    EditText edtGeoGextionCodigo;
    @ViewById
    Button btnGextionValidar;

    //Controles content_log
    @ViewById
    TextView txvGeoGextionLog;

    /**
     * #############################################################################################
     * Instanciamientos de las clases
     * #############################################################################################
     */
    //Instanciamiento de la interface MainScreenPresenter
    private MainScreenPresenter mainScreenPresenter;

    /**
     * #############################################################################################
     * Constructor  de  la clase
     * #############################################################################################
     */
    @AfterViews
    void SplashInit() {

        //Instanciamiento e inicializacion del presentador
        mainScreenPresenter = new MainScreenPresenterImpl(this);

        //Llamada al metodo onCreate del presentador para el registro del bus de datos
        mainScreenPresenter.onCreate();

        //Metodo para colocar la orientacion de la app
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Inicializa las vistas
        inicializarVistas();

        //Mostrar la barra de progreso
        showProgress();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Metodo para validar la configuracion inicial
                mainScreenPresenter.validateInitialConfig(MainScreenActivity.this);
            }
        }, 100);

    }

    /*
    #############################################################################################
    Metodo propios de la clase
    #############################################################################################
   */
    private void inicializarVistas() {

        mainContent.setVisibility(View.VISIBLE);
        logContent.setVisibility(View.GONE);
        txvGeoGextionLogInicio.setVisibility(View.GONE);

        edtGeoGextionCodigo.setEnabled(false);
        btnGextionValidar.setEnabled(false);
        txvGeoGextionLog.setEnabled(false);

    }


    /**
     * Metodo para validar el documento
     */
    @Click(R.id.btnGextionValidar)
    public void validarDocumento() {
        identificacion = edtGeoGextionCodigo.getText().toString();
        if (identificacion.length() > 0) {

            //Mostrar la barra de progreso
            showProgress();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainScreenPresenter.validarDocumento(MainScreenActivity.this, identificacion);
                }
            }, 100);


        } else {
            Snackbar.make(main_container, getString(R.string.error_numero_identificacion), Snackbar.LENGTH_SHORT).show();
            edtGeoGextionCodigo.setText("");
        }

    }

    /**
     * Metodo para mostrar la barra de progreso
     */
    private void showProgress() {
        //Muestra la barra  de progreso
        frlPgbGeoGextion.setVisibility(View.VISIBLE);
        frlPgbGeoGextion.bringToFront();
        frlPgbGeoGextion.invalidate();
    }

    /**
     * Metodo para ocultar la barra de progreso
     */
    private void hideProgress() {
        //Oculta la barra de progreso
        frlPgbGeoGextion.setVisibility(View.GONE);
    }

    /**
     *
     */
    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constants.ACTION_RUN_SERVICE)) {

                mainScreenPresenter.registrarPosicion(MainScreenActivity.this, identificacion);

            } else if (intent.getAction().equals(Constants.ACTION_RUN_FINISH)) {

                Toast.makeText(MainScreenActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /*
      #############################################################################################
      Sobrecarga metodos de la interface
      #############################################################################################
     */

    /**
     * Metodo para manejar el acceso inicial
     */
    @Override
    public void onVerifySuccess() {
        edtGeoGextionCodigo.setEnabled(true);
        btnGextionValidar.setEnabled(true);
        txvGeoGextionLog.setEnabled(true);
        hideProgress();
    }

    /**
     * Metodo para manejar el error en acceso inicial
     */
    @Override
    public void onVerifyError() {

        txvGeoGextionLogInicio.setVisibility(View.VISIBLE);

        txvGeoGextionLogInicio.setText(txvGeoGextionLogInicio.getText() + "\n\n" + getString(R.string.error_text));

        hideProgress();
    }

    /**
     * Metodo para manejar el error en la conexion de internet
     */
    @Override
    public void onVerifyInternetConnectionError() {

        txvGeoGextionLogInicio.setVisibility(View.VISIBLE);

        txvGeoGextionLogInicio.setText(txvGeoGextionLogInicio.getText() + "\n\n" + getString(R.string.error_connection_internet));

    }

    /**
     * Metodo para manejar el error en la conexion de GPS
     */
    @Override
    public void onVerifyGPSConnectionError() {

        txvGeoGextionLogInicio.setVisibility(View.VISIBLE);

        txvGeoGextionLogInicio.setText(txvGeoGextionLogInicio.getText() + "\n\n" + getString(R.string.error_connection_gps));

    }

    /**
     * Metodo para manejar el documento valido al iniciar
     */
    @Override
    public void onIdentificacionValida() {
        mainContent.setVisibility(View.GONE);
        logContent.setVisibility(View.VISIBLE);
        hideProgress();

        Intent intentGeoGextionService = new Intent(getApplicationContext(), GeoGextionService.class);
        startService(intentGeoGextionService);

        // Filtro de acciones que serán alertadas
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_RUN_SERVICE);
        intentFilter.addAction(Constants.ACTION_RUN_FINISH);

        // Crear un nuevo ResponseReceiver
        ResponseReceiver responseReceiver = new ResponseReceiver();

        // Registrar el responseReceiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver, intentFilter);
    }

    /**
     * Metodo para manejar el documento no valido al iniciar
     */
    @Override
    public void onIdentificacionNoValida() {

        txvGeoGextionLogInicio.setVisibility(View.VISIBLE);

        String errorValidacion = String.format(getString(R.string.error_identificacion_no_valida), identificacion);

        txvGeoGextionLogInicio.setText(errorValidacion);

        identificacion = "";

        hideProgress();

    }

    /**
     * Metodo para manejar el error al verificar el documento
     */
    @Override
    public void onIdentificacionError() {

        txvGeoGextionLogInicio.setVisibility(View.VISIBLE);

        txvGeoGextionLogInicio.setText(txvGeoGextionLogInicio.getText() + "\n\n" + getString(R.string.error_validar_identificacion_error));

        hideProgress();

    }

    @Override
    public void onPosicionRegistrada() {
        txvGeoGextionLog.setText("Posicion registrada" + "\n" + txvGeoGextionLog.getText());
    }

    @Override
    public void onPosicionNoRegistrada() {
        txvGeoGextionLog.setText("Posicion NO registrada" + "\n" + txvGeoGextionLog.getText());
    }

    @Override
    public void onPosicionError() {
        txvGeoGextionLog.setText("Error al obtener posicion" + "\n" + txvGeoGextionLog.getText());
    }
}
