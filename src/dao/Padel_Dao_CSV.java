package src.dao;

import src.padelmodel.Reserva;

import java.util.List;

public class Padel_Dao_CSV implements Padel_Dao {
    @Override
    public void guardarReserva(Reserva padel) {

    }

    @Override
    public List<Reserva> llegirPadel() {
        return List.of();
    }

    @Override
    public Reserva servirPadel() {
        return null;
    }
}
