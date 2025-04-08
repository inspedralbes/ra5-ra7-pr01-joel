package src.padelmodel;

import java.io.Serializable;
import java.util.ArrayList;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Horario: ").append(horario).append("\n");
        sb.append("Pista: ").append(pista).append("\n");
        sb.append("Ubicación: ").append(ubicacion).append("\n");
        sb.append("Participantes:\n");
        for (Participante p : participantes) {
            sb.append("  - ").append(p).append("\n");
        }
        return sb.toString();
    }
}
