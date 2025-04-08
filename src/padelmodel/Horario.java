package src.padelmodel;

import java.io.Serializable;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String inicio;
    private String fin;

    public Horario(String inicio, String fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return inicio + " - " + fin;
    }

    public boolean esFechaValida(String fecha) {
        try {
            String[] dt = fecha.split(" ");
            String[] d = dt[0].split("[/-]");
            String[] t = dt[1].split(":");


            int day = Integer.parseInt(d[0]);
            int month = Integer.parseInt(d[1]);
            int year= Integer.parseInt(d[2]);
            int hour = Integer.parseInt(t[0]);
            int min = Integer.parseInt(t[1]);

            return day <= 31 && month <= 12 && year>2024 && hour <= 23 && min <= 59;
        } catch (Exception e) {
            return false;
        }
    }


}
