package gextion.geogextion.database;

/**
 * Clase que maneja:
 * - Scripts de la base de datos
 * - Atributos de la base de datos
 * - DDL de la base de datos
 */

class DatabaseManager {

    // Informacion de los tipos de los campos
    private static final String STRING_TYPE = "TEXT";
    private static final String INT_TYPE = "INTEGER";
    private static final String TIMESTAMP_TYPE = "TIMESTAMP";

    //Informacion de las  caracteristicas de los campos
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String AUTOINCREMENT = "AUTOINCREMENT";
    private static final String ATTR_NULL = "NULL";
    private static final String ATTR_NOT_NULL = "NOT NULL";

    /**
     * Informacion de la base de datos
     */
    static class DatabaseApp {

        static final String DATABASE_NAME = "geogextion.db";
        static final int DATABASE_VERSION = 1;

    }

}
