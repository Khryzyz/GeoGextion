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
        static final int DATABASE_VERSION = 2;

    }
    /**
     * #############################################################################################
     * Tabla Registro:
     * - Modelado de la tabla registro
     * - Scripts de la tabla registro
     * #############################################################################################
     */
    static class TableRegistro {

        /**
         * Modelado de la tabla Registro
         * Nombre de la tabla
         */
        static final String TABLE_NAME_REGISTRO = "registro";

        /**
         * Modelado de la tabla productos
         * Columnas de la tabla
         */
        static final String COLUMN_REGISTRO_ID = "id";
        static final String COLUMN_REGISTRO_IDENTIFICACION = "identificacion";
        static final String COLUMN_REGISTRO_REGISTRO = "registro";
        static final String COLUMN_REGISTRO_ESTADO = "estado";

        /**
         * Scripts de la tabla producti
         * Comando CREATE para la tabla Registro de la base de datos
         */
        static final String CREATE_TABLE_REGISTRO =
                "CREATE TABLE " + TABLE_NAME_REGISTRO + "(" +
                        COLUMN_REGISTRO_ID + " " + INT_TYPE + " " + PRIMARY_KEY + " " + AUTOINCREMENT + "," +
                        COLUMN_REGISTRO_IDENTIFICACION + " " + STRING_TYPE + " " + ATTR_NOT_NULL + "," +
                        COLUMN_REGISTRO_REGISTRO + " " + STRING_TYPE + " " + ATTR_NOT_NULL + "," +
                        COLUMN_REGISTRO_ESTADO + " " + INT_TYPE + " " + ATTR_NOT_NULL + ")";

        /**
         * Scripts de la tabla registro
         * Comando DROP para la tabla Registro de la base de datos
         */
        static final String DROP_TABLE_REGISTRO =
                "DROP TABLE IF EXISTS '" + TABLE_NAME_REGISTRO + "'";
    }
}
