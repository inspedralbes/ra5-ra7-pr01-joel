package src.padelmodel;

import java.io.Serializable;

public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;

    public Participante(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
