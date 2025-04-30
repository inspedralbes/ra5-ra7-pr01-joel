package src.dao;

/**
 * Factory para crear instancias de Padel_Dao según el tipo de almacenamiento deseado
 */
public class Padel_Dao_Factory {

    public enum TipoAlmacenamiento {
        CSV,
        MYSQL
    }

    /**
     * Crea una instancia del DAO según el tipo de almacenamiento especificado
     * @param tipo El tipo de almacenamiento (CSV o MYSQL)
     * @return Una instancia de Padel_Dao
     */
    public static Padel_Dao crearPadelDao(TipoAlmacenamiento tipo) {
        switch (tipo) {
            case CSV:
                return new Padel_Dao_CSV();
            case MYSQL:
                return new Padel_Dao_MySQL();
            default:
                throw new IllegalArgumentException("Tipo de almacenamiento no soportado: " + tipo);
        }
    }
}