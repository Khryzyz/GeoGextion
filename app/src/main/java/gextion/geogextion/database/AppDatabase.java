package gextion.geogextion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase que maneja:
 * - DML de la base de datos
 * - instancia general de acceso a datos
 */
public final class AppDatabase extends SQLiteOpenHelper {

    //Instancia singleton
    private static AppDatabase singleton;

    /**
     * Constructor de la clase
     * Crea la base de datos si no existe
     *
     * @param context instancia desde la que se llaman los metodos
     */
    private AppDatabase(Context context) {
        super(context,
                DatabaseManager.DatabaseApp.DATABASE_NAME,
                null,
                DatabaseManager.DatabaseApp.DATABASE_VERSION);
    }

    /**
     * Retorna la instancia unica del singleton
     *
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized AppDatabase getInstance(Context context) {

        if (singleton == null) {
            singleton = new AppDatabase(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Metodo ejecutado en el evento de la instalacion de la aplicacion
     * Crea las tablas necesarias para el funcionamiento de la aplicacion
     *
     * @param db Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseManager.TableRegistro.CREATE_TABLE_REGISTRO);
    }

    /**
     * Metodo ejecutado en la actualizacion de la aplicacion
     *
     * @param db         Database
     * @param oldVersion Version Anterior
     * @param newVersion Version Actual
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseManager.TableRegistro.DROP_TABLE_REGISTRO);
        onCreate(db);
    }

    /**
     * Metodo para insertar registro de login
     *
     * @param producto_identificacion String Nombre del registro
     * @return Boolean estado de la transaccion del registro inicial de los productos
     */
    public boolean insertRegistroLogin(String producto_identificacion) {

        boolean transaction = false;

        long conteo = 0;

        // Inicializacion de la variable de contenidos del registro
        ContentValues contentValues = new ContentValues();

        // Almacena los valores a insertar
        contentValues.put(DatabaseManager.TableRegistro.COLUMN_REGISTRO_IDENTIFICACION, producto_identificacion);
        contentValues.put(DatabaseManager.TableRegistro.COLUMN_REGISTRO_REGISTRO, getDateTime());
        contentValues.put(DatabaseManager.TableRegistro.COLUMN_REGISTRO_ESTADO, 1);

        try {

            getWritableDatabase().insert(
                    DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO,
                    null,
                    contentValues
            );

            conteo = obtenerConteoCambios();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        if (conteo == 1) {
            transaction = true;
        }

        return transaction;
    }

    /**
     * Metodo para cerrar todos los registros de login
     *
     * @return
     */
    public boolean closeLogin() {
        boolean transaction = false;

        long conteo = 0;

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseManager.TableRegistro.COLUMN_REGISTRO_ESTADO, 2);
        try {

            getWritableDatabase().update(
                    DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO,
                    contentValues,
                    null, null
            );

            conteo = obtenerConteoCambios();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        if (conteo > 0) {
            transaction = true;
        }

        return transaction;
    }

    public int conteoRegistroLogin() {

        int count;

        Cursor cursorQuery;

        cursorQuery = getWritableDatabase().rawQuery(
                "SELECT COUNT(1) FROM " +
                        DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO +
                        " WHERE " + DatabaseManager.TableRegistro.COLUMN_REGISTRO_ESTADO + " = '1'",
                null
        );

        cursorQuery.moveToFirst();

        count = cursorQuery.getInt(0);

        cursorQuery.close();

        return count;

    }

    public String getIdentificacionLogin() {

        String identificacion;

        Cursor cursorQuery;

        cursorQuery = getWritableDatabase().rawQuery(
                "SELECT " + DatabaseManager.TableRegistro.COLUMN_REGISTRO_IDENTIFICACION +
                        " FROM " + DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO +
                        " WHERE " + DatabaseManager.TableRegistro.COLUMN_REGISTRO_ESTADO + " = '1' " +
                        " LIMIT 1",
                null
        );

        cursorQuery.moveToFirst();

        identificacion = cursorQuery.getString(0);

        cursorQuery.close();

        return identificacion;

    }

    /*
      #############################################################################################
      Metodos privados auxiliares
      #############################################################################################
     */

    /**
     * Metodo para Obtener el String de fecha y hora
     *
     * @return String fecha
     */
    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());

        Date date = new Date();

        return dateFormat.format(date);
    }

    /**
     * Metodo que obtiene los cambios registrados en la ultima sesion de lla base de datos
     *
     * @return long conteo de filas afectadas en la ultima transaccion
     */
    private long obtenerConteoCambios() {
        SQLiteStatement statement = getWritableDatabase().compileStatement("SELECT changes()");
        return statement.simpleQueryForLong();
    }

}
