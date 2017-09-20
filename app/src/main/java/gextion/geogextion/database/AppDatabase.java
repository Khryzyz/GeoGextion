package gextion.geogextion.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

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

        onCreate(db);
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
