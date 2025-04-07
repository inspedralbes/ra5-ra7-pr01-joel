package src.padelmodel;


import java.util.ArrayList;


public class Reserva {
    private Horario horario;
    private Tipo_pista_padel pista;
    private ArrayList<Participante> participantes;
    private Ubicacion ubicacion;


    public Reserva(Horario horario, Tipo_pista_padel pista, ArrayList<Participante> participantes, Ubicacion ubicacion) {
        this.horario = horario;
        this.pista = pista;
        this.participantes = participantes;
        this.ubicacion = ubicacion;
    }


    public Horario getHorario() {
        return horario;
    }


    public Tipo_pista_padel getPista() {
        return pista;
    }


    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }


    public Ubicacion getUbicacion() {
        return ubicacion;
    }
}
