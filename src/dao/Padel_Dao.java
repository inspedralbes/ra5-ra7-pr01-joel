package src.dao;

import src.padelmodel.*;
import java.util.List;

public interface Padel_Dao {

    /**
     * Guarda una reserva al repositori
     * @param padel
     */
    void guardarReserva(Reserva padel);

    /**
     * Retorna totes les reserves
     * @return totes les reserves
     */
    List<Reserva> llegirPadel();

    /**
     * retorna i elimina el wok més antic
     * @return el wok més antic
     */
     Reserva servirPadel();
}