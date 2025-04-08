package src.padelmodel;

import java.io.Serializable;

public class Ubicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String direccion;

    public Ubicacion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return direccion;
    }
}

